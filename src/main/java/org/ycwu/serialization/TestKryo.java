package org.ycwu.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;

public class TestKryo {

	@Test
	public void test1() throws FileNotFoundException {
		Item item = new Item();
		item.setId(100);
		item.setName("abc");
		Kryo kryo = new Kryo();

		File f = new File("item.ser");
		if (f.exists()) {
			f.delete();
		}

		Output output = new Output(new FileOutputStream(f));
		// only write data
		kryo.writeObject(output, item);
		output.close();

		Input input = new Input(new FileInputStream(f));
		Item item2 = kryo.readObject(input, Item.class);
		assertEquals(item.getId(), item2.getId());

	}

	@Test
	public void test2() throws FileNotFoundException {
		Item item = new Item();
		item.setId(200);
		item.setName("abc");
		Kryo kryo = new Kryo();

		File f = new File("item2.ser");
		if (f.exists()) {
			f.delete();
		}

		Output output = new Output(new FileOutputStream(f));
		// write data and class info
		kryo.writeClassAndObject(output, item);
		output.close();

		Input input = new Input(new FileInputStream(f));
		// notice the cast
		Item item2 = (Item) kryo.readClassAndObject(input);
		assertEquals(item.getId(), item2.getId());

	}

	/*
	 * null object
	 */
	@Test
	public void test3() throws FileNotFoundException {
		Item item = null;
		Kryo kryo = new Kryo();

		File f = new File("item3.ser");
		if (f.exists()) {
			f.delete();
		}

		Output output = new Output(new FileOutputStream(f));
		kryo.writeObjectOrNull(output, item, Item.class);
		output.close();

		Input input = new Input(new FileInputStream(f));
		Item item2 = kryo.readObjectOrNull(input, Item.class);
		assertEquals(null, item2);

	}

	/**
	 * object circular reference
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void test4() throws FileNotFoundException {
		A a = new A();
		B b = new B();

		a.b = b;
		b.a = a;

		File f = new File("a.ser");
		if (f.exists()) {
			f.delete();
		}

		Output output = new Output(new FileOutputStream(f));
		Kryo kryo = new Kryo();
		kryo.writeObject(output, a);
		output.close();

		Input input = new Input(new FileInputStream(f));
		A a2 = kryo.readObject(input, A.class);
		assertEquals(a2, a2.b.a);

	}

	/**
	 * register the class to reduce file size. check the file size vs test2()
	 * 
	 * @throws FileNotFoundException
	 */
	@Test
	public void test5() throws FileNotFoundException {
		Item item = new Item();
		item.setId(500);

		File f = new File("item5.ser");
		if (f.exists()) {
			f.delete();
		}

		Output output = new Output(new FileOutputStream(f));
		Kryo kryo = new Kryo();
		kryo.register(Item.class, 1);
		kryo.writeClassAndObject(output, item);
		output.close();

		Input input = new Input(new FileInputStream(f));
		Item item2 = (Item) kryo.readClassAndObject(input);
		Assert.assertEquals(item.getId(), item2.getId());

	}

	/**
	 * customize serializer to reduce output size
	 */
	@Test
	public void test6() {
		List<String> list = new LinkedList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");

		Kryo kryo = new Kryo();

		CollectionSerializer collectionSerializer = new CollectionSerializer();
		// save 1-2 bytes per element
		collectionSerializer.setElementClass(String.class, kryo.getSerializer(String.class));
		// save 1 byte per element
		collectionSerializer.setElementsCanBeNull(false);

		FieldSerializer<SomeClass> someClassSerializer = new FieldSerializer<>(kryo, SomeClass.class);
		// save 1-2 bytes
		someClassSerializer.getField("list").setClass(LinkedList.class, collectionSerializer);

		// register class serializer
		kryo.register(SomeClass.class, someClassSerializer);

		SomeClass a = new SomeClass();
		a.list = list;

		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		Output output = new Output(buff);
		kryo.writeObject(output, a);
		output.flush();
		int size1 = buff.size();
		System.out.println("optimized output size:" + size1);

		buff.reset();

		Kryo kryo2 = new Kryo();
		output.clear();
		kryo2.writeObject(output, a);
		output.flush();
		int size2 = buff.size();
		System.out.println("default output size:" + size2);
		assertTrue(size1 - size2 < 0);
	}

	/**
	 * serialization and deserialization must use the same class & field serializer 
	 */
	@Test
	public void test7() {
		List<String> list = new LinkedList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");

		Kryo kryo = new Kryo();

		CollectionSerializer collectionSerializer = new CollectionSerializer();
		// save 1-2 bytes per element
		collectionSerializer.setElementClass(String.class, kryo.getSerializer(String.class));
		// save 1 byte per element
		collectionSerializer.setElementsCanBeNull(false);

		FieldSerializer<SomeClass> someClassSerializer = new FieldSerializer<>(kryo, SomeClass.class);
		// save 1-2 bytes
		someClassSerializer.getField("list").setClass(LinkedList.class, collectionSerializer);

		// register class serializer
		kryo.register(SomeClass.class, someClassSerializer);

		SomeClass a = new SomeClass();
		a.list = list;

		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		Output output = new Output(buff);
		kryo.writeObject(output, a);
		output.flush();

		Kryo kryo2 = new Kryo();
		Input input = new Input(new ByteArrayInputStream(buff.toByteArray()));
		// must register customized class serializer
		kryo2.register(SomeClass.class, someClassSerializer);
		SomeClass a2 = kryo2.readObject(input, SomeClass.class);
		assertEquals(a.list.size(), a2.list.size());
	}

	@Test
	public void testCompression() {
		Kryo kryo = new Kryo();

		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		Output output = new Output(new DeflaterOutputStream(buff));
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			list.add(i);
		}
		kryo.writeObject(output, list);
		output.flush();
		output.close();

		Input input = new Input(new InflaterInputStream(new ByteArrayInputStream(buff.toByteArray())));
		List<Integer> list2 = kryo.readObject(input, ArrayList.class);
		assertEquals(list.get(0), list2.get(0));
	}

	/**
	 * output : DeflaterOutputStream <br>
	 * input : InflaterInputStream (NOT DeflaterInputStream !!!)
	 */
	@Test
	public void testCompressionUsingDeflater() {
		Kryo kryo = new Kryo();

		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		Output output = new Output(new DeflaterOutputStream(buff));
		Item item = new Item();
		item.setId(100);
		item.setName("abczzz1112233");
		kryo.writeObject(output, item);
		output.flush();
		output.close();

		Input input = new Input(new InflaterInputStream(new ByteArrayInputStream(buff.toByteArray())));
		Item item2 = kryo.readObject(input, Item.class);
		assertEquals(item.getId(), item2.getId());
	}

	@Test
	public void testCompressionUsingGZIP() throws IOException {
		Kryo kryo = new Kryo();

		ByteArrayOutputStream buff = new ByteArrayOutputStream();
		Output output = new Output(new GZIPOutputStream(buff));
		Item item = new Item();
		item.setId(100);
		item.setName("abczzz1112233");
		kryo.writeObject(output, item);
		output.flush();
		output.close();

		Input input = new Input(new GZIPInputStream(new ByteArrayInputStream(buff.toByteArray())));
		Item item2 = kryo.readObject(input, Item.class);
		assertEquals(item.getId(), item2.getId());
	}


}

class SomeClass {
	public List list;
}

class A {
	public B b;
}

class B {
	public A a;
}

class Item {

	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

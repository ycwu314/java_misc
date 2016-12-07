package org.ycwu.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestGeneric {

	private Map<String, Integer> map;

	@Test
	public void testParameterizedType() throws NoSuchFieldException, SecurityException {
		Field f = TestGeneric.class.getDeclaredField("map");
		Type type = f.getGenericType();

		System.out.println("genericType: " + type.getTypeName() + "\n");
		System.out.println("is ParameterizedType: " + (type instanceof ParameterizedType) + "\n");
		ParameterizedType pType = (ParameterizedType) type;
		System.out.println("rawtype: " + pType.getRawType() + "\n");

		System.out.println("actual type args:");
		Type[] actualTypeArgs = pType.getActualTypeArguments();
		for (Type t : actualTypeArgs) {
			System.out.println(t.getTypeName());
		}

	}

	/**
	 * this case shows how to get the actual type in runtime
	 */
	@Test
	public void testTypeVariable() {
		// this is anonymous inner class !!! Its super class is
		// ArrayList<String>
		List<String> a = new ArrayList<String>() {
		};
		Type superType = a.getClass().getGenericSuperclass();
		System.out.println("generic super class: " + superType.getTypeName());
		if (superType instanceof ParameterizedType) {
			System.out.println("actual type is:" + ((ParameterizedType) superType).getActualTypeArguments()[0]);
		}

		System.out.println("-----------");

		List<String> b = new ArrayList<String>();
		// actually no use of getGenericSuperclass
		Type superType2 = b.getClass().getGenericSuperclass();
		System.out.println("generic super class: " + superType2.getTypeName());
		if (superType2 instanceof ParameterizedType) {
			System.out.println(((ParameterizedType) superType2).getActualTypeArguments()[0]);
		}
	}

	@Test
	public void testGenericArray() {
		ArrayMaker<Integer> arrayMaker = new ArrayMaker(Integer.class);
		int length = 10;
		Integer[] a1 = arrayMaker.makeArray(length);
		for (int i = 0; i < length; i++) {
			Array.set(a1, i, i);
		}

		for (int i = 0; i < length; i++) {
			System.out.println((Integer) Array.get(a1, i));
		}

		List<Integer> list = arrayMaker.makeList(length);
		for (int i = 0; i < length; i++) {
			list.add(i);
		}
	}
}

class ArrayMaker<T> {
	private Class<T> type;

	public ArrayMaker(Class type) {
		this.type = type;
	}

	/*
	 * Even though kind is stored as Class<T> , erasure means that it is
	 * actually just being stored as a Class, with no parameter. So, when you do
	 * some thing with it, as in creating an array, Array.newInstance( ) doesn¡¯t
	 * actually have the type information that¡¯s implied in kind; so it cannot
	 * produce the specific result, which must therefore be cast, which produces
	 * a warning that you cannot satisfy.
	 */
	@SuppressWarnings("unchecked")
	public T[] makeArray(int length) {
		return (T[]) Array.newInstance(type, length);
	}

	public List<T> makeList(int length) {
		return new ArrayList<T>(length);
	}

}

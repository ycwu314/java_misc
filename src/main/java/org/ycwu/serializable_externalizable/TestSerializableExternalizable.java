package org.ycwu.serializable_externalizable;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import org.junit.Test;

public class TestSerializableExternalizable {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	/**
	 * 1. ignore transient (see doNotSave field) <br>
	 * 2. de-serializable uses reflection, so that does not invoke constructor
	 * (see the createTime field does not change) <br>
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testSerializable() {
		Item item = new Item();
		item.setDoNotSave(100);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(item);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] array = out.toByteArray();

		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(array))) {
			Item item2 = (Item) ois.readObject();
			System.out.println(item);
			System.out.println(item2);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 1. Externalizable will call the no-arg constructor, then invoke the
	 * readExternal(ObjectInput in) to set properties <br>
	 */
	@Test
	public void testExternalize() {
		ItemEx itemEx = new ItemEx();
		itemEx.id = 99;
		itemEx.name = "moto";
		itemEx.createTime = new Date();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.writeObject(itemEx);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		byte[] array = out.toByteArray();

		try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(array))) {
			ItemEx itemEx2 = (ItemEx) ois.readObject();
			System.out.println(itemEx);
			System.out.println(itemEx2);
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("serial")
	static class Item implements Serializable {

		private transient int doNotSave = -1;
		private Date createTime;

		public Item() {
			// TODO Auto-generated constructor stub
			createTime = new Date();
			System.out.println("Item constructor is called.");
		}

		public int getDoNotSave() {
			return doNotSave;
		}

		public void setDoNotSave(int doNotSave) {
			this.doNotSave = doNotSave;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		@Override
		public String toString() {
			return "Item [doNotSave=" + doNotSave + ", createTime=" + createTime + "]";
		}

	}

	static class ItemEx implements Externalizable {

		int id;
		String name;
		Date createTime;

		public ItemEx() {
			// TODO Auto-generated constructor stub
			System.out.println("ItemEx constructor is called.");
		}

		public void writeExternal(ObjectOutput out) throws IOException {
			// TODO Auto-generated method stub
			out.writeInt(id);
			System.out.println("write id=" + id);

			out.writeUTF(name);
			System.out.println("write name=" + name);

			out.writeLong(createTime != null ? createTime.getTime() : -1);
			System.out.println("write createTime=" + createTime);
		}

		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			// TODO Auto-generated method stub
			id = in.readInt();
			System.out.println("read id=" + id);

			name = in.readUTF();
			System.out.println("read name=" + name);

			long _createTime = in.readLong();
			System.out.println("read createTime=" + _createTime);
			if (_createTime != -1) {
				createTime = new Date(_createTime);
			}
		}

		@Override
		public String toString() {
			return "ItemEx [id=" + id + ", name=" + name + ", createTime=" + createTime + "]";
		}

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

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

	}

}

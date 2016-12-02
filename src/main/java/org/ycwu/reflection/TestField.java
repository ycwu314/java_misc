package org.ycwu.reflection;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

public class TestField {

	@Test
	public void testField()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Item item = new Item();
		Class<? extends Item> cls = item.getClass();
		Field f1 = cls.getDeclaredField("id");
		// private field, need setAccessible to true, otherwise throws
		// IllegalAccessException
		f1.setAccessible(true);
		f1.set(item, 100);
		Assert.assertEquals(100, item.getId());

		Field f2 = cls.getDeclaredField("code");
		Assert.assertEquals(int.class, f2.getType());

		// static field, set null to object
		f2.set(null, 999);
		Assert.assertEquals(999, Item.code);

	}

}

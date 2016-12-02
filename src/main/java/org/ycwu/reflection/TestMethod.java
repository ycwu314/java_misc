package org.ycwu.reflection;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class TestMethod {

	@Test
	public void testMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Item item = new Item();
		Class<Item> cls = (Class<Item>) item.getClass();
		// if no parameter in method, then set null
		Method m1 = cls.getDeclaredMethod("doSomething", null);
		assertEquals(void.class, m1.getReturnType());

		Method m2 = cls.getDeclaredMethod("doSomething", new Class[] { int.class });
		assertEquals(int.class, m2.getReturnType());

		int param = 1;
		// when calling instance method, pass in the current instance object as
		// invoke()'s param
		assertEquals(param, m2.invoke(item, 1));

		Method m3 = cls.getDeclaredMethod("doStaticSomething", new Class[] { int.class });
		// when calling class's static method, pass null as param into invoke()
		assertEquals(param, m3.invoke(null, param));

	}

}

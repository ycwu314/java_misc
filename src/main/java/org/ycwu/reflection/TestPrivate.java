package org.ycwu.reflection;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class TestPrivate {

	private PrivateObject privateObject;
	private Class<PrivateObject> cls;
	private String message = "hello moto";

	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		privateObject = new PrivateObject(message);
		cls = (Class<PrivateObject>) privateObject.getClass();
	}

	@Test(expected = IllegalAccessException.class)
	public void testPrivateField()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = cls.getDeclaredField("msg");
		// can't directly access private field
		f.get(privateObject);
	}

	@Test
	public void testPrivateField2()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = cls.getDeclaredField("msg");
		// By calling Field.setAcessible(true) you turn off the access checks
		// for this particular Field instance, for reflection only.
		f.setAccessible(true);
		String msg = (String) f.get(privateObject);
		assertEquals(msg, message);
	}

	@Test
	public void testPrivateMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = cls.getDeclaredMethod("getMsg", null);
		method.setAccessible(true);
		String returnVal = (String) method.invoke(privateObject, null);
		assertEquals(message, returnVal);
	}
}

class PrivateObject {
	private String msg;

	public PrivateObject(String msg) {
		super();
		this.msg = msg;
	}

	private String getMsg() {
		return msg;
	}

}
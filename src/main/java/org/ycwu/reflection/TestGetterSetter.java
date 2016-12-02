package org.ycwu.reflection;

import java.lang.reflect.Method;

import org.junit.Test;

public class TestGetterSetter {

	@Test
	public void testFindGetterAndSetter() {
		Method[] methods = Item.class.getDeclaredMethods();
		for (Method method : methods) {
			if (isGetter(method)) {
				System.out.println("getter: " + method.getName());
			}
			if (isSetter(method)) {
				System.out.println("setter: " + method.getName());
			}
		}
	}

	private static boolean isGetter(Method method) {
		if (method.getName().startsWith("get") && method.getParameterTypes().length == 0
				&& !void.class.equals(method.getReturnType())) {
			return true;
		}

		return false;
	}

	private static boolean isSetter(Method method) {
		// getParameterTypes: Returns an array of length 0 if the underlying
		// executable takes no parameters.
		if (method.getName().startsWith("set") && method.getParameterTypes().length == 1
				&& void.class.equals(method.getReturnType())) {
			return true;
		}
		return false;
	}
}

class Item {
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	// not a setter
	public void setNotASetter() {
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

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + "]";
	}

}

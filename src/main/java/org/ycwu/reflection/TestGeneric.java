package org.ycwu.reflection;

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

}

package org.ycwu.reflection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.junit.Test;

public class TestClass {

	@Test
	public void testPrintClassTree() {
		Class<? extends C> cls = C.class;

		List<String> classTree = new ArrayList<>();
		classTree.add(cls.getName());

		Class c = cls.getSuperclass();
		while (Object.class != c) {
			classTree.add(c.getName());
			c = c.getSuperclass();
		}
		classTree.add(c.getName());

		Collections.reverse(classTree);
		int tabCount = 0;
		for (String s : classTree) {
			for (int i = 0; i < tabCount; i++) {
				System.out.print("\t");
			}
			if (tabCount != 0) {
				System.out.print("|--");
			}

			System.out.println(s);
			tabCount++;
		}

	}

	@Test
	public void testFindAllInterfaces() {
		Class cls = C.class;

		List<Class[]> interfaces = new ArrayList<>();
		cls.getInterfaces();

		while (Object.class != cls) {
			interfaces.add(cls.getInterfaces());
			cls = cls.getSuperclass();
		}

		Collections.reverse(interfaces);

		int tabCount = 0;
		for (Class[] interfaceArray : interfaces) {

			for (int i = 0; i < tabCount; i++) {
				System.out.print("\t");
			}
			if (tabCount != 0) {
				System.out.print("|--");
			}

			StringJoiner sj = new StringJoiner(",");
			for (Class anInterface : interfaceArray) {
				sj.add(anInterface.getName());
			}
			System.out.println(sj.toString());

			tabCount++;
		}

	}

}

class A implements IA {

}

class B extends A implements IB, Serializable {

}

class C extends B implements IC {

}

interface IA {

}

interface IB {

}

interface IC {

}

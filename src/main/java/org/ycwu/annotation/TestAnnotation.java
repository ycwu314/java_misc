package org.ycwu.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.Test;

public class TestAnnotation {

	@Test
	public void testBeforeAnnotation() throws NoSuchMethodException, SecurityException, NoSuchFieldException {
		Worker worker = new Worker();
		Class<? extends Worker> cls = worker.getClass();

		// field annotation
		// getField(x) : can only access public fields, including itself's,
		// inherited from super class, or implemented of interfaces
		// getDeclaredField(x) : can access public/protected/private fields
		for (Annotation a : cls.getDeclaredField("name").getAnnotations()) {
			if (a instanceof NotNull) {
				System.out.println("found annotaion of " + a.annotationType().getName());
			}
		}

		Method method = cls.getDeclaredMethod("doSomething", new Class[] { int.class, String.class });

		// method annotation
		// 1. loop over all annotations
		for (Annotation a : method.getAnnotations()) {
			if (a instanceof Before) {
				System.out.println("found annotation of " + a.annotationType().getName());
			}
		}

		// 2. or specify annotation class
		Before beforeAnnotation = method.getAnnotation(Before.class);
		if (beforeAnnotation.log()) {
			System.out.println("start logging xxx");
		}

		// parameter annotation
		// return 2-d array
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (Annotation[] param : parameterAnnotations) {
			for (Annotation a : param) {
				if (a instanceof Range) {
					System.out.println("found annotation of " + a.annotationType().getName());
					Range range = (Range) a;
					System.out.printf("Range[min=%d, max=%d]\n", range.min(), range.max());
				}
			}
		}

	}
}

package org.ycwu.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 
 * @author Administrator
 * 
 *         annotations on parameters will not be inherited
 *
 */
public class TestAnnotationOnInterface {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		ComputeService cs = new ComputeServiceImpl();
		Method method = cs.getClass().getDeclaredMethod("add", Integer.class, Integer.class);
		for (Parameter param : method.getParameters()) {
			Annotation[] anno = param.getAnnotations();
			System.out.println(param.getName() + ": annotations count=" + anno.length);
		}

		System.out.println();
		Method method2 = ComputeService.class.getMethod("add", Integer.class, Integer.class);
		for (Parameter param : method2.getParameters()) {
			Annotation[] anno = param.getAnnotations();
			System.out.println(param.getName() + ": annotations count=" + anno.length);
		}
	}
}

class ComputeServiceImpl implements ComputeService {

	@Override
	public Integer add(@RequestParam Integer a, Integer b) {
		// TODO Auto-generated method stub
		return a + b;
	}

}

interface ComputeService {

	public Integer add(@RequestParam Integer a, @RequestParam Integer b);

}

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface RequestParam {

	String name() default "";
}

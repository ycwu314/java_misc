package org.ycwu.reflection;

public class Item {
	private int id;
	private String name;

	public static int code;
	
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

	// below 2 methods are for TestMethod
	public void doSomething() {

	}

	public int doSomething(int x) {
		return x;
	}
	
	public static int doStaticSomething(int x){
		return x;
	}

}

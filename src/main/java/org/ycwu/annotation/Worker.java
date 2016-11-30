package org.ycwu.annotation;

public class Worker {

	@NotNull
	private String name;

	@Before(log = true)
	public void doSomething(@Range(min = 5, max = 10) int times, String msg) {
		for (int i = 0; i < times; i++) {
			System.out.println(msg);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

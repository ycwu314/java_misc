package org.ycwu.reflection;

public class Item {
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

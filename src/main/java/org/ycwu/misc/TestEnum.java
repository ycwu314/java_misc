package org.ycwu.misc;

public class TestEnum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (Color color : Color.values()) {
			System.out.println(color.name() + ":" + color.ordinal() + ":" + color.getCode());

		}

	}

}

// cannot extends any class
enum Color {
	RED(10), GREEN(20), BLUE(30);

	private int code;

	// the constructor must be private
	private Color(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

}

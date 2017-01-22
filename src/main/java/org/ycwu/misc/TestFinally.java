package org.ycwu.misc;

public class TestFinally {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(test());
	}

	public static int test() {
		try {
			return 100;
		} finally {
			return 200;
		}
	}

}

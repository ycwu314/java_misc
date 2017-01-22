package org.ycwu.misc;

public class TestCast {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		A a = new A();
		Holder holder = new Holder();
		holder.setCommon(a);
		System.out.println(((HasType) holder.getCommon()).getType());

	}

	static class Holder {
		Common common;

		public Common getCommon() {
			return common;
		}

		public void setCommon(Common common) {
			this.common = common;
		}

	}

	static class A extends Common implements HasType {

		@Override
		public String getType() {
			// TODO Auto-generated method stub
			return "A";
		}

	}

	static abstract class Common {
		protected Common common;

		public Common getCommon() {
			return common;
		}

		public void setCommon(Common common) {
			this.common = common;
		}

	}

	static interface CommonRequest {
	}

	static interface HasType {
		String getType();
	}

}

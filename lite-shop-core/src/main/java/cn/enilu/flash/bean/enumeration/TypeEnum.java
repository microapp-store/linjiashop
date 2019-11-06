package cn.enilu.flash.bean.enumeration;

public class TypeEnum {

	/**
	 * 输入数据的html类型
	 */
	public enum DataItemShowType {
		/**
		 * 0为文本框；1为下拉框；2为日期框
		 */
		TEXT(0), SELECT(1), DATE(2);

		private int value;

		private DataItemShowType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}




}

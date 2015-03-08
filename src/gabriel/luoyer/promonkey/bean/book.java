package gabriel.luoyer.promonkey.bean;


public class book {

	private String B_NO;
	private String B_name;
	private String B_address;
	/**
	 * 书本出版社
	 */
	private String B_press;
	
	/**
	 * 用于 android 未换界面的借书时间的显示
	 */
	private String B_accessTime;
	
	public String getB_NO() {
		return B_NO;
	}
	public void setB_NO(String b_NO) {
		B_NO = b_NO;
	}
	public String getB_name() {
		return B_name;
	}
	public void setB_name(String b_name) {
		B_name = b_name;
	}
	public String getB_address() {
		return B_address;
	}
	public void setB_address(String b_address) {
		B_address = b_address;
	}
	public String getB_press() {
		return B_press;
	}
	public void setB_press(String b_press) {
		B_press = b_press;
	}
	public book() {
		super();
	}
	public book(String b_NO) {
		super();
		B_NO = b_NO;
	}
	public String getB_accessTime() {
		return B_accessTime;
	}
	public void setB_accessTime(String b_accessTime) {
		B_accessTime = b_accessTime;
	}
	
	
}

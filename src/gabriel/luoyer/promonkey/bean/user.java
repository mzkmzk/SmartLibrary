package gabriel.luoyer.promonkey.bean;

public class user {

	private String U_NO;
	private String U_password;
	private String U_Name;
	private book book_return;
	
	public String getU_NO() {
		return U_NO;
	}
	public void setU_NO(String u_NO) {
		U_NO = u_NO;
	}
	public String getU_password() {
		return U_password;
	}
	public void setU_password(String u_password) {
		U_password = u_password;
	}
	public String getU_Name() {
		return U_Name;
	}
	public void setU_Name(String u_Name) {
		U_Name = u_Name;
	}
	public book getBook_return() {
		return book_return;
	}
	public void setBook_return(book book_return) {
		this.book_return = book_return;
	}
	
}

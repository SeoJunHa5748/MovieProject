package projectmovie;

public class Sign {
	private String Id; 
	private String Pass; 
	private String InfoName; 
	private String PhoneNumber;
	
	public Sign(String id, String pass, String infoName, String phoneNumber) {
		super();
		Id = id;
		Pass = pass;
		InfoName = infoName;
		PhoneNumber = phoneNumber;
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPass() {
		return Pass;
	}
	public void setPass(String pass) {
		Pass = pass;
	}
	public String getInfoName() {
		return InfoName;
	}
	public void setInfoName(String infoName) {
		InfoName = infoName;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "\n------------------------------------------------\n" 
				+ "아이디\t : " + Id + "\n비밀번호\t : " + Pass
				+ "\n이름\t : " + InfoName + "\n전화번호\t : " + PhoneNumber;
	}
}

import java.io.Serializable;

public class BankAccount  implements Serializable{
	//need this field for the serialization
	private static final long serialVersionUID = 1L;
	
	private String BankName;
	private String AccountID;
	public BankAccount(String bankName, String accountID) {
		BankName = bankName;
		AccountID = accountID;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getAccountID() {
		return AccountID;
	}
	public void setAccountID(String accountID) {
		AccountID = accountID;
	}

}

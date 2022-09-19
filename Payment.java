import java.io.IOException;
import java.io.Serializable;

public class Payment extends Transaction implements Serializable{

	//need this field for the serialization
	private static final long serialVersionUID = 1L;
	
	private PaymentType paymentType;
	private BankAccount account;
	
	public Payment(double amount, PaymentType type, BankAccount account) throws IOException {
		super(amount);
		this.paymentType = type;
		this.account = account;
	}
	
	public PaymentType getPaymentType() {
		return paymentType;
	}

	public BankAccount getAccount() {
		return account;
	}


}

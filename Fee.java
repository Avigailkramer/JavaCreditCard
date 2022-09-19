import java.io.IOException;
import java.io.Serializable;

public class Fee extends Transaction implements Serializable{
	
	//need this field for the serialization
	private static final long serialVersionUID = 1L;

	public Fee(double amount) throws IOException {
		super(amount);
	}

	private FeeType FeeType;

	public FeeType getFeeType() {
		return FeeType;
	}
}

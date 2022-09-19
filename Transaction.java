import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Transaction implements Serializable {
	// need this field for the serialization
	private static final long serialVersionUID = 1L;

	// static integer that all transactions will share and will be incremented for
	// each card
	private static int idCounter;

	// we need to set the id counter to what it was last time we ran the program
	// it is therefore saved in a file. main will call this method to initialize it
	public static void initializeIdCounter() throws IOException {

		BufferedReader input = new BufferedReader(new FileReader("TransactionIdCounter.txt"));
		idCounter = Integer.parseInt(input.readLine());
		input.close();

	}



	// every time a new transaction is created, besides for updating the idcounter,
	// we need to update it in the file
	private static void updateLastUsedIdInFile() throws IOException {
		FileWriter output = new FileWriter("TransactionIdCounter.txt");
		output.write(idCounter + "");
		output.close();

	}
	protected long transactionID;
	protected long lastTransactionID;
	protected LocalDateTime transactionDateAndTime;
	protected TransactionType transactionType;

	// how much money this transaction is
	protected double amount;
	public Transaction(double amount) throws IOException {
		this.amount = amount;
		// automatically give this transaction the next available id
		this.transactionID = ++idCounter;
		updateLastUsedIdInFile();

		// the transaction date is whatever date the transaction is created
		this.transactionDateAndTime = LocalDateTime.now();

		// set it to the correct transaction type based on which class this is an
		// instance of
		if (this instanceof Purchase) {
			this.transactionType = TransactionType.PURCHASE;
		} else if (this instanceof Fee) {
			this.transactionType = TransactionType.FEE;
		} else if (this instanceof Payment) {
			this.transactionType = TransactionType.PAYMENT;
		} else {
			throw new RuntimeException(
					"The constructor transaction cannot be called. Must create a transaction using one of its children classes (either Fee, Purchase, or Payment");
		}
	}
	public double getAmount() {
		return this.amount;
	}

	public long getLastTransactionID() {
		return lastTransactionID;
	}

	public LocalDateTime getTransactionDateAndTime() {
		return transactionDateAndTime;
	}

	public long getTransactionID() {

		return transactionID;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(transactionType);
		str.append(": Transaction ID: ");
		str.append(transactionID);
		str.append(" Amount: ");
		str.append(String.format("$%,.2f", amount));
		str.append(" Date: ");
		str.append(transactionDateAndTime.toLocalDate());
		str.append(" Time: ");
		str.append(String.format("%02d", transactionDateAndTime.getHour()));
		str.append(":");
		str.append(String.format("%02d", transactionDateAndTime.getMinute()));

		return str.toString();
	}

}

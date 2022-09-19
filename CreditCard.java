import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.*;
import java.util.ArrayList;

public class CreditCard implements Serializable {
	// need this field for the serialization
	private static final long serialVersionUID = 1L;

	// static integer that all credit cards will share and will be incremented for
	// each card
	private static int idCounter;

	// we need to set the id counter to what it was last time we ran the program
	// it is therefore saved in a file. main will call this method to initialize it
	public static void initializeIdCounter() throws IOException {

		BufferedReader input = new BufferedReader(new FileReader("CreditCardIdCounter.txt"));
		idCounter = Integer.parseInt(input.readLine());
		input.close();

	}

	// every time a new credit card is created, besides for updating the idcounter,
	// we need to update it in the file
	private static void updateLastUsedIdInFile() throws IOException {
		FileWriter output  = new FileWriter("CreditCardIdCounter.txt");
			output.write(idCounter + "");
			output.close();
		
	}

	private String creditCardId;
	private LocalDate issueDate;
	private LocalDate expirationDate;
	private CreditCardType issueCompany;
	private CreditCardStatus status;
	private double creditCardLimit;
	private double currBalance;
	private double availCredit;
	private ArrayList<Transaction> transactions;

	// constructor
	public CreditCard(LocalDate issueDate, LocalDate expirationDate, CreditCardType company) throws IOException {
		// increments the id and converts it to a string at sets it to the credit card
		// id of this card
		this.creditCardId = ((Integer) (++idCounter)).toString();
		//now that we changed the value of id counter, we need to save it in the file as well
		updateLastUsedIdInFile();

		this.issueDate = issueDate;
		this.expirationDate = expirationDate;
		this.issueCompany = company;
		// if its passed the expirationDate, make the status expired, otherwise make it
		// active
		this.status = ((expirationDate.compareTo(LocalDate.now()) < 0) ? CreditCardStatus.EXPIRED
				: CreditCardStatus.ACTIVE);
		this.creditCardLimit = 2000;
		this.currBalance = 0;
		this.availCredit = creditCardLimit;
		this.transactions = new ArrayList<>();
	}
 
	public void addFee(Fee f) {
		this.transactions.add(f);
		this.currBalance += f.getAmount();
		availCredit = creditCardLimit - currBalance;
	}
	public void addPayment(Payment p) {
		//if they pay more than their balance, it only pays upto their balance
		double amount = //p.getAmount()>currBalance ? currBalance : p.getAmount();
				p.getAmount();
		this.transactions.add(p);
		this.currBalance -= amount;
		availCredit += amount;

	}
	public void addPurchase(Purchase p) {
		if(!status.equals(CreditCardStatus.ACTIVE)) {
			throw new RuntimeException("Card is not active");
		}
		this.transactions.add(p);
		this.currBalance += p.getAmount();
		availCredit = creditCardLimit - currBalance;
	}
	public double getAvailCredit() {
		return this.availCredit;
	}
	public String getCreditCardId() {
		return this.creditCardId;
	}
	public double getCreditLimit() {
		return this.creditCardLimit;
	}
	public double getCurrBalance() {
		return this.currBalance;

	}
	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}

	public Purchase getLargestPurchaseOnThisCard(){

		Purchase largestPurchase = null;
		double largestPurchaseAmount = 0;

		// loop thru all the transactions and see if any purchases have an amount
		// greater than the highest so far
		for (Transaction t : transactions) {
			// if this transaction is a purchase and its amount is higher than the highiest
			// purchase amount,
			if (t instanceof Purchase && t.getAmount() > largestPurchaseAmount) {
				// make this the largest purchase
				largestPurchase = (Purchase) t;
				// make this the largest purchase amount
				largestPurchaseAmount = t.getAmount();
			}
		}

		// if largest purchase is still null cuz there are no purchase, return null.
		// else return thisp purhcase
		return largestPurchase == null ? null : largestPurchase;
	}
	public CreditCardStatus getStatus() {
		return status;
	}

	public double getTotalFees() {
		double total = 0;
		for (Transaction t : transactions) {
			if (t instanceof Fee) {
				total += t.getAmount();
			}

		}
		return total;
	}
	public double getTotalSpentOnCertainCategoryOfExpense(PurchaseType p) {
		double total = 0;
		// loop thru all the transactions of the card
		for (Transaction t : transactions) {
			// if this transaction is a purchase..
			if (t instanceof Purchase) {
				// if the purchase type is the type that we are looking for..
				if (((Purchase) t).getPurchaseType().equals(p)) {
					// then add this amount to the total;
					total += t.getAmount();
				}
			}
		}
		return total;
	}
	public void markCancelled() {
		this.status = CreditCardStatus.CANCELLED;
	}

	public void markLost() {
		this.status = CreditCardStatus.LOST;
	}

	public Payment mostRecentPayment() {
		for(int i=transactions.size()-1; i>0; i--) {
			if(transactions.get(i) instanceof Payment) {
				//return a deep copy 
				return (Payment)transactions.get(i);
			}
		}
		return null;
	}



	public Purchase mostRecentPurchase() {
		for(int i=transactions.size()-1; i>=0; i--) {
			if(transactions.get(i) instanceof Purchase) {
				return (Purchase)transactions.get(i);
		
			}
		}

		return null;
	}

	public int numOfTransactions() {
		return transactions.size();
	}

	public void setStatus(CreditCardStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Credit Card Id: " + creditCardId);
		str.append("\tCredit Card Status: " + status);
		str.append("\tCredit Card Type: " + issueCompany);
		return str.toString();
	}
	
	public String transactions() {
		StringBuilder str = new StringBuilder();
		for(Transaction t: transactions) {
			str.append(t);
			str.append(" \n");
		}
		return str.toString();
	}

}

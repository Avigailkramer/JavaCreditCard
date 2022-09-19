import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class User implements Serializable {

	// need this field for the serialization
	private static final long serialVersionUID = 1L;

	private LinkedList<CreditCard> cards;
	private BankAccount bankAccount;
	private String name;

	public User(String name, String bankName, String acctNumber) {
		this.name = name;
		this.cards = new LinkedList<>();
		this.bankAccount = new BankAccount(bankName, acctNumber);
	}

	public void addCard(CreditCard c) {
		this.cards.add(c);
	}
	public void addFee(String id, Fee f) {
		getCardBasedOnId(id).addFee(f);
	}
	public void addPayment(String id, Payment p) {
		getCardBasedOnId(id).addPayment(p);
	}

	public boolean addPurchase(String id, Purchase p) {
		CreditCard card = getCardBasedOnId(id);

		if (!card.getStatus().equals(CreditCardStatus.ACTIVE) || p.getAmount() > card.getAvailCredit()) {
			return false;
		}
		card.addPurchase(p);
		return true;
	}

	public void checkIfAnyCardsExpired() {
		for (CreditCard c : cards) {
			if (c.getExpirationDate().compareTo(LocalDate.now()) < 0) {
				c.setStatus(CreditCardStatus.EXPIRED);
			}
		}
	}

	public boolean containsCard(String id) {
		for (CreditCard c : cards) {
			if (c.getCreditCardId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public String creditCardsToString() {
		StringBuilder str = new StringBuilder();
		for (CreditCard c : cards) {
			str.append("\t" + c.toString() + "\n");
		}
		return str.toString();
	}

	public double getAvailCredit(String id) {
		return getCardBasedOnId(id).getAvailCredit();
	}

	public double getBalanceOfCreditCard(String id) {
		return getCardBasedOnId(id).getCurrBalance();
	}

	public double getCardBalance(String id) {
		return getCardBasedOnId(id).getCurrBalance();
	}
	private CreditCard getCardBasedOnId(String id) {
		for (CreditCard c : cards) {
			if (id.equals(c.getCreditCardId())) {
				return c;
			}
		}

		throw new IllegalArgumentException("This card doesnt exist in this wallet");

	}

	public Purchase getLargestPurchase() {
		double largestPurchaseAmountFromAllCards = 0;
		Purchase largestPurchaseOnAllCards = null;

		for (CreditCard c : cards) {
			if (c.getLargestPurchaseOnThisCard() == null) {
				continue;
			}
			if (c.getLargestPurchaseOnThisCard().getAmount() > largestPurchaseAmountFromAllCards) {
				largestPurchaseOnAllCards = c.getLargestPurchaseOnThisCard();
				largestPurchaseAmountFromAllCards = c.getLargestPurchaseOnThisCard().getAmount();
			}
		}

		return largestPurchaseOnAllCards == null ? null : largestPurchaseOnAllCards;

	}

	public Payment getMostRecentPayment() {
		if (cards.size() == 0) {
			return null;
		}

		Payment mostRecentPayment = null;
		LocalDateTime dateAndTimeOfMostRecentPayment = LocalDateTime.MIN;
		for (CreditCard c : cards) {
			// if there is a most recent payment on this credit card
			if (c.mostRecentPayment() != null) {
				// if the date and time on this payment is later than the one on the most recent
				// payment,
				if (c.mostRecentPayment().getTransactionDateAndTime().compareTo(dateAndTimeOfMostRecentPayment) > 0) {
					// make it the most recent payment
					mostRecentPayment = c.mostRecentPayment();
					dateAndTimeOfMostRecentPayment = c.mostRecentPayment().getTransactionDateAndTime();
				}
			}
		}

		return mostRecentPayment;
	}

	public String getName() {
		return this.name;
	}

	public double getTotalSpentOnCertainCategoryOfExpense(PurchaseType p) {
		double total = 0;
		for (CreditCard c : cards) {
			total += c.getTotalSpentOnCertainCategoryOfExpense(p);
		}
		return total;
	}

	public void markCardAsCancelled(String id) {
		getCardBasedOnId(id).markCancelled();
	}

	public void markCardAsLost(String id) {
		getCardBasedOnId(id).markLost();
	}

	public Purchase mostRecentPurchaseOnCard(String id) {
		return getCardBasedOnId(id).mostRecentPurchase();

	}

	public int numberOfCards() {
		return cards.size();
	}

	public int numOfTransactionsOnCard(String id) {
		return getCardBasedOnId(id).numOfTransactions();
	}

	public void payBill(String id, double amount, PaymentType type) throws IOException {

		getCardBasedOnId(id).addPayment(new Payment(amount, type, bankAccount));
	}

	/**
	 * removes a credit card card based on the id
	 * 
	 * @param id
	 * @return if this card was able to be reomved or if no because it wasnt found
	 */
	public void removeCard(String id) {
		cards.remove(getCardBasedOnId(id));
	}

	public double totalAvailCredit() {
		double total = 0;
		for (CreditCard c : cards) {
			total += c.getAvailCredit();
		}
		return total;
	}

	public double totalBalance() {
		double total = 0;
		for (CreditCard c : cards) {
			total += c.getCurrBalance();
		}

		return total;
	}

	public String transactionsOnCardToString(String id) {
		return getCardBasedOnId(id).transactions();
	}
}

import java.util.ArrayList;

public class CreditCards {
	private ArrayList<CreditCard> cards;

	public CreditCards() {
		this.cards = new ArrayList<>();
	}

	public double totalBalance() {
		double total = 0;
		for (CreditCard c : cards) {
			total += c.getCurrBalance();
		}

		return total;
	}

	public double totalAvailCredit() {
		double total = 0;
		for (CreditCard c : cards) {
			total += c.getAvailCredit();
		}
		return total;
	}

	public void addCard(CreditCard c) {
		this.cards.add(c);
	}

	public void removeCard(CreditCard c) {
		this.cards.remove(c);
	}

	public CreditCard findCard(String id) {
		for (CreditCard c : cards) {
			if (c.getCreditCardId().equals(id)) {
				return c;
			}
		}
		// if there is no card with this id
		return null;
	}

	public void addPurchase(CreditCard card, Purchase p) {
		boolean thisCardExists = false;
		for (CreditCard c : cards) {
			if (card.equals(c)) {
				c.addPurchase(p);
				thisCardExists = true;
				break;
			}
		}
		if (!thisCardExists) {
			throw new IllegalArgumentException("This card doesnt exist in this wallet");
		}
	}

	public void addFee(CreditCard card, Fee f) {
		boolean thisCardExists = false;
		for (CreditCard c : cards) {
			if (card.equals(c)) {
				c.addFee(f);
				thisCardExists = true;
				break;
			}
		}
		if (!thisCardExists) {
			throw new IllegalArgumentException("This card doesnt exist in this wallet");
		}
	}

	public void addPayment(CreditCard card, Payment p) {
		boolean thisCardExists = false;
		for (CreditCard c : cards) {
			if (card.equals(c)) {
				c.addPayment(p);
				thisCardExists = true;
				break;
			}
		}
		if (!thisCardExists) {
			throw new IllegalArgumentException("This card doesnt exist in this wallet");
		}
	}
}

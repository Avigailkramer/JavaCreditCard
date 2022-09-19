import java.util.Scanner;

public class main2 {
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println(getPurchaseType());
	}

	public static String getPurchaseType() {
		String choice;
		do {
			System.out.println("jjjjjjj");
			choice = input.nextLine();
			choice = choice.toUpperCase();
		} while (!choice.equals("CAR")&& !choice.equals("CLOTHING")&& !choice.equals("FOOD")&& !choice.equals("GROCERIES")
				&& !choice.equals("LODGING")&& !choice.equals("RESTAURANT")&& !choice.equals("TRAVEL")&& !choice.equals("UTILITIES"));
		return choice;
	}
}

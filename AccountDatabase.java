import java.util.*;

/**
 * The Class AccountDatabase.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class AccountDatabase {
	
	static LinkedList<BankAccount> list = new LinkedList<BankAccount>();
	
	/**
	 * Read users.
	 */
	public static void readUsers(){
		list.add(new BankAccount(1234123412341234L, 1000, 1234, null));
		list.add(new BankAccount(1111111111111111L, 2000, 1111, null));
		list.add(new BankAccount(2222222222222222L, 3000, 2222, null));
	}

	/**
	 * Login, gets requested bank account.
	 *
	 * @param cardnumber the cardnumber
	 * @param pin the pin
	 * @return the bank account
	 */
	public static BankAccount login(long cardnumber, int pin){

		Iterator<BankAccount> iterator = list.iterator();
		while (iterator.hasNext()) {
			BankAccount current = iterator.next();
			if (current.cardnumber == cardnumber && current.pin == pin) {
				iterator.remove();
				return current;
			}
		}
		return null;
	}

	/**
	 * Logout, put the account back into list on exit..
	 *
	 * @param account the account
	 */
	public static void logout(BankAccount account){
		list.add(account);
	}
}

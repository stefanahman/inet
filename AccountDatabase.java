import java.util.*;


public class AccountDatabase {
	
	static LinkedList<BankAccount> list = new LinkedList<BankAccount>();
	
	public static void readUsers(){
		list.add(new BankAccount(1234123412341234L, 1000, 1234, null));
		list.add(new BankAccount(1111111111111111L, 2000, 1111, null));
		list.add(new BankAccount(2222222222222222L, 3000, 2222, null));
	}

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

	public static void logout(BankAccount account){
		list.add(account);
	}
}

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class BankAccount.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class BankAccount {

	long cardnumber, balance;
	int pin;
	List<Integer> seccodes = new ArrayList<Integer>();

	/**
	 * Instantiates a new bank account.
	 *
	 * @param cardnumber the cardnumber
	 * @param balance the balance
	 * @param pin the pin
	 * @param seccodes the seccodes
	 */
	public BankAccount(long cardnumber, long balance, int pin, List<Integer> seccodes) {
		this.cardnumber = cardnumber;
		this.balance = balance;
		this.pin = pin;
		this.seccodes = generateSecurityCodes();
	}

	/**
	 * Generate security codes.
	 *
	 * @return the list
	 */
	public List<Integer> generateSecurityCodes(){
		for(int i = 0,j = 1; j < 100;i++,j+=2)
		{
			seccodes.add(i, j);
		}
		return seccodes;
	}
	
	/**
	 * Removes the security code from codelist.
	 *
	 * @param seccode the seccode
	 */
	public void removeSecurityCode(int seccode){
		Iterator<Integer> iterator = seccodes.iterator();
		while (iterator.hasNext()) {
			int current = iterator.next();
			iterator.remove();
			if (current == seccode){
				break;
			}
		}
	}

	/**
	 * Gets the balance of bank account.
	 *
	 * @return the balance
	 */
	public long getBalance() {
		return balance;
	}

	/**
	 * Withdrawal from bank account.
	 *
	 * @param amount the amount
	 * @param securityCode the security code
	 * @return true, if successful
	 */
	public boolean withdrawal(long amount, int securityCode) {
		if(amount <= this.balance && validateCode(securityCode)) {
			this.balance = this.balance - amount;
			removeSecurityCode(securityCode);
			return true;
		}
		return false;
	}

	/**
	 * Deposit to bank account.
	 *
	 * @param amount the amount
	 * @param securityCode the security code
	 * @return true, if successful
	 */
	public boolean deposit(long amount, int securityCode) {
		if(validateCode(securityCode)) {
			this.balance = this.balance + amount;
			removeSecurityCode(securityCode);
			return true;
		}
		return false;
	}

	/**
	 * Validate security code.
	 *
	 * @param securityCode the security code
	 * @return true, if successful
	 */
	public boolean validateCode(int securityCode) {
		return seccodes.contains(securityCode);
	}
}

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BankAccount {

	long cardnumber, balance;
	int pin;
	List<Integer> seccodes = new ArrayList<Integer>();

	public BankAccount(long cardnumber, long balance, int pin, List<Integer> seccodes) {
		this.cardnumber = cardnumber;
		this.balance = balance;
		this.pin = pin;
		this.seccodes = generateSecurityCodes();
	}

	public List<Integer> generateSecurityCodes(){
		for(int i = 0,j = 1; j < 100;i++,j+=2)
		{
			seccodes.add(i, j);
		}
		return seccodes;
	}
	
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

	public long getBalance() {
		return balance;
	}

	public boolean withdrawal(long amount, int securityCode) {
		if(amount <= this.balance && validateCode(securityCode)) {
			this.balance = this.balance - amount;
			removeSecurityCode(securityCode);
			return true;
		}
		return false;
	}

	public boolean deposit(long amount, int securityCode) {
		if(validateCode(securityCode)) {
			this.balance = this.balance + amount;
			removeSecurityCode(securityCode);
			return true;
		}
		return false;
	}

	public boolean validateCode(int securityCode) {
		return seccodes.contains(securityCode);
	}
}

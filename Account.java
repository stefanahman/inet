
public class Account {

	long cardnumber, balance;
	int pin;

	public Account(long cardnumber, long balance, int pin) {
		this.cardnumber = cardnumber;
		this.balance = balance;
		this.pin = pin;
	}


	public long getBalance() {
		return balance;
	}

	public boolean withdrawal(long amount, int securityCode) {
		if(amount <= this.balance && validateCode(securityCode)) {
			this.balance = this.balance - amount;
			return true;
		}
		return false;
	}

	public boolean deposit(long amount, int securityCode) {
		if(validateCode(securityCode)) {
			this.balance = this.balance + amount;
			return true;
		}
		return false;
	}

	public boolean validateCode(int securityCode) {
		return ((securityCode < 100) && (securityCode > 0) && (securityCode % 2 != 0));
	}
}

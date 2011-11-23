
public class ClientBytePacker {
	
	byte[] bytePackage = new byte[10];
	
	final byte LOGIN = 0x00;
	final byte BALANCE = 0x01;
	final byte WITHDRAWAL = 0x02;
	final byte DEPOSIT = 0x03;
	final byte EXIT = 0x07;
	
	public byte[] login(long cardnumber, int pin){
		bytePackage[0] = LOGIN;
		bytePackage[1] = (byte)(pin >> 8);
		bytePackage[2] = (byte)(pin >> 0);
		bytePackage[3] = (byte)(cardnumber >> 48);
		bytePackage[4] = (byte)(cardnumber >> 40);
		bytePackage[5] = (byte)(cardnumber >> 32);
		bytePackage[6] = (byte)(cardnumber >> 24);
		bytePackage[7] = (byte)(cardnumber >> 16);
		bytePackage[8] = (byte)(cardnumber >> 8);
		bytePackage[9] = (byte)(cardnumber >> 0);
		return bytePackage;	
	}
	
	public byte[] balance(){
		bytePackage[0] = BALANCE;
		bytePackage[1] = (byte)(0);
		bytePackage[2] = (byte)(0);
		bytePackage[3] = (byte)(0);
		bytePackage[4] = (byte)(0);
		bytePackage[5] = (byte)(0);
		bytePackage[6] = (byte)(0);
		bytePackage[7] = (byte)(0);
		bytePackage[8] = (byte)(0);
		bytePackage[9] = (byte)(0);
		return bytePackage;	
	}
	
	public byte[] withdrawal(int securityCode, long amount){
		bytePackage[0] = WITHDRAWAL;
		bytePackage[1] = (byte)(securityCode);
		bytePackage[2] = (byte)(amount >> 56);
		bytePackage[3] = (byte)(amount >> 48);
		bytePackage[4] = (byte)(amount >> 40);
		bytePackage[5] = (byte)(amount >> 32);
		bytePackage[6] = (byte)(amount >> 24);
		bytePackage[7] = (byte)(amount >> 16);
		bytePackage[8] = (byte)(amount >> 8);
		bytePackage[9] = (byte)(amount >> 0);
		return bytePackage;	
	}
	
	public byte[] deposit(int securityCode, long amount){
		bytePackage[0] = DEPOSIT;
		bytePackage[1] = (byte)(securityCode);
		bytePackage[2] = (byte)(amount >> 56);
		bytePackage[3] = (byte)(amount >> 48);
		bytePackage[4] = (byte)(amount >> 40);
		bytePackage[5] = (byte)(amount >> 32);
		bytePackage[6] = (byte)(amount >> 24);
		bytePackage[7] = (byte)(amount >> 16);
		bytePackage[8] = (byte)(amount >> 8);
		bytePackage[9] = (byte)(amount >> 0);
		return bytePackage;	
	}
	 
	public byte[] exit(){
		bytePackage[0] = EXIT;
		bytePackage[1] = (byte)(0);
		bytePackage[2] = (byte)(0);
		bytePackage[3] = (byte)(0);
		bytePackage[4] = (byte)(0);
		bytePackage[5] = (byte)(0);
		bytePackage[6] = (byte)(0);
		bytePackage[7] = (byte)(0);
		bytePackage[8] = (byte)(0);
		bytePackage[9] = (byte)(0);
		return bytePackage;
	}
}

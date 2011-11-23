
public class ClientBytePacker {
	
	byte[] bytePackage1 = new byte[1];
	byte[] bytePackage10 = new byte[10];
	
	final byte LOGIN = 0x00;
	final byte BALANCE = 0x01;
	final byte WITHDRAWAL = 0x02;
	final byte DEPOSIT = 0x03;
	final byte EXIT = 0x07;
	
	public byte[] login(long cardnumber, int pin){
		bytePackage10[0] = LOGIN;
		bytePackage10[1] = (byte)(pin >> 8);
		bytePackage10[2] = (byte)(pin >> 0);
		bytePackage10[3] = (byte)(cardnumber >> 48);
		bytePackage10[4] = (byte)(cardnumber >> 40);
		bytePackage10[5] = (byte)(cardnumber >> 32);
		bytePackage10[6] = (byte)(cardnumber >> 24);
		bytePackage10[7] = (byte)(cardnumber >> 16);
		bytePackage10[8] = (byte)(cardnumber >> 8);
		bytePackage10[9] = (byte)(cardnumber >> 0);
		return bytePackage10;	
	}
	
	public byte[] balance(){
		bytePackage1[0] = BALANCE;
		return bytePackage1;	
	}
	
	public byte[] withdrawal(int securityCode, long amount){
		bytePackage10[0] = WITHDRAWAL;
		bytePackage10[1] = (byte)(securityCode);
		bytePackage10[2] = (byte)(amount >> 56);
		bytePackage10[3] = (byte)(amount >> 48);
		bytePackage10[4] = (byte)(amount >> 40);
		bytePackage10[5] = (byte)(amount >> 32);
		bytePackage10[6] = (byte)(amount >> 24);
		bytePackage10[7] = (byte)(amount >> 16);
		bytePackage10[8] = (byte)(amount >> 8);
		bytePackage10[9] = (byte)(amount >> 0);
		return bytePackage10;	
	}
	
	public byte[] deposit(int securityCode, long amount){
		bytePackage10[0] = DEPOSIT;
		bytePackage10[1] = (byte)(securityCode);
		bytePackage10[2] = (byte)(amount >> 56);
		bytePackage10[3] = (byte)(amount >> 48);
		bytePackage10[4] = (byte)(amount >> 40);
		bytePackage10[5] = (byte)(amount >> 32);
		bytePackage10[6] = (byte)(amount >> 24);
		bytePackage10[7] = (byte)(amount >> 16);
		bytePackage10[8] = (byte)(amount >> 8);
		bytePackage10[9] = (byte)(amount >> 0);
		return bytePackage10;	
	}
	 
	public byte[] exit(){
		bytePackage1[0] = EXIT;
		return bytePackage1;
	}
}

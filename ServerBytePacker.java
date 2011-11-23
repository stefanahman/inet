
public class ServerBytePacker {
	
	byte[] bytePackage = new byte[10];
	
	final byte SUCCESS = 0x00;
	final byte FAILED = 0x01;
	final byte BALANCE = 0x02;
	final byte EXIT = 0x07;
	
	public byte[] success(){
		bytePackage[0] = SUCCESS;
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
	
	public byte[] failed(){
		bytePackage[0] = FAILED;
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
	
	public byte[] balance(long balance){
		bytePackage[0] = BALANCE;
		bytePackage[1] = (byte)(balance >> 64);
		bytePackage[2] = (byte)(balance >> 56);
		bytePackage[3] = (byte)(balance >> 48);
		bytePackage[4] = (byte)(balance >> 40);
		bytePackage[5] = (byte)(balance >> 32);
		bytePackage[6] = (byte)(balance >> 24);
		bytePackage[7] = (byte)(balance >> 16);
		bytePackage[8] = (byte)(balance >> 8);
		bytePackage[9] = (byte)(balance >> 0);
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

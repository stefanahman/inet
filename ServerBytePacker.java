
public class ServerBytePacker {
	
	final byte VERIFICATION = 0x00;
	final byte BALANCE = 0x01;
	final byte VERSION = 0x05;
	final byte EXIT = 0x07;
	
	public byte[] header(byte size){
		byte[] bytePackage1 = new byte[1];
		bytePackage1[0] = size;
		return bytePackage1;
	}
	
	public byte[] verify(int status){
		byte[] bytePackage2 = new byte[2];
		bytePackage2[0] = VERIFICATION;
		bytePackage2[1] = (byte) status;
		return bytePackage2;
	}
	
	public byte[] balance(long balance){
		byte[] bytePackage10 = new byte[10];
		bytePackage10[0] = BALANCE;
		bytePackage10[1] = (byte)(balance >> 64);
		bytePackage10[2] = (byte)(balance >> 56);
		bytePackage10[3] = (byte)(balance >> 48);
		bytePackage10[4] = (byte)(balance >> 40);
		bytePackage10[5] = (byte)(balance >> 32);
		bytePackage10[6] = (byte)(balance >> 24);
		bytePackage10[7] = (byte)(balance >> 16);
		bytePackage10[8] = (byte)(balance >> 8);
		bytePackage10[9] = (byte)(balance >> 0);
		return bytePackage10;
	}
	
	public byte[] verison(int ver){
		byte[] bytePackage2 = new byte[2];
		bytePackage2[0] = VERSION;
		bytePackage2[1] = (byte) ver;
		return bytePackage2;
	}
	
	public byte[] exit(){
		byte[] bytePackage1 = new byte[1];
		bytePackage1[0] = EXIT;
		return bytePackage1;
	}
}


public class ServerBytePacker {
	
	byte[] bytePackage = new byte[10];
	
	final byte LOGINSUCESS = 0x00;
	final byte LOGINFAILED = 0x01;
	final byte EXIT = 0x07;
	
	public byte[] loginsucess(){
		bytePackage[0] = LOGINSUCESS;
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
	
	public byte[] loginfailed(){
		bytePackage[0] = LOGINFAILED;
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

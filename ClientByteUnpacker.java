
public class ClientByteUnpacker {
	
	private long tempLong;
	private int tempInt;

	public long getBalance(byte[] bytePackage){
		tempLong = 0; 
		tempLong = (tempLong << 8 | (bytePackage[2] & 0xFF));
		tempLong = (tempLong << 8 | (bytePackage[3] & 0xFF));
		tempLong = (tempLong << 8 | (bytePackage[4] & 0xFF));
		tempLong = (tempLong << 8 | (bytePackage[5] & 0xFF));
		tempLong = (tempLong << 8 | (bytePackage[6] & 0xFF));
		tempLong = (tempLong << 8 | (bytePackage[7] & 0xFF));
		tempLong = (tempLong << 8 | (bytePackage[8] & 0xFF));
		tempLong = (tempLong << 8 | (bytePackage[9] & 0xFF));
		return tempLong;	
	}
	
	public int getVerify(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		return tempInt;
	}
	
	public int getVersion(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		return tempInt;
	}
	
}


public class ClientByteUnpacker {
	
	private long tempLong;
	
	public ClientByteUnpacker(){
		
	}

	
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
	
}

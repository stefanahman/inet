
public class ServerByteUnpacker {
	
	
	private byte tempByte;
	
	public ServerByteUnpacker(){
		
	}

	
	public long loginGetCardNumber(byte[] bytePackage){
		tempByte = 0;
		tempByte = (byte) (tempByte << 0 | bytePackage[3]);
		tempByte = (byte) (tempByte << 8 | bytePackage[4]);
		tempByte = (byte) (tempByte << 16 | bytePackage[5]);
		tempByte = (byte) (tempByte << 24 | bytePackage[6]);
		tempByte = (byte) (tempByte << 32 | bytePackage[7]);
		tempByte = (byte) (tempByte << 40 | bytePackage[8]);
		tempByte = (byte) (tempByte << 48 | bytePackage[9]);
		return tempByte;
		
	}
	
	public int loginGetPin(byte[] bytePackage){
		tempByte = (byte) (tempByte << 0 | bytePackage[1]);
		tempByte = (byte) (tempByte << 8 | bytePackage[2]);
		return tempByte;
		
	}
	
	public int getSecurityCode(byte[] bytePackage){
		tempByte = (byte) (tempByte << 0 | bytePackage[1]);
		return tempByte;
		
	}
	
	public long getAmount(byte[] bytePackage){
		tempByte = 0;
		tempByte = (byte) (tempByte << 0 | bytePackage[3]);
		tempByte = (byte) (tempByte << 8 | bytePackage[4]);
		tempByte = (byte) (tempByte << 16 | bytePackage[5]);
		tempByte = (byte) (tempByte << 24 | bytePackage[6]);
		tempByte = (byte) (tempByte << 32 | bytePackage[7]);
		tempByte = (byte) (tempByte << 40 | bytePackage[8]);
		tempByte = (byte) (tempByte << 48 | bytePackage[9]);
		return tempByte;
		
	}
	
	public long writeBalance(byte[] bytePackage){
		return tempByte;
		
	}
	
}

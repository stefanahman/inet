/**
 * The Class ClientByteUnpacker.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class ClientByteUnpacker {
	
	private long tempLong;
	private int tempInt;

	/**
	 * Gets the balance from byte package.
	 *
	 * @param bytePackage the byte package
	 * @return the balance
	 */
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
	
	/**
	 * Gets the verify field from byte package.
	 *
	 * @param bytePackage the byte package
	 * @return the verify
	 */
	public int getVerify(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		return tempInt;
	}
	
	/**
	 * Gets the version from byte package.
	 *
	 * @param bytePackage the byte package
	 * @return the version
	 */
	public int getVersion(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		return tempInt;
	}
	
}

/**
 * The Class ServerByteUnpacker.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class ServerByteUnpacker {
	
	private byte tempByte;
	private long tempLong;
	private int tempInt;
	
	/**
	 * Login get card number from byte package.
	 *
	 * @param bytePackage the byte package
	 * @return the long
	 */
	public long loginGetCardNumber(byte[] bytePackage){
		tempLong = 0;
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
	 * Login get pin from byte package.
	 *
	 * @param bytePackage the byte package
	 * @return tempInt with pincode
	 */
	public int loginGetPin(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		tempInt = (tempInt << 8 | (bytePackage[2] & 0xFF));
		return tempInt;
	}
	
	/**
	 * Gets the security code from byte package.
	 *
	 * @param bytePackage the byte package
	 * @return tempInt with the security code
	 */
	public int getSecurityCode(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		return tempInt;
	}
	
	/**
	 * Gets the amount from byte package.
	 *
	 * @param bytePackage the byte package
	 * @return tempInt with the amount
	 */
	public long getAmount(byte[] bytePackage){
		tempLong = 0; 
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
	 * Write balance, send  on get balance request.
	 *
	 * @param bytePackage the byte package
	 * @return tempInt with the balance
	 */
	/*public long writeBalance(byte[] bytePackage){
		tempByte = 0;
		return tempByte;
	}*/
	
	/**
	 * Gets the language.
	 *
	 * @param bytePackage the byte package
	 * @return tempInt with the language
	 */
	public int getLanguage(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		return tempInt;
	}
	
	/**
	 * Gets the version.
	 *
	 * @param bytePackage the byte package
	 * @return tempInt with the version
	 */
	public int getVersion(byte[] bytePackage){
		tempInt = 0;
		tempInt = (tempInt << 8 | (bytePackage[1] & 0xFF));
		return tempInt;
	}
	
}

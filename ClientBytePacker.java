/**
 * The Class ClientBytePacker.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class ClientBytePacker {

	/**
	 * Status codes for byte packages.
	 */
	final byte LOGIN = 0x00;
	final byte BALANCE = 0x01;
	final byte WITHDRAWAL = 0x02;
	final byte DEPOSIT = 0x03;
	final byte LANGUAGE = 0x04;
	final byte REQUEST = 0x05;
	final byte EXIT = 0x07;
	
	/**
	 * Header package, containing the size of the message package in bytes.
	 *
	 * @param size     the size
	 * @return byte package for header
	 */
	public byte[] header(byte size){
		byte[] bytePackage1 = new byte[1];
		bytePackage1[0] = size;
		return bytePackage1;
	}
	
	/**
	 * Login message.
	 *
	 * @param cardnumber the cardnumber
	 * @param pin the pin
	 * @return byte package for login
	 */
	public byte[] login(long cardnumber, int pin){
		byte[] bytePackage10 = new byte[10];
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
	
	/**
	 * Balance message packagee.
	 *
	 * @return byte package for balance
	 */
	public byte[] balance(){
		byte[] bytePackage1 = new byte[1];
		bytePackage1[0] = BALANCE;
		return bytePackage1;	
	}
	
	/**
	 * Withdrawal message package.
	 *
	 * @param securityCode the security code
	 * @param amount the amount
	 * @return byte package for withdrawal
	 */
	public byte[] withdrawal(int securityCode, long amount){
		byte[] bytePackage10 = new byte[10];
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
	
	/**
	 * Deposit message package.
	 *
	 * @param securityCode the security code
	 * @param amount the amount
	 * @return byte package for deposit
	 */
	public byte[] deposit(int securityCode, long amount){
		byte[] bytePackage10 = new byte[10];
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
	
	/**
	 * Lang request package.
	 *
	 * @param lang the lang
	 * @return byte package for lang request
	 */
	public byte[] setLang(int lang){
		byte[] bytePackage2 = new byte[2];
		bytePackage2[0] = LANGUAGE;
		bytePackage2[1] = (byte) lang;
		return bytePackage2;
	}
	 
	/**
	 * Exit message.
	 *
	 * @return byte package for exit
	 */
	public byte[] exit(){
		byte[] bytePackage1 = new byte[1];
		bytePackage1[0] = EXIT;
		return bytePackage1;
	}

	/**
	 * Request banner message.
	 *
	 * @param ver the ver
	 * @return byte package for banner
	 */
	public byte[] requestBanner(int ver) {
		byte[] bytePackage2 = new byte[2];
		bytePackage2[0] = REQUEST;
		bytePackage2[1] = (byte) ver;
		return bytePackage2;
	}
}

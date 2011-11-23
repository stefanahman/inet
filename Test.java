
public class Test {

	/**
	 * @param args
	 */
	static ClientBytePacker cbp = new ClientBytePacker();
	static ServerByteUnpacker sbu = new ServerByteUnpacker();
	private static long[] returnArray;
    private static void checkStatus(byte[] bytePackage){
		switch(bytePackage[0]){
		case 0x00:
			returnArray = new long[2];
			returnArray[0] = sbu.loginGetCardNumber(bytePackage);
			returnArray[1] = sbu.loginGetPin(bytePackage);
			System.out.println("Login:");
			System.out.println("cn: "+returnArray[0]);
			System.out.println("p: "+returnArray[1]);
			break;
		case 0x01: 
			returnArray[0] = sbu.writeBalance(bytePackage);
			System.out.println("Balance:");
			System.out.println(returnArray[0]);
			break;
		case 0x02: 
			returnArray[0] = sbu.getSecurityCode(bytePackage);
			returnArray[1] = sbu.getAmount(bytePackage);
			System.out.println("Withraw:");
			System.out.println(returnArray[0]);
			System.out.println(returnArray[1]);
			break;
		case 0x03: 
			returnArray[0] = sbu.getSecurityCode(bytePackage);
			returnArray[1] = sbu.getAmount(bytePackage);
			System.out.println("Deposit:");
			System.out.println(returnArray[0]);
			System.out.println(returnArray[1]);
			break;
		default:
			break;
		}
		
	}
    
	public static void main(String[] args) {
		
		
		long cardNumber = 1234123412341234L;
		int pin = 1234;
		
		checkStatus(cbp.login(cardNumber, pin));

	}

}

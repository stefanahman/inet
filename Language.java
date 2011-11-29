// Class for client to read langpacks from server

public class Language {

	public String menu = "What would you like to do?";
	public String menu1 = "(1)Balance"; 
	public String menu2 = "(2)Withdrawal"; 
	public String menu3 = "(3)Deposit"; 
	public String menu6 = "(6)Change language"; 
	public String menu7 = "(7)Exit"; 
	public String anyKeymessage = "Press any key to continue.."; 
	public String misingIP = "Missing argument ip-adress"; 
	public String unknownHost = "Unknown host: "; 
	public String noConnection = "Couldn't open connection to ";
	public String welcome = "Welcome to iBank "; 
	public String cardnumber = "cardnumber> "; 
	public String pin = "pin> "; 
	public String verificate = "Verificating ... ";
	public String valid = "Valid!"; 
	public String invalid = "Invalid!"; 
	public String receivingBalance = "Receiving balance ..."; 
	public String securityCode = "securitycode> ";
	public String amount = "amount>";
	public String successful = "Successful!";
	public String unsuccessful = "Unsuccessful!";
	public String goodBye = "Good bye!";
	public String account = "Account";

	public Language(){
	}

	public void updateLanguage(String pack[]){
		this.menu = pack[0];
		this.menu1 = pack[1];
		this.menu2 = pack[2];
		this.menu3 = pack[3];
		this.menu6 = pack[4];
		this.menu7 = pack[5];
		this.anyKeymessage = pack[6];
		this.misingIP = pack[7];
		this.unknownHost = pack[8];
		this.noConnection = pack[9];
		this.welcome = pack[10];
		this.cardnumber = pack[11];
		this.pin = pack[12];
		this.verificate = pack[13];
		this.valid = pack[14];
		this.invalid = pack[15];
		this.receivingBalance = pack[16];
		this.securityCode = pack[17];
		this.amount = pack[18];
		this.successful = pack[19];
		this.unsuccessful = pack[20];
		this.goodBye = pack[21];
		this.account = pack[22];

	}
}

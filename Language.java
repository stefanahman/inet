// Class for client to read langpacks from server

public class Language {

	public String menu;
	public String menu1; 
	public String menu2; 
	public String menu3; 
	public String menu6; 
	public String menu7; 
	public String anyKeymessage; 
	public String misingIP; 
	public String unknownHost; 
	public String noConnection;
	public String welcome; 
	public String cardnumber; 
	public String pin; 
	public String verificate;
	public String valid; 
	public String invalid; 
	public String receivingBalance; 
	public String securityCode;
	public String amount;
	public String successful;
	public String unsuccessful;
	public String goodBye;
	public String account;
	public String sv_se;
	public String en_us;

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
		this.sv_se = pack[23];
		this.en_us = pack[24];

	}
}

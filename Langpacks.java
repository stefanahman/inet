// Class for server to create langpacks
public class Langpacks {

	String[] swedish = new String[25];
	String[] english = new String[25];
	
	public Langpacks(){
	}

	public String[] getSwedish() {
		swedish[0] = "Vad vill du gora?";
		swedish[1] = "(1)Balans";
		swedish[2] = "(2)Uttag";
		swedish[3] = "(3)Insattning";
		swedish[4] = "(6)Andra sprak";
		swedish[5] = "(7)Exit";
		swedish[6] = "Tryck pa valfri tangent";
		swedish[7] = "Argument for ip saknas";
		swedish[8] = "Okand vard";
		swedish[9] = "Kunde inte koppla upp mot";
		swedish[10] = "Valkommen till iBank";
		swedish[11] = "kortnummer> ";
		swedish[12] = "pin> ";
		swedish[13] = "Verifierar ... ";
		swedish[14] = "Giltigt!";
		swedish[15] = "Ogiltigt!";
		swedish[16] = "Tar emot balans ...";
		swedish[17] = "sakerhetskod>";
		swedish[18] = "Belopp";
		swedish[19] = "Lyckades!";
		swedish[20] = "Misslyckades!";
		swedish[21] = "Ha en bra dag!";
		swedish[22] = "Konto";
		swedish[23] = "Svenska";
		swedish[24] = "Engelska (US)";
		return swedish;
	}
	public String[] getEnglish() {
		
		english[0] = "What would you like to do?";
		english[1] = "(1)Balance"; 
		english[2] = "(2)Withdrawal"; 
		english[3] = "(3)Deposit"; 
		english[4] = "(6)Change language"; 
		english[5] = "(7)Exit"; 
		english[6] = "Press any key to continue.."; 
		english[7] = "Missing argument ip-adress"; 
		english[8] = "Unknown host: "; 
		english[9] = "Couldn't open connection to ";
		english[10] = "Welcome to iBank "; 
		english[11] = "cardnumber> "; 
		english[12] = "pin> "; 
		english[13] = "Verificating ... ";
		english[14] = "Valid!"; 
		english[15] = "Invalid!"; 
		english[16] = "Receiving balance ..."; 
		english[17] = "securitycode> ";
		english[18] = "amount>";
		english[19] = "Successful!";
		english[20] = "Unsuccessful!";
		english[21] = "Have a good day!";
		english[22] = "Account";
		english[23] = "Swedish";
		english[24] = "English (US)";
		return english;
	}
}

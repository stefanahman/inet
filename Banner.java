import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Banner {

	int verOfIni;
	Properties p = new Properties();

	public Banner() {
		try {
			p.load(new FileInputStream("bank.ini"));
			this.verOfIni = Integer.valueOf(p.getProperty("Ver"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int bannerNeedsUpdate(int ver){
		try {
			p.load(new FileInputStream("bank.ini"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.verOfIni = Integer.valueOf(p.getProperty("Ver"));
		return this.verOfIni;
	}
	
	public String getLatest(int lang) {
		try {
			p.load(new FileInputStream("bank.ini"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(lang == 1)
			return p.getProperty("HEADER_sv_se");
		if(lang == 2)
			return p.getProperty("HEADER_en_us");
		else
			return null;
	}
	
	
}

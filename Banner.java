import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The Class Banner.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class Banner {

	int verOfIni;
	Properties p = new Properties();

	/**
	 * Instantiates a new banner.
	 */
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
	
	/**
	 * Banner needs update, returns version of ini-file.
	 *
	 * @param ver the ver
	 * @return the int
	 */
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
	
	/**
	 * Gets the banner from ini-file.
	 *
	 * @param lang the lang
	 * @return the latest
	 */
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

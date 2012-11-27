import javax.swing.Icon;
import javax.swing.ImageIcon;

/** Kaikki ImageIcon-kuvat päheille miinoille ja lipuille yms. */
public enum NappiKuvat {
	MIINA("media/pommi.gif"), HUTI("media/huti.gif"), LIPPU("media/lippu.gif");

	/** Luotu icon on tallessa tässä attribuutissa. */
	public final Icon icon;

	NappiKuvat(String tiedostopolku) {
		java.net.URL imgURL = getClass().getResource(tiedostopolku);
		if (imgURL != null) {
			this.icon = new ImageIcon(imgURL, this.name());
		}
		else {
			System.err.println("Ei osattu ladata kuvaa " + tiedostopolku);
			this.icon = new PuuttuvaKuva();
		}
	}
}

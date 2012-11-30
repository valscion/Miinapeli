import java.awt.MediaTracker;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/** Kaikki ImageIcon-kuvat päheille miinoille ja lipuille yms. */
public enum NappiKuvat {
	MIINA("media/pommi.gif"), HUTI("media/huti.gif"), LIPPU("media/lippu.gif");

	/** Luotu icon on tallessa tässä attribuutissa. */
	public final Icon icon;

	NappiKuvat(String tiedostopolku) {
		ImageIcon kuvake = new ImageIcon(tiedostopolku, this.name());
		if (kuvake.getImageLoadStatus() == MediaTracker.ERRORED) {
			// Temppikuva jos lataus failaa
			this.icon = new PuuttuvaKuva();
		}
		else {
			this.icon = kuvake;
		}
	}
}

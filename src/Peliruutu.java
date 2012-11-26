import java.awt.Point;

/**
 * Tämän luokan oliot kuvastavat yksittäistä peliruutua.
 */
public class Peliruutu {

	/** Onko ruutu liputettu */
	private boolean onLiputettu;

	/** Onko ruutu avattu */
	private boolean onAuki;

	/** Onko ruudussa miinaa vai ei */
	private boolean onMiina;

	/** Ruudun sijainti peliruudukossa. */
	private Point sijainti;

	/**
	 * Luo uuden ruudun haluttuun sijaintiin, jossa on tai ei ole miinaa.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @param onkoMiina
	 *            onko ruudussa miinaa vai ei
	 */
	public Peliruutu(int x, int y, boolean onkoMiina) {
		this.onMiina = onkoMiina;
		this.onLiputettu = false;
		this.onAuki = false;
		this.sijainti = new Point(x, y);
	}

	/** @return onko ruutu avattu */
	public boolean onAuki() {
		return this.onAuki;
	}

	/** @return onko ruutu liputettu */
	public boolean onLiputettu() {
		return this.onLiputettu;
	}

	/** @return onko ruudussa miina */
	public boolean onMiina() {
		return this.onMiina;
	}

	/** @return ruudun tuntema sijainti */
	public Point annaSijainti() {
		return this.sijainti;
	}

	/**
	 * Asettaa ruudun liputetuksi tai poistaa lipun. Lipun asettaminen voi myös
	 * epäonnistua, esimerkiksi silloin jos ruutu on avattu tai ruudussa oli jo
	 * lippu ja siihen yritettiin asettaa sitä uudelleen tai jos ruudussa ei
	 * ollut lippua ja sitä yritettiin poistaa.
	 * 
	 * @param onLiputettu
	 *            asetetaanko vai poistetaanko lippu
	 * 
	 * @return onnistuiko lipun tilan asettaminen
	 */
	public boolean asetaLiputetuksi(boolean onLiputettu) {
		if (this.onAuki) {
			return false;
		}
		if (this.onLiputettu == onLiputettu) {
			return false;
		}
		this.onLiputettu = onLiputettu;
		return true;
	}

	/** Avaa ruudun eli asettaa sen avatuksi. */
	public void avaa() {
		this.onAuki = true;
	}

}

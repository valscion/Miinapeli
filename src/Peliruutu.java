/**
 * Tämän luokan oliot kuvastavat yksittäistä peliruutua.
 */
public class Peliruutu {

	/** Onko ruutu liputettu */
	private boolean onLiputettu;

	/** Onko ruutu avattu */
	private boolean onAvattu;

	/** Onko ruudussa miinaa vai ei */
	private boolean onMiina;

	/**
	 * Luo uuden ruudun, jossa on tai ei ole miinaa.
	 * 
	 * @param onkoMiina
	 *            onko ruudussa miinaa vai ei
	 */
	public Peliruutu(boolean onkoMiina) {
		this.onMiina = onkoMiina;
		this.onLiputettu = false;
		this.onAvattu = false;
	}

	/** @return onko ruutu avattu */
	public boolean onAvattu() {
		return this.onAvattu;
	}

	/** @return onko ruutu liputettu */
	public boolean onLiputettu() {
		return this.onLiputettu;
	}

	/** @return onko ruudussa miina */
	public boolean onMiina() {
		return this.onMiina;
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
		if (this.onAvattu) {
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
		this.onAvattu = true;
	}

}

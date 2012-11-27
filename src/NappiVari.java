import java.awt.Color;

/** Kaikki eri tilanteiden nappuloiden värit. */
public enum NappiVari {
	AVAAMATON(Color.getHSBColor(0f, 0f, 0.8f)), // Alkutilanne
	HIIRI_POHJASSA(Color.getHSBColor(0.2f, 0.1f, 0.7f)), // Hiirtä painetaan
	HIIRI_PAALLA(Color.getHSBColor(0f, 0f, 0.85f)), // Hiiri päällä
	AVATTU(Color.getHSBColor(0f, 0f, 0.85f)), // Palikka auki
	RAJAHTANYT(Color.getHSBColor(0f, 0.8f, 0.8f)); // Poksahtanut miina

	private Color vari;

	NappiVari(Color vari) {
		this.vari = vari;
	}

	public Color annaVari() {
		return this.vari;
	}
}
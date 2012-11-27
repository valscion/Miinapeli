import java.awt.Color;

/** Kaikki eri tilanteiden nappuloiden ja tekstien värit. */
public enum NappiVari {
	AVAAMATON(Color.getHSBColor(0f, 0f, 0.8f)), // Alkutilanne
	HIIRI_POHJASSA(Color.getHSBColor(0.2f, 0.1f, 0.7f)), // Hiirtä painetaan
	HIIRI_PAALLA(Color.getHSBColor(0f, 0f, 0.85f)), // Hiiri päällä
	AVATTU(Color.getHSBColor(0f, 0f, 0.85f)), // Palikka auki
	AVATTU_REUNA(Color.getHSBColor(0f, 0f, 0.7f)), // Avatun nappulan reunaväri
	RAJAHTANYT(Color.getHSBColor(0f, 0.8f, 0.8f)), // Poksahtanut miina
	VIHJE1(Color.getHSBColor(0.67f, 0.8f, 0.8f)), // Vihjeväri 1
	VIHJE2(Color.getHSBColor(0.33f, 0.8f, 0.8f)), // Vihjeväri 2
	VIHJE3(Color.getHSBColor(0.00f, 0.8f, 0.8f)), // Vihjeväri 3
	VIHJE4(Color.getHSBColor(0.83f, 0.8f, 0.8f)), // Vihjeväri 4
	VIHJE5(Color.getHSBColor(0.50f, 0.8f, 0.8f)), // Vihjeväri 5
	VIHJE6(Color.getHSBColor(0.16f, 0.8f, 0.8f)), // Vihjeväri 6
	VIHJE7(Color.getHSBColor(0.585f, 0.8f, 0.8f)), // Vihjeväri 7
	VIHJE8(Color.getHSBColor(0.085f, 0.8f, 0.8f)), // Vihjeväri 8
	VIHJEVIRHE(Color.getHSBColor(0.915f, 0.8f, 0.8f)); // Virheellisen vihjeväri

	public final Color color;

	NappiVari(Color color) {
		this.color = color;
	}
}
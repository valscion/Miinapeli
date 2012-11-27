import java.awt.*;

import javax.swing.*;

// Ei meit kinosta.
@SuppressWarnings("serial")
/**
 * Miinakentästä huolehtiva paneeliluokka
 */
public class Pelipaneeli extends JPanel {

	/**
	 * Kaikki eri miinanappulat tallennetaan tähän 2D-taulukkoon. Ensimmäinen
	 * ulottuvuus on x-koordinaatti, toinen y-koordinaatti.
	 */
	private Ruutunappi[][] miinat;

	/** Luo uuden pelipaneelin ja asettaa sinne peliruudukon. */
	public Pelipaneeli(Peliruudukko ruudukko) {
		// Sisältö menee taulukkopussileiskaan.
		this.setLayout(new GridBagLayout());

		// Luodaan sopivan kokoinen taulukko aivan aluksi.
		int leveys = ruudukko.annaLeveys();
		int korkeus = ruudukko.annaKorkeus();
		this.miinat = new Ruutunappi[leveys][korkeus];

		// Luodaan uusi GridBagConstraints-olio, jonka gridx- ja
		// gridy-attribuutteja asetellaan myöhemmin tarvittaessa.
		// Oletusarvot kelpaavat meille, joten niistä ei huolestuta.
		GridBagConstraints c = new GridBagConstraints();

		// Luodaan nappulat sisäkkäisillä for-silmukoilla.
		for (int x = 0; x < leveys; x++) {
			c.gridx = x * 25;
			for (int y = 0; y < korkeus; y++) {
				c.gridy = y * 25;

				// Luodaan se nappi.
				Ruutunappi nappi = new Ruutunappi(x, y, ruudukko);
				
				// Tallennetaan se
				this.miinat[x][y] = nappi;
				this.add(nappi, c);
			}
		}
	}
}

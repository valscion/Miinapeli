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
	private JButton[][] miinat;

	/** Luo uuden pelipaneelin ja asettaa sinne miinakentän. */
	public Pelipaneeli(int leveys, int korkeus) {
		// Sisältö menee taulukkopussileiskaan.
		this.setLayout(new GridBagLayout());

		// Luodaan sopivan kokoinen taulukko aivan aluksi.
		this.miinat = new JButton[leveys][korkeus];

		// Luodaan uusi GridBagConstraints-olio, jonka gridx- ja
		// gridy-attribuutteja asetellaan myöhemmin tarvittaessa.
		// Oletusarvot kelpaavat meille, joten niistä ei huolestuta.
		GridBagConstraints c = new GridBagConstraints();

		// Luodaan nappulat sisäkkäisillä for-silmukoilla.
		for (int x = 0; x < leveys; x++) {
			c.gridx = x * 25;
			for (int y = 0; y < korkeus; y++) {
				c.gridy = y * 25;

				// Luodaan se nappi ja tallennetaan se.
				JButton miinaNappi = new JButton();
				miinaNappi.setPreferredSize(new Dimension(25, 25));
				this.miinat[x][y] = miinaNappi;
				this.add(miinaNappi, c);
			}
		}
	}

}

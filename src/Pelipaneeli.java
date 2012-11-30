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

	/**
	 * Pelipaneelin tieto siitä, mikä ruudukko hoitaa pelin logiikkaa.
	 */
	private Peliruudukko peliruudukko;

	/** Luo uuden pelipaneelin ja asettaa sinne peliruudukon. */
	public Pelipaneeli(Peliruudukko ruudukko) {
		// Sisältö menee taulukkopussileiskaan.
		this.setLayout(new GridBagLayout());

		// Luodaan sopivan kokoinen taulukko aivan aluksi.
		int leveys = ruudukko.annaLeveys();
		int korkeus = ruudukko.annaKorkeus();
		this.miinat = new Ruutunappi[leveys][korkeus];

		this.peliruudukko = ruudukko;

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
				Ruutunappi nappi = new Ruutunappi(x, y, ruudukko, this);

				// Tallennetaan se
				this.miinat[x][y] = nappi;
				this.add(nappi, c);
			}
		}
	}

	/**
	 * Hoitaa yhden Ruutunapin avaamisen ja kaikkien muiden varmojen naapurien
	 * avaamisen.
	 */
	public void avaa(int x, int y, int vihjenumero) {
		this.avaaYksittainen(x, y, vihjenumero);

		if (vihjenumero == 0) {
			this.avaaNaapurit(x, y);
		}
	}

	/**
	 * Apumetodi, joka hoitaa vain yksittäisen napin avaamisen mutta ei
	 * mahdollisten naapurien availua.
	 */
	private void avaaYksittainen(int x, int y, int vihjenumero) {
		Ruutunappi avattu = this.miinat[x][y];
		avattu.naytaVihje(vihjenumero);
	}

	/**
	 * Hoitaa yhden Ruutunapin kaikkien naapurien avaamisen.
	 */
	public void avaaNaapurit(int x, int y) {
		java.util.List<Ruutunappi> naapurit = this.annaNaapurit(x, y);

		// Pidetään tallessa kaikki uniikit naapurit, jotta ei lähdettäisi
		// availemaan yksittäistä nappia useampaan kertaan.
		java.util.Set<Ruutunappi> uniikit = new java.util.HashSet<Ruutunappi>();

		for (int i = 0; i < naapurit.size(); i++) {
			Ruutunappi tmpNaapuri = naapurit.get(i);
			if (!uniikit.contains(tmpNaapuri)) {
				uniikit.add(tmpNaapuri);

				// Avataan se löydetty nappi
				int tmpX = tmpNaapuri.annaX();
				int tmpY = tmpNaapuri.annaY();
				int vihjenumero = this.peliruudukko.annaVihjenumero(tmpX, tmpY);
				this.avaaYksittainen(tmpX, tmpY, vihjenumero);
				if (vihjenumero == 0) {
					// Täytyy avata tämänkin napin naapurit, joten lisätään
					// naapurit-listaan kaikki tämän napin naapurit.
					naapurit.addAll(this.annaNaapurit(tmpX, tmpY));
				}
			}
		}
	}

	/**
	 * Palauttaa listan kaikista annetuissa koordinaateissa sijaitsevan napin
	 * naapureista (enintään kahdeksan kappaletta). Käytetään vain tämän luokan
	 * sisällä apumetodina.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 */
	private java.util.List<Ruutunappi> annaNaapurit(int x, int y) {
		java.util.List<Peliruutu> naapuriRuudut = this.peliruudukko
				.annaNaapurit(x, y);
		java.util.List<Ruutunappi> naapuriNapit =
				new java.util.ArrayList<Ruutunappi>(8);

		for (Peliruutu ruutu : naapuriRuudut) {
			Point p = ruutu.annaSijainti();
			Ruutunappi nappi = this.miinat[p.x][p.y];
			naapuriNapit.add(nappi);
		}
		return naapuriNapit;
	}
	
	/** Hoitaa kaikkien loppujen ruutujen avaamisen. */
	public void avaaKaikki() {
		for (int x = 0; x < this.peliruudukko.annaLeveys(); x++) {
			for (int y = 0; y < this.peliruudukko.annaKorkeus(); y++) {
				Ruutunappi nappi = miinat[x][y];
				if (!this.peliruudukko.onAuki(x, y)) {
					int avausArvo = this.peliruudukko.avaa(x, y);
					if (avausArvo >= 0) {
						// Tyhjä, avaamaton paikka.
						nappi.naytaVihje(avausArvo);
					}
					else if (avausArvo == Peliruudukko.OLI_MIINA) {
						nappi.naytaMiina();
					}
					// TODO: Lipullisen paikan näyttö.
				}
			}
		}
	}
}

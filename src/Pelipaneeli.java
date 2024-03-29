import java.awt.*;

import javax.swing.*;

/**
 * Miinakentästä huolehtiva paneeliluokka
 */
public class Pelipaneeli extends JPanel {

	/** Swing-komponentit pitää voida serialisoida, joten tämä. */
	private static final long serialVersionUID = -169273474225400220L;

	/**
	 * Kaikki eri ruutunapit tallennetaan tähän 2D-taulukkoon. Ensimmäinen
	 * ulottuvuus on y-koordinaatti, toinen x-koordinaatti.
	 */
	private Ruutunappi[][] napit;

	/**
	 * Pelipaneelin tieto siitä, mikä ruudukko hoitaa pelin logiikkaa.
	 */
	private Peliruudukko peliruudukko;

	/** Tieto siitä, mihin peliin pelipaneeli kuuluu. */
	private Miinapeli peli;

	/** Onko ensimmäinen nappi jo avattu vai ei, eli onko peli aloitettu. */
	private boolean onPeliAloitettu = false;

	/** Luo uuden pelipaneelin ja asettaa sinne peliruudukon. */
	public Pelipaneeli(Peliruudukko ruudukko, Miinapeli peli) {
		// Sisältö menee taulukkopussileiskaan.
		this.setLayout(new GridBagLayout());

		// Luodaan sopivan kokoinen taulukko aivan aluksi.
		int leveys = ruudukko.annaLeveys();
		int korkeus = ruudukko.annaKorkeus();
		this.napit = new Ruutunappi[korkeus][leveys];

		// Tiedot talteen, tiedot talteen, paaarlevuu...
		this.peliruudukko = ruudukko;
		this.peli = peli;

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
				this.napit[y][x] = nappi;
				this.add(nappi, c);
			}
		}
	}

	/**
	 * Päivittää miinapelin tilasta kertovan tekstin nykyistä pelitilannetta
	 * vastaavaksi.
	 */
	private void paivitaPelitilanneTeksti() {
		String teksti = String.format(
				"Miinoja jäljellä %d",
				this.peliruudukko.annaMiinojenLkm()
						- this.peliruudukko.annaLiputettujenRuutujenLkm());
		this.peli.muutaTilaTeksti(teksti);
	}

	/** Hoitaa yhden Ruutunapin liputtamisen ja siihen liittyvän logiikan. */
	public void liputa(int x, int y) {
		if (!peliruudukko.miinojenPaikatArvottu()) {
			String viesti = "Avaa ensin joku ruutu ennen liputtamista.";
			this.peli.muutaTilaTeksti(viesti);
		}
		else {
			Ruutunappi nappi = napit[y][x];
			if (this.peliruudukko.onLiputettu(x, y)) {
				if (this.peliruudukko.asetaLippu(x, y, false)) {
					nappi.naytaLippu(false);
				}
			}
			else {
				if (this.peliruudukko.asetaLippu(x, y, true)) {
					nappi.naytaLippu(true);
				}
			}

			this.paivitaPelitilanneTeksti();
		}
	}

	/**
	 * Hoitaa yhden Ruutunapin avaamisen ja siihen liittyvän logiikan.
	 */
	public void avaa(int x, int y) {
		if (!this.onPeliAloitettu) {
			// Aika.... alkaaa.... NYT!
			this.peli.kaynnistaAjastin();
			this.onPeliAloitettu = true;
		}

		int avausArvo = this.peliruudukko.avaa(x, y);
		this.paivitaPelitilanneTeksti();

		if (avausArvo == Peliruudukko.OLI_MIINA) {
			Ruutunappi nappi = this.napit[y][x];
			nappi.naytaRajahtanytMiina();
			this.peli.peliPaattyi(false);
		}
		else if (avausArvo >= 0) {
			// Avaa ruutunappi ja hoida logiikka.
			this.avaaYksittainen(x, y, avausArvo);
			if (avausArvo == 0) {
				this.avaaNaapurit(x, y);
			}
			if (this.peliruudukko.peliVoitettu()) {
				// Voitto kottiin!
				this.peli.peliPaattyi(true);
			}
		}
	}

	/**
	 * Apumetodi, joka hoitaa vain yksittäisen napin avaamisen mutta ei
	 * mahdollisten naapurien availua.
	 */
	private void avaaYksittainen(int x, int y, int vihjenumero) {
		Ruutunappi avattu = this.napit[y][x];
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
				int avausArvo = this.peliruudukko.avaa(tmpX, tmpY);
				if (avausArvo == Peliruudukko.OLI_MIINA) {
					// Luultavasti käynyt niin, että oli liputettu väärä paikka
					// ja nyt sen takia automaattiavaus epäonnistui.
					// Sekin on GAME OVER! BUAHAHAHAA!
					tmpNaapuri.naytaRajahtanytMiina();
					this.peli.peliPaattyi(false);
					return;
				}
				else if (avausArvo >= 0) {
					this.avaaYksittainen(tmpX, tmpY, avausArvo);
					if (avausArvo == 0) {
						// Täytyy avata tämänkin napin naapurit, joten lisätään
						// naapurit-listaan kaikki tämän napin naapurit.
						naapurit.addAll(this.annaNaapurit(tmpX, tmpY));
					}
					// Swingi on jännä, tämän avulla toimii smoothisti
					// nappuloiden avaus.
					this.repaint();
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
		java.util.List<Peliruutu> naapuriRuudut =
				this.peliruudukko.annaNaapurit(x, y);
		java.util.List<Ruutunappi> naapuriNapit =
				new java.util.ArrayList<Ruutunappi>(8);

		for (Peliruutu ruutu : naapuriRuudut) {
			Point p = ruutu.annaSijainti();
			Ruutunappi nappi = this.napit[p.y][p.x];
			naapuriNapit.add(nappi);
		}
		return naapuriNapit;
	}

	/**
	 * Hoitaa kaikkien loppujen ruutujen avaamisen.
	 * 
	 * @param voitettu
	 *            mikäli <code>true</code>, nappien ulkoasu on voitetun pelin
	 *            mukainen, muutoin hävityn pelin mukainen.
	 */
	public void avaaKaikki(boolean voitettu) {
		for (int x = 0; x < this.peliruudukko.annaLeveys(); x++) {
			for (int y = 0; y < this.peliruudukko.annaKorkeus(); y++) {
				Ruutunappi nappi = napit[y][x];
				// Poistetaan heti hiirikuuntelija
				nappi.poistaKuuntelijat();

				int avausArvo = this.peliruudukko.avaa(x, y);
				if (avausArvo >= 0) {
					// Tyhjiä, avaamattomia paikkoja ei koskaan näytetä.
					continue;
				}
				else if (avausArvo == Peliruudukko.OLI_MIINA) {
					// Voitetun pelin miinat näytetään oikein liputettuna,
					// hävityn pelin miinat näytetään ihan vain miinoina.
					if (voitettu) {
						nappi.naytaLippu(true);
					}
					else {
						nappi.naytaMiina();
					}
				}
				else if (avausArvo == Peliruudukko.OLI_LIPUTETTU) {
					if (!this.peliruudukko.onMiina(x, y)) {
						// Trollol, failasit!
						nappi.naytaVirheellinenLiputus();
					}
				}
				// Swingi on jännä, tämän avulla toimii smoothisti nappuloiden
				// avaus.
				this.repaint();
			}
		}
	}

	/**
	 * Avaa kaikki annetun ruudun ympäriltä olevat, lippujen määräämät varmat
	 * ruudut.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 */
	public void avaaVarmat(int x, int y) {
		java.util.List<Ruutunappi> naapurit = this.annaNaapurit(x, y);
		// Käydään kaikki naapurit läpi ja avataan kaikki ruudut, joiden
		// ympäriltä löytyvien liputettujen ruutujen määrä on vähintään yhtä
		// suuri kuin ruudun vihjenumero.
		int vihjenumero = this.peliruudukko.annaVihjenumero(x, y);
		int liputettuja = this.peliruudukko.annaLiputettujenNaapurienLkm(x, y);
		if (liputettuja >= vihjenumero) {
			for (Ruutunappi naapuri : naapurit) {
				this.avaa(naapuri.annaX(), naapuri.annaY());
			}
		}
	}
}

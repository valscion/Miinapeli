import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Miinapelin käytännön logiikasta vastaava luokka.
 */
public class Peliruudukko {

	/**
	 * Vakioarvo, joka kuvaa sitä, että avattava ruutu oli jo auki. Arvo on
	 * merkiltään negatiivinen.
	 */
	public static final int OLI_JO_AUKI = -1;

	/**
	 * Vakioarvo, joka kuvaa sitä, että ruutu, jota yritettiin avata, oli
	 * merkitty lipulla eikä avaaminen näin ollen onnistunut. Arvo on merkiltään
	 * negatiivinen.
	 */
	public static final int OLI_LIPUTETTU = -2;

	/**
	 * Vakioarvo, joka kuvaa sitä, että avattu ruutu oli miina (ja peli päättyi
	 * ikävästi). Arvo on merkiltään negatiivinen.
	 */
	public static final int OLI_MIINA = -3;

	/**
	 * Ruudukko, johon miinat tallennetaan. Ensimmäinen ulottuvuus sisältää
	 * x-koordinaatin, toinen y-koordinaatin.
	 */
	private Peliruutu[][] ruudukko;

	/**
	 * Luo uuden peliruudukon, jonka leveys ja korkeus on annettu parametreina.
	 * Ruudukon ruuduista valitaan satunnaisesti miinoiksi niin monta kuin
	 * parametri miinoja määrää, ei kuitenkaan enempää kuin puolet kaikista
	 * ruuduista. Jos leveydeksi, korkeudeksi tai miinamääräksi on annettu nolla
	 * tai negatiivinen luku, sen arvoksi asetetaan 1.
	 * 
	 * @param leveys
	 *            peliruudukon leveys eli ruutujen määrä vaakasuunnassa
	 * @param korkeus
	 *            peliruudukon korkeus eli ruutujen määrä pystysuunnassa
	 * @param miinoja
	 *            peliruudukkoon sijoitettavien miinojen määrä
	 */
	public Peliruudukko(int leveys, int korkeus, int miinoja) {
		this.ruudukko = new Peliruutu[leveys][korkeus];
		this.arvoMiinojenPaikat(miinoja);
	}

	/**
	 * Hoitaa miinojen paikoilleen arpomisen.
	 * 
	 * @param miinoja
	 *            kuinka monta miinaa peliin asetetaan.
	 */
	private void arvoMiinojenPaikat(int miinoja) {
		// Ideana on, että luodaan luodaan aluksi 1D-esitys 2D-taulukosta,
		// jossa on tieto että mitkä ruuduista ovat miinoja ja mitkä ei. Kun
		// tähän taulukkoon laitetaan alkuun haluttu määrä miinallisia ja sitten
		// sekoitetaan, saadaan miinojen paikat arvottua kätevästi.
		int koko = this.annaKorkeus() * this.annaLeveys();

		// HUOM! Täytyy käyttää booleanin oliovastinetta, sillä muuten taulukkoa
		// ei saada sekoitettua!
		Boolean[] miinalliset = new Boolean[koko];

		for (int i = 0; i < miinoja; i++) {
			miinalliset[i] = true;
		}
		for (int i = miinoja; i < koko; i++) {
			miinalliset[i] = false;
		}
		Collections.shuffle(Arrays.asList(miinalliset));

		for (int x = 0; x < this.annaLeveys(); x++) {
			for (int y = 0; y < this.annaKorkeus(); y++) {
				boolean miina = miinalliset[(y * this.annaLeveys()) + x];
				ruudukko[x][y] = new Peliruutu(x, y, miina);
			}
		}
	}

	/**
	 * @return ruudukon korkeus
	 */
	public int annaKorkeus() {
		if (this.ruudukko.length > 0) {
			return this.ruudukko[0].length;
		}
		else {
			return 0;
		}
	}

	/**
	 * @return ruudukon leveys.
	 */
	public int annaLeveys() {
		return this.ruudukko.length;
	}

	/**
	 * Palauttaa kaikki annettujen koordinaattien ympäriltä löytyvät ruudut.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @return lista kaikista naapureista
	 * @throws ArrayIndexOutOfBoundsException
	 *             jos koordinaatit olivat ruudukon rajojen ulkopuolella
	 */
	public List<Peliruutu> annaNaapurit(int x, int y) {
		Peliruutu ruutu = this.ruudukko[x][y];
		return this.annaNaapurit(ruutu);
	}

	/**
	 * Palauttaa kaikki annetun ruudun ympäriltä löytyvät ruudut listassa.
	 * Listan toteutustapa on ArrayList.
	 * 
	 * @param ruutu
	 *            ruutu, jonka naapurit haetaan
	 * @return lista kaikista naapureista
	 */
	public List<Peliruutu> annaNaapurit(Peliruutu ruutu) {
		List<Peliruutu> naapurit = new ArrayList<Peliruutu>(8);

		for (int xSiirtyma = -1; xSiirtyma <= 1; xSiirtyma++) {
			for (int ySiirtyma = -1; ySiirtyma <= 1; ySiirtyma++) {
				if (xSiirtyma == 0 && ySiirtyma == 0) {
					// Ei lasketa ruutua itseään mukaan laskuihin.
					continue;
				}
				Peliruutu naapuri = annaSuhteellinenNaapuri(ruutu, xSiirtyma,
						ySiirtyma);
				if (naapuri != null) {
					naapurit.add(naapuri);
				}
			}
		}
		return naapurit;
	}

	/**
	 * Palauttaa ruudun naapurin suhteellisten koordinaattien avulla. Mikäli
	 * ruutua annetuista suhteellisista koordinaateista ei löydy (eli jos haku
	 * menisi taulukon rajojen yli), palautetaan <code>null</code>. Esimerkiksi
	 * jos haluaisit ruudun vasemmalla ylhäällä olevan naapurin, kutsuisit tätä
	 * metodia koordinaateilla <code>(-1, -1)</code>.
	 * 
	 * @param ruutu
	 *            <code>Peliruutu</code>, jonka suhteen naapuria haetaan.
	 * @param x
	 *            suhteellinen x-koordinaatti. Negatiiviset arvot merkitsevät
	 *            hakua vasemmaltapäin, positiiviset oikealta.
	 * @param y
	 *            suhteellinen y-koordinaatti. Negatiiviset arvot merkitsevät
	 *            hakua ylhäältäpäin, positiiviset alhaalta.
	 * @return annettujen koordinaattien määrittelemä Peliruutu tai
	 *         <code>null</code>, jos ruutua ei ole (olisi luettu taulukon yli).
	 */
	public Peliruutu annaSuhteellinenNaapuri(Peliruutu ruutu, int x, int y) {
		Point alkuSijainti = ruutu.annaSijainti();

		int oikeaX = x + alkuSijainti.x;
		int oikeaY = y + alkuSijainti.y;

		if (oikeaX < this.annaLeveys() && oikeaY < this.annaKorkeus()
				&& oikeaX >= 0 && oikeaY >= 0) {
			return this.ruudukko[oikeaX][oikeaY];
		}
		else {
			return null;
		}
	}

	/**
	 * Palauttaa annettuja koordinaatteja vastaavan ruudun vihjenumeron eli
	 * ruutua ympäröivien miinaruutujen määrän. Myös miinaruuduilla on
	 * vihjenumero, vaikka käytännössä sitä ei koskaan tarvita mihinkään.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @return ruudun ympärillä olevien miinojen määrä – siis jokin luku
	 *         nollasta kahdeksaan
	 * @throws ArrayIndexOutOfBoundsException
	 *             jos koordinaatit olivat ruudukon rajojen ulkopuolella
	 */
	public int annaVihjenumero(int x, int y) {
		Peliruutu ruutu = this.ruudukko[x][y];
		List<Peliruutu> naapurit = this.annaNaapurit(ruutu);
		int vihjenumero = 0;

		for (Peliruutu naapuri : naapurit) {
			if (naapuri.onMiina()) {
				vihjenumero++;
			}
		}
		return vihjenumero;
	}

	/**
	 * Asettaa annetuissa koordinaateissa olevalle, vielä avaamattomalle
	 * ruudulle lipun tai ottaa sen pois parametrin lippu arvosta riippuen –
	 * true tarkoittaa liputusta, false liputuksen poistoa.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @param lippu
	 *            <code>true</code>, jos ruutuun halutaan asettaa lippu,
	 *            <code>false</code>, jos lippu halutaan ottaa pois
	 * @return <code>true</code>, jos liputuksen muuttaminen onnistui (eli ruutu
	 *         ei vielä ollut avattu ja lisäksi liputusta muutettiin oikeaan
	 *         suuntaan), muutoin <code>false</code>
	 * @throws ArrayIndexOutOfBoundsException
	 *             jos annetut koordinaatit olivat ruudukon rajojen
	 *             ulkopuolella.
	 */
	public boolean asetaLippu(int x, int y, boolean lippu) {
		Peliruutu ruutu = this.ruudukko[x][y];
		return ruutu.asetaLiputetuksi(lippu);
	}

	/**
	 * Kaivaa annetuissa koordinaateissa olevan ruudun auki, mikäli se ei ollut
	 * merkitty lipulla tai auki jo entuudestaan. Metodin palauttama lukuarvo
	 * kertoo tarkemmin siitä, mitä avatessa tapahtui. Jos ruutu oli liputettu,
	 * sitä ei avata vaan palautetaan tässä luokassa määritelty (negatiivinen)
	 * vakioarvo <code>OLI_LIPUTETTU</code>. Samoin jos ruutu oli ennestään
	 * auki, sitä ei avata uudestaan vaan palautetaan (negatiivinen) vakio
	 * <code>OLI_JO_AUKI</code>. Jos ruudun avaaminen onnistui ja siinä ei ollut
	 * miinaa, palautetaan ruudun vihjenumero eli jokin luku nollasta
	 * kahdeksaan, riippuen siitä, montako miinaa ruudun naapureiden joukossa
	 * on. Jos avattu ruutu taas oli miina, palautetaan tämän merkiksi (niin
	 * ikään negatiivinen) vakioarvo <code>OLI_MIINA</code>. Metodin kutsuja
	 * reagoikoon paluuarvoon parhaaksi katsomallaan tavalla.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @return nolla tai positiivinen vihjenumero, jos avattu ruutu ei
	 *         sisältänyt miinaa, tai jonkin tässä luokassa määritellyistä
	 *         negatiivisista vakioarvoista, jos ruudussa oli miina tai sitä ei
	 *         voinut syystä tai toisesta avata
	 * @throws ArrayIndexOutOfBoundsException
	 *             jos koordinaatit olivat ruudukon rajojen ulkopuolella
	 */
	public int avaa(int x, int y) {
		Peliruutu ruutu = this.ruudukko[x][y];
		if (ruutu.onAuki()) {
			return OLI_JO_AUKI;
		}
		else if (ruutu.onLiputettu()) {
			return OLI_LIPUTETTU;
		}
		else if (ruutu.onMiina()) {
			return OLI_MIINA;
		}

		ruutu.avaa();

		int vihjenumero = this.annaVihjenumero(x, y);
		return vihjenumero;
	}

	/**
	 * Kertoo, onko annetuissa koordinaateissa oleva ruutu kaivettu auki.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @return <code>true</code>, jos kyseinen ruutu on kaivettu auki, muuten
	 *         <code>false</code>
	 * @throws ArrayIndexOutOfBoundsException
	 *             jos koordinaatit olivat ruudukon rajojen ulkopuolella
	 */
	public boolean onAuki(int x, int y) {
		Peliruutu ruutu = this.ruudukko[x][y];
		return ruutu.onAuki();
	}

	/**
	 * Kertoo, onko annetuissa koordinaateissa oleva ruutu merkitty lipulla.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @throws ArrayIndexOutOfBoundsException
	 *             jos koordinaatit olivat ruudukon rajojen ulkopuolella
	 */
	public boolean onLiputettu(int x, int y) {
		Peliruutu ruutu = this.ruudukko[x][y];
		return ruutu.onLiputettu();
	}

	/**
	 * Kertoo, onko annetuissa koordinaateissa oleva ruutu miina.
	 * 
	 * @param x
	 *            ruudun x-koordinaatti
	 * @param y
	 *            ruudun y-koordinaatti
	 * @return <code>true</code>, jos kyseinen ruutu on miina, muuten
	 *         <code>false</code>
	 * @throws ArrayIndexOutOfBoundsException
	 *             jos koordinaatit olivat ruudukon rajojen ulkopuolella
	 */
	public boolean onMiina(int x, int y) {
		Peliruutu ruutu = this.ruudukko[x][y];
		return ruutu.onMiina();
	}

	/**
	 * Palauttaa kivasti merkkijonona ruudukon, josta löytyy tieto miinoista.
	 * Avatuissa ruuduissa on vihjenumero, jos se on suurempi kuin 0, muuten
	 * avatut ruudut esitetään välimerkillä. Liputetut paikat esitetään
	 * tähtimerkillä. Miinat esitetään @-merkillä. Miinattomat ja avaamattomat
	 * paikat esitetään pisteellä.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(this.annaLeveys()
				* this.annaKorkeus());

		for (int y = 0; y < this.annaKorkeus(); y++) {
			for (int x = 0; x < this.annaLeveys(); x++) {

				if (this.onLiputettu(x, y)) {
					sb.append('*');
				}
				else if (this.onMiina(x, y)) {
					sb.append('@');
				}
				else if (this.onAuki(x, y)) {
					int vihje = this.annaVihjenumero(x, y);
					if (vihje > 0) {
						sb.append(vihje);
					}
					else {
						sb.append(' ');
					}
				}
				else {
					sb.append('.');
				}
			}
			sb.append(String.format("%n"));
		}

		return sb.toString();
	}
}

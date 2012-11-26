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
		// jossa alkuun laitetaan miinat ja loput tyhjiksi, sitten sekoitetaan
		// taulukko täysin ja lopulta laitetaan 1D-taulukon arvot takaisin
		// 2D-vastineeseen.
		int koko = this.annaKorkeus() * this.annaLeveys();

		java.util.List<Peliruutu> ruudut = new java.util.ArrayList<Peliruutu>(
				koko);

		for (int i = 0; i < miinoja; i++) {
			ruudut.add(new Peliruutu(true));
		}
		for (int i = miinoja; i < koko; i++) {
			ruudut.add(new Peliruutu(false));
		}
		java.util.Collections.shuffle(ruudut);

		for (int x = 0; x < this.annaLeveys(); x++) {
			for (int y = 0; y < this.annaKorkeus(); y++) {
				this.ruudukko[x][y] = ruudut.get((y * this.annaLeveys()) + x);
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
		// TODO: laske
		return 0;
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
		// TODO: aseta
		return false;
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
		// TODO: avaa
		return 0;
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
		// TODO: onAuki
		return false;
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
		// TODO: onLiputettu
		return false;
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
		// TODO: onMiina
		return false;
	}

}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

// Ei meit kinosta.
@SuppressWarnings("serial")
/**
 * Peli, jonka veroista ideaa ei ole olemassakaan. Miinaharava. Tai siis
 * Miinapeli. Swingillä tehty. Swääg.
 */
public class Miinapeli extends JFrame {

	/** Pelin sisältämä iso harmaa paneeli keskellä ruutua */
	private JPanel paneeliKeski;

	/** paneeliKeski sisältää tämän Pelipaneelin, jossa kaikki napit ovat */
	private Pelipaneeli pelipaneeli;

	/** Pelin logiikan sisältämä peliruudukko */
	private Peliruudukko peliruudukko;

	/** Alapaneeli, jonka kautta tiedotetaan pelin tilasta. */
	private JPanel paneeliAla;

	/** Alapaneelin sisältämä pelitilanteesta kertova tekstilabel */
	private JLabel labelPelitila;

	/** Tieto siitä, onko peli päättynyt vai ei. */
	private boolean peliPaattynyt;

	public Miinapeli() {
		// Ikkunan sisällön asettelija
		this.setLayout(new BorderLayout());

		// Keskipaneeli
		this.paneeliKeski = new JPanel();
		this.paneeliKeski.setBackground(Color.getHSBColor(0f, 0.0f, 0.5f));
		this.add(this.paneeliKeski, BorderLayout.CENTER);

		// Alapaneeli
		this.paneeliAla = new JPanel();
		this.paneeliAla.setBackground(Color.getHSBColor(0.2f, 0.5f, 0.75f));
		this.labelPelitila = new JLabel();
		this.paneeliAla.add(this.labelPelitila);
		this.add(this.paneeliAla, BorderLayout.SOUTH);

		// Luodaan valikko
		JMenuBar valikkopalkki = new JMenuBar();
		JMenu pelivalikko = this.luoPelivalikko(valikkopalkki);
		valikkopalkki.add(pelivalikko);
		this.setJMenuBar(valikkopalkki);

		// Asetetaan ikkunalle otsikko
		this.setTitle("Miinaharava");

		// Peli käyntiin. "Puudeli" vaikeusaste eli 10x10 jossa 10 miinaa
		this.resetoi(10, 10, 10);
	}

	/**
	 * Käynnistää/alustaa uuden pelin, asettaen sinne annetun kokoisen ruudukon
	 * jossa on annettu määrä miinoja.
	 * 
	 * @param leveys
	 *            kuinka leveä peliruudukko luodaan
	 * @param korkeus
	 *            kuinka korkea peliruudukko luodaan
	 * @param miinoja
	 *            kuinka monta miinaa peliruudukkoon asetetaan
	 */
	public void resetoi(int leveys, int korkeus, int miinoja) {
		this.peliPaattynyt = false;
		this.peliruudukko = new Peliruudukko(leveys, korkeus, miinoja);
		this.pelipaneeli = new Pelipaneeli(this.peliruudukko, this);

		this.paneeliKeski.removeAll();
		this.paneeliKeski.add(this.pelipaneeli);

		this.labelPelitila.setText("Peli käynnissä.");

		// Asetetaan ikkunan kooksi se, jonka komponenttien haluamat koot
		// määrittävät. Samaten laitetaan ikkuna keskitetyksi ruudulle.
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/** Aloittaa nykyisen pelin alusta. */
	public void aloitaAlusta() {
		int leveys = this.peliruudukko.annaLeveys();
		int korkeus = this.peliruudukko.annaKorkeus();
		int miinoja = this.peliruudukko.annaMiinojenLkm();
		this.resetoi(leveys, korkeus, miinoja);
	}

	/**
	 * Pelin loppumisen, joko voiton tai häviön takia.
	 * 
	 * @param voittoTuli
	 *            true, mikäli <code>peli</code> loppui voiton takia, muutoin
	 *            <code>false</code>.
	 */
	public void peliPaattyi(boolean voittoTuli) {
		this.peliPaattynyt = true;
		if (voittoTuli) {
			this.muutaTilaTeksti("Voitto kottiin!");
		}
		else {
			this.muutaTilaTeksti("Hävisit pelin.");
		}
		this.pelipaneeli.avaaKaikki(voittoTuli);
	}

	/** @return tieto siitä, onko peli päättynyt */
	public boolean peliPaattynyt() {
		return this.peliPaattynyt;
	}

	/** Muuttaa alapaneelin tekstiä annetun parametrin mukaiseksi */
	public void muutaTilaTeksti(String teksti) {
		this.labelPelitila.setText(teksti);
	}

	/** Apumetodi, joka luo annettuun valikkopalkkiin pelivalikon. */
	private JMenu luoPelivalikko(JMenuBar valikkopalkki) {
		JMenu pelivalikko = new JMenu("Peli");

		// Tapahtumien kuuntelija
		ValikkoKuuntelija kuuntelija = new ValikkoKuuntelija();

		JMenuItem aloitaAlusta = new JMenuItem("Aloita alusta");
		aloitaAlusta.addActionListener(kuuntelija);
		pelivalikko.add(aloitaAlusta);

		// Vaikeusastevalikot
		JMenu vaikeusasteValikko = new JMenu("Vaihda vaikeusaste");
		JMenuItem vaikeusasteet[] = {
				this.luoVaikeusasteValikko("Puudeli", 10, 10, 10),
				this.luoVaikeusasteValikko("Kultainen noutaja", 15, 15, 30),
				this.luoVaikeusasteValikko("Rottweiler", 12, 12, 30),
				this.luoVaikeusasteValikko("Dobermanni", 20, 20, 80) };
		for (JMenuItem vAste : vaikeusasteet) {
			vAste.addActionListener(kuuntelija);
			vaikeusasteValikko.add(vAste);
		}
		pelivalikko.add(vaikeusasteValikko);

		pelivalikko.addSeparator();

		// Lopeta-vaihtoehto
		JMenuItem lopeta = new JMenuItem("Lopeta");
		lopeta.addActionListener(kuuntelija);
		pelivalikko.add(lopeta);

		return pelivalikko;
	}

	/** Apumetodi, joka luo yksittäisen vaikeusastevalikkopalasen. */
	private JMenuItem luoVaikeusasteValikko(String otsikko, int leveys,
			int korkeus, int miinoja) {
		JMenuItem valikko = new JMenuItem(otsikko);
		valikko.putClientProperty("leveys", leveys);
		valikko.putClientProperty("korkeus", korkeus);
		valikko.putClientProperty("miinoja", miinoja);
		return valikko;
	}

	/** Valikon toiminnoista vastaava kuuntelija-sisäluokka. */
	private class ValikkoKuuntelija implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent tapahtuma) {
			// Ei ehkä ole paras mahdollinen tapa, että kaikki valikon osat
			// kytketään tähän samaan kuuntelijaan ja käskyjä verrataan
			// toisiinsa vain niiden tekstien avulla, mutta tuntuu siltä että
			// erilliset kuuntelijat olisivat aikamoinen overkill tähän.
			String kasky = tapahtuma.getActionCommand();

			if (kasky.equals("Aloita alusta")) {
				Miinapeli.this.aloitaAlusta();
			}
			else if (kasky.equals("Lopeta")) {
				System.exit(0);
			}
			else if (tapahtuma.getSource() instanceof JMenuItem) {
				JMenuItem valikko = (JMenuItem) tapahtuma.getSource();
				if (valikko.getClientProperty("leveys") != null) {
					// Taisi olla vaikeusastevalikko kun "leveys" oli olemassa.
					int leveys = (int) valikko.getClientProperty("leveys");
					int korkeus = (int) valikko.getClientProperty("korkeus");
					int miinoja = (int) valikko.getClientProperty("miinoja");
					Miinapeli.this.resetoi(leveys, korkeus, miinoja);
				}
			}
		}
	}

	/** Time to play The Game! */
	public static void main(String[] args) {
		Miinapeli peli = new Miinapeli();

		// Sammutetaan peli oletuksena kun ruksista klikataan
		peli.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Pistetään peli näkymään.
		peli.setVisible(true);
	}

}

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

	/**
	 * Luokalla on aina tieto siitä, mikä on nykyinen Miinapeli. Ja se tieto
	 * löytyy tästä.
	 */
	private static Miinapeli peli;

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

		// Otetaan talteen tieto nykyisestä pyörivästä pelistä.
		Miinapeli.peli = this;

		// Peli käyntiin.
		Miinapeli.resetoi(10, 10, 5);
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
	public static void resetoi(int leveys, int korkeus, int miinoja) {
		peli.peliruudukko = new Peliruudukko(leveys, korkeus, miinoja);
		peli.pelipaneeli = new Pelipaneeli(peli.peliruudukko);

		peli.paneeliKeski.removeAll();
		peli.paneeliKeski.add(peli.pelipaneeli);
		peli.paneeliKeski.validate();

		peli.labelPelitila.setText("Peli käynnissä.");
	}

	/** Aloittaa nykyisen pelin alusta. */
	public static void aloitaAlusta() {
		int leveys = peli.peliruudukko.annaLeveys();
		int korkeus = peli.peliruudukko.annaKorkeus();
		int miinoja = peli.peliruudukko.annaMiinojenLkm();
		Miinapeli.resetoi(leveys, korkeus, miinoja);
	}

	/**
	 * Pelin loppumisen, joko voiton tai häviön takia.
	 * 
	 * @param voittoTuli
	 *            true, mikäli <code>peli</code> loppui voiton takia, muutoin
	 *            <code>false</code>.
	 */
	public static void peliPaattyi(boolean voittoTuli) {
		if (voittoTuli) {
			peli.labelPelitila.setText("Voitto kottiin!");
		}
		else {
			peli.labelPelitila.setText("Hävisit pelin.");
		}
		peli.pelipaneeli.avaaKaikki(voittoTuli);
	}

	/** Apumetodi, joka luo annettuun valikkopalkkiin pelivalikon. */
	private JMenu luoPelivalikko(JMenuBar valikkopalkki) {
		JMenu pelivalikko = new JMenu("Peli");

		// Tapahtumien kuuntelija
		ValikkoKuuntelija kuuntelija = new ValikkoKuuntelija();

		JMenuItem aloitaAlusta = new JMenuItem("Aloita alusta");
		aloitaAlusta.addActionListener(kuuntelija);
		pelivalikko.add(aloitaAlusta);

		pelivalikko.addSeparator();

		JMenuItem lopeta = new JMenuItem("Lopeta");
		lopeta.addActionListener(kuuntelija);
		pelivalikko.add(lopeta);

		return pelivalikko;
	}

	/** Valikon toiminnoista vastaava kuuntelija-sisäluokka. */
	private class ValikkoKuuntelija implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent tapahtuma) {
			String kasky = tapahtuma.getActionCommand();

			if (kasky.equals("Aloita alusta")) {
				Miinapeli.aloitaAlusta();
			}
			else if (kasky.equals("Lopeta")) {
				System.exit(0);
			}
		}
	}

	/** Time to play The Game! */
	public static void main(String[] args) {
		Miinapeli peli = new Miinapeli();

		// Asetetaan ikkunan kooksi se, jonka komponenttien haluamat koot
		// määrittävät.
		peli.pack();

		// Asetetaan peli keskelle ruutua
		peli.setLocationRelativeTo(null);

		// Sammutetaan peli oletuksena kun ruksista klikataan
		peli.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Pistetään peli näkymään.
		peli.setVisible(true);
	}

}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

/**
 * Peli, jonka veroista ideaa ei ole olemassakaan. Miinaharava. Tai siis
 * Miinapeli. Swingillä tehty. Swääg.
 */
public class Miinapeli extends JFrame {

	/** Swing-komponentit pitää voida serialisoida, joten tämä. */
	private static final long serialVersionUID = -1877957233689821991L;

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

	/** Nykyisen pelin vaikeusaste */
	private Vaikeusaste vaikeusaste;

	/** Ajankohta, jolloin pelaaminen on aloitettu. */
	private long aloitushetki;

	/** Fontit taulukossa, josta haetaan aivan alussa jokin toimiva. */
	private static final String[] fontit = { "Helvetica", "Dejavu Sans Serif",
			"Arial", "Verdana" };

	public Miinapeli() {
		// Asetetaan oletusfontit ihan aluksi.
		this.asetaOletusFontit();

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
		this.resetoi(Vaikeusaste.TASO1);
	}

	/**
	 * Käynnistää/alustaa uuden pelin, jonka koon ja miinojen määrän kertoo
	 * parametrina annettu vaikeusaste.
	 * 
	 * @param v
	 *            uuden pelin vaikeusaste
	 */
	public void resetoi(Vaikeusaste v) {
		this.peliPaattynyt = false;
		this.vaikeusaste = v;
		this.peliruudukko = new Peliruudukko(v.leveys, v.korkeus, v.miinoja);
		this.pelipaneeli = new Pelipaneeli(this.peliruudukko, this);

		this.paneeliKeski.removeAll();
		this.paneeliKeski.add(this.pelipaneeli);

		this.labelPelitila.setText(String.format("Peli aloitettu. Miinoja %d",
				this.peliruudukko.annaMiinojenLkm()));

		// Asetetaan ikkunan kooksi se, jonka komponenttien haluamat koot
		// määrittävät. Samaten laitetaan ikkuna keskitetyksi ruudulle.
		this.pack();
		this.setLocationRelativeTo(null);
	}

	/** Apumetodi, joka asettaa oletusfontit kuntoon. */
	private void asetaOletusFontit() {
		// Haetaan tähän muuttujaan talteen ensimmäinen toimiva fontti
		FontUIResource f = null;

		// Kaikki fontit, mitä järjestelmästä löytyy on tallessa täällä
		String[] okFontit = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();

		for (String fontti : Miinapeli.fontit) {
			// Tarkistetaan, että fontti löytyy järjestelmän tiedoista.
			for (String okFontti : okFontit) {
				if (fontti.equals(okFontti)) {
					// Löytyi.
					f = new FontUIResource(fontti, Font.PLAIN, 13);
					break;
				}
			}
			if (f != null) break;
		}
		// Mikäli mitään fonttia ei löytynyt, ei vaihdella oletuksia.
		if (f == null) {
			return;
		}

		// Asetetaan UIManagerin kaikkien FontUIResource-tyyppisten
		// oletustietojen paikalle meidän oma fonttimme.
		java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource) {
				UIManager.put(key, f);
			}
		}
	}

	/** Aloittaa nykyisen pelin alusta. */
	public void aloitaAlusta() {
		this.resetoi(this.vaikeusaste);
	}

	/** Käynnistää pelin ajastimen. */
	public void kaynnistaAjastin() {
		this.aloitushetki = System.currentTimeMillis();
	}

	/**
	 * Pelin loppumisen, joko voiton tai häviön takia.
	 * 
	 * @param voittoTuli
	 *            true, mikäli <code>peli</code> loppui voiton takia, muutoin
	 *            <code>false</code>.
	 */
	public void peliPaattyi(boolean voittoTuli) {
		// Otetaan heti ylös päättymisaika.
		long paattymishetki = System.currentTimeMillis();

		this.peliPaattynyt = true;
		if (voittoTuli) {
			this.muutaTilaTeksti("Voitto kottiin!");
		}
		else {
			this.muutaTilaTeksti("Hävisit pelin.");
		}
		this.pelipaneeli.avaaKaikki(voittoTuli);

		if (voittoTuli) {
			// Nyt kun kaikki on auki, niin kerrotaanpa käyttäjälle kivassa
			// dialogiboksissa pelin läpi pääsemiseen kulunut aika :)
			double kulunutAika = (paattymishetki - this.aloitushetki) / 1000.0;
			String voittoteksti = String.format("Voitit pelin! Sinulla kului "
					+ "siihen aikaa %.2f sekuntia.", kulunutAika);
			JOptionPane.showMessageDialog(this, voittoteksti, "Voitto!",
					JOptionPane.DEFAULT_OPTION);
		}
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
		for (Vaikeusaste v : Vaikeusaste.values()) {
			JMenuItem valikko = new JMenuItem(v.nimi);
			valikko.putClientProperty("vaikeusaste", v);
			valikko.addActionListener(kuuntelija);
			vaikeusasteValikko.add(valikko);
		}
		pelivalikko.add(vaikeusasteValikko);

		pelivalikko.addSeparator();

		// Lopeta-vaihtoehto
		JMenuItem lopeta = new JMenuItem("Lopeta");
		lopeta.addActionListener(kuuntelija);
		pelivalikko.add(lopeta);

		return pelivalikko;
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
				Object prop = valikko.getClientProperty("vaikeusaste");
				if (prop instanceof Vaikeusaste) {
					// Oli vaikeusastevalikko kun "vaikeusaste" oli olemassa.
					Miinapeli.this.resetoi((Vaikeusaste) prop);
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

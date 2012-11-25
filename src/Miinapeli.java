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
		// Itse miinat myös. Pelipaneeli lopulta määrittää
		// keskipaneelikomponentin haluaman koon.
		this.paneeliKeski.add(new Pelipaneeli(30, 10));
		this.add(this.paneeliKeski, BorderLayout.CENTER);

		// Alapaneeli
		this.paneeliAla = new JPanel();
		this.paneeliAla.setBackground(Color.getHSBColor(0.2f, 0.5f, 0.75f));
		this.labelPelitila = new JLabel("Peli kesken", JLabel.CENTER);
		this.paneeliAla.add(this.labelPelitila);
		this.add(this.paneeliAla, BorderLayout.SOUTH);

		// Luodaan valikko
		JMenuBar valikkopalkki = new JMenuBar();
		JMenu pelivalikko = this.luoPelivalikko(valikkopalkki);
		valikkopalkki.add(pelivalikko);
		this.setJMenuBar(valikkopalkki);

		// Asetetaan ikkunalle otsikko
		this.setTitle("Miinaharava");
	}

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
			// TODO: Kun valmis, poista printti.
			System.out.println("Käsky kävi: " + kasky);

			if (kasky.equals("Aloita alusta")) {
				// Käskettiin aloittamaan peli alusta. Ei onnistu vielä.
				final String alkupTxt = labelPelitila.getText();
				final String kieltoTxt = "Vain rekisteröidyssä versiossa.";
				Miinapeli.this.labelPelitila.setText(kieltoTxt);
				// 2 sek päästä takaisin alkup. teksti.
				if (!alkupTxt.equals(kieltoTxt)) {
					Timer ajastin = new Timer(2000, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Miinapeli.this.labelPelitila.setText(alkupTxt);
						}
					});
					ajastin.setRepeats(false);
					ajastin.start();
				}
			}
			else if (kasky.equals("Lopeta")) {
				// TODO: Kun valmis, poista printti.
				System.out.println("Peli sammuu.");
				System.exit(0);
			}
		}
	}

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

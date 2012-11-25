import java.awt.*;

import javax.swing.*;

public class Miinapeli extends JFrame {

	/**
	 * Pakollinen serialVersionUID. Tai Eclipse valittaisi.
	 */
	private static final long serialVersionUID = 5734077433440873477L;

	/** Pelin sisältämä iso harmaa paneeli keskellä ruutua */
	private JPanel paneeliKeski;

	/** Alapaneeli, jonka kautta tiedotetaan pelin tilasta. */
	private JPanel paneeliAla;

	/** Alapaneelin sisältämä pelitilanteesta kertova tekstilabel */
	private JLabel labelPelitila;

	/** Ylävalikko */
	private JMenuBar valikko;

	public Miinapeli() {
		// Ikkunan sisällön asettelija
		this.setLayout(new BorderLayout());

		// Keskipaneeli
		this.paneeliKeski = new JPanel();
		this.paneeliKeski.setPreferredSize(new Dimension(400, 300));
		this.paneeliKeski.setBackground(Color.getHSBColor(0f, 0.0f, 0.5f));
		this.add(this.paneeliKeski, BorderLayout.CENTER);

		// Alapaneeli
		this.paneeliAla = new JPanel();
		this.paneeliAla.setBackground(Color.getHSBColor(0.2f, 0.5f, 0.75f));
		this.labelPelitila = new JLabel("Peli kesken", JLabel.CENTER);
		this.paneeliAla.add(this.labelPelitila);
		this.add(this.paneeliAla, BorderLayout.SOUTH);

		// Luodaan valikko
		this.valikko = new JMenuBar();
		this.valikko.setOpaque(true);

		JMenu peliValikko = new JMenu("Peli");
		peliValikko.add(new JMenuItem("Aloita alusta"));
		peliValikko.addSeparator();
		peliValikko.add(new JMenuItem("Lopeta"));
		this.valikko.add(peliValikko);
		this.add(this.valikko, BorderLayout.NORTH);

		// Asetetaan ikkunalle otsikko
		this.setTitle("Miinaharava");
	}

	public static void main(String[] args) {
		Miinapeli peli = new Miinapeli();

		// Asetetaan ikkunan kooksi se, jonka komponenttien haluamat koot
		// määrittävät.
		peli.pack();

		// Asetetaan peli keskelle ruutua
		peli.setLocationRelativeTo(null);

		// Pistetään peli näkymään.
		peli.setVisible(true);
	}

}

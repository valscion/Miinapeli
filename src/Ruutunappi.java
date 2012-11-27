import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

//Ei meit kinosta.
@SuppressWarnings("serial")
/**
 * Yksittäisiä miinaharavamaisia nappeja esittävien olioiden luokka. Tämä
 * periytetään paneelista eikä JButtonista, sillä ulkoasua halutaan hifistellä.
 * Luokan sisällä on tieto JButtonista, jolloin päästään tarkemmin muuttamaan
 * nappulan ulkoasua.
 */
public class Ruutunappi extends JPanel implements MouseListener {

	/** Napikan paikka ruudukossa, x-koordinaatti */
	private int x;
	/** Napikan paikka ruudukossa, y-koordinaatti */
	private int y;

	/** Peliruudukko, jossa pelaillaan. */
	private Peliruudukko ruudukko;

	/** Oikeasti nappula. */
	private JButton nappula;

	/** Kaikki eri tilanteiden nappuloiden värit. */
	private enum Varit {
		AVAAMATON(Color.getHSBColor(0f, 0f, 0.8f)), // Alkutilanne
		HIIRI_POHJASSA(Color.getHSBColor(0f, 0f, 0.7f)), // Hiirtä painetaan
		HIIRI_PAALLA(Color.getHSBColor(0f, 0f, 0.85f)), // Hiiri päällä
		AVATTU(Color.getHSBColor(0f, 0f, 0.85f)); // Palikka auki

		private Color vari;

		Varit(Color vari) {
			this.vari = vari;
		}

		public Color annaVari() {
			return this.vari;
		}
	}

	/**
	 * Luo uuden ruutunapin, jolle kerrotaan sen sijainti ruudukossa.
	 * 
	 * @param x
	 *            napin x-koordinaatti
	 * @param y
	 *            napin y-koordinaatti
	 * @param ruudukko
	 *            missä peliruudukossa nappi on
	 */
	public Ruutunappi(int x, int y, Peliruudukko ruudukko) {
		// Tiedot talteen
		this.x = x;
		this.y = y;
		this.ruudukko = ruudukko;

		// Ulkoasun laitto.
		this.setPreferredSize(new Dimension(25, 25));
		this.setBackground(Varit.AVAAMATON.annaVari());
		this.setLayout(null);

		// Lisätään nappula ja sille ominaisuudet.
		this.nappula = new JButton();
		this.nappula.setLocation(0, 0);
		this.nappula.setSize(new Dimension(25, 25));
		this.nappula.setBorder(BorderFactory.createRaisedBevelBorder());
		this.nappula.setContentAreaFilled(false);

		// Asetetaan hiirikuuntelijakin
		this.nappula.addMouseListener(this);
		
		// Lisätään nappula vielä paneeliin.
		this.add(this.nappula);
	}

	/** Hiiren klikkausten handlaus, koko pelin suola. */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO: Toteuta avaus.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setBackground(Varit.HIIRI_PAALLA.annaVari());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (this.ruudukko.onAuki(this.x, this.y)) {
			this.setBackground(Varit.AVATTU.annaVari());
		}
		else {
			this.setBackground(Varit.AVAAMATON.annaVari());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Näytetään reunusten avulla että painettiin.
		this.nappula.setBorder(BorderFactory.createLoweredBevelBorder());
		this.setBackground(Varit.HIIRI_POHJASSA.annaVari());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Näytetään reunusten avulla että painallus loppui.
		this.nappula.setBorder(BorderFactory.createRaisedBevelBorder());

		if (this.ruudukko.onAuki(this.x, this.y)) {
			this.setBackground(Varit.AVATTU.annaVari());
		}
		else {
			this.setBackground(Varit.AVAAMATON.annaVari());
		}
	}
}

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

	/**
	 * Ykkössolussa tieto siitä, onko BUTTON1 alhaalla, kakkossolussa onko
	 * BUTTON3 alla. Muista ei välitetä.
	 */
	private boolean[] painetutNappulat = { false, false };

	/** Onko hiiri nappulan päällä vai ei */
	private boolean onHiirenAlla;

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
		this.setLayout(null);

		// Lisätään nappula ja sille ominaisuudet.
		this.nappula = new JButton();
		this.nappula.setLocation(0, 0);
		this.nappula.setSize(new Dimension(25, 25));
		this.nappula.setBorder(BorderFactory.createRaisedBevelBorder());
		this.nappula.setContentAreaFilled(false);
		
		if (!ruudukko.onMiina(x, y)) {
			this.setBackground(NappiVari.AVAAMATON.annaVari());
		}
		else {
			//this.setBackground(NappiVari.RAJAHTANYT.annaVari());
			this.nappula.setIcon(NappiKuvat.MIINA.icon);
		}

		// Asetetaan hiirikuuntelijakin
		this.nappula.addMouseListener(this);

		// Lisätään nappula vielä paneeliin.
		this.add(this.nappula);
	}

	/**
	 * Hiiren klikkausten handlaus, koko pelin suola.
	 * 
	 * @param avausNappi
	 *            jos true, painettiin ruudun avaavaa nappia hiirellä, muutoin
	 *            painettiin lipun asettavaa nappia.
	 */
	private void handlaaKlikkaus(boolean avausNappi) {
		System.out.println("Klik. " + avausNappi);
	}

	/*
	 * Hiiren klikkaukset handlataan muualla, tämä on tyhjä koska Java ei
	 * käsittele klikkauksia loogisesti. Vaikka hiiri kävisikin nappulan
	 * ulkopuolella ja palaa takaisin nappulan ollessa alhaalla, on se kaiken
	 * järjen mukaan klikkaus! Hiton Swingi kun ei tee näin jo oletuksena. Plus
	 * mouseClicked tuntuu muutenkin olevan aikalailla buginen mössö, vaatii
	 * ettei hiirtä liikutella paljoa ja muutenkin.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Screw you, Swing. I'm going home.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (!this.ruudukko.onAuki(this.x, this.y)) {
			if (this.painetutNappulat[0] || this.painetutNappulat[1]) {
				// Piirretään uudelleen painettu väri, palattiin takasin.
				this.setBackground(NappiVari.HIIRI_POHJASSA.annaVari());
			}
			else {
				this.setBackground(NappiVari.HIIRI_PAALLA.annaVari());
			}
		}
		this.onHiirenAlla = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!this.ruudukko.onAuki(this.x, this.y)) {
			this.setBackground(NappiVari.AVAAMATON.annaVari());
		}
		this.onHiirenAlla = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!this.ruudukko.onAuki(this.x, this.y)) {
			// Näytetään reunusten avulla että painettiin.
			this.nappula.setBorder(BorderFactory.createLoweredBevelBorder());
			this.setBackground(NappiVari.HIIRI_POHJASSA.annaVari());
		}

		int nappi = e.getButton();
		if (nappi == MouseEvent.BUTTON1) {
			this.painetutNappulat[0] = true;
		}
		else if (nappi == MouseEvent.BUTTON3) {
			this.painetutNappulat[1] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Muutellaan ulkomuotoa jos oikeasti molemmat hiiren napit on tämän
		// jälkeen päästetty irti.
		if (!(this.painetutNappulat[0] && this.painetutNappulat[1])) {
			this.nappula.setBorder(BorderFactory.createRaisedBevelBorder());

			if (this.ruudukko.onAuki(this.x, this.y)) {
				this.setBackground(NappiVari.AVATTU.annaVari());
			}
			else {
				this.setBackground(NappiVari.AVAAMATON.annaVari());
			}
		}

		// Mikäli nappi oli aikaisemmin painettuna ja hiiri on nappulan päällä
		// nyt, tarkoittaa että painallus on tapahtunut! Tämä on paljon
		// järkevämpi tapa kuin Swingin oletustapa. Huh. Vielä siihen päälle
		// se tarkistus, että mikään hiiren nappuloista ei ole alhaalla. Ja se,
		// että lopullinen klikkaus tapahtui sillä napilla, joka oli vikana
		// alhaalla.
		int nappi = e.getButton();

		if (this.onHiirenAlla) {
			// Täytyy tarkistella vielä että painettujen status vastaa sitä
			// nappulaa, joka päästettiin irti. Muuten voisi tulla turhia
			// klikkauksia jos painalluksen alkua ei rekisteröity ohjelman
			// tietoon.
			if (this.painetutNappulat[0] && this.painetutNappulat[1]) {
				// Molemmat oli alhaalla, joten ei tehdä mitään.
			}
			else if (nappi == MouseEvent.BUTTON1 && this.painetutNappulat[0]) {
				// Nonne, ny klikattiin sit ykkösel.
				this.handlaaKlikkaus(true);
			}
			else if (nappi == MouseEvent.BUTTON3 && this.painetutNappulat[1]) {
				// Kakkosklikki tapahtu.
				this.handlaaKlikkaus(false);
			}
		}

		// Resetoidaan painetun status.
		if (nappi == MouseEvent.BUTTON1) {
			this.painetutNappulat[0] = false;
		}
		else if (nappi == MouseEvent.BUTTON3) {
			this.painetutNappulat[1] = false;
		}
	}
}

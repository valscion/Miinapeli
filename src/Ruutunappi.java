import java.awt.Dimension;
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

	/** Pelipaneeli, jossa nappula sijaitsee. */
	private Pelipaneeli paneeli;

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
	public Ruutunappi(int x, int y, Peliruudukko ruudukko, Pelipaneeli panel) {
		// Tiedot talteen
		this.x = x;
		this.y = y;
		this.ruudukko = ruudukko;
		this.paneeli = panel;

		// Ulkoasun laitto.
		this.setPreferredSize(new Dimension(25, 25));
		this.setLayout(null);

		// Lisätään nappula ja sille ominaisuudet.
		this.nappula = new JButton();
		this.nappula.setLocation(0, 0);
		this.nappula.setSize(new Dimension(25, 25));
		this.nappula.setBorder(BorderFactory.createRaisedBevelBorder());
		this.nappula.setContentAreaFilled(false);
		this.nappula.setFocusable(false);

		this.setBackground(NappiVari.AVAAMATON.color);

		// Asetetaan hiirikuuntelijakin
		this.nappula.addMouseListener(this);

		// Lisätään nappula vielä paneeliin.
		this.add(this.nappula);
	}

	/** Palauttaa napin x-sijainnin peliruudukossa. */
	public int annaX() {
		return this.x;
	}

	/** Palauttaa napin y-sijainnin peliruudukossa. */
	public int annaY() {
		return this.y;
	}

	/** Näyttää napin avattuna miinana. Poistaa myös hiirikuuntelijan. */
	public void naytaMiina() {
		this.nappula.setIcon(NappiKuvat.MIINA.icon);
		this.poistaKaytosta();
	}

	/** Näyttää nappulan räjähtäneenä miinana. */
	public void naytaRajahtanytMiina() {
		this.nappula.setBorder(BorderFactory
				.createLineBorder(NappiVari.AVATTU_REUNA.color));
		this.setBackground(NappiVari.RAJAHTANYT.color);
		this.nappula.setIcon(NappiKuvat.MIINA.icon);
		this.poistaKaytosta();
	}

	/**
	 * Näyttää napin avattuna tyhjänä paikkana ja lisää tekstiksi vihjenron.
	 * Poistaa myös hiirikuuntelijan.
	 */
	public void naytaVihje(int vihjenumero) {
		if (vihjenumero >= 1 && vihjenumero <= 8) {
			this.nappula.setText(String.valueOf(vihjenumero));
		}
		this.nappula.setBorder(BorderFactory
				.createLineBorder(NappiVari.AVATTU_REUNA.color));
		this.setBackground(NappiVari.AVATTU.color);
		this.poistaKaytosta();

		switch (vihjenumero) {
			case 0: break;
			case 1: this.nappula.setForeground(NappiVari.VIHJE1.color); break;
			case 2: this.nappula.setForeground(NappiVari.VIHJE2.color); break;
			case 3: this.nappula.setForeground(NappiVari.VIHJE3.color); break;
			case 4: this.nappula.setForeground(NappiVari.VIHJE4.color); break;
			case 5: this.nappula.setForeground(NappiVari.VIHJE5.color); break;
			case 6: this.nappula.setForeground(NappiVari.VIHJE6.color); break;
			case 7: this.nappula.setForeground(NappiVari.VIHJE7.color); break;
			case 8: this.nappula.setForeground(NappiVari.VIHJE8.color); break;
			default:
				this.nappula.setText("?");
				this.nappula.setForeground(NappiVari.VIHJEVIRHE.color);
		}
	}

	/** Asettaa napille lipun tai ottaa sen pois. */
	public void naytaLippu(boolean lippu) {
		if (lippu) {
			this.nappula.setIcon(NappiKuvat.LIPPU.icon);
		}
		else {
			this.nappula.setIcon(null);
		}
	}

	/** Näyttää virheellisen liputuksen */
	public void naytaVirheellinenLiputus() {
		this.setBackground(NappiVari.VAARA_LIPUTUS.color);
		this.nappula.setBackground(NappiVari.VAARA_LIPUTUS.color);
		this.poistaKaytosta();
	}

	/** Ottaa nappulan pois käytöstä. */
	public void poistaKaytosta() {
		this.nappula.removeMouseListener(this);
	}

	/**
	 * Hiiren klikkausten varsinainen handlaus.
	 * 
	 * @param avausNappi
	 *            jos true, painettiin ruudun avaavaa nappia hiirellä, muutoin
	 *            painettiin lipun asettavaa nappia.
	 */
	private void handlaaKlikkaus(boolean avausNappi) {
		if (avausNappi) {
			int avausArvo = this.ruudukko.avaa(this.x, this.y);
			if (avausArvo == Peliruudukko.OLI_MIINA) {
				// TODO: game over
				System.out.println("You phailed.");
				this.naytaRajahtanytMiina();
				this.paneeli.avaaKaikki();
			}
			else if (avausArvo >= 0) {
				// Avaa ruutunappi ja hoida logiikka.
				this.paneeli.avaa(this.x, this.y, avausArvo);
			}
		}
		else {
			// Liputusnappi siis.
			if (this.ruudukko.onLiputettu(this.x, this.y)) {
				this.ruudukko.asetaLippu(this.x, this.y, false);
				this.naytaLippu(false);
			}
			else {
				this.ruudukko.asetaLippu(this.x, this.y, true);
				this.naytaLippu(true);
			}
		}
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
				this.setBackground(NappiVari.HIIRI_POHJASSA.color);
			}
			else {
				this.setBackground(NappiVari.HIIRI_PAALLA.color);
			}
		}
		this.onHiirenAlla = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!this.ruudukko.onAuki(this.x, this.y)) {
			this.setBackground(NappiVari.AVAAMATON.color);
		}
		this.onHiirenAlla = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!this.ruudukko.onAuki(this.x, this.y)) {
			// Näytetään reunusten avulla että painettiin.
			this.nappula.setBorder(BorderFactory.createLoweredBevelBorder());
			this.setBackground(NappiVari.HIIRI_POHJASSA.color);
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
				this.setBackground(NappiVari.AVATTU.color);
			}
			else {
				this.setBackground(NappiVari.AVAAMATON.color);
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

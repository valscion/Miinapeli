import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

// Ei meit kinosta.
@SuppressWarnings("serial")
/**
 * Miinakentästä huolehtiva paneeliluokka
 */
public class Pelipaneeli extends JPanel implements MouseListener {

	/**
	 * Kaikki eri miinanappulat tallennetaan tähän 2D-taulukkoon. Ensimmäinen
	 * ulottuvuus on x-koordinaatti, toinen y-koordinaatti.
	 */
	private JButton[][] miinat;

	/**
	 * Jokaisella nappulalla on sisäisessä muistissaan avain-arvo pariin
	 * tallennettuna tieto nykyisestä nappulan x- ja y-koordinaatista. Avaimena
	 * toimii tämän enumin vakiot.
	 */
	private enum Paikka {
		X, Y;
	}

	/** Luo uuden pelipaneelin ja asettaa sinne miinakentän. */
	public Pelipaneeli(int leveys, int korkeus) {
		// Sisältö menee taulukkopussileiskaan.
		this.setLayout(new GridBagLayout());

		// Luodaan sopivan kokoinen taulukko aivan aluksi.
		this.miinat = new JButton[leveys][korkeus];

		// Luodaan uusi GridBagConstraints-olio, jonka gridx- ja
		// gridy-attribuutteja asetellaan myöhemmin tarvittaessa.
		// Oletusarvot kelpaavat meille, joten niistä ei huolestuta.
		GridBagConstraints c = new GridBagConstraints();

		// Luodaan nappulat sisäkkäisillä for-silmukoilla.
		for (int x = 0; x < leveys; x++) {
			c.gridx = x * 25;
			for (int y = 0; y < korkeus; y++) {
				c.gridy = y * 25;

				// Luodaan se nappi.
				JButton miinaNappi = new JButton();
				miinaNappi.setPreferredSize(new Dimension(25, 25));

				// Kerrotaan sille suoraan, missä koordinaateissa se sijaitsee.
				miinaNappi.putClientProperty(Paikka.X, x);
				miinaNappi.putClientProperty(Paikka.Y, y);

				// Liitetään sille kuuntelija
				miinaNappi.addMouseListener(this);

				// Tallennetaan se
				this.miinat[x][y] = miinaNappi;
				this.add(miinaNappi, c);
			}
		}
	}

	/** Hiiren klikkausten handlaus, koko pelin suola. */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Mousehandleria ei ole liitetty mihinkään muuhun kuin buttoneihin,
		// joten saatu komponentti on aina button.
		Component c = e.getComponent();
		if (!(c instanceof JButton)) {
			// WUT?! Ai ettei ollut!?
			return;
		}
		JButton nappula = (JButton) c;

		// Jokaisella miinanappulalla on tiedossaan koordinaatit, jossa se
		// sijaitsee. Otetaan ne ylös. Mutta tarkistuksien kera.
		int x, y;

		{
			Object tmpX = nappula.getClientProperty(Paikka.X);
			Object tmpY = nappula.getClientProperty(Paikka.Y);
			if (!(tmpX instanceof Integer) || !(tmpY instanceof Integer)) {
				// Ohhoh. Outoa.
				System.out.println("Nappulalle ei oltu tallennettu oikeaa "
						+ "koordinaattia!");
				return;
			}
			x = (Integer) tmpX;
			y = (Integer) tmpY;
		}

		// TODO: Poista printti kun valmis.
		System.out.printf("Painettiin nappulaa @ (%d, %d)%n", x, y);

		if (e.getButton() == MouseEvent.BUTTON1) {
			// Hiiren vasen nappi, muutetaan miinabutton siniseksi.
			nappula.setBackground(Color.getHSBColor(0.65f, 0.5f, 0.5f));
		}
		else if (e.getButton() == MouseEvent.BUTTON3) {
			// Hiiren oikea nappi, muutetaan miinabutton vihreäksi.
			nappula.setBackground(Color.getHSBColor(0.4f, 0.5f, 0.5f));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

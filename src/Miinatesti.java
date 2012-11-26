import java.awt.Point;
import java.util.Scanner;

public class Miinatesti {

	private Peliruudukko ruudukko;
	private Scanner skan;

	public Miinatesti(int leveys, int korkeus, int miinoja) {

		ruudukko = new Peliruudukko(leveys, korkeus, miinoja);
		skan = new Scanner(System.in);

		boolean peliHavitty = false;

		while (!peliHavitty) {
			this.tulostaRuudukko();
			Point koordsit = this.kysyKoordinaatit();

			int avaustulos = ruudukko.avaa(koordsit.x, koordsit.y);

			if (avaustulos == Peliruudukko.OLI_MIINA) {
				peliHavitty = true;
			}
			else if (avaustulos == Peliruudukko.OLI_JO_AUKI) {
				System.out.println("Älä sitä avaa, se on auki jo.");
			}
			else if (avaustulos == Peliruudukko.OLI_LIPUTETTU) {
				System.out.println("Äläpäs koske siihen, siinä on lippu.");
			}
		}

		if (peliHavitty) {
			this.avaaKaikkiMiinat();
			this.tulostaRuudukko();
			System.out.println("\nEPÄONNISTUIT!");
		}
	}

	private void tulostaRuudukko() {

		System.out.println();

		for (int y = 0; y < ruudukko.annaKorkeus(); y++) {
			for (int x = 0; x < ruudukko.annaLeveys(); x++) {

				if (!ruudukko.onAuki(x, y)) {
					if (ruudukko.onLiputettu(x, y)) {
						System.out.print('*');
					}
					else {
						System.out.print('.');
					}
				}
				else {
					if (ruudukko.onMiina(x, y)) {
						System.out.print('@');
					}
					else {
						int vihje = ruudukko.annaVihjenumero(x, y);
						if (vihje > 0) {
							System.out.print(vihje);
						}
						else {
							System.out.print(' ');
						}
					}
				}
			}
			System.out.println();
		}
	}

	private Point kysyKoordinaatit() {

		int x = -1;
		int y = -1;
		boolean kelpaa = false;

		do {
			try {
				System.out.print("X: ");
				x = Integer.parseInt(skan.nextLine());
				if (x >= 0 && x < ruudukko.annaLeveys()) {
					kelpaa = true;
				}
			}
			catch (NumberFormatException nfe) {
			}
		} while (!kelpaa);

		kelpaa = false;

		do {
			try {
				System.out.print("Y: ");
				y = Integer.parseInt(skan.nextLine());
				if (y >= 0 && y < ruudukko.annaKorkeus()) {
					kelpaa = true;
				}
			}
			catch (NumberFormatException nfe) {
			}
		} while (!kelpaa);

		return new Point(x, y);
	}

	private void avaaKaikkiMiinat() {

		for (int x = 0; x < ruudukko.annaLeveys(); x++) {
			for (int y = 0; y < ruudukko.annaKorkeus(); y++) {

				if (!ruudukko.onAuki(x, y) && ruudukko.onMiina(x, y)) {
					ruudukko.avaa(x, y);
				}
			}
		}
	}

	public static void main(String[] args) {

		new Miinatesti(10, 6, 15);
	}
}

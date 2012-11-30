/** Enum kaikkia vaikeusasteita varten. */
public enum Vaikeusaste {
	TASO1(10, 10, 10, "Puudeli"), TASO2(15, 15, 30, "Kultainen noutaja"),
	TASO3(12, 12, 30, "Rottweiler"), TASO4(20, 20, 80, "Dobermanni");

	/** Kuinka leveä ruudukko tässä vaikeusasteessa luodaan */
	public final int leveys;
	/** Kuinka korkea ruudukko tässä vaikeusasteessa luodaan */
	public final int korkeus;
	/** Kuinka monta miinaa vaikeusasteessa on */
	public final int miinoja;
	/** Vaikeusasteilla on aina joku hieno nimi myös. */
	public final String nimi;

	Vaikeusaste(int leveys, int korkeus, int miinoja, String nimi) {
		this.leveys = leveys;
		this.korkeus = korkeus;
		this.miinoja = miinoja;
		this.nimi = nimi;
	}
}
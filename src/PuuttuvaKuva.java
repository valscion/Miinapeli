import java.awt.*;

import javax.swing.Icon;

/**
 * Puuttuva kuvake on punainen ruksi pienessä boksissa.
 */
public class PuuttuvaKuva implements Icon {

	private int leveys = 16;
	private int korkeus = 16;

	private BasicStroke stroke = new BasicStroke(1);

	/** Luo kuvan, joka kuvaa puuttuvaa kuvaketta. */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2d = (Graphics2D) g.create();

		// Tausta
		g2d.setColor(Color.WHITE);
		g2d.fillRect(x + 1, y + 1, leveys - 2, korkeus - 2);

		// Reunat
		g2d.setColor(Color.BLACK);
		g2d.drawRect(x + 1, y + 1, leveys - 2, korkeus - 2);

		// Ruksi
		g2d.setColor(Color.RED);

		g2d.setStroke(stroke);
		g2d.drawLine(x + 5, y + 5, x + leveys - 5, y + korkeus - 5);
		g2d.drawLine(x + 5, y + korkeus - 5, x + leveys - 5, y + 5);

		g2d.dispose();
	}

	@Override
	public int getIconWidth() {
		return leveys;
	}

	@Override
	public int getIconHeight() {
		return korkeus;
	}

}

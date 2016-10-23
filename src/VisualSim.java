import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;

public class VisualSim extends JComponent {

	private static final long serialVersionUID = 1L;
	private Model mod;

	public VisualSim(Model mod) {
		this.mod = mod;
		Thread simThread = new Thread(new Runnable() {
			public void run() {
				double fps = mod.getFps();
				double step = mod.getStep();
				double t = 0;
				double counter = 0;
				double delta;
				double last = System.nanoTime();
				double frameTime;
				for (SimObject s : mod.getObjs()) {s.setScale(mod.getScale());}

				while (true) {
					


					for (Field e : mod.getFields()) {
						e.action();
					}
					
					for (RigidBody e : mod.getRigids()) {
						e.action();
					}
					
					for (FlexibelBody e : mod.getFlexis()) {
						e.action();
					}
					
				
					Methods.dynamics(mod, t);
					
					
					t += step;
					counter += step;
					if (counter >= (1 / fps)) {
						
						

						frameTime = System.nanoTime() - last;
						delta = ((1 / fps) - (frameTime / 1000000000)) * 1000;
						if (delta > 0) {
							try {
								Thread.sleep((long) delta);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}

						}
						last = System.nanoTime();
						repaint();
						counter = 0;
					}
				}
			}

		});

		simThread.start();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		double w = mod.getWinWidth();
		double h = mod.getWinHeigth();
		double scale = mod.getScale();

		g2.translate(w / 2, h / 2);
		g2.scale(scale, -scale);

		// Coordinate axis
		float stroke = (float) (1/scale);
		g2.setStroke(new BasicStroke(stroke));
		Line2D x = new Line2D.Double(0, 0, 45/scale, 0);
		Line2D y = new Line2D.Double(0, 0, 0, 45/scale);
		g2.draw(x);
		g2.draw(y);

		for (int i = 0; i < mod.getVisuals().size(); i++) {
			mod.getVisuals().get(i).render(g2);
		}

	}

}

interface Visual {
}
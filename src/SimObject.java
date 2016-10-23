import java.awt.Graphics2D;

abstract public class SimObject {

	// Private fields
	private boolean log;
	private int type;
	private double scale;
	private Model mod;

	public Model getMod() {
		return mod;
	}

	public void setMod(Model mod) {
		this.mod = mod;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double[] getCircleData() {return null;} // Give center and radius of
												// circle, null if type == 0 or
												// 1

	public double[] getLinesData() {return null;} // Give lines as list of x- and
												// y's, null if type == 0 or 2

	// Interface
	public boolean getLog() {
		return log;
	}

	public void setLog(boolean newLog) {
		log = newLog;
	}

	public int getType() {
		return type;
	}

	public void setType(int newType) {
		type = newType;
	}// Geometry: 0 equals non-geometric
		// 1 equals lines
		// 2 equals circle

	public void init() {
	}

	public void action() {
	}

	public void render(Graphics2D g2) {
	}

}

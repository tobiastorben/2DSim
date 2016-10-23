public class ForceField extends Field {

	private double Fx;
	private double Fy;

	public ForceField(double Fx, double Fy) {
		this.Fx = Fx;
		this.Fy = Fy;
	}

	public double getFx() {
		return Fx;
	}

	public void setFx(double fx) {
		Fx = fx;
	}

	public double getFy() {
		return Fy;
	}

	public void setFy(double fy) {
		Fy = fy;
	}

}

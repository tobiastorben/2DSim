public class ConstantGravity extends Field {

	private double Ax;
	private double Ay;

	public ConstantGravity(double Ax, double Ay) {
		this.Ax = Ax;
		this.Ay = Ay;
	}

	public double getAx() {
		return Ax;
	}

	public void setAx(double ax) {
		Ax = ax;
	}

	public double getAy() {
		return Ay;
	}

	public void setAy(double ay) {
		Ay = ay;
	}
}

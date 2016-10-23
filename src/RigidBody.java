public class RigidBody extends SimObject {


	private double[] pos;
	private double[] vel;
	private double ang;
	private double angVel;
	private double mass;
	private double I;
	private double[] sigmaF;
	private double sigmaM;
	private double myS;
	private double myD;
	private double COR;// Coefficient of restitution

	public double getCOR() {
		return COR;
	}

	public void setCOR(double cOR) {
		COR = cOR;
	}

	public double getMyS() {
		return myS;
	}

	public void setMyS(double myS) {
		this.myS = myS;
	}

	public double getMyD() {
		return myD;
	}

	public void setMyD(double myD) {
		this.myD = myD;
	}

	public double getI() {
		return I;
	}

	public void setI(double i) {
		I = i;
	}

	public double getSigmaM() {
		return sigmaM;
	}

	public void setSigmaM(double sigmaM) {
		this.sigmaM = sigmaM;
	}

	private boolean colliding;

	public void dynCollisionResponse(RigidBody k, double[] ict) {
	}

	public double[] getNormal(double[] contactPoint) {

		return new double[2];

	}

	public void calcForce(double t) {
	}

	public double[] getPos() {
		return pos;
	}

	public void setPos(double[] pos) {
		this.pos = pos;
	}

	public double[] getVel() {
		return vel;
	}

	public void setVel(double[] vel) {
		this.vel = vel;
	}

	public double getAng() {
		return ang;
	}

	public void setAng(double ang) {
		this.ang = ang;
	}

	public double getAngVel() {
		return angVel;
	}

	public void setAngVel(double angvel) {
		this.angVel = angvel;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double[] getSigmaF() {
		return sigmaF;
	}

	public void setSigmaF(double[] sigmaF) {
		this.sigmaF = sigmaF;
	}


}

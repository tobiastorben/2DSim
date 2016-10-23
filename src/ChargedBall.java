import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class ChargedBall extends RigidBody implements Visual {

	// private fields
	private ArrayList<ChargedBall> chargedBalls;
	private InPort force;

	// Attributes
	public final double charge;
	public final double radius;
	public final Color color;
	static final double k = 8.987551787e9;

	// Constructor
	public ChargedBall(double m, double Q, double r, double COR, double myS, double myD,
			double[] initPos, double[] initVel,double initAng, double initAngVel, boolean l, Color color) {
		setMass(m);
		charge = Q;
		radius = r;
		setSigmaF(new double[2]);
		chargedBalls = new ArrayList<ChargedBall>();
		setPos(initPos);
		setVel(initVel);
		this.setLog(l);
		force = new InPort(2);
		this.color = color;
		setType(2);
		setCOR(COR);
		setAngVel(initAngVel);
		setAng(initAng);
		setI(2*getMass()*radius*radius/5);
		setMyS(myS);
		setMyD(myD);
	}

	// public functions

	public double[] getCircleData() {
		double[] data = { getPos()[0], getPos()[1], radius };
		return data;
	}

	public double[] getNormal(double[] contactPoint) {

		double[] n = new double[2];

		n[0] = contactPoint[0] - getPos()[0];
		n[1] = contactPoint[1] - getPos()[1];
		double length = Math.sqrt(n[0] * n[0] + n[1] * n[1]);
		if (length != 0) {
			n[0] = n[0] / length;
			n[1] = n[1] / length;

		}
		return n;
	}


	public void init(Model mod) {

		chargedBalls = findChargedBalls(mod.getRigids());

	}

	public ArrayList<ChargedBall> getChargedBalls() {
		return chargedBalls;
	}

	public void setChargedBalls(ArrayList<ChargedBall> chargedBalls) {
		this.chargedBalls = chargedBalls;
	}

	public InPort getForce() {
		return force;
	}

	public void setForce(InPort force) {
		this.force = force;
	}

	public double getCharge() {
		return charge;
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	public void calcForce(double t) {

		double[] r = new double[2];
		double[] otherPos = { 0, 0 };
		ChargedBall otherBall;
		double[] F = { 0, 0 };
		double otherCharge = 0;
		double rSquared;
		Model mod = getMod();

		// Interference terms
		for (int i = 0; i < chargedBalls.size(); i++) {

			otherBall = chargedBalls.get(i);
			otherPos = otherBall.getPos();
			otherCharge = otherBall.charge;

			for (int e = 0; e < 2; e++) {

				r[e] = otherPos[e] - getPos()[e];
			}
			rSquared = (r[0] * r[0] + r[1] * r[1]);
			F[0] = F[0]
					- ((k * charge * otherCharge) / (Math.pow(rSquared, 1.5)))
					* r[0];
			F[1] = F[1]
					- ((k * charge * otherCharge) / (Math.pow(rSquared, 1.5)))
					* r[1];

		}

		// Port terms
		double[] in = force.getValue();
		F[0] = F[0] + in[0];
		F[1] = F[1] + in[1];

		// Fields terms
		for (Field f : mod.getFields())
			if (f instanceof ForceField) {
				ForceField field = (ForceField) f;
				F[0] = F[0] + field.getFx();
				F[1] = F[1] + field.getFy();
			} else if (f instanceof ConstantGravity) {
				ConstantGravity G = (ConstantGravity) f;
				F[0] = F[0] + G.getAx() * getMass();
				F[1] = F[1] + G.getAy() * getMass();
			}

		setSigmaF(F);

	}

	private ArrayList<ChargedBall> findChargedBalls(ArrayList<RigidBody> objList) {

		ArrayList<ChargedBall> cb = new ArrayList<ChargedBall>();

		for (int i = 0; i < objList.size(); i++) {
			if (objList.get(i) instanceof ChargedBall && objList.get(i) != this) {
				cb.add((ChargedBall) objList.get(i));
			}

		}
		return cb;
	}


	public void render(Graphics2D g2) {

		Ellipse2D ball = new Ellipse2D.Double(getPos()[0] - radius, getPos()[1]
				- radius, radius * 2, radius * 2);
		Line2D r = new Line2D.Double(getPos()[0], getPos()[1], getPos()[0] + radius*Math.cos(getAng()), getPos()[1] + radius*Math.sin(getAng()));
		g2.setPaint(color);
		g2.fill(ball);
		g2.setPaint(Color.BLACK);
		g2.setStroke(new BasicStroke((float) (2/getScale())));
		g2.draw(ball);
		g2.setStroke(new BasicStroke((float) (1/getScale())));
		g2.draw(r);
	}
}

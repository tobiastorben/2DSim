import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class Plane extends StaticBody implements Visual {

	private double[] p1;
	private double[] p2;
	private Color color;

	public Plane(double[] p1, double[] p2, Color color) {
		this.color = color;
		this.p1 = p1;
		this.p2 = p2;
		Path2D shape = new Path2D.Double();
		shape.moveTo(p1[0], p1[1]);
		shape.lineTo(p1[0] - 0.001, p1[1] + 0.002);
		shape.lineTo(p2[0], p2[1]);
		shape.closePath();
		setType(1);
	}

	public double[] getLinesData() {
		return new double[] { p1[0], p1[1], p2[0], p2[1] };
	}

	public double[] getNormal(double[] ict) {
		double[] n = { p1[0] - p2[0], p1[1] - p2[1] };
		n = new double[] { -n[1], n[0] };
		double length = Math.sqrt(n[0] * n[0] + n[1] * n[1]);
		return new double[] { n[0] / length, n[1] / length };
	}

	public void staticCollisionResponse(RigidBody other, double[] ict) {

		double[] n = getNormal(ict);
		if (Methods.dot(n, Methods.sub(ict, other.getPos())) < 0)
			n = Methods.times(n, -1);
		double I = other.getI();
		double COR = other.getCOR();
		double[] pos = other.getPos();
		double[] vel = other.getVel();
		double angVel = other.getAngVel();
		double[] r = Methods.sub(ict, pos);
		double[] velPoint = Methods.add(vel,
				Methods.cross(new double[] { 0, 0, angVel }, Methods.to3D(r)));
		double[] T = {-n[1], n[0]};
		if (Methods.dot(T,Methods.times(velPoint,-1)) < 0) T = Methods.times(T,-1);
		double relVel = -Methods.dot(velPoint, n);
		double c = Methods.dot(n, Methods.times(Methods.cross(
				Methods.cross(Methods.to3D(r), Methods.to3D(n)),
				Methods.to3D(r)), 1 / I));
		double Jr = (-relVel * (COR + 1)) / ((1 / other.getMass()) + c);
		
		double myS = other.getMyS();
		double myD = other.getMyD();
		double Jf = 0;
		if (myS != 0 || myD != 0) {
			double Js = myS*Jr;
			double Jd = myD*Jr;
			if (Methods.dot(Methods.times(velPoint,-1), T) == 0.0f || other.getMass()*Methods.dot(Methods.times(velPoint,-1), T) <= Js){
				Jf = -other.getMass()*Methods.dot(Methods.times(velPoint,-1), T);
				}
			else {
				Jf = -Jd;
			}
			
		}
		
		double[] J = Methods.add(Methods.times(n,Jr), Methods.times(T,Jf/10));

		double[] newVel = Methods.sub(vel, Methods.times(J, 1 / other.getMass()));
		double newAngVel = other.getAngVel() - Methods.planarCross(r, J)/ I;
		other.setVel(newVel);
		other.setAngVel(newAngVel);

	}

	public void render(Graphics2D g2) {
		Line2D plane = new Line2D.Double(p1[0], p1[1], p2[0], p2[1]);
		g2.setStroke(new BasicStroke((float) (5 / getScale())));
		g2.setColor(color);
		g2.draw(plane);
	}
}

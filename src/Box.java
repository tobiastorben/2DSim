import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

public class Box extends RigidBody implements Visual {

	private double[] p1;
	private double[] p2;
	private double[] p3;
	private double[] p4;
	private Color color;
	private double width;
	private double heigth;
	private InPort force;

	public Box(double mass, double width, double heigth, double[] initPos,
			double[] initVel, double initAng, double initAngVel, double COR, double myS, double myD, Color color) {
		setVel(initVel);
		setPos(initPos);
		setAng(initAng);
		setAngVel(initAngVel);
		setMass(mass);
		p1 = new double[] { initPos[0] - width / 2, initPos[1] + heigth / 2 };
		p2 = new double[] { initPos[0] + width / 2, initPos[1] + heigth / 2 };
		p3 = new double[] { initPos[0] + width / 2, initPos[1] - heigth / 2 };
		p4 = new double[] { initPos[0] - width / 2, initPos[1] - heigth / 2 };
		setType(1);
		this.color = color;
		this.width = width;
		this.heigth = heigth;
		setAngVel(initAngVel);
		setAng(initAng);
		setSigmaF(new double[2]);
		force = new InPort(2);
		setCOR(COR);
		setI(getMass()*(width*width+heigth*heigth)/12);
		setMyS(myS);
		setMyD(myD);
		
	}
	
	public double[] getNormal(double[] ict) {
		double d1 = Line2D.ptSegDist(p1[0], p1[1], p2[0], p2[1] , ict[0], ict[1]);
		double d2 = Line2D.ptSegDist(p3[0], p3[1], p2[0], p2[1] , ict[0], ict[1]);
		double d3 = Line2D.ptSegDist(p3[0], p3[1], p4[0], p4[1] , ict[0], ict[1]);
		double d4 = Line2D.ptSegDist(p1[0], p1[1], p4[0], p4[1] , ict[0], ict[1]);
		double[] d = {d1,d2,d3,d4};
		double[] lines = getLinesData();
		double record = 1e15;
		
		int i = 0;
		
		for (int k = 0; k < 4; k++) {
			if (d[k]<record) {
				record = d[k];
				i = k*4;
			} 
		}
		double[] n = Methods.getUnitNormal(lines[i], lines[i+1], lines[i+2], lines[i+3]);
		if (Methods.dot(n, Methods.sub(ict, getPos())) < 0) return Methods.times(n,-1);
		
		return n;
	}

	public double[] getLinesData() {
		return new double[] { p1[0], p1[1], p2[0], p2[1], p2[0], p2[1], p3[0],
				p3[1], p3[0], p3[1], p4[0], p4[1], p4[0], p4[1], p1[0], p1[1] };
	}

	public void render(Graphics2D g2) {
		g2.setPaint(Color.BLACK);
		Path2D box = new Path2D.Double();
		box.moveTo(p1[0], p1[1]);
		box.lineTo(p2[0], p2[1]);
		box.lineTo(p3[0], p3[1]);
		box.lineTo(p4[0], p4[1]);
		box.closePath();
		g2.setStroke(new BasicStroke((float) (3/getScale())));
		g2.draw(box);
		g2.setPaint(color);
		g2.fill(box);
	}

	public void calcForce(double t) {
		double[] F = new double[2];
		Model mod = getMod();
		
		// Port terms
		double[] in = force.getValue();
		F[0] = F[0] + in[0];
		F[1] = F[1] + in[1];
		
		// Field terms
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

	public double[] getP1() {
		return p1;
	}

	public void setP1(double[] p1) {
		this.p1 = p1;
	}

	public double[] getP2() {
		return p2;
	}

	public void setP2(double[] p2) {
		this.p2 = p2;
	}

	public double[] getP3() {
		return p3;
	}

	public void setP3(double[] p3) {
		this.p3 = p3;
	}

	public double[] getP4() {
		return p4;
	}

	public void setP4(double[] p4) {
		this.p4 = p4;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeigth() {
		return heigth;
	}

	public void setHeigth(double heigth) {
		this.heigth = heigth;
	}

	public InPort getForce() {
		return force;
	}

	public void setForce(InPort force) {
		this.force = force;
	}
	
	public void action() {
		double[] pos = getPos();
		double ang = getAng();
		setP1(Methods.add(pos, Methods.rotate(new double[] {-width/2, heigth/2}, ang)));
		setP2(Methods.add(pos, Methods.rotate(new double[] {width/2, heigth/2}, ang)));
		setP3(Methods.add(pos, Methods.rotate(new double[] {width/2, -heigth/2}, ang)));
		setP4(Methods.add(pos, Methods.rotate(new double[] {-width/2,-heigth/2}, ang)));
		
	}

}

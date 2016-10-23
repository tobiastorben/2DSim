import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
// TODO : FIX INSTABILITY
//Differential equation based cable model

public class Cable extends FlexibelBody implements Visual {

	private double[] end1;
	private double[] end2;
	private ArrayList<double[]> pointLoads;
	private ArrayList<double[]> r;
	private double elementLength;
	private double[] lines;
	private double k;
	private double E;
	private double I;
	private double A;

	public Cable(double mass, double[] end1, double[] end2,
			ArrayList<double[]> initElementPos,
			ArrayList<double[]> initElementVel, ArrayList<double[]> pointLoads,
			double COR, double myS, double myD, double damping, double A,
			double E, double I, Color color) {

		setType(2);
		setMass(mass);
		this.end1 = end1;
		this.end2 = end2;
		setElementPos(initElementPos);
		setElementVel(initElementVel);
		this.pointLoads = pointLoads;
		setMyS(myS);
		setMyD(myD);
		setCOR(COR);
		setDamping(damping);
		this.E = E;
		this.A = A;
		this.I = I;
		setColor(color);
		elementLength = Methods.length(new double[] {
				end1[0] - getElementPos().get(0)[0],
				end1[1] - getElementPos().get(0)[1] });
		this.k = E*A/elementLength;
		setNumElems(getElementPos().size() + 1);
		setElementMass(getMass() / (getNumElems()-1));
		calcLines();

	}

	public Cable(double mass, double startX, double endX,
			ArrayList<double[]> initElementVel, ArrayList<double[]> pointLoads,
			double COR, double myS, double myD, double damping, double A,
			double E, double I, Color color, double ds, double a, double b, double c) {

		setType(2);
		setMass(mass);
		ArrayList<double[]> points = initParabola(a, b, c, startX, endX, ds);
		end1 = points.get(0);
		end2 = points.get(points.size() - 1);
		points.remove(0);
		points.remove(points.size() - 1);
		setElementPos(points);
		setNumElems(getElementPos().size() + 1);
		setElementVel(new ArrayList<double[]>(getNumElems() - 1));
		this.pointLoads = new ArrayList<double[]>(getNumElems() - 1);
		for (int i = 0; i < getNumElems(); i++)
			getElementVel().add(new double[2]);
		if (initElementVel != null) {
			for (int i = 0; i < initElementVel.size(); i++)
				getElementVel().set(
						(int) initElementVel.get(i)[0],
						new double[] { initElementVel.get(i)[1],
								initElementVel.get(i)[2] });
		}

		for (int i = 0; i < getNumElems() - 1; i++)
			this.pointLoads.add(new double[2]);
		if (pointLoads != null) {
			for (int i = 0; i < pointLoads.size(); i++)
				this.pointLoads.set((int) pointLoads.get(i)[0], new double[] {
						pointLoads.get(i)[1], pointLoads.get(i)[2] });
		}
		setMyS(myS);
		setMyD(myD);
		setCOR(COR);
		setDamping(damping);
		this.E = E;
		this.I = I;
		this.A = A;
		this.k = E*A/ds;
		setColor(color);
		elementLength = ds;
		setElementMass(getMass() / (getNumElems()-1));
		calcLines();

	}
	
	
	public Cable(double mass, double startX, double startY, int numElems,
			ArrayList<double[]> initElementVel, ArrayList<double[]> pointLoads,
			double COR, double myS, double myD, double damping, double A,
			double E, double I, double T0, Color color, double ds) {

		setType(2);
		setMass(mass);
		setNumElems(numElems);
		ArrayList<double[]> points = initElasticCatenary(T0, mass/(numElems*ds),ds,E*A, startX, startY, numElems);
		end1 = points.get(0);
		end2 = points.get(points.size() - 1);
		points.remove(0);
		points.remove(points.size() - 1);
		setElementPos(points);
		setElementVel(new ArrayList<double[]>(getNumElems() - 1));
		this.pointLoads = new ArrayList<double[]>(getNumElems() - 1);
		for (int i = 0; i < getNumElems(); i++)
			getElementVel().add(new double[2]);
		if (initElementVel != null) {
			for (int i = 0; i < initElementVel.size(); i++)
				getElementVel().set(
						(int) initElementVel.get(i)[0],
						new double[] { initElementVel.get(i)[1],
								initElementVel.get(i)[2] });
		}

		for (int i = 0; i < getNumElems() - 1; i++)
			this.pointLoads.add(new double[2]);
		if (pointLoads != null) {
			for (int i = 0; i < pointLoads.size(); i++)
				this.pointLoads.set((int) pointLoads.get(i)[0], new double[] {
						pointLoads.get(i)[1], pointLoads.get(i)[2] });
		}
		setMyS(myS);
		setMyD(myD);
		setCOR(COR);
		setDamping(damping);
		this.E = E;
		this.I = I;
		this.A = A;
		this.k = E*A/ds;
		setColor(color);
		elementLength = ds;
		setElementMass(getMass() / (getNumElems()-1));
		calcLines();


	}

	public void calcForce(double t) {

		Model mod = getMod();

		// Calculate element loads
		setLoads(new ArrayList<double[]>(getNumElems() - 1));

		for (int i = 0; i < getNumElems() - 1; i++) {
			getLoads().add(new double[2]);
		}

		if (pointLoads != null) {
			for (int i = 0; i < pointLoads.size(); i++) {
				getLoads().set(i, pointLoads.get(i));
			}
		}

		for (int i = 0; i < getLoads().size(); i++) {

			for (Field f : mod.getFields())
				if (f instanceof ForceField) {
					ForceField field = (ForceField) f;
					getLoads()
							.set(i,
									Methods.add(
											getLoads().get(i),
											new double[] { field.getFx(),
													field.getFy() }));
				} else if (f instanceof ConstantGravity) {
					ConstantGravity G = (ConstantGravity) f;
					getLoads().set(
							i,
							Methods.add(
									getLoads().get(i),
									new double[] {
											G.getAx() * getElementMass(),
											G.getAy() * getElementMass() }));
				}
		}
		// Tensile forces for 1st element
		double delta = Methods
				.length(Methods.sub(end1, getElementPos().get(0)))
				- elementLength;
		getLoads().set(
				0,
				Methods.add(getLoads().get(0),
						Methods.times(r.get(0), -k * delta)));
		delta = Methods.length(Methods.sub(getElementPos().get(1),
				getElementPos().get(0))) - elementLength;
		getLoads().set(
				0,
				Methods.add(getLoads().get(0),
						Methods.times(r.get(1), k * delta)));

		// Bending moment for 1st element
		double[] n1 = { -r.get(1)[1], r.get(1)[0] };
		double[] n2 = { -r.get(0)[1], r.get(0)[0] };
		double theta = Math.atan2(r.get(1)[1], r.get(1)[0])
				- Math.atan2(r.get(0)[1], r.get(0)[0]);
		double kappa = theta / elementLength;
		getLoads().set(
				0,
				Methods.add(getLoads().get(0), Methods.add(
						Methods.times(n1, E*I * kappa / elementLength),
						Methods.times(n2, E*I * kappa / elementLength))));
		getLoads().set(
				1,
				Methods.add(getLoads().get(1),
						Methods.times(n1, -E*I * kappa / elementLength)));

		for (int i = 1; i < getNumElems() - 2; i++) {

			// Tensile forces for intermediate elements
			delta = Methods.length(Methods.sub(getElementPos().get(i - 1),
					getElementPos().get(i))) - elementLength;

			getLoads().set(
					i,
					Methods.add(getLoads().get(i),
							Methods.times(r.get(i), -k * delta)));
			delta = Methods.length(Methods.sub(getElementPos().get(i),
					getElementPos().get(i + 1))) - elementLength;
			getLoads().set(
					i,
					Methods.add(getLoads().get(i),
							Methods.times(r.get(i + 1), k * delta)));

			// Bending moment for other elements
			n1 = new double[] { -r.get(i + 1)[1], r.get(i + 1)[0] };
			n2 = new double[] { -r.get(i)[1], r.get(i)[0] };
			theta = Math.atan2(r.get(i + 1)[1], r.get(i + 1)[0])
					- Math.atan2(r.get(i)[1], r.get(i)[0]);
			kappa = theta / elementLength;
			getLoads().set(
					i - 1,
					Methods.add(getLoads().get(i - 1),
							Methods.times(n2, -E*I * kappa / elementLength)));

//			if (i == (int) (getNumElems() / 2))
//				System.out.println("M = " + (E*I * kappa));
			getLoads().set(
					i,
					Methods.add(
							getLoads().get(i),
							Methods.add(
									Methods.times(n1, E*I * kappa
											/ elementLength),
									Methods.times(n2, E*I * kappa
											/ elementLength))));
			getLoads().set(
					i + 1,
					Methods.add(getLoads().get(i + 1),
							Methods.times(n1, -E*I * kappa / elementLength)));

		}
		// Tensile forces for last element
		delta = Methods.length(Methods.sub(
				getElementPos().get(getNumElems() - 2),
				getElementPos().get(getNumElems() - 3)))
				- elementLength;
		getLoads().set(
				getNumElems() - 2,
				Methods.add(getLoads().get(getNumElems() - 2),
						Methods.times(r.get(getNumElems() - 2), -k * delta)));
		delta = Methods.length(Methods.sub(end2,
				getElementPos().get(getNumElems() - 2)))
				- elementLength;
		getLoads().set(
				getNumElems() - 2,
				Methods.add(getLoads().get(getNumElems() - 2),
						Methods.times(r.get(getNumElems() - 1), k * delta)));

		// Bending moment for last element
		n1 = new double[] { -r.get(getNumElems() - 1)[1],
				r.get(getNumElems() - 1)[0] };
		n2 = new double[] { -r.get(getNumElems() - 2)[1],
				r.get(getNumElems() - 2)[0] };
		theta = Math.atan2(r.get(getNumElems() - 1)[1],
				r.get(getNumElems() - 1)[0])
				- Math.atan2(r.get(getNumElems() - 2)[1],
						r.get(getNumElems() - 2)[0]);
		kappa = theta / elementLength;
		getLoads().set(
				getNumElems() - 3,
				Methods.add(getLoads().get(getNumElems() - 3),
						Methods.times(n2, -E*I * kappa / elementLength)));
		getLoads().set(
				getNumElems() - 2,
				Methods.add(getLoads().get(getNumElems() - 2), Methods.add(
						Methods.times(n1, E*I * kappa / elementLength),
						Methods.times(n2, E*I * kappa / elementLength))));

	}

	public void action() {
		calcLines();
	}

	public void render(Graphics2D g2) {
		g2.setStroke(new BasicStroke((float) (2 / getScale())));
		g2.setPaint(getColor());
		Line2D l;


		for (int i = 0; i < lines.length; i += 4) {

			l = new Line2D.Double(lines[i], lines[i + 1], lines[i + 2],
					lines[i + 3]);
			g2.draw(l);

		}
	}

	public void calcLines() {

		lines = new double[getNumElems() * 4];
		// Calc first line
		lines[0] = end1[0];
		lines[1] = end1[1];
		lines[2] = getElementPos().get(0)[0];
		lines[3] = getElementPos().get(0)[1];

		// Calc last line
		lines[getNumElems() * 4 - 1] = end2[1];
		lines[getNumElems() * 4 - 2] = end2[0];
		lines[getNumElems() * 4 - 3] = getElementPos().get(
				getElementPos().size() - 1)[1];
		lines[getNumElems() * 4 - 4] = getElementPos().get(
				getElementPos().size() - 1)[0];

		// Calc intermediate lines
		for (int i = 0; i < getElementPos().size() - 1; i += 1) {

			lines[3 + 4 * i + 1] = getElementPos().get(i)[0];
			lines[3 + 4 * i + 2] = getElementPos().get(i)[1];
			lines[3 + 4 * i + 3] = getElementPos().get(i + 1)[0];
			lines[3 + 4 * i + 4] = getElementPos().get(i + 1)[1];

		}

		setLines(lines);

		ArrayList<double[]> r = new ArrayList<double[]>(getNumElems() * 4);
		// Calculate vectors between elements
		for (int i = 0; i < lines.length; i += 4) {
			r.add(Methods.times(
					new double[] { lines[i + 2] - lines[i],
							lines[i + 3] - lines[i + 1] },
					1 / Methods.length(new double[] { lines[i + 2] - lines[i],
							lines[i + 3] - lines[i + 1] })));
		}
		this.r = r;
	}

	public ArrayList<double[]> initParabola(double a, double b, double c,
			double startX, double endX, double ds) { // Define initial cable
														// shape of ax^2 + bx +
														// c

		ArrayList<double[]> points = new ArrayList<double[]>();

		points.add(new double[] { startX, a * startX * startX + b * startX + c });

		int i = 0;
		double phi;
		double x = points.get(i)[0];
		double y;

		while (x < endX) {

			x = points.get(i)[0];
			y = points.get(i)[1];
			phi = Math.atan(2 * a * x + b);
			points.add(new double[] { x + Math.cos(phi) * ds,
					y + Math.sin(phi) * ds });
			i++;
		}

		return points;

	}
	// TODO : Fix T0 and start point and loop variable
	public ArrayList<double[]> initElasticCatenary(double T0, double lineDens, double ds, double E,
			double startX, double startY, int numElems) { // Define initial cable shape of the elastic catenary

		double a = T0/(9.81*lineDens);
		double x,y,p;
		
		
		ArrayList<double[]> points = new ArrayList<double[]>();

		//points.add(new double[] {startX, startY});
		
		for (int i = -50; i < 51; i++) {
			
		p = i*ds;	
			
		x =  a* Math.log((p/a) + Math.sqrt((p/a)*(p/a) + 1.0)) + T0*p/E;
		
		y = Math.sqrt(a*a+ p*p) + 0.5*T0*p*p/(E*a);
		
		points.add(new double[] {x,y});
		
		}

		return points;

	}

	
	public double[] getEnd1() {
		return end1;
	}

	public void setEnd1(double[] end1) {
		this.end1 = end1;
	}

	public double[] getEnd2() {
		return end2;
	}

	public void setEnd2(double[] end2) {
		this.end2 = end2;
	}

	public ArrayList<double[]> getPointLoads() {
		return pointLoads;
	}

	public void setPointLoads(ArrayList<double[]> pointLoads) {
		this.pointLoads = pointLoads;
	}

	public ArrayList<double[]> getR() {
		return r;
	}

	public void setR(ArrayList<double[]> r) {
		this.r = r;
	}

	public double getElementLength() {
		return elementLength;
	}

	public void setElementLength(double elementLength) {
		this.elementLength = elementLength;
	}

	public double[] getLines() {
		return lines;
	}

	public void setLines(double[] lines) {
		this.lines = lines;
	}

	public double[] getLinesData() {

		return lines;
	}
}

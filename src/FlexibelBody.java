import java.awt.Color;
import java.util.ArrayList;

public class FlexibelBody extends SimObject {

	private ArrayList<double[]> elementPos;
	private ArrayList<double[]> elementVel;
	private ArrayList<double[]> loads;
	private ArrayList<double[]> r;
	public ArrayList<double[]> getR() {
		return r;
	}

	public void setR(ArrayList<double[]> r) {
		this.r = r;
	}

	private int numElems;
	private Color color;
	private double elementMass;
	private double mass;
	private double myS;
	private double myD;
	private double COR;
	private double damping;
	
	
	public double getDamping() {
		return damping;
	}

	public void setDamping(double damping) {
		this.damping = damping;
	}

	public ArrayList<double[]> getElementPos() {
		return elementPos;
	}

	public void setElementPos(ArrayList<double[]> elementPos) {
		this.elementPos = elementPos;
	}

	public ArrayList<double[]> getElementVel() {
		return elementVel;
	}

	public void setElementVel(ArrayList<double[]> elementVel) {
		this.elementVel = elementVel;
	}

	public ArrayList<double[]> getLoads() {
		return loads;
	}

	public void setLoads(ArrayList<double[]> loads) {
		this.loads = loads;
	}

	public int getNumElems() {
		return numElems;
	}

	public void setNumElems(int numElems) {
		this.numElems = numElems;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getElementMass() {
		return elementMass;
	}

	public void setElementMass(double elementMass) {
		this.elementMass = elementMass;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
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

	public double getCOR() {
		return COR;
	}

	public void setCOR(double cOR) {
		COR = cOR;
	}

	public void dynCollisionResponse(FlexibelBody k, int elementNr) {
	}

	public void calcForce(double t) {
	}

}

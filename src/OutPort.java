
public class OutPort {
	
private int portWidth;
private double[] value;
private double value1;

public OutPort(int w){
	portWidth = w;
	value = new double[w];
	}

public OutPort(int w, double[] val){
	portWidth = w;
	value = val;
	}

public OutPort(double val){
	portWidth = 1;
	value1 = val;
	}

public double[] giveValue() {return value;}
public double giveValue(boolean isSingular) {return value1;}
public void update(double[] newVal) {value = newVal;}
public void update(double newVal) {value1 = newVal;}


	
}

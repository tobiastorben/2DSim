
public class InPort {
	
private OutPort signalIn;//Connected outport
private int portWidth;
private double[] constVal;
private double constVal1;

public InPort(int w) {
	portWidth = w;
	constVal =  new double[w];
	constVal1 = 0;
}

public InPort(int w, double[] cv) {
	portWidth = w;
	constVal = cv;
}

public InPort(double cv) {
	portWidth = 1;
	constVal1 = cv;
}

public double[] getValue() {
	for (int i = 0; i < portWidth; i++){
		if (constVal[i] > 0) {return constVal;}
	}
	return this.signalIn == null ? new double[portWidth] : signalIn.giveValue();//Get value from port
}

public double getValue(boolean isSingular) {
	
		if (constVal1 > 0) {return constVal1;}

	return this.signalIn == null ? 0 : signalIn.giveValue(true);//Get value from port
}

public void setValue(double[] val) {constVal = val;}

public void setValue(double val) {constVal1 = val;}

public void connect(OutPort newPort) {signalIn = newPort;}

}

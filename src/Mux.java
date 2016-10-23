
public class Mux extends SimObject {

	private InPort[] in;
	private OutPort out;
	private int portWidth;
	
	public Mux(int pW) {
		
		portWidth = pW;
		in = new InPort[portWidth];
		for (int i = 0; i < pW; i++) {
			in[i] = new InPort(0.0);
		}
		out = new OutPort(portWidth);
	}
	
	public InPort[] getInPorts() {return in;}
	
	public OutPort getOutPort() {return out;}
	
	public void action(double t, double step, double logStep) {
		
		double[] value = new double[portWidth];
		
		for (int i = 0; i < portWidth; i++) {
			value[i] = in[i].getValue(true);
			out.update(value);
			
		}
	}
}

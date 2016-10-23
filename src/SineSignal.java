
public class SineSignal extends SimObject {
	
	private double angVel;
	private double amplitude;
	private double phase;
	private OutPort signal;
	
	public SineSignal(double omega, double A, double phi) {
		
		angVel = omega;
		amplitude = A;
		phase = phi;
		signal = new OutPort(0.0);
	}
	
	public void action(double t, double dt, double logStep) {
		double val = amplitude*Math.sin(angVel*t + phase);
		signal.update(val);
	}
	
	public OutPort getOutPort() {return signal;}
}

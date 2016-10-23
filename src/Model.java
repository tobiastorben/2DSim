import java.util.ArrayList;

//This class contains the model structure for a particular simulation, along with the settings.
//TODO: Implement read-from-file constructor 
public class Model {

	// Private fields
	private double fps;
	private double step; // The integration step
	private double logStep;
	private boolean vis; // True = show visualisation off simulation
	private int winWidth;
	private int winHeigth;
	private double scale; // Pixels per meter in visualization
	private ArrayList<SimObject> objs; // All sim objects in model
	private ArrayList<RigidBody> rigids; // All dynamic bodies in model
	private ArrayList<StaticBody> statics;// All static bodies in model
	private ArrayList<FlexibelBody> flexis;
	private ArrayList<Field> fields;// All fields in model
	private ArrayList<SimObject> others;// Objects who don't fall into any
										// category
	private ArrayList<SimObject> visuals;// Objects who will be visualized
	
	
	public ArrayList<FlexibelBody> getFlexis() {
		return flexis;
	}

	public void setFlexis(ArrayList<FlexibelBody> flexis) {
		this.flexis = flexis;
	}

	
	
	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public void setFps(double fps) {
		this.fps = fps;
	}


	// Constructors
	public Model(ArrayList<SimObject> simObjs, double step, double fps, double logStep, double scale,
			boolean vis, int winWIDTH, int winHEIGTH) {
		
		this.objs = simObjs;
		this.fps = fps;
		this.step = step;
		this.logStep = logStep;
		this.scale = scale;
		this.vis = vis;
		this.winHeigth = winHEIGTH;
		this.winWidth = winWIDTH;
		rigids = new ArrayList<RigidBody>();
		statics = new ArrayList<StaticBody>();
		fields = new ArrayList<Field>();
		others = new ArrayList<SimObject>();
		visuals = new ArrayList<SimObject>();
		flexis = new ArrayList<FlexibelBody>();
		idfyObjs();
	}

	// Getters and setters
	public double getFps() {
		return fps;
	}
	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

	public double getLogStep() {
		return logStep;
	}

	public void setLogStep(double logStep) {
		this.logStep = logStep;
	}

	public boolean isVis() {
		return vis;
	}

	public void setVis(boolean vis) {
		this.vis = vis;
	}

	public int getWinWidth() {
		return winWidth;
	}

	public void setWinWidth(int winWidth) {
		this.winWidth = winWidth;
	}

	public int getWinHeigth() {
		return winHeigth;
	}

	public void setWinHeigth(int winHeigth) {
		this.winHeigth = winHeigth;
	}

	public ArrayList<SimObject> getObjs() {
		return objs;
	}

	public void setObjs(ArrayList<SimObject> objs) {
		this.objs = objs;
	}

	public ArrayList<RigidBody> getRigids() {
		return rigids;
	}

	public void setRigids(ArrayList<RigidBody> rigids) {
		this.rigids = rigids;
	}

	public ArrayList<StaticBody> getStatics() {
		return statics;
	}

	public void setStatics(ArrayList<StaticBody> statics) {
		this.statics = statics;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public ArrayList<SimObject> getOthers() {
		return others;
	}

	public void setOthers(ArrayList<SimObject> others) {
		this.others = others;
	}

	public ArrayList<SimObject> getVisuals() {
		return visuals;
	}

	public void setVisuals(ArrayList<SimObject> visuals) {
		this.visuals = visuals;
	}

	// Implementation
	private void idfyObjs() {
	for (int i = 0; i < this.objs.size(); i++) {
		SimObject obj = objs.get(i);
		if (obj instanceof Visual) visuals.add(obj);
		if (obj instanceof RigidBody) rigids.add((RigidBody) obj);
		if (obj instanceof FlexibelBody) flexis.add((FlexibelBody) obj);
		else if (obj instanceof StaticBody) statics.add((StaticBody) obj);
		else if (obj instanceof Field) fields.add((Field) obj);
		else others.add(obj);	
	}
}
}

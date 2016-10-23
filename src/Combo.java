
public class Combo {

private double value;
private double[] values;
private boolean is;
private String string;

public Combo(double val, double[] vals, boolean bool, String s) {
	
	value = val;
	values = vals;
	is = bool;
	string = s;
}

public double getValue() {
	return value;
}
public void setValue(double value) {
	this.value = value;
}
public double[] getValues() {
	return values;
}
public void setValues(double[] values) {
	this.values = values;
}
public boolean isTrue() {
	return is;
}
public void setBool(boolean is) {
	this.is = is;
}
public String getString() {
	return string;
}
public void setString(String string) {
	this.string = string;
}


}

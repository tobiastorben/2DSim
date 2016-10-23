//Depraceted for now
abstract public class Environment {
	
private double width; //[m]
private double height;//[m]
private boolean gravity;//Enable/Disable gravity

public double getWidth() {return width;}
public double getHeight() {return height;}
public void setHeigth(double newHeight) {height = newHeight;}
public void setWidth(double newWidth) {width = newWidth;}
public boolean getGravity() {return gravity;}
public void setGravity(boolean newGravity) {gravity = newGravity;}

public Environment(double w,double h,boolean g){
	width = w;
	height = h;
	gravity = g;
	}
}

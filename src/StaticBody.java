
public class StaticBody extends SimObject {

	public double[] getNormal(double[] contactPoint) {
		return new double[2];
	}

	public void staticCollisionResponse(RigidBody other, double[] ict) {
	}
}

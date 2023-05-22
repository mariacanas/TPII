package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

//Avanzando hacia un punto fijo

public class MovingTowardsFixedPoint implements ForceLaws {

	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g ) {
		if(c == null || g <= 0) {
			throw new IllegalArgumentException();
		}
		this.c = c;
		this.g = g;
		
	}
	
	@Override
	public void apply(List<Body> bodies) {
		for(int i = 0; i < bodies.size();i++) {
			Vector2D force = calcularFuerza(bodies.get(i));
			bodies.get(i).addForce(force);
		}
	}
	
	private Vector2D calcularFuerza(Body i) {
		Vector2D d = new Vector2D();
		
		d = c.minus(i.getPosition()).direction();
		return d.scale(i.getMass()*g);
		
	}
	
	public String toString() {
		return "Moving Towards: " + c + " with constant acceleration " + g ;
	}

}

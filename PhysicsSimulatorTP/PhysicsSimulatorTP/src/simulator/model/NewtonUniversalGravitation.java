package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

//Ley de Newton de la gravitación Universal

public class NewtonUniversalGravitation implements ForceLaws{

	private double G;
	
	public NewtonUniversalGravitation(double i) {
		if(i <= 0) {
			throw new IllegalArgumentException();
		}
		this.G = i;
	}
	@Override
	public void apply(List<Body> bodies) {
		for(int i=0; i < bodies.size();i++) {
			
			for(int j = 0; j < bodies.size();j++) {
				if(i!=j) {
					if(bodies.get(i).getMass()==0) {
						bodies.get(i).addForce(new Vector2D());
					}
					else {
						bodies.get(i).addForce(calcularFuerza(bodies.get(i),bodies.get(j)));
					}
				}
			}
		}
	}
	
	private Vector2D calcularFuerza(Body i,Body j) {
		double im = i.getMass();
		double jm = j.getMass();
		
		double distancep = i.getPosition().distanceTo(j.getPosition());
		distancep = distancep*distancep;
		
		double multi = im * jm;
		
		//Fuerza escalar
		double res = (G *multi) / distancep;
		
		//nuevo vector con la resta de pj-pi
		Vector2D dij = j.getPosition().minus(i.getPosition());
	
		dij = dij.direction();
		
		return dij.scale(res);
	}
	
	public String toString() {
		return "Newton´s Universal Gravitation with G=" + G;
	}

	
}

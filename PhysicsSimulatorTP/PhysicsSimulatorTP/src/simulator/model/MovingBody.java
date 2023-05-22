package simulator.model;

import org.json.JSONObject;

import simulator.factories.Builder;
import simulator.misc.Vector2D;

public class MovingBody extends Body{

	public MovingBody(String id, String gid, Vector2D p, Vector2D v, Double m) {
		super(id, gid, p,v, m);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void advance(double dt) {
		// TODO Auto-generated method stub
		if(m==0) {
			super.resetForce();
		}
		else {
			 Vector2D a = f.scale(1 / m);
		     Vector2D d = v.scale(dt).plus(a.scale(0.5 * dt * dt));
		     p = p.plus(d); //Pos
		     v = a.scale(dt).plus(v); //Vel
		}
	}

	
}

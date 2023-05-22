package simulator.model;

import simulator.misc.Vector2D;

public class StationaryBody extends Body{

	private double counter;
	
	public StationaryBody(String id, String gid, Vector2D p, Double m) {
		super(id, gid,p,new Vector2D(0,0), m);
		counter = 0;
	}

	@Override
	void advance(double dt) {
		// TODO Auto-generated method stub
		counter += dt;
	}

}

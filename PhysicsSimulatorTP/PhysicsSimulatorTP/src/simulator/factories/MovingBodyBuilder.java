package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;
import simulator.model.StationaryBody;

public class MovingBodyBuilder extends Builder<Body> {

	
	
	public MovingBodyBuilder() {
		super("mv_body", "Moving Body");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		if(data == null) {
			throw new IllegalArgumentException("Invalid value for createInstance of MovingBodybuilders");
		}
		if(data.has("id") && data.has("gid") && data.has("p") && data.has("v") && data.has("m")) {
			String id = data.getString("id");
			String gid = data.getString("gid");
			JSONArray vv = data.getJSONArray("v");
			JSONArray pp = data.getJSONArray("p");
			if( vv.length() != 2 || pp.length() != 2) {
				throw new IllegalArgumentException("Invalid value for createInstance of MovingBodybuilders");
			}
	        Vector2D v = new Vector2D(vv.getDouble(0), vv.getDouble(1));
	        Vector2D p = new Vector2D(pp.getDouble(0), pp.getDouble(1));
	        double m = data.getDouble("m");
	        return new MovingBody(id, gid,p, v, m);
		}
		
		throw new IllegalArgumentException("Invalid value for createInstance of MovingBodybuilders");
	}

	

}

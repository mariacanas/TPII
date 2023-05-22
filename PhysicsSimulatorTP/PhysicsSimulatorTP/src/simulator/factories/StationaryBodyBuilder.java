package simulator.factories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;
import simulator.model.StationaryBody;

public  class StationaryBodyBuilder extends Builder<Body>{

	public StationaryBodyBuilder() {
		super("st_body", "Stationary Builder");
	}

	@Override
	protected Body createInstance(JSONObject data){
		
		if(data == null) {
			throw new IllegalArgumentException("Invalid value for createInstance of StationaryBodyBuilder");
		}
		
		if(data.has("id") && data.has("gid") && data.has("p") && data.has("m")) {
			String id = data.getString("id");
			String gid = data.getString("gid");
			JSONArray pp = data.getJSONArray("p");
			if(pp.length() != 2) {
				throw new IllegalArgumentException("Invalid value for createInstance of StationaryBodyBuilder");
			}
	        Vector2D p = new Vector2D(data.getJSONArray("p").getDouble(0), data.getJSONArray("p").getDouble(1));
	        double m = data.getDouble("m");
	        return new StationaryBody(id, gid, p,m);
		}
		throw new IllegalArgumentException("Invalid value for createInstance of StationaryBodyBuilder");
	}


	

}

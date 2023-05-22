package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;
import simulator.model.NewtonUniversalGravitation;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving Towards Fixed Builder");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) throws IllegalArgumentException{
		
		Vector2D c = new Vector2D(0,0);
		double g = 9.81;
		if(data.has("c")) {
			c = new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1));
		}
		if(data.has("g")) {
			g = data.getDouble("g");
		}
		
		return new MovingTowardsFixedPoint(c,g);
		
	}
	
	//Nuevo Practica2
		public JSONObject getInfo() {
			JSONObject js = new JSONObject();
			js.put("type", "mtfp");
			js.put("desc", "Moving towards a fixed point");
			JSONObject js2 = new JSONObject();
			js2.put("c", "the point towards which bodies move");
			js2.put("g", "the length of the acceleration vector");
			js.put("data", js2);
			
			return js;
		}


}

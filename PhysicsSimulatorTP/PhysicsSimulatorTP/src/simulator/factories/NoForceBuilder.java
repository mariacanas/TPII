package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder() {
		super("nf", "No Force Builder");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) throws IllegalArgumentException{
		return new NoForce();
	}

	//Nuevo Practica2
		public JSONObject getInfo() {
			JSONObject js = new JSONObject();
			js.put("type", "nf");
			js.put("desc", "No force");
			js.put("data", new JSONObject());
			
			return js;
		}


}

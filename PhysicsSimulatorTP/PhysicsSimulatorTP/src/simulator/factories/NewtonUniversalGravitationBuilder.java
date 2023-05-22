package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{
	
	double G = 6.67E-11;

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton Universal");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) throws IllegalArgumentException{
		
		if(data.has("G")) {
			return new NewtonUniversalGravitation(data.getDouble("G"));
		}
		return new NewtonUniversalGravitation(G);
	}

	//Nuevo Practica2
	public JSONObject getInfo() {
		JSONObject js = new JSONObject();
		js.put("type", "nlug");
		js.put("desc", "Newton's law of universal gravitation");
		JSONObject js2 = new JSONObject();
		js2.put("G", "the gravitation constant " + G);
		js.put("data", js2);
		
		return js;
	}

}

package simulator.control;

import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;
import simulator.view.Utils;
import simulator.model.Body;
import simulator.model.ForceLaws;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;

public class Controller {

	private PhysicsSimulator simulator;
    private Factory<Body> bodyFactory;
    private Factory<ForceLaws> forceLawsFactory;
	
	public Controller(PhysicsSimulator simulator, Factory<Body> bodyFactory, Factory<ForceLaws> forceLawsFactory) {
		this.simulator = simulator;
		this.bodyFactory = bodyFactory;
		this.forceLawsFactory = forceLawsFactory;
	}
	
	public void run(int n, OutputStream out) {
		JSONObject json = new JSONObject();
		JSONArray sArray = new JSONArray();
		
		sArray.put(simulator.getState());
		for(int i = 1; i <= n;i++) {
			simulator.advance();
			sArray.put(simulator.getState());
		}
		json.put("states", sArray);
		PrintStream sal = new PrintStream(out);
		sal.println(json.toString());
	}
	
	
	
	//Nuevo Practica 2
	
	public void reset() {
		simulator.reset();
	}
	
	public void setDeltaTime(double dt) {
		simulator.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		simulator.addObserver(o);
	}
	
	public void removeObserver(SimulatorObserver o) {
		simulator.removeObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return forceLawsFactory.getInfo();
	}
	
	public void setForceLaws(String gId, JSONObject info) {
		ForceLaws flnew = forceLawsFactory.createInstance(info);
		simulator.setForceLaws(gId, flnew);
	}
	
	public void run(int n) {
		for(int i = 0; i < n; i++) {
			simulator.advance();
		}
	}

	public void loadBodies(InputStream in) {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonInput = new JSONObject(new JSONTokener(in));
			
			if(jsonInput.has("groups")) {
				JSONArray groups = jsonInput.getJSONArray("groups");		 
				for(int i = 0; i < groups.length(); i++) {
					simulator.addGroup(groups.getString(i));							
				}
			}
			
			
			if(jsonInput.has("laws")) {
				JSONArray laws = jsonInput.getJSONArray("laws");		  
				for(int i = 0; i < laws.length(); i++) {		
					simulator.setForceLaws(laws.getJSONObject(i).getString("id"), forceLawsFactory.createInstance(laws.getJSONObject(i).getJSONObject("laws")));
				}
			}
			
			if(jsonInput.has("bodies")) {
				JSONArray bodies = jsonInput.getJSONArray("bodies");			
				for(int i = 0; i < bodies.length(); i++) {
					simulator.addBody(bodyFactory.createInstance(bodies.getJSONObject(i))); 
				}
			}
		}
		catch (Exception ex) {
            Utils.showErrorMsg("Error while loading the file: " + ex.getMessage());
        }
	}
}

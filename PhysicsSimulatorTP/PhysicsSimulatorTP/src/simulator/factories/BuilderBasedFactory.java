package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.ForceLaws;

public class BuilderBasedFactory<T> implements Factory<T> {

	private Map<String,Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;

	public BuilderBasedFactory() {
		 _builders = new HashMap<String,Builder<T>>();
	      _buildersInfo = new LinkedList<JSONObject>();
	}
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		 for (Builder<T> b : builders) {
	            addBuilder(b);
	     }
	}
	
	
	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		  
		if(info.has("type")){
		    String type = info.getString("type");
		    JSONObject data = info.optJSONObject("data");
		    
		    Builder<T> builder = _builders.get(type);
		    if (builder != null) {
		        T instance;
		        if (data != null) {
		            instance = builder.createInstance(data);
		        } else {
		            instance = builder.createInstance(new JSONObject());
		        }
		        if (instance != null) {
		            return instance;
		        }
		    }
		}
	    
	    throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}

	public void addBuilder(Builder<T> b) {
		String tag = b.getTypeTag();
	    if (_builders.containsKey(tag)) {
	        throw new IllegalArgumentException("Builder with tag " + tag + " already exists");
	    }
	    _builders.put(tag, b);
	    _buildersInfo.add(b.getInfo());
	}

}

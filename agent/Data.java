package agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	private String _name;
	private Map<String,Parameter> _params;
	
	public Data(String name){
		_name=name;
		_params=new HashMap<String,Parameter>();
	}
	
	public void addParam(Parameter p){
		_params.put(p.getName(),p);
	}
	
	public Parameter getParameter(String name) throws NoParameterFound{
		if(!_params.containsKey(name)){
			throw new NoParameterFound("The parameter "+name+" does not belong to "+_name);
		} 
		return _params.get(name);
	}
	
	public void addParam(String paramName, Parameter newParam) {
		_params.put(paramName, newParam);
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		_name = name;
	}

	public Map<String,Parameter> getParameters() {
		return _params;
	}
}

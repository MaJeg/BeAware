package agent;

public abstract class Parameter {
	
	private String _name;
	
	public Parameter(String name){
		_name=name;
	}
	
	public String getName(){
		return _name;
	}
	
}

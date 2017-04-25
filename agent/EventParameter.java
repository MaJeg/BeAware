package agent;

public class EventParameter extends Parameter {
	private String _value;
	
	public EventParameter(String name,String value) {
		super(name);
		_value=value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return _value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		_value = value;
	}

}

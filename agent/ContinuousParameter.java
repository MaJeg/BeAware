package agent;

public class ContinuousParameter extends Parameter {

	private double _value;
	
	public ContinuousParameter(String name,double value) {
		super(name);
		_value=value;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return _value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		_value = value;
	}
}

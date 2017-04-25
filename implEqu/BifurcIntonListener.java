package implEqu;

import action.AttrFunc;
import utils.MathUtils;

public class BifurcIntonListener implements AttrFunc {

	@Override
	public double getAttractor(double... params) {
		double m=params[0];
		double gamm=params[1];
		double val=0;
		val=0.5*MathUtils.heaviside(2*m+gamm-0.9);
		return val;
	}

}

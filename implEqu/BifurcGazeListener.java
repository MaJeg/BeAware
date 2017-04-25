package implEqu;

import action.AttrFunc;
import utils.MathUtils;

public class BifurcGazeListener implements AttrFunc {

	@Override
	public double getAttractor(double... params) {
		double m=params[0];
		double gamm=params[1];
		double val=0;
		val=1.0-MathUtils.heaviside(gamm+m);
		return val;
	}
}

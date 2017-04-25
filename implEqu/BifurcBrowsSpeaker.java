package implEqu;

import action.AttrFunc;
import utils.MathUtils;

public class BifurcBrowsSpeaker implements AttrFunc {

	@Override
	public double getAttractor(double... params) {
		double m=params[0];
		double gamm=params[1];
		double val=1.0*MathUtils.heaviside(gamm)*MathUtils.heaviside(-m);
		return val;
	}

}

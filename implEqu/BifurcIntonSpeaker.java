package implEqu;

import action.AttrFunc;
import utils.MathUtils;

public class BifurcIntonSpeaker implements AttrFunc {

	@Override
	public double getAttractor(double... params) {
		double m=params[0];
		double gamm=params[1];
		double val=0;
		val=0.5*(MathUtils.heaviside(-m-gamm)*MathUtils.heaviside(m)+MathUtils.heaviside(-m));
		return val;
	}

}

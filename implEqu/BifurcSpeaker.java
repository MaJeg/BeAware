package implEqu;

import action.AttrFunc;
import utils.MathUtils;

public class BifurcSpeaker implements AttrFunc {

	@Override
	public double getAttractor(double... params) {
		double m=params[0];
		double gamm=params[1];
		double val=0;
		val=0.5*MathUtils.heaviside(-m -gamm)*MathUtils.heaviside(m-0.05)*MathUtils.heaviside(gamm)
				+0.5*MathUtils.heaviside(gamm+m-1.0)
				+0.5*MathUtils.heaviside(-10*m)
				-0.5*m*MathUtils.heaviside(10*gamm)*MathUtils.heaviside(-10*m);
		return val;
	}
}

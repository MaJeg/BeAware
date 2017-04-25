package implEqu;

import action.AttrFunc;
import agent.BehaviorUpdater;
import agent.Data;
import agent.ParameterTypeException;
import utils.MathUtils;

public class BifurcBrowsListener implements AttrFunc {

	@Override
	public double getAttractor(double... params) {
		double m=params[0];
		double gamm=params[1];
		double val=1.0*MathUtils.heaviside(gamm-0.5)*MathUtils.heaviside(m)
				-1.0*MathUtils.heaviside(-gamm-0.5)*MathUtils.heaviside(m);
		return val;
	}
}

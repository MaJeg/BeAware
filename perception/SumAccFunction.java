package perception;

import java.util.ArrayList;
import java.util.List;

public class SumAccFunction extends AccFunction {
	private List<Double> _params;

	public SumAccFunction(){
		_params=new ArrayList<Double>();
		
	}
	
	public void addParam(int index,double value){
		_params.add(index, value);
	}
	
	@Override
	public double getAcc(double s, double sDot) {
		double constCoeff=_params.get(0);
		double sCoeff=_params.get(1);
		double sDCoeff=_params.get(2);
		double acc=sCoeff*s+sDCoeff*sDot+constCoeff;
		return acc;
	}
	
	public double getAcc(){
		double constCoeff=_params.get(0);
		double sCoeff=_params.get(1);
		double sDCoeff=_params.get(2);
		double acc=sCoeff*super.getS()+sDCoeff*super.getsDot()+constCoeff;
		return acc;
	}

}

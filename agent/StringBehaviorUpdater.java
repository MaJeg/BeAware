package agent;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import utils.TimeUtils;

public class StringBehaviorUpdater implements BehaviorUpdater {
	private Map<Double,Double> _timeValues;
	
	public StringBehaviorUpdater(){
		_timeValues=new TreeMap<Double,Double>();
	}
	
	@Override
	public void updateBehavior(Data s) throws ParameterTypeException {
		double val=0.0;
		ContinuousParameter cp=null;
		Parameter p=null;
		
		try {
			p = s.getParameter("value");
			cp=(ContinuousParameter)p;
			
			if(cp!=null){
				val = cp.getValue();
			} else{
				throw new ParameterTypeException("Parameter "+ p.getName() + " is of the wrong type");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoParameterFound e) {
			e.printStackTrace();
		}
		_timeValues.put(TimeUtils.getCurrentTime()/1000.0, val);
	}
	
	public Map<Double,Double> getTimeValues(){
		return _timeValues;
	}
}

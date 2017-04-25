package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.SignalEquation;
import perception.AccFunction;
import perception.PercEquation;
import utils.TimeUtils;

public class Executer {
	private String _execState;
	private PercEquation _pe;
	private List<SignalEquation> _signalEqus;
	private Map<String,AccFunction> _accFunctions;
	private double _motivation;
	private double _confidence;
	private double _positiveThreshold;
	private double _negativeThreshold;
	private List<Double> _localConfs;
	private List<Double> _accValues;
	private Map<String,double[]> _lastValues;
	private double _lastAccValue;
	
	public Executer(String execState){
		_execState=execState;
		_signalEqus=new ArrayList<SignalEquation>();
		_accFunctions=new HashMap<String,AccFunction>();
		_pe=new PercEquation(0.0);
		_positiveThreshold=1.0;
		_negativeThreshold=-1.0;
		_confidence=0.0;
		_motivation=1.0;
		_localConfs = new ArrayList<Double>();
		_accValues=new ArrayList<Double>();
		_lastValues=new HashMap<String,double[]>();
		_lastAccValue=0.0;
	}
	
	/**
	 * @return the execState
	 */
	public String getExecState() {
		return _execState;
	}

	public double getMotivation() {
		return _motivation;
	}

	public void setMotivation(double motivation) {
		_motivation = motivation;
	}
	
	public double getConfidence(){
		return _pe.getConf();
	}
	
	public void addSignalEquation(SignalEquation se){
		if(!_signalEqus.contains(se)){
			_signalEqus.add(se);
		}
	}
	
	public void addAccFunction(String signalId,AccFunction af){
		if(!_accFunctions.containsKey(signalId) || _accFunctions.get(signalId)==null){
			_accFunctions.put(signalId, af);
		}
	}
	
	public boolean thresholdCrossed(){
		return _confidence>=_positiveThreshold;
	}

	public Map<String,Double> execAgent(){
		double accValue=0.0;
		double[] currVals=null;
		Map<String,Double> val=new HashMap<String,Double>();
		
		// Perception Equation 
		for(String s : _accFunctions.keySet()){
			accValue=accValue+_accFunctions.get(s).getAcc();
		}
		_accValues.add(accValue);
		_pe.setAccValue(accValue);
		_confidence=Math.max(Math.min(_pe.execute()[0], _positiveThreshold),_negativeThreshold);
		String id="";
		_localConfs.add(_confidence);
		_lastAccValue=accValue;
		
		// Signal Equation
		for(SignalEquation se : _signalEqus){
			 se.setConfidence(_confidence);
			 se.setMotivation(_motivation);
			 id = se.getSignalId();
			 currVals=se.execute();
			 _lastValues.put(id,currVals);
			 val.put(id, currVals[1]);
		}
		
		if(_confidence<=_negativeThreshold){
			_pe.resetConfidence();
		}
		return val;
	}
	
	public void init(double currTime){
		_pe.initTime(currTime);
		for(SignalEquation se : _signalEqus){
			se.initTime(currTime);
		}	
	}

	public void updateSignals(String idSignal,double s, double sDot) throws NoSignalFoundException{
		AccFunction af = _accFunctions.get(idSignal);
		if(af!=null){
			af.updateValues(s, sDot);
		} else{
			throw new NoSignalFoundException("The signal"+idSignal+" has no corresponding accumulation function");
		}
	}
	
	public void dispLocalConfs(){
		System.out.println("Confidences");
		for(double d : _localConfs){
			System.out.println(d);
		}
	}
	
	public void dispConfidenceAccumulation(){
		List<Double> confs=_pe.getConfidence();
		List<Double> times = _pe.getTimeValues();
		
		System.out.println("Confidence values");
		for(int i=0;i<confs.size();i++){
			System.out.println("Time "+ times.get(i)+ " Conf::"+confs.get(i)+ " AccValues::"+_accValues.get(i));
		}
	}

	public void reset() {
		_pe.resetConfidence();
		double currTime=TimeUtils.getCurrentTime()/1000.0;
		_pe.initTime(currTime);
		for(SignalEquation se : _signalEqus){
			se.initTime(currTime);
		}
	}
	
	public Map<String,double[]> getLastValues(){
		return _lastValues;
	}
	
	public void setPrevValue(String signalId,double[] prevValue){
		for(int i=0;i<_signalEqus.size();i++){
			if(_signalEqus.get(i).getSignalId().equals(signalId)){
				_signalEqus.get(i).setPrevValue(prevValue);
			}
		}
	}

	public double getAccValue() {
		return _lastAccValue;
	}
}

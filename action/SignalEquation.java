package action;

import java.util.List;

import equation.DiffEquation;
import equation.EulerFunc;
import equation.OrdinarySolver;
import utils.TimeUtils;

/**
 * Classe pour le contrôle des signaux de l'agent
 * @author jegou
 *
 */
public class SignalEquation implements DiffEquation{	
	private OrdinarySolver _solver;
	private String _signalId;
	private double _prevStep; 
	private double _currValue[];
	private double _motivation;
	private double _confidence;
	
	public SignalEquation(OrdinarySolver solver,EulerFunc func,double currValue[],String signalId){
		_solver=solver;
		_prevStep=TimeUtils.getCurrentTime()/1000.0;
		_currValue=new double[currValue.length];
		if(currValue!=null){
			System.arraycopy(currValue, 0, _currValue, 0, currValue.length);
		}
		setMotivation(0.0);
		setConfidence(0.0);
		_signalId=signalId;
		_solver.setFunc(func);
	}
	
	public void initTime(double currTime){
		_prevStep=currTime;
	}
	

	public double[] execute(){
		// Calcul de la valeur de dérivée de l'équation
		double step = TimeUtils.getCurrentTime()/1000.0;
		double[] res=null;
		System.out.println("prevValue::"+"sigType::"+_signalId+":"+_currValue[1]);
		
		_currValue=_solver.solve(step, _prevStep, _currValue, _motivation,_confidence);
		System.out.println("currValue::"+"sigType::"+_signalId+":"+_currValue[1]);
		if(_currValue!=null && _currValue.length==2){
			res=_currValue;
		} else {
			try {
				throw new UndefinedResultException("The solver was unable to compute the next value.");
			} catch (UndefinedResultException e) {
				e.printStackTrace();
			}
		}
		_prevStep=step;
		return res;
	}

	/**
	 * @return the _motivation
	 */
	public double getMotivation() {
		return _motivation;
	}

	/**
	 * @param _motivation the _motivation to set
	 */
	public void setMotivation(double motivation) {
		this._motivation = motivation;
	}

	/**
	 * @return the confidence
	 */
	public double getConfidence() {
		return _confidence;
	}

	/**
	 * @param confidence the confidence to set
	 */
	public void setConfidence(double confidence) {
		_confidence = confidence;
	}

	public String getSignalId() {
		return _signalId;
	}

	public void setPrevValue(double[] currValue){
		System.arraycopy(currValue, 0, _currValue, 0, currValue.length);
	}
	
}

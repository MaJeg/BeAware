package perception;

import java.util.ArrayList;
import java.util.List;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import ch.epfl.lis.sde.Sde;
import ch.epfl.lis.sde.solvers.EulerMaruyama;
import equation.DiffEquation;
import utils.TimeUtils;

public class PercEquation implements DiffEquation{
	private double _standardDev;
	private double _accValue;
	private EulerMaruyama _em;
	private double _currentTime;
	private double _previousTime;
	private double _conf;
	
	// Pour le debug à enlever
	private List<Double> _confidence;
	private List<Double> _accValues;
	private List<Double> _timeValues;
	
	public List<Double> getTimeValues() {
		return _timeValues;
	}


	public void setTimeValues(List<Double> timeValues) {
		_timeValues = timeValues;
	}


	public PercEquation(double stdDev){
		_standardDev=stdDev;
		_em=new EulerMaruyama();
		_accValue=0.0;
		_em.setSystem(new Sde(){
			@Override
			public void getDriftAndDiffusion(double t, DoubleMatrix1D xIn, DoubleMatrix1D F, DoubleMatrix2D G)
					throws Exception {
				F.set(0, _accValue);
				G.set(0, 0, 0.0);
			} });
		_em.initialize();
		_currentTime=0.0;
		_previousTime=0.0;
		_em.setX(new DenseDoubleMatrix1D(1).assign(0.0));
		
		_confidence=new ArrayList<Double>();
		_accValues=new ArrayList<Double>();
		_timeValues=new ArrayList<Double>();
	}
		
	
	public List<Double> getConfidence() {
		return _confidence;
	}

	public void setConfidence(List<Double> confidence) {
		_confidence = confidence;
	}
	
	public double getConf(){
		return _conf;
	}

	public List<Double> getAccValues() {
		return _accValues;
	}

	public void setAccValues(List<Double> accValues) {
		_accValues = accValues;
	}
	
	public double getAccValue() {
		return _accValue;
	}

	public void setAccValue(double accValue) {
		_accValue = accValue;
	}

	public void initTime(double currTime){
		_previousTime=currTime;
	}
	
	@Override
	public double[] execute(){
		_currentTime=TimeUtils.getCurrentTime()/1000.0;
		
		// On change le pas de temps pour que cela corresponde au
		// delta par rapport à la dernière fois où le module a 
		// été exécuté
		_em.setH(_currentTime-_previousTime);
		
		// Modifier données d'entrée
		try {
			_em.step();
		} catch (Exception e) {
			e.printStackTrace();
		}

		_previousTime=_currentTime;
		_accValues.add(_accValue);
		_timeValues.add(_currentTime);
		
		DoubleMatrix1D xOut = _em.getX();
		double res = 0.0;
		if(xOut!=null){
			res=xOut.get(0);
		} else {
			System.out.println("The stochastic solver was unable to find the next confidence value");
		}
		_conf=res;
		_confidence.add(res);
		return new double[]{res};
	}


	public void resetConfidence() {
		_em.setX(new DenseDoubleMatrix1D(1).assign(0.0));
	}
}

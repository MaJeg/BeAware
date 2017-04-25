package agent;

import utils.TimeUtils;

public class SimpleLocMotivationUpdater extends MotivationUpdater {
	private double _motValue;
	private double _initTime;
	
	public SimpleLocMotivationUpdater(double initTime) {
		_initTime=initTime;
	}

	@Override
	public double updateMotivation() {
//		double currentTime=TimeUtils.getCurrentTime()/1000;
//		double timeElapsed=currentTime-_initTime;
//		if(timeElapsed>3.0){
//			_motValue=1.0;
//		} else{
//			_motValue=-1.0;
//		}
		return -0.8;
	}
}

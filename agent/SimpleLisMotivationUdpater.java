package agent;

import utils.TimeUtils;

public class SimpleLisMotivationUdpater extends MotivationUpdater {
	private double _initTime;
	private double _motValue;
	
	public SimpleLisMotivationUdpater() {
		_initTime=TimeUtils.getCurrentTime()/1000;
		_motValue=0.0;
	}

	@Override
	public double updateMotivation() {
		double currentTime=TimeUtils.getCurrentTime()/1000;
		double timeElapsed=currentTime-_initTime;
		if(timeElapsed>3.0){
			_motValue=1.0;
		} else{
			_motValue=-1.0;
		}
		return _motValue;
	}
}

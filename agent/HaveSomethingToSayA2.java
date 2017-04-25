package agent;

import utils.TimeUtils;

public class HaveSomethingToSayA2 extends MotivationUpdater {

	private double _initTime;
	
	public HaveSomethingToSayA2(double initTime){
		_initTime=initTime;
		_currentRole="";
	}
	
	@Override
	public double updateMotivation() {
		double currentTime=TimeUtils.getCurrentTime()/1000.0;
		double timeElapsed=currentTime-_initTime;
		double motValue=0.0;
		if(timeElapsed>4.5 && timeElapsed<=7.0){
			motValue= _currentRole.equals("Speaker")?0.6:-0.4;
		} else if(timeElapsed>7.0){
			motValue=_currentRole.equals("Speaker")?-0.6:0.4;
		} else {
			motValue=-0.6;
		}
		return motValue;
	}
}

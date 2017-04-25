package agent;

import utils.TimeUtils;

public class AppraisalMotivationUpdater2Sc2 extends MotivationUpdater {

	private double _unpleasant;
	private double _expected;
	private double _copingPower;
	private double _goalHindrance;
	private double _externalStandards;
	private double _internalStandards;
	private double _goalRelevance;
	private String _cause;
	private double _initTime;
	private MARCSender _mSender;
	
	public AppraisalMotivationUpdater2Sc2(double initTime,MARCSender mSender){
		_initTime=initTime;
		_mSender=mSender;
		_currentRole="";
		_expected=0.0;
		_copingPower=0.0;
		_goalHindrance=0.0;
		_externalStandards=0.0;
		_goalRelevance=0.0;
		_cause="Other";
		updateLabel();
	}
	
	@Override
	public double updateMotivation() {
		updateLabel();
		double currentTime=TimeUtils.getCurrentTime()/1000;
		double timeElapsed=currentTime-_initTime;
		double motivation=0.0;
		if(timeElapsed>=4.0){
			_expected=0.0;
			_copingPower=0.0;
			_goalHindrance=0.0;
			_externalStandards=0.0;
			_goalRelevance=0.0;
			_cause="Other";
		}else{
//			_expected=-0.5;
//			_copingPower=0.5;
//			_goalHindrance=0.5;
//			_externalStandards=-0.5;
//			_goalRelevance=0.5;
//			_cause="Other";
			
			_expected=0.0;
			_copingPower=0.0;
			_goalHindrance=0.0;
			_externalStandards=0.0;
			_goalRelevance=0.0;
			_cause="Other";
		}
		
		if(_emotionalState.equals("Rage")){
			motivation=_currentRole.equals("Speaker")?-0.4:0.4;
		} else if(_emotionalState.equals("Fear")){
			motivation=_currentRole.equals("Speaker")?0.4:-0.4;
		} else if(_emotionalState.equals("Disgust")){
			motivation=_currentRole.equals("Speaker")?-0.4:0.4;
		} else if(_emotionalState.equals("Sadness")){
			motivation=_currentRole.equals("Speaker")?0.4:-0.4;
		} else{
			motivation=_currentRole.equals("Speaker")?0.0:0.0;
		}
		return motivation;
	}
	
	private void updateLabel(){
		if(_expected<0.0 && _copingPower>0.0 && _goalHindrance>0.0 && _cause.equals("Other") && _externalStandards<0.0 && _goalRelevance>0.0){
			_emotionalState="Rage";
		} else if(_unpleasant>0.0 && _expected<0.0 && _copingPower<0.0 && _goalHindrance>0.0 && _cause.equals("Other") && _goalRelevance>0.0){
			_emotionalState="Fear";
		} else if(_unpleasant>0.0 && _expected<0.0 && _goalRelevance<0.0){
			_emotionalState="Disgust";
		} else if(_expected>0.0 && _copingPower<0.0 && _goalHindrance>0.0 && _goalRelevance>0.0){
			_emotionalState="Sadness";
		} else if(_expected==0.0 && _copingPower==0.0 && _goalHindrance==0.0 && _externalStandards==0.0 && _goalRelevance==0.0){
			_emotionalState="Neutral";
		}
		_mSender.setAppraisalVariable("unpleasant", Double.toString(_unpleasant));
		_mSender.setAppraisalVariable("expected", Double.toString(_expected));
		_mSender.setAppraisalVariable("cause", "other");
		_mSender.setAppraisalVariable("externalStandards", Double.toString(_externalStandards));
		_mSender.setAppraisalVariable("goalRelevance", Double.toString(_goalRelevance));
		_mSender.setAppraisalVariable("goalHindrance", Double.toString(_goalHindrance));
		_mSender.setAppraisalVariable("copingPower", Double.toString(_copingPower));
		_mSender.setAppraisalVariable("externalCause", "external");
		_mSender.updateEmotion();
		
	}

}

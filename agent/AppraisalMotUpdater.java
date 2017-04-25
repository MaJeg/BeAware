package agent;

import utils.TimeUtils;

public class AppraisalMotUpdater extends MotivationUpdater {
	private MARCSender _mSender;
	private double _unpleasant;
	private double _expected;
	private double _copingPower;
	private double _goalHindrance;
	private double _externalStandards;
	private double _internalStandards;
	private double _goalRelevance;
	private String _cause;
	private String _currentRole;
	private double _initTime;
	
	
	public AppraisalMotUpdater(double initTime,MARCSender mSender){
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
	
	
	public double getUnpleasant() {
		return _unpleasant;
	}

	public void setUnpleasant(double unpleasant) {
		_unpleasant = unpleasant;
	}
	
	public double getExpected() {
		return _expected;
	}
	
	public void setExpected(double expected) {
		_expected = expected;
	}



	public double getCopingPower() {
		return _copingPower;
	}



	public void setCopingPower(double copingPower) {
		_copingPower = copingPower;
	}



	public double getGoalHindrance() {
		return _goalHindrance;
	}



	public void setGoalHindrance(double goalHindrance) {
		_goalHindrance = goalHindrance;
	}



	public double getExternalStandards() {
		return _externalStandards;
	}



	public void setExternalStandards(double externalStandards) {
		_externalStandards = externalStandards;
	}



	public double getInternalStandards() {
		return _internalStandards;
	}



	public void setInternalStandards(double internalStandards) {
		_internalStandards = internalStandards;
	}



	public double getGoalRelevance() {
		return _goalRelevance;
	}



	public void setGoalRelevance(double goalRelevance) {
		_goalRelevance = goalRelevance;
	}



	public String getCause() {
		return _cause;
	}



	public void setCause(String cause) {
		_cause = cause;
	}


	public String getCurrentRole() {
		return _currentRole;
	}



	public void setCurrentRole(String currentRole) {
		_currentRole = currentRole;
	}
	
	@Override
	public double updateMotivation() {
		updateLabel();
		double currentTime=TimeUtils.getCurrentTime()/1000;
		double timeElapsed=currentTime-_initTime;
		if(timeElapsed>1.0 && timeElapsed<=10.0){
			_expected=-1.0;
			_copingPower=1.0;
			_goalHindrance=1.0;
			_externalStandards=-1.0;
			_goalRelevance=1.0;
			_cause="Other";
//			_expected=0.0;
//			_copingPower=0.0;
//			_goalHindrance=0.0;
//			_externalStandards=0.0;
//			_goalRelevance=0.0;
//			_cause="Other";
		}else if(timeElapsed>10.0){
			_expected=0.0;
			_copingPower=0.0;
			_goalHindrance=0.0;
			_externalStandards=0.0;
			_goalRelevance=0.0;
			_cause="Other";
		}
		
		double motivation=0.0;
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

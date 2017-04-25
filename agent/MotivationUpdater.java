package agent;

import utils.TimeUtils;

public abstract class MotivationUpdater {
	
	protected String _currentRole;
	protected String _emotionalState;
	
	public abstract double updateMotivation();

	public void setCurrentRole(String currentRole) {
		_currentRole=currentRole;
	}
	
	public String getEmotionalState() {
		return _emotionalState;
	}

	public void setEmotionalState(String emotionalState) {
		_emotionalState = emotionalState;
	}

}

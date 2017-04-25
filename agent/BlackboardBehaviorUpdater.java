package agent;

import java.util.Map;

public class BlackboardBehaviorUpdater implements BehaviorUpdater {

	private String _dataName;
	private Map<String,Data> _sharedBlackboard;
	
	public BlackboardBehaviorUpdater(String dataName){
		_dataName=dataName;
	}
	
	public void setSharedBlackboard(Map<String,Data> sharedBlackboard){
		_sharedBlackboard=sharedBlackboard;
	}
	
	@Override
	public void updateBehavior(Data s) {
		_sharedBlackboard.put(_dataName, s);
	}
}

package agent;

public class MarcFaceUpdater extends MarcBehaviorUpdater {

	private String _bmlId;
	private String _trackId;
	private String _actionUnit;
	private MARCSender _mSender;
	
	public MarcFaceUpdater(int port, String bmlId, String trackId,MARCSender mSender) {
		super(port);	
		_bmlId=bmlId;
		_trackId=trackId;
		_mSender=mSender;
		setActionUnit("1");
	}
	

	@Override
	public void updateBehavior(Data s) throws ParameterTypeException {
		double auValue=0.0;
		try {
			Parameter p = s.getParameter("face");
			ContinuousParameter cp = (ContinuousParameter)p;
			
			if(cp!=null){
				_actionUnit= cp.getValue()>=-0.5?"1":"4";
				if(cp.getValue()>0.5){
					auValue = 1.0;
				} else if(cp.getValue()<-0.5){
					auValue = -1.0;
				} else{
					auValue = 0.0;
				}	
			}
			else{
				throw new ParameterTypeException("Parameter au is of the wrong type");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoParameterFound e) {
			e.printStackTrace();
		}
		
		_mSender.sendToMarc("au_"+_actionUnit, _trackId, _bmlId, auValue);
		
	}

	/**
	 * @return the actionUnit
	 */
	public String getActionUnit() {
		return _actionUnit;
	}


	/**
	 * @param actionUnit the actionUnit to set
	 */
	public void setActionUnit(String actionUnit) {
		_actionUnit = actionUnit;
	}

}

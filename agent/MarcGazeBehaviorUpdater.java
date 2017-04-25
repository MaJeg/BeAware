package agent;

public class MarcGazeBehaviorUpdater extends MarcBehaviorUpdater {
	
	private double _currAngle;
	private String _bmlId;
	private String _trackId;
	private MARCSender _mSender;
	
	public MarcGazeBehaviorUpdater(int port, String bmlId, String trackId, MARCSender mSender){
		super(port);
		_currAngle=-1.0;
		_bmlId=bmlId;
		_trackId=trackId;
		_mSender=mSender;
	}
	
	@Override
	public void updateBehavior(Data ed) throws ParameterTypeException {
		// Transformer fréquence de contrôle en direction du gaze
		// v1 : <0.9 : on ne regarde pas l'utilisateur, >0.9 on regarde l'utilisateur
		double s=0.0;
		Parameter p=null;
		ContinuousParameter cp=null;
		try {
			p = ed.getParameter("gaze");
			cp=(ContinuousParameter)p;
			
			if(cp!=null){
				s=cp.getValue();
			}
			else{
				throw new ParameterTypeException("Parameter "+p.getName()+" is of the wrong type");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoParameterFound e) {
			e.printStackTrace();
		}
		double angle=0.0;
		String msg=null;
		// Inférieur à 0.9, on baisse le regard de 10 degrés
		if(s<0.9){
			angle=0.0;
		} else if(s>=0.9){
			angle=-10.0;
		}
		
		if(angle!=_currAngle){
			_mSender.sendToMarc("gaze", _trackId, _bmlId, angle);
		}
	}
}

package agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarcSpeechUpdater extends MarcBehaviorUpdater {
	
	/**
	 * Différentes phrases selon le degré de hauteur de voix
	 */
	private Map<String,List<String>> _utterances;
	private String _currUtterance;
	private double _prevS;
	private String _wavDir;
	private String _wavFile;
	private boolean _isLaunched;
	private String _bmlId;
	private String _trackId;
	private int _bmlBlockNumber;
	private MARCSender _mSender;
	
	public MarcSpeechUpdater(int port,String bmlId,String trackId,MARCSender mSender) {
		super(port);
		_mSender=mSender;
		_prevS=0.0;
		_utterances=new HashMap<String,List<String>>();
		_currUtterance="";
		_isLaunched=false;
		_bmlBlockNumber=0;
		_bmlId=bmlId;
		_trackId=trackId;
		
	}
	
	public void setWavDir(String wavDir){
		_wavDir=wavDir;
	}
	
	public void setWavFile(String wavFile){
		_wavFile=wavFile;
	}
	
	@Override
	public void updateBehavior(Data ed) throws ParameterTypeException {
		double vol=0.0;
		double pitch=0.0;
		String utterance="";
		String toLaunch="";
		Parameter p = null;
		ContinuousParameter cp=null;
		EventParameter ep=null;
		try {
			p = ed.getParameter("volume");
			cp=(ContinuousParameter)p;
			if(cp!=null){
				vol = cp.getValue();
			} else{
				throw new ParameterTypeException("Parameter "+p.getName() + "is of the wrong type");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoParameterFound e) {
			e.printStackTrace();
		}
		
		try {
			p = ed.getParameter("pitch");
			cp=(ContinuousParameter)p;
			if(cp!=null){
				pitch = cp.getValue();
			} else{
				throw new ParameterTypeException("Parameter "+p.getName() + "is of the wrong type");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoParameterFound e) {
			e.printStackTrace();
		}
		
//		try {
//			p = ed.getParameter("utterance");
//			ep=(EventParameter)p;
//			if(ep!=null){
//				_wavFile = ep.getValue()+"_orig";
//			} else{
//				throw new ParameterTypeException("Parameter "+p.getName() + "is of the wrong type");
//			}
//		} catch (NoParameterFound e) {
//			e.printStackTrace();
//		}
		
		
//		List<String> possibleUtts=null;
//				
//		if(pitch<0.3 && pitch>0.3){
//			 // Charger l'énoncé avec le volume le plus faible
//			 possibleUtts=_utterances.get(_currUtterance);
//			 toLaunch=possibleUtts.get(1);
//		} else if(pitch>0.5 && pitch<0.8 && (_prevS<0.5 || _prevS>0.8)){
//			// Charger l'énoncé avec un volume moyen
//			possibleUtts=_utterances.get(_currUtterance);
//			toLaunch=possibleUtts.get(2);
//		} else if(pitch>0.8 && _prevS<0.8){
//			// Charger l'énoncé avec un volume fort
//			possibleUtts=_utterances.get(_currUtterance);
//			toLaunch=possibleUtts.get(2);
//		}
//		_prevS=0.0;
		
		_mSender.sendToMarc("speech", _trackId, _bmlId, vol);
		
	}
	
	public void setUtterance(String utt, List<String> utterances) {
		_utterances.put(utt,utterances);
	}
}
 
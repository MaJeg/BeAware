package agent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class MARCSender {
	private int _emlPort;
	private int _bmlPort;
	private double _expected;
	private double _copingPower;
	private double _goalHindrance;
	private double _externalStandards;
	private double _internalStandards;
	private double _goalRelevance;
	private String _cause;
	private String _emotionLabel;
	private DatagramSocket _ds;
	private double _unpleasant;
	private boolean _isLaunched;
	private String _voice;
//	private String _wavDir;
//	private List<String> _wavFiles;
	private String _utterance;
	
	public MARCSender(int bmlPort,int emlPort,String utterance,String voice) {
		super();
		_emlPort = emlPort;
		_bmlPort = bmlPort;
		_utterance=utterance;
		_voice=voice;
		
		try {
			_ds=new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		_emotionLabel="";
		_expected=0.0;
		_copingPower=0.0;
		_goalHindrance=0.0;
		_externalStandards=0.0;
		_internalStandards=0.0;
		_goalRelevance=0.0;
		_cause="Other";
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

	public String getEmotionLabel() {
		return _emotionLabel;
	}

	public void setEmotionLabel(String emotionLabel) {
		_emotionLabel = emotionLabel;
	}

	// Voir ce qui peut rentrer en conflit 
	// avec l'expression de l'émotion 
	// dans ce cas pour l'instant privilégier 
	// l'émotion de l'agent
	public void sendToMarc(String type,String trackId, String bmlId, double value){
		// Le type peut être au ou gaze
		String msg="";
		if(type.contains("au")){
			// Voir ce qui influe sur les au
			if(!_emotionLabel.equals("Rage") && !_emotionLabel.equals("Sadness")){
				String actionUnit=type.split("_")[1];
				msg = "<bml id=\""+trackId+"\">\n"+
						"<marc:fork id=\""+trackId+"_fork_1\">\n"+
			            "<face id=\""+bmlId+"\" type=\"FACS\" side=\"BOTH\" amount=\""+value+"\" au=\""+actionUnit+"\" marc:interpolate=\"0.2\" marc:interpolation_type=\"linear\" /> \n"+
						"</marc:fork>\n"+
			            "</bml>\n";
				
			}
		} else if(type.equals("gaze")){
			msg="<bml id=\""+trackId+"\">\n"+
				"<marc:fork id=\""+trackId+"_fork_1\">\n"+
		        "<gaze id=\""+bmlId+"\" target=\"start\" direction=\"DOWN\" angle=\""+value+"\" /> \n"+		
				"</marc:fork>\n"+
		        "</bml>\n";
		} else if(type.equals("speech")){
			
			if(value>0.25 && !_isLaunched){
				msg="<bml id=\""+trackId+"\">\n"+
					"<marc:fork id=\""+trackId+"_fork_1\">\n"+
					"<speech"+ "\n"+
					"id=\""+bmlId+"\"\n"+
					"marc:voice=\""+_voice+"\"\n"+
					"marc:synthesizer=\"JSAPI\"\n"+
					"marc:speed=\"1.0\"\n"+
					"marc:f0_shift=\"0.0\"\n"+
					"marc:volume=\"1.0\"\n"+
					"marc:articulate=\"1.0\"\n";
				msg=msg+"text=\""+_utterance+"\\n/>";
				msg=msg+"</marc:fork>\n</bml>\n";
				_isLaunched=true;
			} else if(value<=0.25 && _isLaunched){
				msg="<bml id=\""+trackId+"\">\n"+
			      "<marc:fork id=\""+trackId+"_fork_1\">\n"+
			      "<marc:speech_stop id=\""+bmlId+"_item\" />\n"+ 
			      "</marc:fork>\n"+
			      "</bml>";
				_isLaunched=false;
			}
		}
		if(!msg.equals("")){
			this.sendCommandToMarc(_bmlPort,msg);
		}
	}
	
	public void setAppraisalVariable(String appType,String value){
		double val=0.0;
		if(appType.equals("unpleasant")){
			val=Double.parseDouble(value);
			_unpleasant=val;
		} else if(appType.equals("expected")){
			val=Double.parseDouble(value);
			_expected=val;
		} else if(appType.equals("copingPower")){
			val=Double.parseDouble(value);
			_copingPower=val;
		} else if(appType.equals("goalHindrance")){
			val=Double.parseDouble(value);
			_goalHindrance=val;
		} else if(appType.equals("externalStandards")){
			val=Double.parseDouble(value);
			_externalStandards=val;
		} else if(appType.equals("internalStandards")){
			val=Double.parseDouble(value);
			_internalStandards=val;
		} else if(appType.equals("goalRelevance")){
			val=Double.parseDouble(value);
			_goalRelevance=val;
		} else if(appType.equals("cause")){
			_cause=value;
		} 
	}
	
	private String toEmotionML(){
		String formatEml = "<emotionml>\n"+
				   "<emotion>\n"+
				   "   <appraisals>\n" +
					"	  <expectedness value=\""+_expected+"\" mode=\""+"additive"+"\"/>\n"+
					"     <unpleasantness value=\""+_unpleasant+"\" mode=\""+"additive"+"\"/>\n"+
					"     <goal_hindrance value=\""+_goalHindrance+"\" mode=\""+"additive"+"\"/>\n"+
					"     <copying_control value=\""+_copingPower+"\" mode=\""+"additive"+"\"/>\n"+
					"     <copying_power value=\""+_copingPower+"\" mode=\""+"additive"+"\"/>\n"+
					"     <external_causation value=\"1.0\" mode=\""+"additive"+"\"/>\n"+
					"\t\t</appraisals>"+
					"\t</emotion>\n"+
					"</emotionml>\n";
		return formatEml;
	}
	
	private void sendCommandToMarc(int port,String message){
		byte command[] = message.getBytes();
		DatagramPacket dp = new DatagramPacket(command,command.length ,InetAddress.getLoopbackAddress(),port);
		try {
			_ds.send(dp);
			System.out.println("Sent message::"+message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateEmotion() {
		String prevLabel=_emotionLabel;
		if(_expected<0.0 && _copingPower>0.0 && _goalHindrance>0.0 && _cause.equals("other") && _externalStandards<0.0 && _goalRelevance>0.0){
			_emotionLabel="Rage";
		} else if(_unpleasant>0.0 && _expected<0.0 && _copingPower<0.0 && _goalHindrance>0.0 && _cause.equals("other") && _goalRelevance>0.0){
			_emotionLabel="Fear";
		} else if(_unpleasant>0.0 && _expected<0.0 && _goalRelevance<0.0){
			_emotionLabel="Disgust";
		} else if(_expected>0.0 && _copingPower<0.0 && _goalHindrance>0.0 && _goalRelevance>0.0){
			_emotionLabel="Sadness";
		} else if(_expected==0.0 && _copingPower==0.0 && _goalHindrance==0.0 && _externalStandards==0.0 && _goalRelevance==0.0){
			_emotionLabel="Neutral";
		}
		System.out.println("Emotion updated::"+_emotionLabel);
		// Envoi de l'expression à MARC
		// On n'update que si l'émotion correspondante est 
		// différente de l'émotion actuelle de l'agent
		if(!prevLabel.equals(_emotionLabel)){
			sendCommandToMarc(_emlPort,toEmotionML());
		}
	}
}

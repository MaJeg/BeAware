package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.TimeUtils;

public class Agent {
	private Map<String,Data> _otherSignals;
	private Map<String, Executer> _execs;
	private Map<String,List<BehaviorUpdater>> _updaters;
	private Map<String,List<Double>> _dispValues;
	private List<Double> _timeValues;
	private Map<String,List<Double>> _datsValues;
	private String _currentState;
	private Map<String,Double> _prevSig;
	private double _prevTime;
	private List<String> _signals;
	private List<Double> _motValues;
	private List<Double> _confValues;
	private List<String> _currStates;
	private Map<String,List<MotivationUpdater>> _motUpdaters;
	private Map<String,Double> _lastValues;
	private double _lastConf;
	private double _lastAcc;
	private String _agentId;
	
	public Agent(String initState,String agentId){
		_currentState=initState;
		_prevSig=new HashMap<String,Double>();
		_prevTime=0.0;
		_execs=new HashMap<String,Executer>();
		_updaters=new HashMap<String,List<BehaviorUpdater>>();
		_dispValues = new HashMap<String,List<Double>>();
		_timeValues = new ArrayList<Double>();
		_datsValues = new HashMap<String,List<Double>>();
		_signals=new ArrayList<String>();
		_motValues=new ArrayList<Double>();
		_confValues=new ArrayList<Double>();
		_currStates=new ArrayList<String>();
		_lastValues = new HashMap<String,Double>();
		_agentId=agentId;
		_lastAcc=0.0;
		_motUpdaters=new HashMap<String,List<MotivationUpdater>>();
	}
	
	public void initTime(double prevTime){
		_prevTime=prevTime;
		for(Executer e : _execs.values()){
			e.init(prevTime);
		}
	}
	
	public void addSignal(String sigType){
		_signals.add(sigType);
		// TODO AAAH à modifier !!
		if(sigType.equals("speech")){
			_dispValues.put("volume", new ArrayList<Double>());
			_dispValues.put("pitch", new ArrayList<Double>());
			_datsValues.put("volume", new ArrayList<Double>());
			_datsValues.put("pitch", new ArrayList<Double>());
		} else{
			_dispValues.put(sigType, new ArrayList<Double>());
			_datsValues.put(sigType, new ArrayList<Double>());
		}
	}
	
	public void addUpdater(String signalId,BehaviorUpdater bu){
		List<BehaviorUpdater> sigUpdaters=null;
		if(_updaters.containsKey(signalId)){
			sigUpdaters=_updaters.get(signalId);
			if(sigUpdaters!=null && !sigUpdaters.contains(bu)){
				sigUpdaters.add(bu);
				return;
			}
		} 
		sigUpdaters=new ArrayList<BehaviorUpdater>();
		sigUpdaters.add(bu);
		_updaters.put(signalId, sigUpdaters);
	}
	
	public void addExecuter(String state,Executer exc){
		_execs.put(state, exc);
	}
	
	public void setOtherSignals(Map<String,Data> otherSignals){
		_otherSignals=otherSignals;
	}
	
	public Map<String,Double> getInput(String signalId) throws NoSignalIdFoundException, ParameterTypeException{
		Map<String,Double> values=new HashMap<String,Double>();
		Data cd = null;
		if(_otherSignals.containsKey(signalId)){
			cd=_otherSignals.get(signalId);
		}else{
			throw new NoSignalIdFoundException("No signal value corresponding to "+signalId + " has been found");
		}

		if(cd!=null){
			for(Parameter p : cd.getParameters().values()){
				// Cast solution pas terrible mais seule solution pour le moment
				ContinuousParameter contParam=(ContinuousParameter)p;
				if(contParam!=null){
					values.put(p.getName(),contParam.getValue());
					List<Double> inps=_datsValues.get(p.getName());
					if(inps!=null){
						inps.add(contParam.getValue());
					}
					_prevSig.put(p.getName(), contParam.getValue());
				}
			} 
		}
		return values;
	}
	
	public void execAgent(){
		Map<String,Double> sig=null;
		double currentTime=TimeUtils.getCurrentTime()/1000.0;
		double deltaT=currentTime-_prevTime;
		
		// Exécuter les équations de perception et de contrôle des signaux
		Executer exc=_execs.get(_currentState);
		List<MotivationUpdater> currRoleMotUpdater = _motUpdaters.get(_currentState);
		double currentMotivation=0.0;
		if(currRoleMotUpdater!=null){
			for(MotivationUpdater mu : currRoleMotUpdater){
				currentMotivation=currentMotivation+mu.updateMotivation();
			}
		} else{
			System.err.println("No mot updater specified for the "+_agentId+" role "+_currentState);
		}
		exc.setMotivation(currentMotivation);
		
		// Récupérer les éléments de perception de l'agent
		try {
			for(int i=0;i<_signals.size();i++){
				sig = getInput(_signals.get(i));
				for(String k : sig.keySet()){
					exc.updateSignals(k, sig.get(k), (sig.get(k)-_prevSig.get(k)/deltaT));
				}
			}
		} catch (NoSignalIdFoundException e) {
			e.printStackTrace();
		} catch (NoSignalFoundException e) {
			e.printStackTrace();
		} catch (ParameterTypeException e) {
			e.printStackTrace();
		}

		// Renvoie l'ensemble des paires nom du signal, valeur du signal
		Map<String, Double> val=exc.execAgent();
		updateDispValues(val);
		_lastValues=val;
		
		 // TODO : À changer et à mettre dans un autre Thread
		 try {
			for(String modality : _signals){
				Data ed = createData(modality,val);
				updateBehavior(modality,ed);
			}
		} catch (NoUpdaterFoundException e) {
			e.printStackTrace();
		}
		_motValues.add(exc.getMotivation());
		_confValues.add(exc.getConfidence());
		_timeValues.add(currentTime);
		_prevTime=currentTime;
		_currStates.add(_currentState);
		_lastConf=exc.getConfidence();
		_lastAcc=exc.getAccValue();
		
		// Vérifier si l'on a franchit le seuil de confiance dans ce cas on change d'état
		if(exc.thresholdCrossed()){
			Map<String,double[]> lastStateValues=_execs.get(_currentState).getLastValues();
			changeState(_currentState);
			exc=_execs.get(_currentState);
			exc.reset();
			// Initialiser les équations avec les valeurs les plus récentes des signaux
			for(String k : lastStateValues.keySet()){
				exc.setPrevValue(k, lastStateValues.get(k));
			}
		}
	}
	
	private Data createData(String modality,Map<String,Double> val) {
		Data ed = new Data(modality);
		// TODO Affreux, à modifier !
		if(modality.equals("speech")){
			ed.addParam(new ContinuousParameter("volume",val.get("volume")));
			ed.addParam(new ContinuousParameter("pitch",val.get("pitch")));
		}else if(modality.equals("gaze")){
			ed.addParam(new ContinuousParameter("gaze",val.get("gaze")));
		} else if(modality.equals("face")){
			ed.addParam(new ContinuousParameter("face",val.get("face")));
		}
		return ed;
	}

	public void updateBehavior(String idSignal,Data ed) throws NoUpdaterFoundException{
		List<BehaviorUpdater> updaters=_updaters.get(idSignal);
		if(updaters!=null){
			for(BehaviorUpdater bu : updaters){
				try {
					bu.updateBehavior(ed);
				} catch (ParameterTypeException e) {
					e.printStackTrace();
				}
			}
		} else {
			throw new NoUpdaterFoundException("No behavior updater found corresponding to signal id::"+idSignal);
		}
	}
	
	public void addMotUpdater(String currRole, MotivationUpdater motUpdater){
		List<MotivationUpdater> updaters=null;
		motUpdater.setCurrentRole(_currentState);
		if(_motUpdaters.containsKey(currRole)){
			updaters=_motUpdaters.get(currRole);
			if(updaters==null){
				updaters=new ArrayList<MotivationUpdater>();
				_motUpdaters.put(currRole, updaters);	
			}
			updaters.add(motUpdater);
		}else {
			updaters=new ArrayList<MotivationUpdater>();
			updaters.add(motUpdater);
			_motUpdaters.put(currRole, updaters);
		}
	}

	private void changeState(String currentState) {
		if(currentState=="Listener"){
			_currentState="Speaker";
			
		} else if(currentState=="Speaker"){
			_currentState="Listener";
		}
		
		for(List<MotivationUpdater> mus : _motUpdaters.values()){
			for(MotivationUpdater mu : mus){
				mu.setCurrentRole(_currentState);
			}
		}
	}

	public void dispConfidenceAccumulation() {
		for(Executer exc : _execs.values()){
			exc.dispConfidenceAccumulation();
		}
	}
	
	public String displaySignalValues(){
		String res=displayHeader();
		for(int i=0;i<_timeValues.size();i++){
			res=res+_timeValues.get(i);
			for(String k : _dispValues.keySet()){
				res=res+displayValue(k,i);
			}
			res=res+"\n";
		}
		return res;
	}
	
	private void updateDispValues(Map<String,Double> currValues){
		for(String k: currValues.keySet()){
			updateValue(k,currValues.get(k));
		}
	}
	
	private void updateValue(String sig,double val){
		List<Double> vals=_dispValues.get(sig);
		if(vals!=null){
			vals.add(val);
		}
	}

	
	public String displayHeader(){
		String res="Time";
		for(String key : _dispValues.keySet()){
			res=res+" "+key;
		}
		res=res+"\n";
		return res;
	}
	
	public String displayValue(String sigType,int index){
		List<Double> vals=_dispValues.get(sigType);
		String res=null;
		if(vals!=null && vals.size()>0){
			res=" "+vals.get(index);
		}
		return res;
	}
	
	public String displayInput(String sigType,int index){
		String res="";
		
		List<Double> vals=_datsValues.get(sigType);
		if(index<vals.size()){
			res=" "+vals.get(index);
		}
		return res;
	}
	
	public String displayInputs(){
		String res=displayHeader();
		
		for(int i=0;i<_timeValues.size();i++){
			res=res+_timeValues.get(i);
			for(String k : _datsValues.keySet()){
				res=res+displayInput(k,i);
			}
			res=res+"\n";
		}
		return res;
	}
	
	public void dispConfValues(){
		for(double d : _confValues){
			System.out.println(d);
		}
	}
	
	public void dispMotValues(){
		for(double d: _motValues){
			System.out.print(d+"\t");
		}
		System.out.println();
	}

	public void dispLocalConfs(){
		for(Executer exc : _execs.values()){
			exc.dispLocalConfs();
		}
	}
	
	public void dispStates(){
		for(String s : _currStates){
			System.out.print(s+"\t");
		}
		System.out.println();
	}
	
	public void dispConfidenceAccumulation(String sigId){
		Executer ex=_execs.get(sigId);
		ex.dispConfidenceAccumulation();
	}

	public Map<String, Double> getLastValues() {
		return _lastValues;
	}
	
	public String getAgentId(){
		return _agentId;
	}

	public double getLastConf() {
		return _lastConf;
	}

	public double getLastAcc() {
		return _lastAcc;
	}
	
	public double getMotivation(){
		Executer exc=_execs.get(_currentState);
		return exc.getMotivation();
	}
	
	public void setMotivation(double motivation){
		Executer exc=_execs.get(_currentState);
		exc.setMotivation(motivation);
	}

	public String getEmotionalState() {
		List<MotivationUpdater> mus = _motUpdaters.get(_currentState);
		for(MotivationUpdater mu : mus){
			if(mu instanceof AppraisalMotUpdater || mu instanceof AppraisalMotivationUpdater2){
				return mu.getEmotionalState();
			}
		}
		return "";
	}
}

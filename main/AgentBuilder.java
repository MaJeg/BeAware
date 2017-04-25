package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.SignalEquation;
import agent.Agent;
import agent.AppraisalMotUpdater;
import agent.BehaviorUpdater;
import agent.Data;
import agent.Executer;
import agent.MotivationUpdater;
import agent.StringBehaviorUpdater;
import equation.EulerFunc;
import equation.OrdinarySolver;
import equation.RungeKuttaSolver;
import implEqu.BifurcBrowsListener;
import implEqu.BifurcBrowsSpeaker;
import implEqu.BifurcGazeListener;
import implEqu.BifurcGazeSpeaker;
import implEqu.BifurcIntonListener;
import implEqu.BifurcIntonSpeaker;
import implEqu.BifurcListener;
import implEqu.BifurcSpeaker;
import implEqu.GeneEqu;
import perception.SumAccFunction;
import utils.TimeUtils;

public class AgentBuilder {
	private String _agentId;
	private List<MotivationUpdater> _locMotUpdater;
	private List<MotivationUpdater> _lisMotUpdater;
	
	
	public AgentBuilder(){
		_locMotUpdater = new ArrayList<MotivationUpdater>();
		_lisMotUpdater = new ArrayList<MotivationUpdater>();
	}
	
	public List<MotivationUpdater> getLocMotUpdaters() {
		return _locMotUpdater;
	}

	public Agent createAgent(List<BehaviorUpdater> speechUpdaters , List<BehaviorUpdater> gazeUpdaters, List<BehaviorUpdater> faceUpdaters ,  String initState,Map<String,Data> sharedData,double motivationLoc,double motivationLis, double initVol){
		Agent ag = new Agent(initState,_agentId);
		ag.addSignal("speech");
//		ag.addSignal("face");
		ag.addSignal("gaze");
		for(MotivationUpdater mu : _lisMotUpdater){
			ag.addMotUpdater("Listener", mu);
		}
		
		for(MotivationUpdater mu : _locMotUpdater){
			ag.addMotUpdater("Speaker", mu);
		}
		
		BifurcSpeaker bl = new BifurcSpeaker();
		BifurcIntonSpeaker blSpeak = new BifurcIntonSpeaker();
		BifurcGazeSpeaker blGaze = new BifurcGazeSpeaker();
		BifurcBrowsSpeaker blFace = new BifurcBrowsSpeaker();
		
		EulerFunc f = new GeneEqu(2.0, 10.0,bl);
		EulerFunc fPit = new GeneEqu(2.0, 10.0,blSpeak);
		EulerFunc fGaze = new GeneEqu(4.0, 20.0,blGaze);
		EulerFunc fFace = new GeneEqu(1.0, 5.0,blFace);
		
		OrdinarySolver os = new RungeKuttaSolver(f);
		OrdinarySolver osPit = new RungeKuttaSolver(fPit);
		OrdinarySolver osGaze = new RungeKuttaSolver(fGaze);
		OrdinarySolver osFace = new RungeKuttaSolver(fFace);
		
		SignalEquation seVol = new SignalEquation(os, f,new double[]{0.0,0.5},"volume");
		SignalEquation sePit = new SignalEquation(osPit, fPit,new double[]{0.0,0.5},"pitch");
		SignalEquation seGaze = new SignalEquation(osGaze, fGaze,new double[]{0.0,0.5},"gaze");
		SignalEquation seFace = new SignalEquation(osFace, fFace,new double[]{0.0,0.5},"face");
		
		SumAccFunction af = new SumAccFunction();
		// Coefficient pour la constante
		af.addParam(0, -1.0);
		// Coefficient pour le terme du signal
		af.addParam(1, 10.0);
		// Coefficient pour la dérivée du signal
		af.addParam(2, 0.0);
		
		SumAccFunction afPit = new SumAccFunction();
		afPit.addParam(0, -1.0);
		afPit.addParam(1, 10.0);
		afPit.addParam(2, 0.0);
		
		SumAccFunction afGaze = new SumAccFunction();
		afGaze.addParam(0, 1.0);
		afGaze.addParam(1, -1.5);
		afGaze.addParam(2, 0.0);
		
//		SumAccFunction afBrows = new SumAccFunction();
//		afBrows.addParam(0, 0.0);
//		afBrows.addParam(1, 1.0);
//		afBrows.addParam(2, 0.0);
		
		for(BehaviorUpdater bu : speechUpdaters){
			ag.addUpdater("speech", bu);
		}
		
		for(BehaviorUpdater bu : gazeUpdaters){
			ag.addUpdater("gaze", bu);
		}
		
		for(BehaviorUpdater bu : faceUpdaters){
			ag.addUpdater("face",bu);
		}
		
		Executer exc = new Executer("Speaker");
		exc.addSignalEquation(seVol);
		exc.addSignalEquation(sePit);
		exc.addSignalEquation(seGaze);
		exc.addSignalEquation(seFace);
		
		exc.addAccFunction("volume", af);
		exc.addAccFunction("pitch",afPit);
		exc.addAccFunction("gaze", afGaze);
//		exc.addAccFunction("face", afBrows);
		exc.setMotivation(motivationLoc);
		ag.addExecuter("Speaker", exc);
		ag.setOtherSignals(sharedData);
		
		BifurcListener blLis = new BifurcListener();
		BifurcIntonListener blPitLis = new BifurcIntonListener();
		BifurcGazeListener blGazeLis = new BifurcGazeListener();
		BifurcBrowsListener blFaceLis = new BifurcBrowsListener();
		
		EulerFunc fLis = new GeneEqu(2.0, 10.0,blLis);
		EulerFunc fPitLis = new GeneEqu(4.0, 20.0,blPitLis);
		EulerFunc fGazeLis = new GeneEqu(4.0, 20.0,blGazeLis);
		EulerFunc fFaceLis = new GeneEqu(4.0, 20.0,blFaceLis);
		
		OrdinarySolver osLis = new RungeKuttaSolver(fLis);
		OrdinarySolver osPitLis = new RungeKuttaSolver(fPitLis);
		OrdinarySolver osGazeLis = new RungeKuttaSolver(fGazeLis);
		OrdinarySolver osFaceLis = new RungeKuttaSolver(fFaceLis);
		
		SignalEquation seLis = new SignalEquation(osLis, fLis,new double[]{0.0,0.0},"volume");
		SignalEquation sePitLis = new SignalEquation(osPitLis, fPitLis,new double[]{0.0,0.0},"pitch");
		SignalEquation seGazeLis = new SignalEquation(osGazeLis, fGazeLis,new double[]{0.0,1.0},"gaze");
		SignalEquation seFaceLis = new SignalEquation(osFaceLis, fFaceLis,new double[]{0.0,1.0},"face");
		
		SumAccFunction afLis = new SumAccFunction();
		// Coefficient pour la constante
		afLis.addParam(0, 2.0);
		
		// Coefficient pour le terme du signal
		afLis.addParam(1, -4.0);
		
		// Coefficient pour la dérivée du signal
		afLis.addParam(2, 0.0);
		
		SumAccFunction afPitLis = new SumAccFunction();
		// Coefficient pour la constante
		afPitLis.addParam(0, 2.0);
		
		// Coefficient pour le terme du signal
		afPitLis.addParam(1, -4.0);
		
		// Coefficient pour la dérivée du signal
		afPitLis.addParam(2, 0.0);
		
		SumAccFunction afGazeLis = new SumAccFunction();
		// Coefficient pour la constante
		afGazeLis.addParam(0, -1.0);
		
		// Coefficient pour le terme du signal
		afGazeLis.addParam(1, 1.5);
		
		// Coefficient pour la dérivée du signal
		afGazeLis.addParam(2, 0.0);
		
//		SumAccFunction afBrowsLis = new SumAccFunction();
//		// Coefficient pour la constante
//		afBrowsLis.addParam(0, 0.0);
//		
//		// Coefficient pour le terme du signal
//		afBrowsLis.addParam(1, -1.0);
//		
//		// Coefficient pour la dérivée du signal
//		afBrowsLis.addParam(2, 0.0);

		Executer exc1 = new Executer("Listener");
		exc1.addSignalEquation(seLis);
		exc1.addSignalEquation(sePitLis);
		exc1.addSignalEquation(seGazeLis);
		exc1.addSignalEquation(seFaceLis);
		exc1.addAccFunction("volume", afLis);
		exc1.addAccFunction("pitch", afPitLis);
		exc1.addAccFunction("gaze", afGazeLis);
//		exc1.addAccFunction("face", afBrowsLis);
		exc1.setMotivation(motivationLis);
		ag.addExecuter("Listener", exc1);

		return ag;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return _agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		_agentId = agentId;
	}


	public void addLocMotUpdater(MotivationUpdater appraisalMotUpdater) {
		_locMotUpdater.add(appraisalMotUpdater);
	}

	public void addLisMotUpdater(MotivationUpdater appraisalMotUpdater) {
		_lisMotUpdater.add(appraisalMotUpdater);
	}

	public void cleanMotUpdater() {
		_locMotUpdater.clear();
		_lisMotUpdater.clear();
	}
}

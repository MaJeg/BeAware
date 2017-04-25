package test.sdeTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.Agent;
import agent.ContinuousParameter;
import agent.Data;
import agent.StringBehaviorUpdater;
import main.AgentBuilder;
import utils.TimeUtils;

public class TestOneAgent {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		TimeUtils.init();
////		BlackboardBehaviorUpdater bVolSp = new BlackboardBehaviorUpdater("volume");
////		BlackboardBehaviorUpdater bPitSp = new BlackboardBehaviorUpdater("pitch");
////		BlackboardBehaviorUpdater bGazeSp = new BlackboardBehaviorUpdater("gaze");
////		
////		BlackboardBehaviorUpdater bVolLis = new BlackboardBehaviorUpdater("volume");
////		BlackboardBehaviorUpdater bPitLis = new BlackboardBehaviorUpdater("pitch");
////		BlackboardBehaviorUpdater bGazeLis= new BlackboardBehaviorUpdater("gaze");
//		
//		StringBehaviorUpdater bVol = new StringBehaviorUpdater();
//		StringBehaviorUpdater bPit = new StringBehaviorUpdater();
//		StringBehaviorUpdater bGaze= new StringBehaviorUpdater();
//		
//		// Tableau de données partagé entre les agents
//		Map<String,Data> otherSignals = new HashMap<String,Data>();
//		Map<String,Data> otherSignalsA2 = new HashMap<String,Data>();
//		AgentBuilder ab = new AgentBuilder();
//		Agent ag=ab.createAgent(bVol, bPit,bGaze, "Listener", otherSignals,1.0,0.4,0.5);
////		Agent ag2=ab.createAgent(bVolLis, bPitLis, bGazeLis, "Speaker", otherSignalsA2);
//		
//		
//		double duration=10.0;
//		long timeStep=100;
//		double startTime=TimeUtils.getCurrentTime()/1000.0;
//		double currentTime=startTime;
//		List<Double> times = new ArrayList<Double>();
//		List<Double> values = new ArrayList<Double>();
//		List<Double> confidence = new ArrayList<Double>();
//		List<Double> motivation = new ArrayList<Double>();
//		times.add(currentTime);
//		values.add(0.0);
//		confidence.add(-1.0);
//		motivation.add(-1.0);
//
//		while(currentTime<(startTime+duration)){
//			
//			System.out.println("Sleeping "+timeStep+" ms");
//			try {
//				Thread.sleep((long)timeStep);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			if(currentTime>(startTime+duration)/3 && currentTime<2*(startTime+duration)/3){
//				Data cd=new Data("volume");
//				cd.addParam(new ContinuousParameter("value",0.0));
//				System.out.println("Volume value::"+0.0);
//				otherSignals.put("volume", cd);
//			}else{
//				Data cd=new Data("volume");
//				cd.addParam(new ContinuousParameter("value",0.0));
//				otherSignals.put("volume", cd);
//				System.out.println("Volume value::"+0.5);
//			}
//
//			ag.execAgent();
//			currentTime=TimeUtils.getCurrentTime()/1000;
//			System.out.println("CurrentTime "+currentTime);
//			System.out.println("Start time "+startTime);
//		}
//		
//		Map<Double,Double> timeValues = bVol.getTimeValues();
//		for(double time : timeValues.keySet()){
//			System.out.println("Time::"+time+" Value::"+timeValues.get(time));
//		}
//		
//		System.out.println("Speaker");
//		ag.dispConfidenceAccumulation();
////		System.out.println("Listener");
////		ag.dispConfidenceAccumulation();
	}

}

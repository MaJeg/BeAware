package test.sdeTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import agent.Agent;
import agent.BlackboardBehaviorUpdater;
import agent.Data;
import main.AgentBuilder;
import utils.TimeUtils;

public class TestTwoAgentsVolOnly {

	public static void main(String[] args) {
//		TimeUtils.init();
//		Map<String,Data> otherSignalsA1 = new HashMap<String,Data>();
//		Map<String,Data> otherSignalsA2 = new HashMap<String,Data>();
//		AgentBuilder ab = new AgentBuilder();
//		String resFileAg1="agent1_vol.dat";
//		String resFileAg2="agent2_vol.dat";
//		
//		
//		FileWriter fw1=null;
//		FileWriter fw2=null;
//		
//		try {
//			fw1 = new FileWriter(new File(resFileAg1));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		try {
//			fw2 = new FileWriter(new File(resFileAg2));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		BlackboardBehaviorUpdater bVolSp = new BlackboardBehaviorUpdater("volume");
//		// En entrée : les sorties de l'autre agent
//		bVolSp.setSharedBlackboard(otherSignalsA2);
//		BlackboardBehaviorUpdater bPitSp = new BlackboardBehaviorUpdater("pitch");
//		bPitSp.setSharedBlackboard(otherSignalsA2);
//		BlackboardBehaviorUpdater bGazeSp = new BlackboardBehaviorUpdater("gaze");
//		bGazeSp.setSharedBlackboard(otherSignalsA2);
//		Agent ag1=ab.createAgent(bVolSp, bPitSp,bGazeSp, "Listener", otherSignalsA1,-1.0,0.4,0.5);
//		
//		BlackboardBehaviorUpdater bVolLis = new BlackboardBehaviorUpdater("volume");
//		bVolLis.setSharedBlackboard(otherSignalsA1);
//		BlackboardBehaviorUpdater bPitLis = new BlackboardBehaviorUpdater("pitch");
//		bPitLis.setSharedBlackboard(otherSignalsA1);
//		BlackboardBehaviorUpdater bGazeLis= new BlackboardBehaviorUpdater("gaze");
//		bGazeLis.setSharedBlackboard(otherSignalsA1);
//		Agent ag2=ab.createAgent(bVolLis, bPitLis,bGazeLis, "Speaker", otherSignalsA2,1.0,-1.0,0.5);
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
//			System.out.println("Sleeping "+timeStep+" ms");
//			try {
//				Thread.sleep((long)timeStep);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			ag1.execAgent();
//			ag2.execAgent();
//			currentTime=TimeUtils.getCurrentTime()/1000;
//			System.out.println("CurrentTime "+currentTime);
//			System.out.println("Start time "+startTime);
//		}
//		System.out.println("Agent 1");
//		String valsAg1=ag1.displaySignalValues();
//		
//		System.out.println("Agent 2");
//		String valsAg2=ag2.displaySignalValues();
//		
//		try {
//			fw1.write(valsAg1);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			fw2.write(valsAg2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			fw1.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			fw2.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}

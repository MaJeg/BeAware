package test.sdeTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.SwingUtilities;
import agent.Agent;
import agent.AppraisalMotUpdater;
import agent.AppraisalMotivationUpdater2;
import agent.AppraisalMotivationUpdater2Sc2;
import agent.BehaviorUpdater;
import agent.BlackboardBehaviorUpdater;
import agent.Data;
import agent.HaveSomethingToSayA1;
import agent.HaveSomethingToSayA2;
import agent.MARCSender;
import agent.MarcFaceUpdater;
import agent.MarcGazeBehaviorUpdater;
import agent.MarcSpeechUpdater;
import main.AgentBuilder;
import main.SignalPlotter;
import utils.MathUtils;
import utils.TimeUtils;

public class TestTwoAgent {

	public static SignalPlotter sp = null;
	public static SignalPlotter sp2=null;
	public static SignalPlotter sp3=null;
	public static SignalPlotter sp4=null;
	public static SignalPlotter sp5=null;
	public static SignalPlotter sp6=null;
	public static SignalPlotter sp7=null;
	public static Map<String,Double> valsAg1 = null;
	public static Map<String,Double> valsAg2 = null;
	public static double _lastConfidenceA1=0.0;
	public static double _lastConfidenceA2=0.0;
	public static double _lastAccValueA1=0.0;
	public static double _lastAccValueA2=0.0;
	public static double _motivationA1=0.0;
	public static double _motivationA2=0.0;
	private static double _emotionStateA2=0.0;
	private static double _emotionStateA1=0.0;
	
	public static void main(String[] args) {
		TimeUtils.init();
		String resFileAg1="agent1.dat";
		String resFileAg2="agent2.dat";
		
		List<String> maleFiles=new ArrayList<String>();
		maleFiles.add("male_voice_2");
		List<String> femaleFiles = new ArrayList<String>();
		femaleFiles.add("pluie");
		femaleFiles.add("female_voice_sadness");
		MARCSender mSenderA1 = new MARCSender(4010,4012,"Look at Salim, he is in socks, he threw his boot on the roof to make the ball fall. He missed it, the ball is still stuck and the boot too","Microsoft Hortense Desktop");
		MARCSender mSenderA2 = new MARCSender(4013,4015,"I see that he threw his boot on the roof","Microsoft Zira Desktop");
		
		Map<String,Data> otherSignalsA1 = new HashMap<String,Data>();
		Map<String,Data> otherSignalsA2 = new HashMap<String,Data>();
		Random r = new Random();
		FileWriter fw1=null;
		FileWriter fw2=null;

//		SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//            	sp = new SignalPlotter(0,1,2000,2,"Volume levels","Volume");
//                sp.setVisible(true);
//                sp2 = new SignalPlotter(0,1,2000,2,"Gaze levels","Gaze");
//                sp2.setVisible(true);
//                sp3 = new SignalPlotter(-10,1,2000,2,"Confidence level agent1","Confidence");
//                sp3.setVisible(true);
//                sp4 = new SignalPlotter(-10,1,2000,2,"Confidence level agent2","Confidence");
//                sp4.setVisible(true);
//                sp5 = new SignalPlotter(-10,1,2000,2,"Face levels","Face");
//                sp5.setVisible(true);
//                sp6 = new SignalPlotter(-1,1,2000,2,"Motivation Agents","Motivation");
//                sp6.setVisible(true);
//                sp7 = new SignalPlotter(0,5,2000,2,"Emotions Agents","Emotions");
//                sp7.setVisible(true);
//            }
//		});
		
		try {
			fw1 = new FileWriter(new File(resFileAg1));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			fw2 = new FileWriter(new File(resFileAg2));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		AgentBuilder ab = new AgentBuilder();
		BlackboardBehaviorUpdater bVolSp = new BlackboardBehaviorUpdater("speech");
		// En entrée : les sorties de l'autre agent
		bVolSp.setSharedBlackboard(otherSignalsA2);
		BlackboardBehaviorUpdater bGazeSp = new BlackboardBehaviorUpdater("gaze");
		bGazeSp.setSharedBlackboard(otherSignalsA2);
		BlackboardBehaviorUpdater bFaceSp = new BlackboardBehaviorUpdater("face");
		bFaceSp.setSharedBlackboard(otherSignalsA2);
		
		MarcSpeechUpdater msu = new MarcSpeechUpdater(4010,"bml1","Track0",mSenderA1);
		
		MarcGazeBehaviorUpdater mgbu = new MarcGazeBehaviorUpdater(4010,"bml10","Track0",mSenderA1);
		
		MarcFaceUpdater mbu = new MarcFaceUpdater(4010,"bml10","Track0",mSenderA1);
		
		List<BehaviorUpdater> speechUpdaters = new ArrayList<BehaviorUpdater>();
		speechUpdaters.add(bVolSp);
		speechUpdaters.add(msu);
		
		List<BehaviorUpdater> gazeUpdaters = new ArrayList<BehaviorUpdater>();
		gazeUpdaters.add(bGazeSp);
		gazeUpdaters.add(mgbu);
		
		List<BehaviorUpdater> faceUpdaters = new ArrayList<BehaviorUpdater>();
		faceUpdaters.add(bFaceSp);
		faceUpdaters.add(mbu);
		double initTime=TimeUtils.getCurrentTime()/1000;
		
		ab.setAgentId("Agent1");
		ab.addLocMotUpdater(new AppraisalMotUpdater(initTime,mSenderA1));
		ab.addLocMotUpdater(new HaveSomethingToSayA1(initTime));
		
		ab.addLisMotUpdater(new AppraisalMotUpdater(initTime,mSenderA1));
		ab.addLisMotUpdater(new HaveSomethingToSayA1(initTime));
		
		Agent ag1=ab.createAgent(speechUpdaters,gazeUpdaters,faceUpdaters, "Listener", otherSignalsA1,-1.0,1.0,0.0);
		
		BlackboardBehaviorUpdater bVolLis = new BlackboardBehaviorUpdater("speech");
		bVolLis.setSharedBlackboard(otherSignalsA1);
		BlackboardBehaviorUpdater bGazeLis= new BlackboardBehaviorUpdater("gaze");
		bGazeLis.setSharedBlackboard(otherSignalsA1);
		BlackboardBehaviorUpdater bFaceLis= new BlackboardBehaviorUpdater("face");
		bFaceLis.setSharedBlackboard(otherSignalsA1);
		
		MarcSpeechUpdater msu2 = new MarcSpeechUpdater(4013,"bml2","Track1",mSenderA2);
		
		List<BehaviorUpdater> speechUpdaters2 = new ArrayList<BehaviorUpdater>();
		speechUpdaters2.add(bVolLis);
		speechUpdaters2.add(msu2);
		
		List<BehaviorUpdater> gazeUpdaters2 = new ArrayList<BehaviorUpdater>();
		gazeUpdaters2.add(bGazeLis);
		
		List<BehaviorUpdater> faceUpdaters2 = new ArrayList<BehaviorUpdater>();
		faceUpdaters2.add(bFaceLis);
		
		ab.setAgentId("Agent2");
		ab.cleanMotUpdater();
		ab.addLocMotUpdater(new AppraisalMotivationUpdater2(initTime,mSenderA2));
		ab.addLocMotUpdater(new HaveSomethingToSayA2(initTime));
		
		ab.addLisMotUpdater(new AppraisalMotivationUpdater2(initTime,mSenderA2));
		ab.addLisMotUpdater(new HaveSomethingToSayA2(initTime));
		
		Agent ag2=ab.createAgent(speechUpdaters2,gazeUpdaters2,faceUpdaters2,"Speaker", otherSignalsA2,1.0,1.0,0.5);
		
		double duration=20.0;
		long timeStep=10;
		
		double startTime=TimeUtils.getCurrentTime()/1000.0;
		ag1.initTime(startTime);
		ag2.initTime(startTime);
		double currentTime=startTime;
		int execIndex=0;
		List<Integer> execIndexes = new ArrayList<Integer>();
		
		List<Agent> _agents=new ArrayList<Agent>();
		_agents.add(ag1);
		_agents.add(ag2);
		
		while(currentTime<(startTime+duration)){
			try {
				Thread.sleep((long)timeStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Faire en sorte que l'ordre d'exécution des agents soit aléatoire
			execIndex=r.nextInt(2);
			
			Agent a = _agents.get(execIndex);
			a.execAgent();
			if(a.getAgentId().equals("Agent1")){
				valsAg1 = a.getLastValues();
				_lastConfidenceA1=a.getLastConf();
				_lastAccValueA1=a.getLastAcc();
				_motivationA1=a.getMotivation();
				_emotionStateA1=getEmotionValue(a.getEmotionalState());
			} else{
				valsAg2 = a.getLastValues();
				_lastConfidenceA2=a.getLastConf();
				_lastAccValueA2=a.getLastAcc();
				_motivationA2=a.getMotivation();
				_emotionStateA2=getEmotionValue(a.getEmotionalState());
			}
			execIndexes.add(execIndex);
			// Exécuter l'index restant
			execIndex=(execIndex+1)%2;
			
			a = _agents.get(execIndex);
			a.execAgent();
			if(a.getAgentId().equals("Agent2")){
				valsAg2 = a.getLastValues();
				_lastConfidenceA2=a.getLastConf();
				_lastAccValueA2=a.getLastAcc();
				_motivationA2=a.getMotivation();
				_emotionStateA2=getEmotionValue(a.getEmotionalState());
			} else{
				valsAg1 = a.getLastValues();
				_lastConfidenceA1=a.getLastConf();
				_lastAccValueA1=a.getLastAcc();
				_motivationA1=a.getMotivation();
				_emotionStateA1=getEmotionValue(a.getEmotionalState());
			}
			
			execIndexes.add(execIndex);
			currentTime=TimeUtils.getCurrentTime()/1000;
			
//			SwingUtilities.invokeLater( new Runnable(){
//					public void run(){
//						sp.addNewData("Agent1", new float[]{(float)valsAg1.get("volume").doubleValue(),(float)valsAg2.get("volume").doubleValue()} );
//						sp2.addNewData("Agent1", new float[]{(float)valsAg1.get("gaze").doubleValue(),(float)valsAg2.get("gaze").doubleValue()} );
//						sp3.addNewData("Agent2", new float[]{(float)_lastConfidenceA1,(float)_lastAccValueA1} );
//						sp4.addNewData("Agent2", new float[]{(float)_lastConfidenceA2,(float)_lastAccValueA2} );
//						sp5.addNewData("Agent2", new float[]{(float)valsAg1.get("face").doubleValue(),(float)valsAg2.get("face").doubleValue()});
//						sp6.addNewData("Agent1", new float[]{(float)_motivationA1,(float)_motivationA2});
//						sp7.addNewData("Agent1", new float[]{(float)_emotionStateA1,(float) _emotionStateA2});
//					}
//				});
		}
//		System.out.println("Agent 1");
		String resAg1=ag1.displaySignalValues();
		
		try {
			fw1.write(resAg1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println("Agent 2");
		String resAg2=ag2.displaySignalValues();
		
		try {
			fw2.write(resAg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String inpAg2 = ag2.displayInputs();
		ag2.dispConfidenceAccumulation();
		
//		System.out.println("############# Inputs #############");
//		System.out.println(inpAg2);
//		System.out.println("##################################");
//		System.out.println("############# Result agent 2 #############");
//		System.out.println(resAg2);
//		System.out.println("##################################");
		ag2.dispStates();
//		System.out.println(resAg1);
		System.out.println(resAg2);
		ag2.dispConfidenceAccumulation();
		ag2.dispLocalConfs();
		ag1.dispMotValues();
		
		System.out.println("Confidence");
		ag2.dispConfValues();
		
		System.out.println("###################");
		
		ag1.dispStates();
		
		System.out.println("###################");
		
		ag2.dispStates();
		
		try {
			fw1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fw2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static double getEmotionValue(String emotionalState) {
		double res=0.0;
		if(emotionalState.equals("Rage")){
			res=1.0;
		} else if(emotionalState.equals("Fear")){
			res=2.0;
		} else if(emotionalState.equals("Disgust")){
			res=3.0;
		} else if(emotionalState.equals("Sadness")){
			res=4.0;
		} else if(emotionalState.equals("Neutral")){
			res=5.0;
		}
		return res;
	}

}

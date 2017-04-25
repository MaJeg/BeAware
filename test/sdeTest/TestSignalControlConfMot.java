package test.sdeTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import action.SignalEquation;
import equation.EulerFunc;
import equation.OrdinarySolver;
import equation.RungeKuttaSolver;
import implEqu.BifurcSpeaker;
import implEqu.GeneEqu;
import utils.TimeUtils;

public class TestSignalControlConfMot {

	public static void main(String[] args) {
		TimeUtils.init();
		BifurcSpeaker bl = new BifurcSpeaker();
		EulerFunc f = new GeneEqu(4.0, 20.0,bl);
		OrdinarySolver os = new RungeKuttaSolver(f);
		SignalEquation se = new SignalEquation(os, f,new double[]{1.0,0.0},"volumeId");
		
		FileWriter resFile=null;
		try {
			resFile = new FileWriter(new File("testSigControl.dat"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		double duration=10.0;
		long timeStep=10;
		double startTime=TimeUtils.getCurrentTime()/1000.0;
		double currentTime=startTime;
		double currVal=0.0;
		se.setMotivation(-1.0);
		se.setConfidence(-1.0);
		System.out.println(startTime+duration);
		System.out.println(currentTime);
		List<Double> times = new ArrayList<Double>();
		List<Double> values = new ArrayList<Double>();
		List<Double> confidence = new ArrayList<Double>();
		List<Double> motivation = new ArrayList<Double>();
		times.add(currentTime);
		values.add(0.0);
		confidence.add(-1.0);
		motivation.add(-1.0);
		
		while(currentTime<(startTime+duration)){
			System.out.println("Sleeping "+timeStep+" ms");
			try {
				Thread.sleep((long)timeStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(currentTime>(startTime+duration)/3 && currentTime<2*(startTime+duration)/3){
				se.setConfidence(1.0);
				se.setMotivation(1.0);
			}else{
				se.setConfidence(-1.0);
				se.setMotivation(-1.0);
			}
			
			// Temps d'exécution en millisecondes transcrit en secondes (/1000)
			currVal=se.execute()[1];
			values.add(currVal);
			confidence.add(se.getConfidence());
			motivation.add(se.getMotivation());
			currentTime=(TimeUtils.getCurrentTime()/1000.0);
			times.add(currentTime);
		}
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("./res_signal_control.csv"));
			bw.write("time,volume,confidence,motivation");
			bw.newLine();
			for(int i=0;i<times.size();i++){
				bw.write(times.get(i).toString() +","+values.get(i).toString()+ ","+confidence.get(i).toString()+","+motivation.get(i).toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

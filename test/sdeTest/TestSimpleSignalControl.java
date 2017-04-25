package test.sdeTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import action.AttrFunc;
import action.SignalEquation;
import equation.EulerFunc;
import equation.OrdinarySolver;
import equation.RungeKuttaSolver;
import implEqu.GeneEqu;
import implEqu.SimpleAttractorFunction;
import utils.TimeUtils;

public class TestSimpleSignalControl {
	public static void main(String args[]){
TimeUtils.init();
		
		AttrFunc af = new SimpleAttractorFunction();
		EulerFunc f = new GeneEqu(4.0, 20.0,af);
		OrdinarySolver os = new RungeKuttaSolver(f);
		SignalEquation se = new SignalEquation(os, f,new double[]{1.0,1.0},"volumeId");
		List<Double> resVec = new ArrayList<Double>();
		FileWriter resFile=null;
		try {
			resFile = new FileWriter(new File("testSigControl.dat"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		double duration=10.0;
		long timeStep=100;
		double startTime=TimeUtils.getCurrentTime()/1000.0;
		double currentTime=startTime;
		se.setConfidence(1.0);
		se.setMotivation(-1.0);
		System.out.println(startTime+duration);
		System.out.println(currentTime);
		
		while(currentTime<(startTime+duration)){
			System.out.println("Sleeping "+timeStep+" ms");
			try {
				Thread.sleep((long)timeStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resVec.add(se.execute()[1]);
			// Temps d'exécution en millisecondes transcrit en secondes (/1000)
			currentTime=(TimeUtils.getCurrentTime()/1000.0);	
		}
		for(int i=0;i<resVec.size();i++){
			try {
				resFile.write(Double.toString(resVec.get(i)));
				resFile.write("\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			resFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}

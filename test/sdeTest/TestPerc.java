package test.sdeTest;

import java.util.ArrayList;
import java.util.List;

import perception.PercEquation;
import perception.SumAccFunction;
import utils.TimeUtils;

public class TestPerc {
	public static void main(String args[]){
		TimeUtils.init();
		SumAccFunction af = new SumAccFunction();
		
		// Coefficient pour la constante
		af.addParam(0, 0.5);
		
		// Coefficient pour le terme du signal
		af.addParam(1, -2.0);
		
		// Coefficient pour la dérivée du signal
		af.addParam(2, -0.5);
		
		PercEquation pe = new PercEquation(0.0);
		double duration=10.0;
		long timeStep=100;
		double startTime=TimeUtils.getCurrentTime()/1000.0;
		double currentTime=startTime;
		List<Double> times = new ArrayList<Double>();
		List<Double> values = new ArrayList<Double>();
		times.add(currentTime);
		values.add(0.0);
		double vol=0.0;
		double volDot=0.0;
		double currVal=0.0;

		while(currentTime<(startTime+duration)){
			try {
				Thread.sleep((long)timeStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(currentTime>(startTime+duration)/3 && currentTime<2*(startTime+duration)/3){
				vol=0.5;
				volDot=0.0;
			} else {
				vol=0.0;
				volDot=0.0;
			}
//			pe.setS(vol);
//			pe.setsDot(volDot);
			currVal=pe.execute()[0];
			values.add(currVal);
			currentTime=(TimeUtils.getCurrentTime()/1000.0);
			times.add(currentTime);
			System.out.println(currVal);
		}
	}
}

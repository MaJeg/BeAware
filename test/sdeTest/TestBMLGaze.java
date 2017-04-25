package test.sdeTest;

import java.util.ArrayList;
import java.util.Scanner;

import agent.ContinuousParameter;
import agent.Data;
import agent.MARCSender;
import agent.MarcBehaviorUpdater;
import agent.MarcGazeBehaviorUpdater;
import agent.ParameterTypeException;

public class TestBMLGaze {

	public static void main(String[] args) {
		int port=4010;
		MARCSender mSender = new MARCSender(4010,4012,"Hello world","Microsoft Hortense Desktop");
		MarcBehaviorUpdater mbu = new MarcGazeBehaviorUpdater(port,"bml10","Track_0",mSender);
		
		Data ed = new Data("gaze");
		ed.addParam(new ContinuousParameter("gaze",0.8));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("gaze",0.7));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("gaze",-1.0));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		ed.addParam(new ContinuousParameter("gaze",1.0));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("gaze",1.5));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("gaze",0.7));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		sc.close();
	}
	
	
}

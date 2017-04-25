package test.sdeTest;

import java.util.ArrayList;
import java.util.Scanner;

import agent.ContinuousParameter;
import agent.Data;
import agent.MARCSender;
import agent.MarcBehaviorUpdater;
import agent.MarcFaceUpdater;
import agent.ParameterTypeException;

public class TestBMLBrows {

	public static void main(String args[]){
		MARCSender mSender=new MARCSender(4010,4012,"Hello world","Microsoft Hortense Desktop");
		MarcBehaviorUpdater mbu = new MarcFaceUpdater(4010,"bml20","Track_0",mSender);
		
		Data ed = new Data("Brows");
		ed.addParam("au", new ContinuousParameter("au",0.9));
		
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
		
		ed.addParam("au", new ContinuousParameter("au",0.8));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.7));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.6));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.5));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.4));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.3));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.2));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.1));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.0));
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
		
		ed.addParam("au", new ContinuousParameter("au",0.9));
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

package test.sdeTest;

import java.util.ArrayList;
import java.util.List;

import agent.MARCSender;

public class TestMarcSender {
	public static void main(String args[]){
		List<String> femaleVoices=new ArrayList<String>();
		MARCSender ms = new MARCSender(4010,4012,"",femaleVoices);
		ms.setAppraisalVariable("unpleasant", "0.0");
		ms.setAppraisalVariable("expected", "1.0");
		ms.setAppraisalVariable("cause", "other");
		ms.setAppraisalVariable("externalStandards", "0.0");
		ms.setAppraisalVariable("goalRelevance", "1.0");
		ms.setAppraisalVariable("goalHindrance", "1.0");
		ms.setAppraisalVariable("copingPower", "-1.0");
		ms.setAppraisalVariable("externalCause", "external");
		ms.updateEmotion();
		// À priori tempo nécessaire pour tenir compte modifications
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ms.sendToMarc("au_2", "track_0","bml_item_1", 1.0);
		// À priori tempo nécessaire pour tenir compte modifications
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ms.setAppraisalVariable("unpleasant", "0.0");
		ms.setAppraisalVariable("expected", "0.0");
		ms.setAppraisalVariable("cause", "other");
		ms.setAppraisalVariable("externalStandards", "0.0");
		ms.setAppraisalVariable("goalRelevance", "0.0");
		ms.setAppraisalVariable("goalHindrance", "0.0");
		ms.setAppraisalVariable("copingPower", "0.0");
		ms.setAppraisalVariable("externalCause", "external");
		ms.updateEmotion();
		// À priori tempo nécessaire pour tenir compte modifications
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ms.sendToMarc("au_2", "track_0","bml_item_1", 1.0);
		// À priori tempo nécessaire pour tenir compte modifications
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

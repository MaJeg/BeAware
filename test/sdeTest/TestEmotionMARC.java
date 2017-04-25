package test.sdeTest;

import java.util.ArrayList;

import agent.MARCSender;

public class TestEmotionMARC {
	public static void main(String args[]){
		
//		if(appType.equals("unpleasant")){
//			val=Double.parseDouble(value);
//			_unpleasant=val;
//		} else if(appType.equals("expected")){
//			val=Double.parseDouble(value);
//			_expected=val;
//		} else if(appType.equals("copingPower")){
//			val=Double.parseDouble(value);
//			_copingPower=val;
//		} else if(appType.equals("goalHindrance")){
//			val=Double.parseDouble(value);
//			_goalHindrance=val;
//		} else if(appType.equals("externalStandards")){
//			val=Double.parseDouble(value);
//			_externalStandards=val;
//		} else if(appType.equals("internalStandards")){
//			val=Double.parseDouble(value);
//			_internalStandards=val;
//		} else if(appType.equals("goalRelevance")){
//			val=Double.parseDouble(value);
//			_goalRelevance=val;
//		} else if(appType.equals("cause")){
//			_cause=value;
//		} 
//		
		MARCSender ms = new MARCSender(4010,4012,"Hello world","Microsoft Hortense Desktop");
		
		// Expression de rage
		ms.setAppraisalVariable("unpleasant", "1.0");
		ms.setAppraisalVariable("expected", "-1.0");
		ms.setAppraisalVariable("cause", "other");
		ms.setAppraisalVariable("externalStandards", "-1.0");
		ms.setAppraisalVariable("goalRelevance", "1.0");
		ms.setAppraisalVariable("goalHindrance", "1.0");
		ms.setAppraisalVariable("copingPower", "1.0");
		ms.setAppraisalVariable("externalCause", "external");
		
		// Expression de tristesse
//		ms.setAppraisalVariable("unpleasant", "0.0");
//		ms.setAppraisalVariable("expected", "1.0");
//		ms.setAppraisalVariable("cause", "other");
//		ms.setAppraisalVariable("externalStandards", "0.0");
//		ms.setAppraisalVariable("goalRelevance", "1.0");
//		ms.setAppraisalVariable("goalHindrance", "1.0");
//		ms.setAppraisalVariable("copingPower", "-1.0");
//		ms.setAppraisalVariable("externalCause", "external");
		ms.updateEmotion();
	
//		if(_expected<0.0 && _copingPower>0.0 && _goalHindrance>0.0 && _cause.equals("Other") && _externalStandards<0.0 && _goalRelevance>0.0){
//			_emotionalState="Rage";
//		} else if(_unpleasant>0.0 && _expected<0.0 && _copingPower<0.0 && _goalHindrance>0.0 && _cause.equals("Other") && _goalRelevance>0.0){
//			_emotionalState="Fear";
//		} else if(_unpleasant>0.0 && _expected<0.0 && _goalRelevance<0.0){
//			_emotionalState="Disgust";
//		} else if(_expected>0.0 && _copingPower<0.0 && _goalHindrance>0.0 && _goalRelevance>0.0){
//			_emotionalState="Sadness";
//		} else if(_expected==0.0 && _copingPower==0.0 && _goalHindrance==0.0 && _externalStandards==0.0 && _goalRelevance==0.0){
//			_emotionalState="Neutral";
//		}
	}
}

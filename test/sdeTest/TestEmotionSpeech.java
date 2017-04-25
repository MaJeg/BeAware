package test.sdeTest;

import java.util.ArrayList;
import java.util.List;

import agent.MARCSender;

public class TestEmotionSpeech {

	public static void main(String[] args) {
		List<String> femaleVoices=new ArrayList<String>();
		femaleVoices.add("female_voice_smoothed");
		MARCSender ms = new MARCSender(4013,4015,"C:\\Users\\mat29\\Desktop\\",femaleVoices);
		// Expression de rage
//		ms.setAppraisalVariable("unpleasant", "1.0");
//		ms.setAppraisalVariable("expected", "-1.0");
//		ms.setAppraisalVariable("cause", "other");
//		ms.setAppraisalVariable("externalStandards", "-1.0");
//		ms.setAppraisalVariable("goalRelevance", "1.0");
//		ms.setAppraisalVariable("goalHindrance", "1.0");
//		ms.setAppraisalVariable("copingPower", "1.0");
//		ms.setAppraisalVariable("externalCause", "external");
		ms.sendToMarc("speech", "Track_0", "bml1", 0.3);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Expression de tristesse
		ms.setAppraisalVariable("unpleasant", "0.0");
		ms.setAppraisalVariable("expected", "1.0");
		ms.setAppraisalVariable("cause", "other");
		ms.setAppraisalVariable("externalStandards", "0.0");
		ms.setAppraisalVariable("goalRelevance", "1.0");
		ms.setAppraisalVariable("goalHindrance", "1.0");
		ms.setAppraisalVariable("copingPower", "-1.0");
		ms.setAppraisalVariable("externalCause", "external");
		ms.updateEmotion();
		
//		<emotionml>
//		<emotion>
//		   <appraisals>
//			  <expectedness value="1.0" mode="additive"/>
//		     <unpleasantness value="0.0" mode="additive"/>
//		     <goal_hindrance value="1.0" mode="additive"/>
//		     <copying_control value="-1.0" mode="additive"/>
//		     <copying_power value="-1.0" mode="additive"/>
//		     <external_causation value="1.0" mode="additive"/>
//				</appraisals>	</emotion>
//		</emotionml>
			
		
//		<emotion>
//		   <appraisals>
//			  <expectedness value="1.0" mode="additive"/>
//		     <unpleasantness value="0.0" mode="additive"/>
//		     <goal_hindrance value="1.0" mode="additive"/>
//		     <copying_control value="-1.0" mode="additive"/>
//		     <copying_power value="-1.0" mode="additive"/>
//		     <external_causation value="1.0" mode="additive"/>
//				</appraisals>	</emotion>
//		</emotionml>
	}

}

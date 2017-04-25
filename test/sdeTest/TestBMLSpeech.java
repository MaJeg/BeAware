package test.sdeTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import agent.ContinuousParameter;
import agent.Data;
import agent.EventParameter;
import agent.MARCSender;
import agent.MarcSpeechUpdater;
import agent.ParameterTypeException;

public class TestBMLSpeech {
	public static void main(String args[]){
		MARCSender mSender = new MARCSender(4013,4015,"Hello world","Microsoft Hortense Desktop");
		MarcSpeechUpdater mbu = new MarcSpeechUpdater(4013,"bml1","Track0",mSender);
		List<String> utterances = new ArrayList<String>();
		utterances.add("sample_resynth_orig.wav");
		utterances.add("sample_resynth_upset.wav");
		utterances.add("sample_resynth_insist.wav");
		mbu.setUtterance("sample_resynth",utterances);
		mbu.setWavDir("C:\\Users\\mat29\\Desktop\\");
		mbu.setWavFile("male_voice");
		
//		Clip clip = null;
//		try {
//			clip = AudioSystem.getClip();
//		} catch (LineUnavailableException e10) {
//			// TODO Auto-generated catch block
//			e10.printStackTrace();
//		}
//        AudioInputStream inputStream = null;
//		try {
//			inputStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\mat29\\Desktop\\male_voice.wav"));
//		} catch (UnsupportedAudioFileException e9) {
//			// TODO Auto-generated catch block
//			e9.printStackTrace();
//		} catch (IOException e9) {
//			// TODO Auto-generated catch block
//			e9.printStackTrace();
//		}
//        try {
//			clip.open(inputStream);
//		} catch (LineUnavailableException e9) {
//			// TODO Auto-generated catch block
//			e9.printStackTrace();
//		} catch (IOException e9) {
//			// TODO Auto-generated catch block
//			e9.printStackTrace();
//		}
//        clip.start(); 
		
		Data ed = new Data("speech");
		ed.addParam(new EventParameter("utterance", "sample_resynth"));
		ed.addParam(new ContinuousParameter("volume",0.1));
		ed.addParam(new ContinuousParameter("pitch", 0.5));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e8) {
			// TODO Auto-generated catch block
			e8.printStackTrace();
		}
		
		try{
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("volume", 1.0));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e7) {
			// TODO Auto-generated catch block
			e7.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ed.addParam(new ContinuousParameter("volume", 0.9));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e6) {
			// TODO Auto-generated catch block
			e6.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ed.addParam(new ContinuousParameter("volume", 0.8));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("volume", 0.7));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("volume", 0.6));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("volume", 0.5));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		ed.addParam(new ContinuousParameter("volume", 0.0));
		try {
			mbu.updateBehavior(ed);
		} catch (ParameterTypeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

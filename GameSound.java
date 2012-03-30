import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

public class GameSound {
	private static Clip rotateLine;
	private static Clip landLine;
	private static Clip lineClearLine;
	private static Clip levelUpLine;
	
	public static void init() {
		try {
			File rotateFile = new File("sounds/rotate.wav");
			File landFile = new File("sounds/block_land.wav");
			File lineClearFile = new File("sounds/line_clear.wav");
			File levelUpFile = new File("sounds/levelup.wav");
			
			AudioInputStream rotateStream =
				AudioSystem.getAudioInputStream(rotateFile);
	        AudioInputStream landStream = 
				AudioSystem.getAudioInputStream(landFile);
			AudioInputStream lineClearStream =
				AudioSystem.getAudioInputStream(lineClearFile);
			AudioInputStream levelUpStream =
				AudioSystem.getAudioInputStream(levelUpFile);
			
			DataLine.Info rotateInfo =
				new DataLine.Info(Clip.class, rotateStream.getFormat());
			DataLine.Info landInfo = 
				new DataLine.Info(Clip.class, landStream.getFormat());
			DataLine.Info lineClearInfo =
				new DataLine.Info(Clip.class, lineClearStream.getFormat());
			DataLine.Info levelUpInfo =
				new DataLine.Info(Clip.class, levelUpStream.getFormat());
			
			rotateLine = (Clip) AudioSystem.getLine(rotateInfo);	
			landLine = (Clip) AudioSystem.getLine(landInfo);
			lineClearLine = (Clip) AudioSystem.getLine(lineClearInfo);
			levelUpLine = (Clip) AudioSystem.getLine(levelUpInfo);
			
			rotateLine.open(rotateStream);
			landLine.open(landStream);
			lineClearLine.open(lineClearStream);
			levelUpLine.open(levelUpStream);
		} catch (Exception e) {
			System.err.println("Error loading sounds, exiting...");
			System.exit(-1);
		}
	}
	
	public static void playLand() {
		if (landLine.isRunning()) {
			landLine.stop();
		}
		landLine.setFramePosition(0);
		landLine.start();
	}
	
	public static void playRotate() {
		if (rotateLine.isRunning()) {
			rotateLine.stop();
		}
		rotateLine.setFramePosition(0);
		rotateLine.start();
	}
	
	public static void playLineClear() {
		if (lineClearLine.isRunning()) {
			lineClearLine.stop();
		}
		lineClearLine.setFramePosition(500);
		lineClearLine.start();
	}
	
	public static void playLevelUp() {
		if (levelUpLine.isRunning()) {
			levelUpLine.stop();
		}
		levelUpLine.setFramePosition(0);
		levelUpLine.start();
	}
	
	// public static void main(String[] args) {
	// 	playLandSound();
	// 	playLineClearSound();
	// }
}

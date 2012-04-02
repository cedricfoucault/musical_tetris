import javax.swing.*;
import javax.sound.sampled.*;
import java.io.File;

public class GameSound {
	private static Clip rotateLine;
	private static Clip landLine;
	private static Clip fallLine;
	private static Clip hardDropLine;
	private static Clip dropLine[];
	private static Clip lineClearLine;
	private static Clip levelUpLine;
	
	public static void init() {
		try {
			int i;
			dropLine = new Clip[6];
			// File rotateFile = new File("sounds/rotate.wav");
			File rotateFile = new File("sounds/tmbo.wav");
			// File landFile = new File("sounds/block_land.wav");
			File landFile = new File("sounds/kick_snare.wav");
			File fallFile = new File("sounds/kick.wav");
			File hardDropFile = new File("sounds/claves.wav");
			File[] dropFile = {
				new File("sounds/hihat1.wav"),
				new File("sounds/hihat2.wav"),
				new File("sounds/hihat3.wav"),
				new File("sounds/hihat4.wav"),
				new File("sounds/hihat6.wav"),
				new File("sounds/hihat6.wav"),
				new File("sounds/hihat7.wav"),
				new File("sounds/hihat6.wav")
			};
			File lineClearFile = new File("sounds/line_clear.wav");
			File levelUpFile = new File("sounds/levelup.wav");
			
			AudioInputStream rotateStream =
				AudioSystem.getAudioInputStream(rotateFile);
	        AudioInputStream landStream = 
				AudioSystem.getAudioInputStream(landFile);
			AudioInputStream fallStream =
				AudioSystem.getAudioInputStream(fallFile);
			AudioInputStream hardDropStream =
				AudioSystem.getAudioInputStream(hardDropFile);
			AudioInputStream[] dropStream = new AudioInputStream[6];
			for (i = 0; i < 6; i++) {
				dropStream[i] = AudioSystem.getAudioInputStream(dropFile[i]);
			}
			AudioInputStream lineClearStream =
				AudioSystem.getAudioInputStream(lineClearFile);
			AudioInputStream levelUpStream =
				AudioSystem.getAudioInputStream(levelUpFile);
			
			DataLine.Info rotateInfo =
				new DataLine.Info(Clip.class, rotateStream.getFormat());
			DataLine.Info landInfo = 
				new DataLine.Info(Clip.class, landStream.getFormat());
			DataLine.Info fallInfo =
				new DataLine.Info(Clip.class, fallStream.getFormat());
			DataLine.Info hardDropInfo =
				new DataLine.Info(Clip.class, hardDropStream.getFormat());
			DataLine.Info dropInfo[] = new DataLine.Info[6];
			for (i = 0; i < 6; i++) {
				dropInfo[i] =
					new DataLine.Info(Clip.class, dropStream[i].getFormat());
			}
			DataLine.Info lineClearInfo =
				new DataLine.Info(Clip.class, lineClearStream.getFormat());
			DataLine.Info levelUpInfo =
				new DataLine.Info(Clip.class, levelUpStream.getFormat());
			
			rotateLine = (Clip) AudioSystem.getLine(rotateInfo);	
			landLine = (Clip) AudioSystem.getLine(landInfo);
			fallLine = (Clip) AudioSystem.getLine(fallInfo);
			hardDropLine = (Clip) AudioSystem.getLine(hardDropInfo);
			for (i = 0; i < 6; i++) {
				dropLine[i] = (Clip) AudioSystem.getLine(dropInfo[i]);
			}
			lineClearLine = (Clip) AudioSystem.getLine(lineClearInfo);
			levelUpLine = (Clip) AudioSystem.getLine(levelUpInfo);
			
			rotateLine.open(rotateStream);
			landLine.open(landStream);
			fallLine.open(fallStream);
			hardDropLine.open(hardDropStream);
			for (i = 0; i < 6; i++) {
				dropLine[i].open(dropStream[i]);
			}
			lineClearLine.open(lineClearStream);
			levelUpLine.open(levelUpStream);
		} catch (Exception e) {
			System.err.println("Error loading sounds, exiting...");
			System.exit(-1);
		}
	}
	
	public static void playRotate() {
		if (rotateLine.isRunning()) {
			rotateLine.stop();
		}
		rotateLine.setFramePosition(0);
		rotateLine.start();
	}
	
	public static void playLand() {
		if (landLine.isRunning()) {
			landLine.stop();
		}
		landLine.setFramePosition(0);
		landLine.start();
	}
	
	public static void playFall() {
		if (fallLine.isRunning()) {
			fallLine.stop();
		}
		fallLine.setFramePosition(0);
		fallLine.start();
	}
	
	public static void playHardDrop() {
		if (hardDropLine.isRunning()) {
			hardDropLine.stop();
		}
		hardDropLine.setFramePosition(0);
		hardDropLine.start();
	}
	
	public static void playDrop(int level) {
		int i = level - 1;
		if (level > 6) {
			i = 5;
		}
		if (dropLine[i].isRunning()) {
			dropLine[i].stop();
		}
		dropLine[i].setFramePosition(0);
		dropLine[i].loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void stopDrop() {
		for (int i = 0; i < 6; i++) {
			if (dropLine[i].isRunning()) {
				dropLine[i].stop();
			}
		}
	}
	
	public static void playLineClear() {
		if (lineClearLine.isRunning()) {
			lineClearLine.stop();
		}
		lineClearLine.setFramePosition(600);
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

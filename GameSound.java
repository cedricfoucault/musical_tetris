import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Clip;

import java.io.InputStream;
import java.io.BufferedInputStream;

public class GameSound {
	private static Clip rotateLine;
	private static Clip landLine;
	private static Clip fallLine;
	private static Clip hardDropLine;
	private static Clip dropLine[];
	private static Clip lineClearLine;
	private static Clip levelUpLine;
	
	// used to init the audio for the game: load the sounds from raw data
	public static void init() {
		try {
			int i;
			dropLine = new Clip[6];
			InputStream rotateFile = GameSound.class.getResourceAsStream(
			    "sounds/tmbo.wav");
			InputStream landFile = GameSound.class.getResourceAsStream(
			    "sounds/kick_snare.wav");
			InputStream fallFile = GameSound.class.getResourceAsStream(
			    "sounds/kick.wav");
			InputStream hardDropFile = GameSound.class.getResourceAsStream(
			    "sounds/claves.wav");
			InputStream[] dropFile = {
			    GameSound.class.getResourceAsStream("sounds/hihat1.wav"),
			    GameSound.class.getResourceAsStream("sounds/hihat2.wav"),
			    GameSound.class.getResourceAsStream("sounds/hihat3.wav"),
			    GameSound.class.getResourceAsStream("sounds/hihat4.wav"),
			    GameSound.class.getResourceAsStream("sounds/hihat5.wav"),
			    GameSound.class.getResourceAsStream("sounds/hihat6.wav")
			};
			InputStream lineClearFile = GameSound.class.getResourceAsStream(
			    "sounds/line_clear.wav");
			InputStream levelUpFile = GameSound.class.getResourceAsStream(
			    "sounds/levelup.wav");
			    
			InputStream rotateBuffered = new BufferedInputStream(rotateFile);  			
			AudioInputStream rotateStream =
				AudioSystem.getAudioInputStream(rotateBuffered);
			InputStream landBuffered = new BufferedInputStream(landFile);
	        AudioInputStream landStream = 
				AudioSystem.getAudioInputStream(landBuffered);
			InputStream fallBuffered = new BufferedInputStream(fallFile);
			AudioInputStream fallStream =
				AudioSystem.getAudioInputStream(fallBuffered);
			InputStream hardDropBuffered =
			    new BufferedInputStream(hardDropFile);
			AudioInputStream hardDropStream =
				AudioSystem.getAudioInputStream(hardDropBuffered);
			AudioInputStream[] dropStream = new AudioInputStream[6];
			InputStream dropBuffered;
			for (i = 0; i < 6; i++) {
			    dropBuffered = new BufferedInputStream(dropFile[i]);
				dropStream[i] = AudioSystem.getAudioInputStream(dropBuffered);
			}
			InputStream lineClearBuffered =
			    new BufferedInputStream(lineClearFile);
			AudioInputStream lineClearStream =
				AudioSystem.getAudioInputStream(lineClearBuffered);
			InputStream levelUpBuffered =
			    new BufferedInputStream(levelUpFile);
			AudioInputStream levelUpStream =
				AudioSystem.getAudioInputStream(levelUpBuffered);
			
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
			System.err.println(e.toString());
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
	
	public static void playLineClear(int nRows) {
		if (lineClearLine.isRunning()) {
			lineClearLine.stop();
		}
		lineClearLine.setFramePosition(0);
		lineClearLine.start();
	}
	
	public static void playLevelUp() {
		if (levelUpLine.isRunning()) {
			levelUpLine.stop();
		}
		levelUpLine.setFramePosition(0);
		levelUpLine.start();
	}
	
	public static void close(){
	    rotateLine.close();
	    landLine.close();
    	fallLine.close();
    	hardDropLine.close();
    	for (Clip line: dropLine) {
    	    line.close();
    	}
    	lineClearLine.close();
    	levelUpLine.close();
	}
}

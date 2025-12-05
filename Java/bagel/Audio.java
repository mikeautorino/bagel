package bagel;

import java.io.File;
import javax.sound.sampled.*;

/**
 *  Load an audio file (sound or music). 
 *  <br><br>
 *  Sound files are typically small, short audio files 
 *  that are played when discrete game events occur 
 *  (such as when an item is collected, or when two objects collide).
 *  Sound files are completely loaded into memory.
 *  <br><br>
 *  Music files are typically large, long audio files
 *  containing background music that is played continuously during the game.
 *  Music files are streamed from a file as they are played.
 */
public class Audio
{
	private Clip clip;
	private float volume;

	/**
	 *  Empty constructor for internal methods; 
	 *  use {@link #loadMusic(String)} or {@link #loadSound(String)} to create an instance.
	 */
	Audio()
	{
		this.volume = 1.0f;
	}
	
	/**
	 * Create an audio object from a sound file.
	 * @param fileName name of the sound file
	 * @return an Audio object
	 */
	public static Audio loadSound(String fileName)
	{
		Audio audio = new Audio();
		try
		{
			File audioFile = new File(fileName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			audio.clip = AudioSystem.getClip();
			audio.clip.open(audioStream);
		}
		catch (Exception e)
		{
			System.err.println("Error loading sound file: " + fileName);
			e.printStackTrace();
		}
		return audio;
	}
	
	/**
	 * Set whether this audio should repeat forever or play once.
	 * @param loop whether this audio should repeat forever
	 */
	public void setLoop(boolean loop)
	{
		if (clip != null)
		{
			if (loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.loop(0);
		}
	}
	
	/**
	 * Set volume of audio playback. 
	 * @param volume value between 0.0 (silent) and 1.0 (original volume).
	 */
	public void setVolume(double volume)
	{
		this.volume = (float) Math.max(0.0, Math.min(1.0, volume));
		
		if (clip != null)
		{
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float dB = (float) (Math.log(this.volume) / Math.log(10.0) * 20.0);
			volumeControl.setValue(dB);
		}
	}
	
	/**
	 *  Play the loaded audio file.
	 */
	public void play()
	{
		if (clip != null)
		{
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	/**
	 * Stop the audio from currently playing.
	 */
	public void stop()
	{
		if (clip != null)
		{
			clip.stop();
		}
	}
}

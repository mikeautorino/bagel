package bagel;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;


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
	private Sound sound;
	private boolean isLooping;
	private double volume;

	/**
	 *  Empty constructor for internal methods; 
	 *  use {@link #loadMusic(String)} or {@link #loadSound(String)} to create an instance.
	 */
	Audio()
    {
		this.volume = 1.0;
		this.isLooping = false;
	}
    
	/**
	 * Create an audio object from a sound file.
	 * @param fileName name of the sound file
	 * @return an Audio object
	 */
	public static Audio loadSound(String fileName)
    {
        Audio audio = new Audio();
        audio.sound = TinySound.loadSound(fileName);
        return audio;
    }
    
	/**
	 * Create an audio object from a music file.
	 * @param fileName name of the music file
	 * @return an Audio object
	 */
	public static Audio loadMusic(String fileName)
    {
        Audio audio = new Audio();
        audio.sound = TinySound.loadSound(fileName);
        return audio;
    }
    
	/**
	 * Set whether this audio should repeat forever or play once.
	 * @param loop whether this audio should repeat forever
	 */
	public void setLoop(boolean loop)
    {
		this.isLooping = loop;
        if (sound != null)
            sound.setLooping(loop);
    }
    
	/**
	 * Set volume of audio playback. 
	 * @param volume value between 0.0 (silent) and 1.0 (original volume).
	 */
	public void setVolume(double volume)
    {
		this.volume = Math.max(0.0, Math.min(1.0, volume));
        if (sound != null)
            sound.setVolume(this.volume);
    }
    
	/**
	 *  Play the loaded audio file.
	 */
	public void play()
    {
        if (sound != null)
            sound.play(volume);
    }
	
	/**
	 * Stop the audio from currently playing.
	 */
	public void stop()
    {
        if (sound != null)
            sound.stop();
    }
}

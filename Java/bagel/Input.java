package bagel;

import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handle keyboard input:
 *  keyPressed   - happens one time when you press a key (discrete / instant)
 *  keyPressing  - continues to happen while you are holding down the key (continuous)
 *  keyReleased  - happens one time when you release a key (discrete / instant)
 */
public class Input
{
    // store names of keys that have been pressed / pressing / released
    public ArrayList<String> pressedList;
    public ArrayList<String> pressingList;
    public ArrayList<String> releasedList;
    
    // because system checks for input events asynchronously to game loop,
    //   need to store any events when they occur,
    //   to be processed later on during our game loop.
    public ArrayList<String> pressQueue;
    public ArrayList<String> releaseQueue;
 
    /**
     * Input Constructor
     *
     * @param window the window that will register keyboard events
     */
    public Input(JFrame window)
    {
        pressedList = new ArrayList<String>();
        pressingList = new ArrayList<String>();
        releasedList = new ArrayList<String>();
        
        pressQueue = new ArrayList<String>();
        releaseQueue = new ArrayList<String>();
        
        window.addKeyListener(
            new KeyListener()
            {
                public void keyPressed(KeyEvent e)
                {
                    // gets the keyboard key code (number) and converts to a string (name)
                    String keyName = KeyEvent.getKeyText(e.getKeyCode());
                    // add the name to queue to be processed during game loop at correct time
                    pressQueue.add( keyName );

                    // System.out.println("Pressed: " + keyName);
                }

                public void keyReleased(KeyEvent e) 
                {
                    // gets the keyboard key code (number) and converts to a string (name)
                    String keyName = KeyEvent.getKeyText(e.getKeyCode());
                    // add the name to queue to be processed during game loop at correct time
                    releaseQueue.add( keyName );

                    // System.out.println("Released: " + keyName);
                }

                public void keyTyped(KeyEvent e) { }
        });
    }
    
    /**
     * Process the data (key names) stored in queues,
     *  so that keyPressed/keyPressing/keyReleased lists
     *  have correct information at the correct time
     *  (during our game loop).
     */
    public void update()
    {
        // any keys which were previously pressed/released,
        //   only true for one loop after update function called,
        //   so now is the time to clear those lists
        pressedList.clear();
        releasedList.clear();
        
        // update the lists, based on key names stored in queues
        for (String keyName : pressQueue)
        {
            // prevent duplicate key names in lists;
            //   holding down a key can trigger multiple keyPress events
            // continue: immediately skips to next element in loop
            if (pressingList.contains(keyName))
                continue;
                
            pressedList.add(keyName);
            pressingList.add(keyName);
        }
        
        // done with press queue; clear: remove all elements from queue
        pressQueue.clear();
        
        for (String keyName : releaseQueue)
        {
            pressingList.remove(keyName);
            releasedList.add(keyName);
        }
        
        releaseQueue.clear();        
    }
    
    /**
     * Check if a key was just pressed.
     *
     * @param keyName the name of the key to check
     * @return true, if the key was just pressed
     */
    public boolean isKeyPressed(String keyName)
    {
        return pressedList.contains(keyName);
    }
    
    /**
     * Check if a key is currently being held down.
     *
     * @param keyName the name of the key
     * @return true, if the key is being held down
     */
    public boolean isKeyPressing(String keyName)
    {
        return pressingList.contains(keyName);
    }
    
    /**
     * Check if key was just released (just let go).
     *
     * @param keyName the name of the key to check
     * @return true, if key was released
     */
    public boolean isKeyReleased(String keyName)
    {
        return releasedList.contains(keyName);
    }
    
}

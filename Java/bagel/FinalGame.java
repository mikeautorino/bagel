package bagel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *  Main class to be extended for game projects.
 *  Creates the window, {@link Input} and {@link Group} objects, 
 *    and manages the life cycle of the game (initialization and game loop). 
 */
public abstract class FinalGame extends Game
{
	/**
	 * default width of game canvas. may change if desired.
	 */
	public static int windowWidth  = 800;
	
	/**
	 * default height of game canvas. may change if desired.
	 */
	public static int windowHeight = 600;
	
    /**
     * area where game graphics are displayed
     */
    Canvas canvas;

	/**
	 * Used to store and update the state of the keyboard and mouse.
	 */
	public Input input;

    /**
     * timestamp for start of previous game loop; used to calculate {@link #deltaTime}
     */
	long previousTime;

	/**
	 * amount of time that has passed since the last iteration of the game loop
	 */
	public double deltaTime; 

    /**
     * Initialize objects used in this game.
     * This method should be overridden by the specific game extending this class.
     */
	public void create()
    {    }

	/**
	 * Update objects used in this game.
	 * Called 60 times per second when possible.
	 * This method should be overridden by the specific game extending this class. 
	 * @param dt amount of time that has passed since the last iteration of the game loop
	 */
	public void update(double dt)
    {    }
    
        /**
     * Add a sprite to the group with the given name.
     * @param finalSprite sprite to be added
     * @param groupName name of the group to add the sprite to
     */
    public void addSpriteToGroup(FinalSprite finalSprite, String groupName)
    {
        getGroup( groupName ).addFinalSprite( finalSprite );
    }

	/**
	 *  Initializes the window, Input and Group objects, 
	 *  and manages the life cycle of the game (initialization and game loop).
	 */
    public void start()
    {
        // create UI on EDT
        FinalGame self = this;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            this.canvas = new Canvas();
            this.canvas.setSize(FinalGame.windowWidth, FinalGame.windowHeight);

            frame.add(this.canvas);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            this.input = new Input(frame);

            // initialize the group list used by Game
            this.groupList = new ArrayList<Group>();

            this.create();

            // Initialize previousTime and start game loop on a new thread
            this.previousTime = System.nanoTime();

            // create buffer strategy for smooth rendering
            this.canvas.createBufferStrategy(2);
            final BufferStrategy bs = this.canvas.getBufferStrategy();

            Thread loop = new Thread(() -> {
                while (true) {
                    long currentTime = System.nanoTime();
                    self.deltaTime = (currentTime - self.previousTime) / 1_000_000_000.0;
                    self.previousTime = currentTime;

                    // process input
                    self.input.update();

                    // update game state (user-defined)
                    update(self.deltaTime);

                    // render: draw all sprites in all groups
                    Graphics g = bs.getDrawGraphics();
                    try {
                        Graphics2D g2 = (Graphics2D) g;
                        g2.setColor(Color.GRAY);
                        g2.fillRect(0, 0, FinalGame.windowWidth, FinalGame.windowHeight);

                        for (Group gObj : groupList) {
                            for ( FinalSprite s : gObj.getFinalSpriteList() ) {
                                s.draw(g2);
                            }
                        }
                    } finally {
                        g.dispose();
                    }
                    if (!bs.contentsLost()) {
                        bs.show();
                    }

                    // sleep to cap frame rate ~60 FPS
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }, "Game-Loop");
            loop.setDaemon(true);
            loop.start();
        });
    }
}

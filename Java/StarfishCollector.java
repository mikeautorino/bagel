import bagel.Game;
import bagel.Texture;
import bagel.Sprite;

public class StarfishCollector extends Game
{
    public static void main(String[] args) 
    {
        Game g = new StarfishCollector();
        g.setWindowTitle("Starfish Collector");
        g.setWindowSize(800,600);
        g.run();
    }

    // variables referenced in both methods
    Sprite turtle;
    Sprite winMessage;

    public void initialize() 
    {
        // create groups to organize sprites
        createGroup("main");
        createGroup("starfish");

        // water background (must be added first)
        Texture waterTex = new Texture("./assets/starfish-collector/water.png");
        Sprite water = new Sprite();
        water.setPosition(400, 300);
        water.setTexture(waterTex);
        addSpriteToGroup(water, "main");

        // turtle (player character)
        Texture turtleTex = new Texture("./assets/starfish-collector/turtle.png");
        turtle = new Sprite();
        turtle.setPosition(400, 300);
        turtle.setTexture(turtleTex);
        addSpriteToGroup(turtle, "main");

        // starfish (collectible objects)
        int starfishCount = 20;
        // only need to load texture once; all sprites share it
        Texture starfishTex = new Texture("./assets/starfish-collector/starfish.png");
        for (int i = 0; i < starfishCount; i++)
        {
            Sprite starfish = new Sprite();
            double x = 700 * Math.random() + 50;
            double y = 500 * Math.random() + 50;
            starfish.setPosition(x, y);
            starfish.setTexture(starfishTex);
            addSpriteToGroup(starfish, "starfish");
        }

        // win message to display when game is over (all starfish collected)
        Texture winMessageTex = new Texture("./assets/starfish-collector/youwin.png");
        winMessage = new Sprite();
        winMessage.setPosition(400, 300);
        winMessage.setTexture(winMessageTex);
        winMessage.visible = false;
        addSpriteToGroup(winMessage, "main");
    }

    public void update(double deltaTime) 
    {
        // turtle speed, measured in pixels per second
        double moveSpeed = 100;
        // calculate distance to move turtle
        double distance = moveSpeed * deltaTime;

        // move turtle depending on which key is pressed
        if ( input.isKeyPressing("Left") )
            turtle.moveBy( -distance, 0 );
        if ( input.isKeyPressing("Right") )
            turtle.moveBy( distance, 0 );
        if ( input.isKeyPressing("Up") )
            turtle.moveBy( 0, -distance );
        if ( input.isKeyPressing("Down") )
            turtle.moveBy( 0, distance );
        if ( input.isKeyPressing("Space") )
            turtle.rotateBy(1);

        // remove starfish when turtle overlaps (collects) them
        for (Sprite starfish : getGroupSpriteList("starfish"))
        {
            if ( turtle.isOverlapping(starfish) )
            {
                removeSpriteFromGroup(starfish, "starfish");
            }
        }

        // check for win condition (all starfish collected)
        if ( !winMessage.visible && getGroupSpriteCount("starfish") == 0 )
            winMessage.visible = true;
    }
}

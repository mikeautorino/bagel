import bagel.Game;
import bagel.Texture;
import bagel.Sprite;
// import bagel.Group;

public class TestGame extends Game
{
    public static void main(String[] args) 
    {
        Game g = new TestGame();
        g.setWindowTitle("Awesomesauce");
        g.setWindowSize(800,600);
        g.run();
    }

    // variables referenced in both methods
    Sprite turtle;

    public void initialize() 
    {
        createGroup("main");
        createGroup("starfish");

        Texture turtleTex = Texture.load("Java/assets/starfish-collector/turtle.png");
        turtle = new Sprite();
        turtle.setPosition(400, 300);
        turtle.angle = 0;
        turtle.setTexture(turtleTex);
        addSpriteToGroup(turtle, "main");

        int starfishCount = 20;

        Texture starfishTex = Texture.load("Java/assets/starfish-collector/starfish.png");
        for (int i = 0; i < starfishCount; i++)
        {
            Sprite starfish = new Sprite();
            double x = 700 * Math.random() + 50;
            double y = 500 * Math.random() + 50;
            starfish.setPosition(x, y);
            starfish.setTexture(starfishTex);
            addSpriteToGroup(starfish, "starfish");
        }
    }

    public void update(double dt) 
    {
        // turtle.moveBy(1, 0);
        // starfish.rotateBy(1);

        int speed = 2;
        if ( input.isKeyPressing("Left") )
            turtle.moveBy( -speed, 0 );
        if ( input.isKeyPressing("Right") )
            turtle.moveBy( speed, 0 );
        if ( input.isKeyPressing("Up") )
            turtle.moveBy( 0, -speed );
        if ( input.isKeyPressing("Down") )
            turtle.moveBy( 0, speed );
        if ( input.isKeyPressing("Space") )
            turtle.rotateBy(1);

        for (Sprite starfish : getGroupSpriteList("starfish"))
        {
            if ( turtle.isOverlapping(starfish) )
            {
                removeSpriteFromGroup(starfish, "starfish");
                System.out.println("poof");
                // earn points for collecting starfish
                //score += 100;
                //scoreLabel.setText( "Score: " + score );
            }
        }

    }
    
}

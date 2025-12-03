import javax.swing.*;
import bagel.*;
import java.util.ArrayList;
import java.awt.*;

public class Test2 
{
    public static void mainx(String[] args) 
    {
        JFrame window = new JFrame("Example GUI");
        window.setTitle("Hello World!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 400);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // ImageIcon ii = new ImageIcon("assets/starfish-collector/turtle.png");
        // adding component
        // JButton button = new JButton("Button");
        // window.add(button);

        window.repaint();
        window.revalidate();
    }

    public static void mainy(String[] args) 
    {
        JFrame window = new JFrame();
        window.setTitle("Hello World!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 400);
        // window.setResizable(true);
        // window.setLocationRelativeTo(null);
        window.setVisible(true);

        Texture turtleTex = new Texture("Java/assets/starfish-collector/turtle.png");
        Sprite turtle = new Sprite();
        turtle.setPosition(100,50);
        turtle.setTexture(turtleTex);
        // window.add(turtle);

        Texture starfishTex = new Texture("Java/assets/starfish-collector/starfish.png");
        Sprite starfish = new Sprite();
        starfish.setPosition(10,50);
        starfish.setTexture(starfishTex);
        //window.add(starfish); // not both are drawing....

        // adding component
        // JButton button = new JButton("Button");
        // window.add(button);

        window.repaint();
        window.revalidate();
    }

    public static void main(String[] args) 
    {
        JFrame window = new JFrame();
        window.setTitle("Hello World!");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 400);
        window.setVisible(true);

        Texture turtleTex = new Texture("Java/assets/starfish-collector/turtle.png");
        Sprite turtle = new Sprite();
        turtle.setPosition(100,50);
        turtle.angle = 30;
        turtle.setTexture(turtleTex);
        // window.add(turtle);


        Texture starfishTex = new Texture("Java/assets/starfish-collector/starfish.png");
        Sprite starfish = new Sprite();
        starfish.setPosition(10,50);
        starfish.setTexture(starfishTex);
        //window.add(starfish); // not both are drawing....

        ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
        spriteList.add(turtle);
        spriteList.add(starfish);

        JPanel spritecanvas = new JPanel()
        {
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                for (Sprite bs : spriteList)
                    bs.draw(g);
            }
        };

        window.add(spritecanvas);
        // call after adding components
        window.revalidate();


        Timer timer = new Timer(1000 / 60, 
        (event) -> 
        {
            System.out.println("Hello");          
            turtle.moveBy(1, 0);
            turtle.rotateBy(1);
            
            window.repaint();      // Trigger a repaint
        });
        timer.start();
    }
}



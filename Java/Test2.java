import javax.swing.*;
import bagel.*;

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

        ImageIcon ii = new ImageIcon("assets/starfish-collector/turtle.png");
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
        // window.setResizable(true);
        // window.setLocationRelativeTo(null);
        window.setVisible(true);

        Texture turtleTex = Texture.load("Java/assets/starfish-collector/turtle.png");
        BasicSprite turtle = new BasicSprite();
        turtle.setPosition(100,50);
        turtle.setTexture(turtleTex);
        window.add(turtle);

        Texture starfishTex = Texture.load("Java/assets/starfish-collector/starfish.png");
        BasicSprite starfish = new BasicSprite();
        starfish.setPosition(10,50);
        starfish.setTexture(starfishTex);
        //window.add(starfish); // not both are drawing....

        // adding component
        // JButton button = new JButton("Button");
        // window.add(button);

        window.repaint();
        window.revalidate();
    }
}


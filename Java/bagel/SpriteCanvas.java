package bagel;

import javax.swing.*;
import java.awt.*;

public class SpriteCanvas extends JPanel 
{
    ArrayList<Sprite>
    Sprite s1 = new Sprite("image1.jpg");
    Sprite s2 = new Sprite("image2.jpg");

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        s1.draw(g, 0, 0, this);
        s2.draw(g, s1.getWidth() + 10, 0, this);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MultiImageWithSprites());
        f.setSize(800, 600);
        f.setVisible(true);
    }
}

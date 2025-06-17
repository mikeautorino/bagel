import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class ShowImage extends JPanel {
    BufferedImage img;

    public ShowImage() 
    {
        try {
            img = ImageIO.read(new File("Java/assets/starfish-collector/turtle.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null)
            g.drawImage(img, 0, 0, this);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ShowImage panel = new ShowImage();
        f.add(panel);
        f.setSize(800, 600);
        f.setVisible(true);
    }
}
package bagel;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Image data used when drawing a {@link Sprite}.
 * Multiple instances of a {@link Sprite} may share a single Texture reference.
 */
public class Texture
{
	/**
	 *  The image to be drawn.
	 */
	public BufferedImage image;
	
	/**
	 *  A rectangular sub-area of the image to be drawn.
	 *  Used for spritesheet animation.
	 */
	public Rectangle region;

	/**
	 *  Create an empty texture. Useful when reusing a previously loaded image;
	 *  used by {@link Animation} and {@link TileMap}.
	 */
	public Texture(String imageFileName)
    {
		try 
		{
            image = ImageIO.read(new File(imageFileName));
			region  = new Rectangle();
        	region.setValues( 0, 0, image.getWidth(), image.getHeight() );
        } 
		catch (IOException e) 
		{
			System.err.println("error reading file");
            e.printStackTrace();
        }
	}
}

package bagel;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

/**
 * Image data used when drawing a {@link FinalSprite} 
 * or a {@link Tile}. 
 * Texture objects are typically created using the {@link #load(String)} method.
 * Multiple instances of a {@link FinalSprite} may share a single Texture reference.
 *
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
	Texture()
    {  }

	/**
	 * Create a Texture from the image file with the given file name. 
	 * Sets {@link #region} to the original image dimensions.
	 * @param imageFileName name of the image file
	 * @return A Texture object that displays the image file with the given file name.
	 */
	public static Texture load(String imageFileName)
    {
		Texture tex = new Texture();
        // String fileName = new File(imageFileName).toURI().toString();
		
		try 
		{
            tex.image = ImageIO.read(new File(imageFileName));
			tex.region  = new Rectangle();
        	tex.region.setValues( 0, 0, tex.image.getWidth(), tex.image.getHeight() );
        } 
		catch (IOException e) 
		{
			System.err.println("error reading file");
            e.printStackTrace();
        }

		return tex;

		/*
        
        
        
        
		*/
    }
}

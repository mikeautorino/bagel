package bagel;

// import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.JPanel;

public class BasicSprite extends JPanel
{
    // core properties
	
	/**
	 * x-coordinate of center of sprite
	 */
	public double x;
	
	/**
	 * y-coordinate of center of sprite
	 */
	public double y;
	
	/**
	 * image displayed when rendering this sprite
	 */
	Texture texture;
	
	/**
	 * width of sprite
	 */
	public double width;
	
	/**
	 * height of sprite
	 */
	public double height;
	
	/**
	 * determines if sprite will be visible
	 */
	public boolean visible;

	/**
	 * shape used for collision
	 */
	Rectangle boundary;

    /**
	 * initialize default values of sprite properties
	 */
	public BasicSprite()
	{  
		this.x = 0;
		this.y = 0;
		this.visible  = true;

		// collision
		this.boundary = new Rectangle();

		
	}

	// basic methods

	/**
	 * Set the coordinates of the center of this sprite.
	 * @param x x-coordinate of center of sprite
	 * @param y y-coordinate of center of sprite
	 */
	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Move this sprite by the specified amounts.
	 * @param deltaX amount to move sprite along x direction
	 * @param deltaY amount to move sprite along y direction
	 */
	public void moveBy(double deltaX, double deltaY)
	{
		this.x += deltaX;
		this.y += deltaY;
	}   

	/**
	 * Set the texture to be displayed when rendering this sprite.
	 * Also sets width and height of this sprite.
	 * @param tex texture to use for this sprite
	 */
	public void setTexture(Texture tex)
	{
		this.texture = tex;
		this.width   = tex.region.width;
		this.height  = tex.region.height;
	}

	/**
	 * Set size to use when for boundary and when drawing this sprite
	 * @param width width of sprite
	 * @param height height of sprite
	 */
	public void setSize(double width, double height)
	{
		this.width = width;
		this.height = height;
	}

	protected void paintComponent(Graphics g) 
	{
        super.paintComponent(g);
        if (this.texture.image != null)
            g.drawImage(this.texture.image, (int)this.x, (int)this.y, this);
    }
	
}

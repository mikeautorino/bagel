package bagel;

// import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class BasicSprite
{
    // core properties
	
	/**
	 * position of top left corner? center? of sprite
	 */
	public Vector position;
	
	/**
	 * image displayed when rendering this sprite
	 */
	public Texture texture;
	
	/**
	 * width of sprite ???
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
	public Rectangle boundary;

	// angle of sprite rotation
	public double angle;
	
    /**
	 * initialize default values of sprite properties
	 */
	public BasicSprite()
	{  
		this.position = new Vector();
		this.boundary = new Rectangle();
		this.visible  = true;	
		this.angle = 0;	
	}

	// basic methods

	/**
	 * Set the coordinates of the center of this sprite.
	 * @param x x-coordinate of center of sprite
	 * @param y y-coordinate of center of sprite
	 */
	public void setPosition(double x, double y)
	{
		this.position.setValues(x, y);
	}

	/**
	 * Move this sprite by the specified amounts.
	 * @param deltaX amount to move sprite along x direction
	 * @param deltaY amount to move sprite along y direction
	 */
	public void moveBy(double deltaX, double deltaY)
	{
		this.position.addValues(deltaX, deltaY);
	}   

	/**
	 * Set the texture to be displayed when rendering this sprite.
	 * Also sets width and height of this sprite.
	 * @param tex texture to use for this sprite
	 */
	public void setTexture(Texture tex)
	{
		this.texture = tex;
		// ?? set boundary instead ??
		this.width   = tex.region.width;
		this.height  = tex.region.height;
	}

	// angle methods

	// setAngle? use: toRadians?

	/**
	 * Rotate sprite by the specified angle.
	 * @param deltaAngle the angle (in degrees) to rotate this sprite
	 */
	public void rotateBy(double deltaAngle)
	{
		this.angle += deltaAngle;
	}

	/** 
	 * Move sprite by the specified distance at the specified angle.
	 * @param distance the distance to move this sprite
	 * @param angleDegrees the angle (in degrees) along which to move this sprite
	 */
	public void moveAtAngle(double distance, double angleDegrees)
	{
		this.position.addValues( distance * Math.cos(angleDegrees * Math.PI/180),
		                         distance * Math.sin(angleDegrees * Math.PI/180)  );
	}

	/**
	 * Move sprite forward by the specified distance at current angle.
	 * @param distance the distance to move this sprite
	 */
	public void moveForward(double distance)
	{
		this.moveAtAngle(distance, this.angle);
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

	public void draw(Graphics g) 
	{
		if (!this.visible)
			return;

		// basic version
        // g.drawImage(this.texture.image, (int)this.position.x, (int)this.position.y, null);

		Graphics2D g2 = (Graphics2D) g.create();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        // g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        double cx = this.position.x + this.texture.image.getWidth() / 2;
        double cy = this.position.y + this.texture.image.getHeight() / 2;

        g2.rotate(Math.toRadians(angle), cx, cy);
        g2.drawImage(this.texture.image, (int)this.position.x, (int)this.position.y, null);
        g2.dispose();
    }
	
	
}

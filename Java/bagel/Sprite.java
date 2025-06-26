package bagel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;


public class Sprite
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
	 * shape used for collision
	 */
	public Rectangle boundary;

	/**
	 * width of boundary rectangle and rendered image
	 */
	public double width;
	
	/**
	 * height of boundary rectangle and rendered image
	 */
	public double height;
	
	/**
	 * determines if sprite will be visible
	 */
	public boolean visible;

	

	// angle (in degrees) of texture rotation
	public double angle;
	
    /**
	 * initialize default values of sprite properties
	 */
	public Sprite()
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
	 * Also sets default width and height of this sprite.
	 * @param tex texture to use for this sprite
	 */
	public void setTexture(Texture tex)
	{
		this.texture = tex;
		this.width   = tex.region.width;
		this.height  = tex.region.height;
	}

	/**
	 * Set size to use for both collision re and image drawing
	 * @param width width of sprite
	 * @param height height of sprite
	 */
	public void setSize(double width, double height)
	{
		this.width = width;
		this.height = height;
	}

	// collision methods

	/**
	 * Get boundary shape for this sprite, adjusted according to current position.
	 * Angle of rotation has no effect on the boundary.
	 * @return boundary shape for this sprite
	 */
	Rectangle getBoundary()
	{
		this.boundary.setValues(
			this.position.x - this.width/2, this.position.y - this.height/2, this.width, this.height);
		return this.boundary;
	}

	/**
	 * Check if this sprite is overlapping another sprite.
	 * @param other sprite to check for overlap with
	 * @return true if this sprite overlaps other sprite
	 */
	public boolean isOverlapping(Sprite other)
	{
		return this.getBoundary().isOverlapping( other.getBoundary() );
	}

	/**
	 * Prevent this sprite from overlapping another sprite.
	 * @param other sprite to prevent overlap with
	 */
	public void preventOverlap(Sprite other)
	{
		if ( this.isOverlapping(other) )
		{
			Vector mtv = this.getBoundary().getMinTranslationVector( other.getBoundary() );
			this.moveBy(mtv.x, mtv.y);
		}
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

	public void draw(Graphics g) 
	{
		if (!this.visible)
			return;

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.rotate(Math.toRadians(angle), position.x, position.y);
		g2d.translate(-width/2, -height/2);
		// determine scaling factors
		double scaleX = width / texture.region.width;
		double scaleY = height / texture.region.height;
		g2d.scale(scaleX, scaleY);
        g2d.drawImage(texture.image, (int)(position.x / scaleX), (int)(position.y / scaleY), null);
        g2d.dispose();
    }
}

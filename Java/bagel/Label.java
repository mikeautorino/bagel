package bagel;
import java.io.File;

//import javafx.scene.canvas.GraphicsContext;
import java.awt.Font;
import java.awt.Color;
//import javafx.scene.text.TextAlignment;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
/**
 * A structure to store and display text.
 * Most properties are public and should be set directly.
 */
public class Label
{
	/**
	 * Name of the current font. Set in {@link #loadFontFromSystem(String, int)}
	 * or set by file in {@link #loadFontFromFile(String, int)}.
	 */
	String fontName;
	
	/**
	 * Size of the current font. Set in {@link #loadFontFromSystem(String, int)}
	 * or {@link #loadFontFromFile(String, int)}.
	 */
	int fontSize;
	
	/**
	 * Automatically set by load methods.
	 */
	Font font;
	
	/**
	 * color used to draw font
	 */
	public Color fontColor;

	/**
	 * text to display in label
	 */
	public String text;
	
	/**
	 * x-coordinate of anchor of label; see {@link #setPosition(double, double)}
	 */
	public double x;
	
	/**
	 * y-coordinate of anchor of label; see {@link #setPosition(double, double)}
	 */
	public double y;
	
	/**
	 * text alignment ("LEFT", "CENTER", "RIGHT") with respect to anchor point (x,y)
	 */
	public String alignment;
	
	/**
	 * determines if font border will be drawn
	 */
	public boolean borderDraw;
	
	/**
	 * size of font border 
	 */
	public int borderSize;
	
	/**
	 * color used to draw font border
	 */
	public Color borderColor;
	
	/**
	 * determines if label will be visible
	 */
	public boolean visible;
    
	/**
	 * Initialize label to default settings.
     * Default font is "Arial" with "PLAIN" style and size 16.
	 */
    public Label()
    {
        this.fontName = "Arial";
        this.fontSize = 16;
        this.font = new Font( this.fontName, Font.PLAIN, this.fontSize );
        this.fontColor = Color.BLACK;
        this.text = " ";
        this.x = 0;
        this.y = 0;
        this.alignment = "LEFT";
        this.borderDraw = false;
        this.borderSize = 1;
        this.borderColor = Color.BLACK;
        this.visible = true;
    }

    /**
     * Configure this label to use a font already installed on this system.
     * @param fontName name of font (e.g. "Arial", "Times New Roman", "Courier New"); must be installed on system
     * @param fontSize size of font
     * @param style style of font (e.g. Font.PLAIN, Font.BOLD, Font.ITALIC, Font.BOLD + Font.ITALIC)
     */
    public void loadFontFromSystem(String fontName, int fontStyle, int fontSize)
    {
        this.font = new Font( fontName, fontStyle, fontSize );
        this.fontName = fontName;
        this.fontSize = fontSize;
    }

    /**
     * Configure this label to use a font from a specified file.
     * @param fontFileName name of font file
     * @param fontFileStyle style of font 
     * @param fontFileSize size of font
     * 
     */
    public void loadFontFromFile(String fontFileName, int fontFileStyle, int fontFileSize)
    {
        Font customFont = null;
        try {
            // Load the font from the specified file
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontFileName));
        } catch (Exception e) {
            System.err.println("Error loading font from file: " + fontFileName);
            e.printStackTrace();
        }

        this.font = new Font( customFont.getName(), fontFileStyle, fontFileSize );
        this.fontName = this.font.getName();
        this.fontSize = fontFileSize;
    }
    
    /**
     * Set the coordinates of the anchor position of this label;
     *  this may be to the left, center, or right of the text
     *  according to the value of {@link #alignment}.  
     * @param x x-coordinate of anchor of label
     * @param y y-coordinate of anchor of label
     */
    public void setPosition(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Render this text to a canvas used specified parameters. 
     */
  
    public void draw(Graphics g)
    {
        if ( !this.visible )
            return;
            
        g.setFont( this.font );
        g.setColor( this.fontColor );

        int drawX = (int) this.x;
        int drawY = (int) this.y;
        FontMetrics metrics = g.getFontMetrics(this.font);
        int textWidth = metrics.stringWidth(this.text);

        if (this.alignment.equals("CENTER")) {
            drawX -= textWidth / 2;
        } else if (this.alignment.equals("RIGHT")) {
            drawX -= textWidth;
        }
        // drawY is already the baseline
        if (this.borderDraw)
        {
            if (g instanceof Graphics2D) {
                Graphics2D g2 = (Graphics2D) g;
                java.awt.Stroke oldStroke = g2.getStroke();
                java.awt.Color oldColor = g2.getColor();
                g2.setColor(this.borderColor);
                g2.setStroke(new java.awt.BasicStroke(this.borderSize));
                // Draw border by drawing text multiple times offset by 1 pixel in 8 directions
                for (int dx = -this.borderSize; dx <= this.borderSize; dx++) {
                    for (int dy = -this.borderSize; dy <= this.borderSize; dy++) {
                        if (dx != 0 || dy != 0) {
                            g2.drawString(this.text, drawX + dx, drawY + dy);
                        }
                    }
                }
                g2.setStroke(oldStroke);
                g2.setColor(oldColor);
            }
        }
            // g.setLineWidth(this.borderSize);
            // g.strokeText( this.text, this.x, this.y );
        }   
    }


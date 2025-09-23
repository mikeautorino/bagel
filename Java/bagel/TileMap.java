package bagel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Collections;

/**
 *   A selection and arrangement of {@link Tile}s 
 *   that corresponds to an image of the game world environment
 *   and data that corresponds to placement of game world entities.
 *   TileMaps may be used as a background image or as a collection
 *   of solid obstacles (using the {@link #preventSpriteOverlap(FinalSprite)} method).
 */
public class TileMap
{
	/**
	 *  number of rows of tiles in this TileMap
	 */
	public int mapRows;

	/**
	 *  number of columns of tiles in this TileMap
	 */
	public int mapCols; 

	/**
	 * width of each tile (in pixels)
	 */
	public int tileWidth;

	/**
	 * height of each tile (in pixels)
	 */
	public int tileHeight;

	/**
	 * a two-dimensional array storing all the text characters
	 * used to specify this TileMap
	 */
	public String[][]      mapDataGrid;

	/**
	 * a two-dimensional array storing only the {@link Tile} objects
	 * specified by this TileMap. used primarily to determine whether
	 * a tile is adjacent to other tiles, and thereby which tile edges 
	 * should be ignored when calculating collision resolution.
	 */
	public Tile[][]        mapTileGrid;

	/**
	 * a list containing all the {@link Tile} objects specified by this TileMap.
	 */
	public ArrayList<Tile> mapTileList;

	/**
	 * a list containing all the available Tile textures
	 * loaded by the {@link #loadTilesetImage(String)} method
	 */
	public ArrayList<Texture> tileTextureList;

	/**
	 * Specify basic data for this TileMap;
	 * additional data loaded by {@link #loadTilesetImage(String)}
	 * and {@link #loadMapData(String[], String[], int[])} methods.
	 * 
	 * @param mapRows number of rows of tiles in this TileMap
	 * @param mapCols number of columns of tiles in this TileMap
	 * @param tileWidth width of each tile (in pixels)
	 * @param tileHeight height of each tile (in pixels)
	 */
	public TileMap(int mapRows, int mapCols, int tileWidth, int tileHeight)
	{   
		this.mapRows = mapRows;
		this.mapCols = mapCols;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	/**
	 * Load a <i>tileset</i> - 
	 * an image consisting of smaller rectangular images
	 * that represent possible features of the game world environment. 
	 * @param imageFileName file name of the image containing tile images
	 */
	public void loadTilesetImage(String imageFileName)
	{
		this.tileTextureList = new ArrayList<Texture>();
		BufferedImage tileSetImage = null;
		try {
			tileSetImage = ImageIO.read(new File(imageFileName));
		} catch (java.io.IOException e) {
			e.printStackTrace();
			return;
		}
		int tileImageRows  = (int)tileSetImage.getWidth()  / this.tileWidth;
		int tileImageCols  = (int)tileSetImage.getHeight() / this.tileHeight;
		for (int y = 0; y < tileImageRows; y++)
		{
			for (int x = 0; x < tileImageCols; x++)
			{
				Texture texture = new Texture(imageFileName);
				texture.image = tileSetImage;
				texture.region = new Rectangle(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
				this.tileTextureList.add( texture );
			}
		}
	}

	/**
	 * Load text data that indicates placement of Tiles and game world entities.
	 * Map data is stored as an array of Strings, one String per row of the map.
	 * Text characters indicating tiles are listed in <code>mapTileSymbolArray</code>,
	 * and the corresponding tileset index values are listed in <code>tileTextureIndexArray</code>.
	 * Entity placement data can be retrieved later from the method 
	 * {@link #getSymbolPositionList(String)}.
	 * <br><br>
	 * For example, consider a map where "W" represents a wall tile
	 * whose texture has index 3 in the tileset,
	 * and "D" represents a door tile 
	 * whose texture has index 7 in the tileset. 
	 * In addition,
	 * "P" represents the starting location of the player sprite, 
	 * and each "E" represents the location of an enemy sprite.
	 * The parameters for this method could be specified as follows:
	 * <pre>{@code
	 * mapData = {"WWWWDWWWW",
	 *            "W.......W",
	 *            "W.E...E.W",
	 *            "W.......W",
	 *            "W...P...W",
	 *            "WWWWWWWWW"};
	 *            
	 * mapTileSymbolArray = {"W", "D"};
	 * tileTextureIndexArray = {3, 7};
	 * }</pre> 
	 * 
	 * This method also determines if each Tile is adjacent to other tiles, 
	 * and sets tile edge data for use in collision resolution:
	 * edges that occur between two adjacent tiles ("interior edges") are ignored.
	 * 
	 * @param mapData an array of Strings, one String per row of the map, 
	 *     each character representing a Tile or other game world entity
	 * @param mapTileSymbolArray text characters indicating Tile objects
	 * @param tileTextureIndexArray index for Tile textures corresponding to characters in <code>mapTileSymbolArray</code>
	 */
	public void loadMapData(String[] mapData, String[] mapTileSymbolArray, int[] tileTextureIndexArray)
	{
		this.mapDataGrid = new String[this.mapRows][this.mapCols];
		this.mapTileGrid = new Tile[this.mapRows][this.mapCols];
		this.mapTileList = new ArrayList<Tile>();

		List<String> mapTileSymbolList = Arrays.asList(mapTileSymbolArray);

		for (int r = 0; r < this.mapRows; r++)
		{
			for (int c = 0; c < this.mapCols; c++)
			{
				// add all data to mapDataGrid
				String data = mapData[r].substring(c, c+1);
				this.mapDataGrid[r][c] = data;
				
				// add Tile-specific data to mapTileGrid and list
				if ( mapTileSymbolList.contains(data) )
				{
					int i = mapTileSymbolList.indexOf(data);
					int tileTextureIndex = tileTextureIndexArray[i];

					double x = (c + 0.5) * this.tileWidth;
					double y = (r + 0.5) * this.tileHeight;

					Tile tile = new Tile(x, y, this.tileWidth, this.tileHeight, tileTextureIndex);
					this.mapTileGrid[r][c] = tile;
					this.mapTileList.add( tile );
				}
			}
		}

		// after all map data is loaded, use adjacency information to set Tile edge fields
		for (int r = 0; r < this.mapRows; r++)
		{
			for (int c = 0; c < this.mapCols; c++)
			{
				Tile tile = this.mapTileGrid[r][c];
				if ( tile != null )
				{
					Rectangle rect = tile.boundary;
					if ( this.getTileAt(r,c-1) == null )
					{
						tile.edgeLeft = new Rectangle();
						tile.edgeLeft.setValues(rect.left, rect.top, 0, this.tileHeight);
					}
					if ( this.getTileAt(r,c+1) == null )
					{
						tile.edgeRight = new Rectangle();
						tile.edgeRight.setValues(rect.left + this.tileWidth, rect.top, 0, this.tileHeight);
					}
					if ( this.getTileAt(r-1,c) == null )
					{
						tile.edgeTop = new Rectangle();
						tile.edgeTop.setValues(rect.left, rect.top, this.tileWidth, 0);
					}
					if ( this.getTileAt(r+1,c) == null )
					{
						tile.edgeBottom = new Rectangle();
						tile.edgeBottom.setValues(rect.left, rect.top + this.tileHeight, this.tileWidth, 0);
					}
				}
			}
		}
	}

	/**
	 * Get the tile at the corresponding map position (if one exists).
	 * @param mapRow map row index
	 * @param mapCol map column index
	 * @return tile at the given position, if one exists; <code>null</code> otherwise
	 */
	public Tile getTileAt(int mapRow, int mapCol)
	{
		if (mapRow < 0 || mapRow >= this.mapRows || mapCol < 0 || mapCol >= this.mapCols)
			return null;
		else
			return this.mapTileGrid[mapRow][mapCol]; 
	}

	/**
	 * Return the game world coordinates (in pixels) of every occurrence
	 * of <code>symbol</code> in map data.
	 * @param symbol text character to locate in map data
	 * @return list of positions where <code>symbol</code> occurs in map data
	 */
	public ArrayList<Vector> getSymbolPositionList(String symbol)
	{
		ArrayList<Vector> positionList = new ArrayList<Vector>();

		for (int r = 0; r < this.mapRows; r++)
		{
			for (int c = 0; c < this.mapCols; c++)
			{
				if ( mapDataGrid[r][c].equals(symbol) )
				{
					double x = (c + 0.5) * this.tileWidth;
					double y = (r + 0.5) * this.tileHeight;
					positionList.add( new Vector(x,y) );
				}
			}
		}

		return positionList;
	}

	public void draw(Graphics context)
	{
		for (Tile tile : this.mapTileList )
		{
			Graphics2D g2d = (Graphics2D) context;
			g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setTransform(new AffineTransform(1, 0, 0, 1, tile.x, tile.y));
			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));

			Texture tex = this.tileTextureList.get(tile.tileTextureIndex);
			g2d.drawImage(tex.image, (int)(tile.x - tile.width/2), (int)(tile.y - tile.height/2), null);
			g2d.dispose();
		}
	}

	/**
	 * Check if a sprite isOverlapping any Tile in this TileMap.
	 * @param sprite the sprite to check for overlap
	 * @return true if sprite isOverlapping any Tile in this TileMap
	 */
	public boolean checkSpriteOverlap(FinalSprite sprite)
	{
		Rectangle spriteBoundary = sprite.getBoundary();
		for (Tile tile : mapTileList)
		{
			if ( spriteBoundary.isOverlapping( tile.boundary ) )
				return true;
		}
		return false;
	}

	/**
	 * Prevent sprite from overlapping with any Tile in this TileMap.
	 * Tile edges are used when calculating collision resolution 
	 * to avoid "corner snag" or "internal edge" issues that interfere with Sprite movement;
	 * edges that occur between two adjacent tiles are ignored.
	 * @param sprite the sprite to prevent from overlapping with tiles
	 */
	public void preventSpriteOverlap(FinalSprite sprite)
	{
		Rectangle spriteBoundary = sprite.getBoundary();
		for (Tile tile : mapTileList)
		{
			if ( spriteBoundary.isOverlapping( tile.boundary ) )
			{
				ArrayList<Vector> differences = new ArrayList<Vector>();

				if ( tile.edgeLeft != null && spriteBoundary.isOverlapping(tile.edgeLeft) )
					differences.add( new Vector(tile.boundary.left - spriteBoundary.right, 0) ); // to the left
				if ( tile.edgeRight != null && spriteBoundary.isOverlapping(tile.edgeRight) )
					differences.add( new Vector(tile.boundary.right - spriteBoundary.left, 0) ); // how to displace this sprite to the right
				if ( tile.edgeTop != null && spriteBoundary.isOverlapping(tile.edgeTop) )
					differences.add( new Vector(0, tile.boundary.top - spriteBoundary.bottom) ); // to the bottom
				if ( tile.edgeBottom != null && spriteBoundary.isOverlapping(tile.edgeBottom) )
					differences.add( new Vector(0, tile.boundary.bottom - spriteBoundary.top) ); // to the top

				if ( differences.size() > 0 )
				{
					// sortable since Vector implements Comparable interface
					Collections.sort(differences);

					// get minimum (length) vector to translate by
					Vector mtv = differences.get(0);
					sprite.moveBy(mtv.x, mtv.y);

					// if sprite is using physics, come to a stop in appropriate direction
					if (sprite.physics != null)
					{
						if ( Math.abs(mtv.x) > 0 )
						{
							sprite.physics.velocityVector.x = 0;
							sprite.physics.accelerationVector.x = 0;
						}
						if ( Math.abs(mtv.y) > 0 )
						{
							sprite.physics.velocityVector.y = 0;
							sprite.physics.accelerationVector.y = 0;
						}
					}
				}
			}
		}
	}
}
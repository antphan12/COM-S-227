package hw3;

import api.Tile;

/**
 * Represents the game's grid.
 * @author antmanphan
 */
public class Grid {
	
	/**
	 * @param width  number of columns
	 * @param height number of rows 
	 */
	
	private int mainWidth;
	private int mainHeight;
	private Tile[][] tiles;
	
	public Grid(int width, int height) {
		this.mainWidth = width;
		this.mainHeight = height;
		this.tiles = new Tile[height][width];
	}

	/**
	 * Get the grid's width.
	 * 
	 * @return width
	 */
	public int getWidth() {
		return mainWidth;
	}

	/**
	 * Get the grid's height.
	 * 
	 * @return height
	 */
	public int getHeight() {
		return mainHeight;
	}
	/**
	 * Gets the tile for the given column and row.
	 * 
	 * @param x the column
	 * @param y the row
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return tiles[y][x];
	}

	/**
	 * Sets the tile for the given column and row. Calls tile.setLocation().
	 * 
	 * @param tile the tile to set
	 * @param x    the column
	 * @param y    the row
	 */
	public void setTile(Tile tile, int x, int y) {
		tiles[y][x] = tile;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int y = 0; y < getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			str += "[";
			for (int x = 0; x < getWidth(); x++) {
				if (x > 0) {
					str += ",";
				}
				str += getTile(x, y);
			}
			str += "]";
		}
		return str;
	}

	
}

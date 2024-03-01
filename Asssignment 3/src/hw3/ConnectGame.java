package hw3;

import java.util.Random;
import java.util.ArrayList;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a game.
 * @author antmanphan
 * @version 1.0
 */
public class ConnectGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	private int mainWidth;
	private int mainHeight;
	private int mainMin; //the minimum level of tiles that is held
	private int mainMax; //the maximum level of tiles that is held
	private int index;
	private Random rand;
	private Grid mainGrid = null; //creates the main grid for the game
	private boolean firstChosen = false;
	private boolean isVert;
	private boolean isHorizontal;
	private boolean isDiag;
	private boolean beginTwo;
	private long totalScore;
	private static Tile[] selectedArray;
	private ArrayList<Tile> arry;
	
	
	
	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * 
	 * @param width  grid width
	 * @param height grid height
	 * @param min    minimum tile level
	 * @param max    maximum tile level
	 * @param rand   random number generator
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		mainGrid = new Grid(width, height);
		mainWidth = width;
		mainHeight = height;
		mainMin = min;
		mainMax = max;
		this.rand = rand;
		isVert = false;
		isHorizontal = false;
		isDiag = false;
		beginTwo = false;
		index = 0;
		arry = new ArrayList<>();
		
	}

	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * 
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {
		Tile random = new Tile(rand.nextInt(mainMax - mainMin) + mainMin);
		return random;
	}

	/**
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
		for(int i = 0; i < mainWidth; i++) {
			for(int j = 0; j < mainHeight; j++) {
				mainGrid.setTile(getRandomTile(), i, j);
			}
		}
	}

	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1 one of the two tiles
	 * @param t2 one of the two tiles
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) { 
	    int x1 = t1.getX();
	    int y1 = t1.getY();
	    int x2 = t2.getX();
	    int y2 = t2.getY();

	    
	    if(Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1) { //checks to see if the tiles are in x and y postions
	        return true;
	    }
	    
	    if(Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 1) { //checks to see if the tiles are diagonal
	        return true;
	    }
	    return false;
	}

	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		if(!firstChosen) {
			firstChosen = true;
			mainGrid.getTile(x, y).setSelect(firstChosen);
			arry.add(mainGrid.getTile(x, y));
			index++;
			return firstChosen;
		}
		return false;
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		if(!beginTwo && isAdjacent(arry.get(index - 1), mainGrid.getTile(x, y))) {
			if(arry.get(index - 1).getLevel() == mainGrid.getTile(x, y).getLevel()){
				mainGrid.getTile(x, y).setSelect(true);
				arry.add(mainGrid.getTile(x, y));
				beginTwo = true;
				index++;
			}
		} else if(beginTwo && arry.get(index - 2) == mainGrid.getTile(x, y)) {
			arry.get(index -1).setSelect(false);
			arry.remove(index - 1);
			index--;
		} else if(beginTwo && isAdjacent(arry.get(index - 1), mainGrid.getTile(x, y))) {
			if(arry.get(index - 1).getLevel() == mainGrid.getTile(x, y).getLevel() || arry.get(index - 1).getLevel() + 1 == mainGrid.getTile(x, y).getLevel()) {
				mainGrid .getTile(x, y).setSelect(true);
				arry.add(mainGrid.getTile(x, y));
				index++;
			}
		}
	}

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with dropSelected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * 
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
		
		if (arry.size() == 1 && firstChosen) {
			if (arry.get(0) == mainGrid.getTile(x, y)) {
				totalScore = 0;
				arry.remove(0);
				firstChosen = false;
				mainGrid.getTile(x, y).setSelect(false);
				return true;
			} else {
				return false;
			}
		} else if (arry.size() > 1 && firstChosen) {
			if (arry.get(arry.size() - 1) == mainGrid.getTile(x, y)) {
				for (int i = 0; i < arry.size(); i++) {
					totalScore += arry.get(i).getValue();
				}
				upgradeLastSelectedTile();
				dropSelected();
				firstChosen = false;
				for (int i = 0; i < arry.size(); i++) {
					arry.get(i).setSelect(false);
					arry.remove(i);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		
		int newVal = arry.get(arry.size() - 1).getValue() * 2;
		if (newVal <= Math.pow(2, mainMax)){
			arry.get(arry.size() - 1).setLevel(arry.get(arry.size() - 1).getLevel() + 1);
			arry.get(arry.size() - 1).setSelect(false);
		} else if (newVal > Math.pow(2, mainMax)) {
			arry.get(arry.size() - 1).setLevel(mainMax);
			arry.get(arry.size() - 1).setSelect(false);
			mainMax++;
			mainMin++;
			dialogListener.showDialog("New block " + Math.pow(2, mainMax) + ", removing blocks " + Math.pow(2, mainMin - 1));
		}
	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as a array.
	 * 
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		ArrayList<Tile> listHolder = new ArrayList<>();
		
		for (int i = 0; i < mainWidth; i++ ) {
			for (int j = 0; j < mainHeight; j++) {
				if (mainGrid.getTile(i, j).isSelected()) {
					listHolder.add(mainGrid.getTile(i, j));
				}
			}
		}
		selectedArray = new Tile[listHolder.size()];
		for (int i = 0; i < listHolder.size(); i++) {
			selectedArray[i] = listHolder.get(i);
		}
		return selectedArray;
	}

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * 
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
		
		//gameGrid.setTile(gameGrid.getTile(1, 0), 1, 1);
		
		for (int i = mainGrid.getWidth() - 1; i >= 0; i--) {
			for (int j = mainGrid.getHeight() - 1; j >= 0; j--) {
				if (mainGrid.getTile(i, j).getLevel() == level){
					for (int x = 0; x < j; x++) {
						mainGrid.setTile(mainGrid.getTile(i, x), i, x + 1);
					}
					mainGrid.setTile(getRandomTile(), i, 0);
				}
			}
		}
	}

	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 */
	public void dropSelected() {
		
		for (int i = mainGrid.getWidth() - 1; i >= 0; i--) {
			for (int j = mainGrid.getHeight() - 1; j >= 0; j--) {
				if (mainGrid.getTile(i, j).isSelected()){
					for (int x = 0; x < j; x++) {
						mainGrid.setTile(mainGrid.getTile(i, x), i, x + 1);
					}
					mainGrid.setTile(getRandomTile(), i, 0);
				}
			}
		}
	}
	/**
	 * Remove the tile from the selected tiles.
	 * 
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) {
		mainGrid.getTile(x, y).setSelect(false);	
	}

	/**
	 * Gets the player's score.
	 * 
	 * @return the score
	 */
	public long getScore() {
		return totalScore;
	}

	/**
	 * Gets the game grid.
	 * 
	 * @return the grid
	 */
	public Grid getGrid() {
		return mainGrid;
	}

	/**
	 * Gets the minimum tile level.
	 * 
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		return mainMin;
	}

	/**
	 * Gets the maximum tile level.
	 * 
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		return mainMax;
	}

	/**
	 * Sets the player's score.
	 * 
	 * @param score number of points
	 */
	public void setScore(long score) {
		totalScore = score;
	}

	/**
	 * Sets the game's grid.
	 * 
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		this.mainGrid = grid;
	}

	/**
	 * Sets the minimum tile level.
	 * 
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		mainMin = minTileLevel;
	}

	/**
	 * Sets the maximum tile level.
	 * 
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		mainMax = maxTileLevel;
	}

	/**
	 * Sets callback listeners for game events.
	 * 
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * 
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * 
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}
}

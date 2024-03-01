package hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import api.Tile;

/**
 * Utility class with static methods for saving and loading game files.
 * @author antmanphan
 */
public class GameFileUtil {
	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * 
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 */
	public static void save(String filePath, ConnectGame game) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			int mainWidth = game.getGrid().getWidth();
			int mainHeight = game.getGrid().getHeight();
			Grid mainGrid = game.getGrid();
			int mainMin = game.getMinTileLevel();
			int mainMax = game.getMaxTileLevel();
			long totalScore = game.getScore();
			
			writer.write(game.getGrid().getWidth() + " " + game.getGrid().getHeight() + " " + game.getMinTileLevel() + " " + game.getMaxTileLevel() + " " + game.getScore() + "\n");
			
			for (int i = 0; i < game.getGrid().getHeight(); i++) {
				if (i > 0) {
					writer.write("\n");
				}
				for (int j = 0; j < game.getGrid().getWidth(); j++) {
					if (j > 0) {
						writer.write(" ");
					}
					writer.write("" + game.getGrid().getTile(j, i).getLevel());
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * <p>
	 * See the save() method for the specification of the file format.
	 * 
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, ConnectGame game) {
		File f = new File(filePath);
		Scanner scanner;
		int width;
		int height;
		int min;
		int max;
		long score;
		try {
			scanner = new Scanner(f);
			while (scanner.hasNext()) {
				scanner = new Scanner(f);
				width = scanner.nextInt();
				height = scanner.nextInt();
				min = scanner.nextInt();
				max = scanner.nextInt();
				score = scanner.nextLong();
				Grid grid = new Grid(width, height);
				game.setGrid(grid);
				game.setMinTileLevel(min);
				game.setMaxTileLevel(max);
				game.setScore(score);
				int y = 0;
				while (scanner.hasNextLine()) {
					int x = 0;
					while (scanner.hasNextInt() && x < width) {
						int level = scanner.nextInt();
						Tile tile = new Tile(level);
						grid.setTile(tile, x, y);
						x++;
					}
					y++;
				}
			}			
			scanner.close();			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

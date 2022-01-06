import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The runner class for the "Game of Life" that asks a user for a test file and plays the game
 * @author Robert "Drew" Weimer
 * @date 1/24/2020
 * @class CS 111
 * @section 010
 */
public class Main
{
	public static void main(String... args)
	{
		Scanner scan = new Scanner(System.in);

		//Grab the test file
		System.out.println("Name of test file: ");

		//Try and use the file given, if it doesn't work print the error and quit the program
		try
		{
			//Create the path to the file
			Path path = Paths.get("test/" + scan.nextLine());
			//Make a scanner out of the file
			Scanner file = new Scanner(path);

			//Grab the # of rows and # of columns
			int r = file.nextInt();
			int c = file.nextInt();
			file.nextLine(); //Empty nextLine to virtually hit the "enter" key so the next line is read.

			//Create a new game of life
			Life game = new Life(r, c);

			//Fill the board and start playing
			game.fillBoard(file);
			game.playGame();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}

}

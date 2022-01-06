import java.util.Scanner;

/**
 * A simple game composes of cells that either are born or die depending on how many neighbors they have
 * @author Robert "Drew" Weimer
 * @date 1/24/2020
 * @class CS 111
 * @section 010
 */
public class Life
{
	/**
	 * This 2d array represents the game board. Each cell has a state from 0-3 representing Occupied Empty, Born, Dying
	 */
	private int[][] board;
	/**
	 * The number of rows the game board occupied
	 */
	private int numRows;
	/**
	 * The number of columns the game board consists of
	 */
	private int numCols;

	/**
	 * This value represents a cel that is being born this generation
	 */
	private int beingBorn;
	/**
	 * This value represents a cell that is dying this generation
	 */
	private int dying;
	/**
	 * This value represents a cell that is empty this generation
	 */
	private int free;
	/**
	 * This value represents a cell that is occupied this generation
	 */
	private int occupied;

	/*
	 * Cell rules
	 * BORN if EXACTLY 3 occupied neighboring cells
	 * DIE if one of fewer neighbors
	 * DIE if 4 or more neighbors
	 * Cells being born have NO EFFECT on other cells
	 */

	/**
	 * Default constructor which initialises a new board of size 10x10
	 */
	public Life()
	{
		this(10, 10);
	}

	/**
	 * Creates a new Life board of the given row and column size
	 * @param row Number of rows
	 * @param col Number of columns
	 */
	public Life(int row, int col)
	{
		numRows = row;
		numCols = col;

		init();
	}

	/**
	 * Sets the board size based on the numRows and numCols instance data
	 */
	private void setBoardSize()
	{
		board = new int[numRows][numCols];
	}

	/**
	 * Initialises the board and instantiates the cell values
	 */
	private void init()
	{
		setBoardSize();

		free = 0;
		dying = 1;
		beingBorn = 2;
		occupied = 3;
	}

	/**
	 * @param r row of cell
	 * @param c column of cell
	 * @return True if cell is free, false if not
	 */
	public boolean isFree(int r, int c)
	{
		return board[r][c] == free;
	}

	/**
	 * @param r row of cell
	 * @param c column of cell
	 * @return True if cell is occupied, false if not
	 */
	public boolean isOccupied(int r, int c)
	{
		return board[r][c] == occupied;
	}

	/**
	 * @param r row of cell
	 * @param c column of cell
	 * @return True if cell is dying, false if not
	 */
	public boolean isDying(int r, int c)
	{
		return board[r][c] == dying;
	}

	/**
	 * @param r row of cell
	 * @param c column of cell
	 * @return True if cell is being born, false if not
	 */
	public boolean isBorn(int r, int c)
	{
		return board[r][c] == beingBorn;
	}

	/**
	 * Counts the number of neighbors the specified cell has
	 * @param r The row of the specified cell
	 * @param c The column of the specified cell
	 * @return The number of neighbors the given cell has
	 */
	private int countNeighbors(int r, int c)
	{
		int numNeighbors = 0;

		//Determine which directions are possible
		boolean top = (r - 1) >= 0;
		boolean bottom = (r + 1) < numRows;
		boolean left = (c - 1) >= 0;
		boolean right = (c + 1) < numCols;

		//Middle
		if (left)
			if (isOccupied(r, c-1) || isDying(r, c-1))
				numNeighbors++;
		if (right)
			if (isOccupied(r, c+1) || isDying(r, c + 1))
				numNeighbors++;
		//Top
		if (top)
		{
			//Check if the Middle is occupied
			if (isOccupied(r - 1, c) || isDying(r - 1, c))
				numNeighbors++;

			//Check left then right
			if (left)
				if (isOccupied(r-1, c - 1) || isDying(r - 1, c - 1))
					numNeighbors++;
			if (right)
				if (isOccupied(r - 1, c + 1) || isDying(r - 1, c + 1))
					numNeighbors++;
		}
		//Bottom
		if (bottom)
		{
			//Check if middle is occupied
			if (isOccupied(r + 1, c) || isDying(r + 1, c))
				numNeighbors++;

			//Check left then right
			if (left)
				if (isOccupied(r + 1, c - 1) || isDying(r + 1, c - 1))
					numNeighbors++;
			if (right)
				if (isOccupied(r + 1, c + 1) || isDying(r + 1, c + 1))
					numNeighbors++;
		}

		return numNeighbors;
	}

	/**
	 * Sets the specified cell as Born
	 * @param r The row of the cell
	 * @param c The column of the cell
	 */
	public void setBorn(int r, int c)
	{
		board[r][c] = beingBorn;
	}

	/**
	 * Sets the specified cell as Occupied
	 * @param r The row of the cell
	 * @param c The column of the cell
	 */
	public void setOccupied(int r, int c)
	{
		board[r][c] = occupied;
	}

	/**
	 * Sets the specified cell as Dying
	 * @param r The row of the cell
	 * @param c The column of the cell
	 */
	public void setDying(int r, int c)
	{
		board[r][c] = dying;
	}

	/**
	 * Sets the specified cell as Free
	 * @param r The row of the cell
	 * @param c The column of the cell
	 */
	public void setFree(int r, int c)
	{
		board[r][c] = free;
	}

	/**
	 * Clears the board of all cells by setting them to free
	 */
	public void clearBoard()
	{
		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++)
				setFree(i, j);
	}

	/**
	 * Fills the board, setting cells at specified location within the file to occupied.
	 * @param inputFile The Scanner object pointing to the file to read
	 */
	public void fillBoard(Scanner inputFile)
	{
		//Run through the file till the end is hit
		while (inputFile.hasNextLine())
		{
			//Create a new scanner based on the current line
			Scanner scan = new Scanner(inputFile.nextLine());

			//Grab the row and column values
			int r = scan.nextInt();
			int c = scan.nextInt();

			//Set the cell as occupied
			this.setOccupied(r, c);
		}

		setCanvas();
		drawBoard();
	}

	/**
	 * Calculates the state of the cells, draws the new board, and calculates the next generation on repeat until nothing changes
	 */
	public void playGame()
	{
		//Determins if the board has changed state or not
		boolean changed = true;

		//Keep repeating until nothing changed
		while (changed)
		{
			changed = false;
			//Run through each cell
			for (int i = 0; i < numRows; i++)
				for (int j = 0; j < numCols; j++)
				{
					//Count the amount of neighbors the current cell has
					int neighbors = countNeighbors(i, j);

					//If the cell is empty AND has 3 or more neighbors, then we can make a new cell
					if (isFree(i, j) && neighbors >= 3)
					{
						setBorn(i, j);
						changed = true;
					}
					//If the cell is already occupied, determine if it dies
					else if (isOccupied(i, j))
					{
						//If the cell is overcrowded
						if (neighbors >= 4)
						{
							setDying(i, j);
							changed = true;
						}
						//If the cell is lonely
						else if (neighbors <= 1)
						{
							setDying(i, j);
							changed = true;
						}
					}

				}
			//Calculate the next generation
			nextGeneration();
			//Draw the new board
			drawBoard();

			//Wait 7 seconds before proceeding so the user can notice changes
			try
			{
				Thread.sleep(700);
			} catch (InterruptedException e)
			{
			}
		}
	}

	/**
	 * Set the cell values for the next generation based on if the cell if being born or dying
	 */
	private void nextGeneration()
	{
		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++)
			{
				//All cells being born get born and dying cells become free
				if (isBorn(i, j))
					setOccupied(i, j);
				else if (isDying(i, j))
					setFree(i, j);
			}
	}

	/**
	 * Sets the canvas size based on the number of rows and number of columns
	 */
	private void setCanvas()
	{
		StdDraw.setXscale(0.0, 10 * this.numRows);
		StdDraw.setYscale(0.0, 10 * this.numCols);
	}

	/**
	 * Draws the cells on the board based on if they're occupied or free
	 */
	private void drawBoard()
	{
		// This assumes "cell[0][0] if the array is the upper left corner"

		int x, y;

		for (int i = 0; i < this.numRows; i++)
			for (int j = 0; j < this.numCols; j++)
			{
				//use the isOccupied method to see if a cell is occupied
				if (this.isOccupied(i, j))
					StdDraw.setPenColor(StdDraw.BLUE);
				else
					StdDraw.setPenColor(StdDraw.WHITE);

				x = j * 10 + 5;
				y = (10 * (this.numRows - i)) - 5;

				StdDraw.filledSquare(x, y, 5);
			}//inner
	}// end drawBoard

}



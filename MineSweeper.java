public class MineSweeper {

	public static void main(String[] args) {

		final int HEIGHT = 10;
		final int WIDTH = 10;
		MineSweeperWindow window = new MineSweeperWindow(HEIGHT,WIDTH);

		int[][] board = makeBoard(HEIGHT,WIDTH);
		boolean[][] open = new boolean[HEIGHT][WIDTH];

		//To test only the makeBoard method, uncomment the following code:
		/*
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				open[i][j] = true;
			}
		}
		*/

		//To test the makeBoard and computeHints, recomment the code above and uncomment the following code:
		
		computeHints(board);
		/*
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				open[i][j] = true;
			}
		}
		*/

		//To run the full game after finishing clicked, recomment the code above and uncomment this code:
		
		boolean alive = true;
		while (alive) {
			window.printBoard(board, open);
			int[] move = window.getMove();
			alive = clicked(move[0], move[1], board, open);
		}
		

		window.printBoard(board,open);
	}

	public static int[][] makeBoard(int height, int width) {

		int[][] board = new int[height][width];
		final int BOMB = 9;
		int bomb_count = 0;
		int row = 0;
		int column = 0;

		//No more than 10 bombs
		while (bomb_count < height) {

			row = (int) (Math.random() * board.length);
			column = (int) (Math.random() * board[0].length);

			while (board[row][column] == BOMB) {
				row = (int) (Math.random() * board.length);
				column = (int) (Math.random() * board[0].length);
			}

			board[row][column] = BOMB;
			bomb_count++;

		}

	  return board;
	}

	public static void computeHints(int[][] board) {

		final int BOMB = 9;

		for (int i = 0; i < board.length; i++) {
	    for (int j = 0; j < board[0].length; j++) {
	    	if (board[i][j] != BOMB) {
	    		board[i][j] = countNearBombs(board, i, j);
	    	}
	    }
	   }
	}

	public static int countNearBombs(int[][] board, int row, int column) {

		final int BOMB = 9;
		int near_bombs = 0;

		for (int i = row - 1; i <= row + 1; i++) {
			for (int j = column - 1; j <= column + 1; j++) {
				if (insideTheBoard(board, i, j) && board[i][j] == BOMB) {
					near_bombs++;
				}
			}
		}

		return near_bombs;

	}

	public static boolean insideTheBoard(int[][] board, int i, int j) {
		if (i >= 0 && i < board.length && j >= 0 && j < board[i].length) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean insideTheBoardBoolean(boolean[][] open, int i, int j) {
		if (i >= 0 && i < open.length && j >= 0 && j < open[i].length) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean clicked(int x, int y, int[][] board, boolean[][] open) {
	    
		final int BOMB = 9;
		final int VOID = 0;

		//If a bomb is clicked, show the whole board
	    if (board[x][y] == BOMB) {
	    	for (int i = 0; i < open.length; i++) {
	    		for (int j = 0; j < open[i].length; j++) {
	    			open[i][j] = true;
	    		}
	    	}
	    }

	    //Empty case
	    else if (board[x][y] == VOID) {
	    	revealAreaRecursion(x, y, board, open);
	    }

	    //Normal cases
	    else {
			open[x][y] = true;
	    }

	    return true;
	}
	
	public static void revealAreaRecursion(int x, int y, int[][] board, boolean[][] open) {
		final int VOID = 0;

		open[x][y] = true;

		//Loop the area
		int i = -1;
		int j = -1;

		while (i <= 1) {
			j = -1;
			while (j <= 1) {
				if (insideTheBoardBoolean(open, x+i, y+j) && open[x+i][y+j] == false) {
					if (i == 0 && j == 0) {
						j++;
					}
					else {
						if (board[x+i][y+j] != VOID) {
							open[x+i][y+j] = true;
						}
						else {
							revealAreaRecursion(x+i, y+j, board, open);
						}
						j++;
					}
				}
				else {
					j++;
				}
			}
			i++;
		}

	}

}

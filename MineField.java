package Minesweeper;

import java.util.HashSet;
import java.util.Scanner;
import java.security.SecureRandom;

import org.apache.commons.lang3.time.StopWatch;

public class MineField {

	Scanner input;
	StopWatch timer;

	int rowSize;
	int colSize;
	int flags;
	int maxFlags;
	int unflagedMines; //used to know if player wins. When it reaches 0, you win

	boolean lose;
	boolean win;

	Square[][] boxes;
	// char charInput;
	int currentRowValue; //stores input
	int currentColValue; 

	SecureRandom rand;
	HashSet<Integer> locationOfMines = new HashSet<Integer>(); 

	protected MineField(int rowSize, int colSize, int flags) {

		this.rowSize = rowSize;
		this.colSize = colSize;
		this.maxFlags = flags;
		this.flags = flags;
		unflagedMines = flags;
		lose = false;
		win = false;

		// initialize other components
		input = new Scanner(System.in);
		timer = new StopWatch();
		rand = new SecureRandom();

		// initialize boxes
		boxes = new Square[this.rowSize][this.colSize];
		for (int x = 0; x < boxes.length; x++) {
			for (int y = 0; y < boxes[x].length; y++) {
				boxes[x][y] = new Square();
			}
		}

		// first pick - no mines on first pick
		System.out.println(this.toString());
		inputRow((char) 97, (char) (97 + rowSize));
		inputCol(1, colSize);

		int numOfMines = this.flags;
		while (numOfMines > 0) {
			int randomInt = rand.nextInt(rowSize * colSize);
			// int randI = randomInt - 1;

			if (((currentRowValue * colSize)) + currentColValue == randomInt || locationOfMines.contains(randomInt)) {
				continue;
			} else {
				locationOfMines.add(randomInt);
				// realLocationOfMines.add(randI);
				boxes[randomInt / colSize][randomInt % colSize].mine();
				numOfMines--;
			}

		}

		//System.out.println(locationOfMines);

		// System.out.println(realLocationOfMines);

		

		// System.out.println(reveal());

		numberPlacement();
		uncover(currentRowValue,currentColValue);
	}

	public int inputRow(char min, char max) {

		do {

			System.out.print("Enter row letter: ");
			currentRowValue = InputValidator.isChar(input.next().toLowerCase());

		} while (currentRowValue < min || currentRowValue > max);

		currentRowValue = currentRowValue - min;
		return currentRowValue;
	}

	public int inputCol(int min, int max) {

		do {

			try {
				System.out.print("Enter column number: ");
				int colInput = Integer.parseInt(input.next());

				currentColValue = colInput;
			} catch (NumberFormatException e) {
				currentColValue = -1;
			}

		} while (currentColValue < min || currentColValue > max);

		currentColValue = currentColValue - min;
		return currentColValue;
	}

	@Override
	public String toString() {
		String output = "\n----------------------------------------------------------------------\n";
		output = "\nNumber of Flags left " + this.flags + "\n";
		int pad = 4;
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {

				if (row == 0 && col == 0) {
					output += "    ";
					for (int x = 0; x < colSize; x++) {
						//output += (" " + (x + 1) + " ");
						output += String.format("%1$"+pad+ "s", x + 1);
					}
					//output += "\n---------------------------------------------------------------\n";
					output += "\n";
					for (int i=0; i<= 8+(colSize*pad); i++){
						output += "-";
					}
					output += "\n";
				}

				if (col == 0) {
					output += (char) (97 + row) + " | ";
				}
				//output += (" " + boxes[row][col].toString() + " ");
				output += String.format("%1$"+pad+ "s",boxes[row][col].toString());
			}
			output += "\n";
		}
		return output;
	}

	public String reveal() {
		String output = "";
		int pad = 4;
		for (int row = 0; row < rowSize; row++) {
			for (int col = 0; col < colSize; col++) {

				if (row == 0 && col == 0) {
					output += "    ";
					for (int x = 0; x < colSize; x++) {
						output += String.format("%1$"+pad+ "s", x + 1);
					}
					output += "\n";
					for (int i=0; i<= 8+(colSize*pad); i++){
						output += "-";
					}
					output += "\n";
				}

				if (col == 0) {
					output += (char) (97 + row) + " | ";
				}
				output += String.format("%1$"+pad+ "s",boxes[row][col].reveal());
			}
			output += "\n";
		}
		return output;
	}

	public void numberPlacement() {  //hard coded 
		for (int y = 0; y < this.rowSize; y++) {
			for (int x = 0; x < this.colSize; x++) {

				if (boxes[y][x].isMined()) {
					continue;
				}
				// upper left corner
				if (y == 0 && x == 0) {
					if (boxes[y + 1][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x].isMined()) {
						boxes[y][x].addCount();
					}

				} else if (y == 0 && x == this.colSize - 1) { // upper right corner
					if (boxes[y + 1][x].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x - 1].isMined()) {
						boxes[y][x].addCount();
					}

				} else if (y == this.rowSize - 1 && x == this.colSize - 1) {// lower right corner
					if (boxes[y - 1][x].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
				} else if (y == this.rowSize - 1 && x == 0) {// lower left corner
					if (boxes[y - 1][x].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
				} else if (x == this.colSize - 1) { // right border
					if (boxes[y][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x].isMined()) {
						boxes[y][x].addCount();
					}
				} else if (x == 0) { // left border
					if (boxes[y][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x].isMined()) {
						boxes[y][x].addCount();
					}
				} else if (y == 0) { // top border
					if (boxes[y][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
				} else if (y == this.rowSize - 1) { // bottom border
					if (boxes[y - 1][x].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y][x + 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
					if (boxes[y][x - 1].isMined()) {
						boxes[y][x].addCount();
					}
				} else {
					if (boxes[y + 1][x + 1].isMined()) { // LR
						boxes[y][x].addCount();
					}
					if (boxes[y][x + 1].isMined()) {// R
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x].isMined()) {// D
						boxes[y][x].addCount();
					}
					if (boxes[y][x - 1].isMined()) {// L
						boxes[y][x].addCount();
					}
					if (boxes[y + 1][x - 1].isMined()) {// LL
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x].isMined()) {// U
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x - 1].isMined()) {// UL
						boxes[y][x].addCount();
					}
					if (boxes[y - 1][x + 1].isMined()) {// UR
						boxes[y][x].addCount();

					}
				}

			}

		}
	}

//add flag only when there are still avialable flags
	public boolean flag() {

		System.out.println("\nFlag: ");
		inputRow((char) 97, (char) (97 + rowSize));
		inputCol(1, colSize);

		if (!boxes[currentRowValue][currentColValue].isFlagged()&&boxes[currentRowValue][currentColValue].isCovered()  && this.flags > 0) {
			boxes[currentRowValue][currentColValue].flag();
			this.flags--;
			System.out.println("Flag Successful");
			if (boxes[currentRowValue][currentColValue].isMined()) {
				unflagedMines--;
			}
			if (unflagedMines == 0) {
				win = true;
			}
		} else {
			System.out.println("Flag failed");
		}
		return win;
	}

	public void unFlag() {

		System.out.println("\nUnflag: ");
		inputRow((char) 97, (char) (97 + rowSize));
		inputCol(1, colSize);

		if (boxes[currentRowValue][currentColValue].unFlag() && this.flags < this.maxFlags) {
			System.out.println("Unflag Successful");
			this.flags++;
			if (boxes[currentRowValue][currentColValue].isMined()) {
				unflagedMines++;
			}
		} else {
			System.out.println("Unflag failed");
		}

	}

	public boolean uncover() {

		System.out.println("\nUncover: ");
		inputRow((char) 97, (char) (97 + rowSize));
		inputCol(1, colSize);

		if (boxes[currentRowValue][currentColValue].uncover()) {
			System.out.println("Revealed");
			if (boxes[currentRowValue][currentColValue].isMined()) {
				lose = true;
			} else if (boxes[currentRowValue][currentColValue].getSign() == ' ') {
				uncoverzero(currentRowValue + 1, currentColValue);
				uncoverzero(currentRowValue - 1, currentColValue);
				uncoverzero(currentRowValue, currentColValue + 1);
				uncoverzero(currentRowValue, currentColValue - 1);
			}
		} else {
			System.out.println("Reveal failed");
		}

		return lose;

	}

	public boolean uncover(int row, int col) {

		System.out.println("\nUncover: ");
		currentRowValue = row;
		currentColValue = col;

		if (boxes[currentRowValue][currentColValue].uncover()) {
			System.out.println("Revealed");
			if (boxes[currentRowValue][currentColValue].isMined()) {
				lose = true;
			} else if (boxes[currentRowValue][currentColValue].getSign() == ' ') {
				uncoverzero(currentRowValue + 1, currentColValue);
				uncoverzero(currentRowValue - 1, currentColValue);
				uncoverzero(currentRowValue, currentColValue + 1);
				uncoverzero(currentRowValue, currentColValue - 1);
			}
		} else {
			System.out.println("Reveal failed");
		}

		return lose;

	}
	
	private void uncoverzero(int row, int col) {
		if (row < 0 || row > rowSize - 1 || col < 0 || col > colSize - 1) {
			return;
		} else {
			if (boxes[row][col].getSign() == ' ' && boxes[row][col].isCovered() && !boxes[row][col].isFlagged()) {
				boxes[row][col].uncover();
				uncoverzero(row + 1, col);
				uncoverzero(row - 1, col);
				uncoverzero(row, col + 1);
				uncoverzero(row, col - 1);
			}else if(boxes[row][col].getSign() != ' ' && boxes[row][col].isCovered() && !boxes[row][col].isFlagged()){
				boxes[row][col].uncover();
			} else {
				return;
			}
		}
	}
}

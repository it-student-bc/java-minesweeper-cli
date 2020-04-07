package Minesweeper;

public class Square {

	private boolean flagged;
	private boolean mined;
	private boolean covered;
	private char sign;
	private int surroundingMines;
	
	protected Square() {
		flagged = false;
		mined = false;
		covered = true;
		sign = ' ';
		surroundingMines = 0;
	}
	
	public boolean isFlagged() {
		return flagged;
	}

	public boolean isMined() {
		return mined;
	}

	public boolean isCovered() {
		return covered;
	}

	public char getSign() {
		return sign;
	}

	protected boolean mine() {
		if (mined) {
			return false;
		} else {
			mined = true;
			sign = 'x';
			return true;
		}
	}

	protected boolean flag() {
		if (flagged) {
			return false;
		} else {
			flagged = true;
			return true;
		}
	}
	
	protected boolean unFlag() {
		if (!flagged) {
			return false;
		} else {
			flagged = false;
			return true;
		}
	}

	protected boolean uncover() {
		if (covered && !flagged) {
			covered = false;
			return true;
		} else {
			return false;
		}
	}
	
	protected void addCount() {
		surroundingMines++;
		sign = Integer.toString(surroundingMines).charAt(0);
	}

	@Override
	public String toString() {
		if (covered && !flagged) {
			return "O";
		} else if (flagged) {
			return "F";
		} else {
			return Character.toString(sign);
		}
		
		//return Character.toString(sign);

	}
	
	public String reveal() {
		return Character.toString(sign);
	}
	

}

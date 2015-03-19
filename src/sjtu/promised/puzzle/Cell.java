package sjtu.promised.puzzle;

import java.util.Random;


public class Cell {
	
	private int mValid;
	
	private int mColumnIndex;
	private int mRowIndex;
	private int mValue;
	
	public Cell(int rowIndex, int colIndex) {
		mRowIndex = rowIndex;
		mColumnIndex = colIndex;
		mValid = 0;
	}
	
	public boolean isAdjacent(Cell cell) {
		if (mRowIndex == cell.mRowIndex) {
			return Math.abs(mColumnIndex - cell.mColumnIndex) == 1;
		}
		if (mColumnIndex == cell.mColumnIndex) {
			return Math.abs(mRowIndex - cell.mRowIndex) == 1;
		}
		return false;
	}
	
	public int isValid() {
		return mValid;
	}
	
	public int getColumnIndex() {
		return mColumnIndex;
	}
	
	public int getRowIndex() {
		return mRowIndex;
	}
	
	public int getValue() {
		return mValue;
	}
	
	public void updateValue(int value) {
		if (mValid == 0) {
			mValue = mValue + value;
			if (mValue == 9) {
				mValid = 1;
			}
			else {
				mValue = mValue % 10;
			}
		}
	}
	
	public void updateValue2(int value) {
		if (mValid == 0) {
			mValue = mValue + value;
			if (mValue == 9) {
				mValid = 2;
			}
			else if(mValue == 16) {
				mValid = 3;
			}
			else if(mValue == 11 || mValue == 10) {
				mValid = -1;
			}
			else {
				mValue = mValue % 10;
			}
		}
	}
	
	public void RefreshRandomValue() {
		Random mGenerator = new Random();
		mValue = mGenerator.nextInt(6) + 1;
		mValid = 0;
	}
	
}

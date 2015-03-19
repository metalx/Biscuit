package sjtu.promised.puzzle;

public class CellCollection {
	
	private Cell[][] mCells;
	
	public CellCollection(int size) {
		mCells = new Cell[size][size];
		
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				mCells[row][col] = new Cell(row, col);
			}
		}
	}
	
	public void Randomize(int size) {
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				mCells[row][col].RefreshRandomValue();
			}
		}
	}
	
	public Cell getCell(int rowIndex, int colIndex) {
		return mCells[rowIndex][colIndex];
	}
	
}

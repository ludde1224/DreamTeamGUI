package com.dat_rig.sudokusolver;

import java.util.HashSet;
import java.util.Set;

public class Sudoku {
	int[][] board;

	public Sudoku(int[][] table) {
		board = table;
	}
	
	public Sudoku() {
		board = new int[9][9];
	}

	/**
	 * Put kommer läsa in ett tal på platsen givet av row och col som har värdet value och placera det i board.
	 * @param row
	 * Anger vilken rad som är aktuell.
	 * @param col
	 * Anger vilken column som är aktuell.
	 * @param value
	 * Anger värdet som ska placeras in.
	 */
	public void put(int row, int col, int value){
		if(row < 0 || row > 9 ||col < 0 || col > 9 || value < 0 || value > 9){
			throw new IllegalArgumentException("Br�det f�r endast inneh�lla tal inom intervaller 1-9 : " + value);
		}
		board[row][col]=value;
	}


/**
 * RowIsOk ska undersöka en rad och leta efter liknande nummer.
 * @param rowNumber
 * Anger vilket rad som undersöks.
 * @return
 * retunerar true och liknande tal finns annars false.
 */
	public boolean rowIsOk(int rowNumber) {
		boolean[] taken = new boolean[9];
		for (int col = 0; col < 9; col++) {
			int value = board[rowNumber][col];
			if (value != 0 && taken[value-1]) { 
				return false;
			} else if(value != 0) {
				taken[value-1] = true;
			}
		}
		return true;
	}
	
	/**
	 * ColIsOk ska undersöka en column och leta efter liknande nummer.
	 * @param colNumber
	 * Anger den aktuella kolumn.
	 * @return
	 * retunerar true och liknande tal finns annars false.
	 */
	public boolean colIsOk(int colNumber) {
		boolean[] taken = new boolean[9];
		for (int row = 0; row < 9; row++) {
			int value = board[row][colNumber];
			if (value !=0 && taken[value-1]) {
				return false;
			} else if(value != 0){
				taken[value-1] = true;
			}
		}
		return true;

	}

	private int startNumber(int number) {
		if (number < 3) { 
			return 0;
		} else if (number > 5) { 
			return 6;
		} else {	
			return 3;
		}
	}

	/**
	 * Undersöker en av de 9 grupperna och ser till att de följer reglerna.
	 * @param rowNumber
	 * Anger radnumret till den ruta som ligger i den aktuella sektionen.
	 * @param colNumber
	 * Anger columnnumret till den ruta som ligger i den aktuella sektionen.
	 * @return
	 * Retunerar false om reglerna inte stämmer och true annars
	 */
	public boolean sectionIsOk(int rowNumber, int colNumber) {
		Set<Integer> taken = new HashSet<Integer>();
		int rowIndex = startNumber(rowNumber);
		int colIndex = startNumber(colNumber);
		for (int row = rowIndex; row < rowIndex + 3; row++) {
			for (int col = colIndex; col < colIndex + 3; col++) {
				int value = board[row][col];
				if (value != 0 && !taken.add(value)) {
					return false;
				}
			}
		}
		// table[startNumber(rowNumber)][startNumber(colNumber)];
		return true;
	}

	/**
	 * Ska undersöka en ruta och se så alla regler uppfylls för den.
	 * @param rowNumber
	 * Anger radnumret till den ruta som ska undersökas.
	 * @param colNumber
	 * Anger kolumnnumret till den ruta som ska undersökas.
	 * @return
	 * Retunerar true om reglerna följs annars false.
	 */
	public boolean numberIsOk(int rowNumber, int colNumber) {
		if (rowIsOk(rowNumber) && colIsOk(colNumber)
				&& sectionIsOk(rowNumber, colNumber)) {
			return true;
		}
		return false;
	}

	/**
	 * Ska sätta nollor på alla platser på brädet, nollställer det.
	 */
	public void clear() {
		board = new int[9][9];
	}

	public boolean solve() {
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				if(!numberIsOk(row, col))
					return false;
			}
		}
		return backtrace(0, 0);
	}
	
	private boolean backtrace(int row, int col) {
		if(row == 9 && col == 0)
			return true;
		if (board[row][col] == 0) {
			for (int i = 1; i < 10; i++) {
				board[row][col] = i;
				if (numberIsOk(row, col)) {
					if (backtrace(nextRow(row, col), (col + 1)%9)) {
						return true;
					}
				}
			}
			board[row][col] = 0; //Undo
			return false;
		}
		return backtrace(nextRow(row, col), (col + 1)%9);
	}

	private int nextRow(int row, int col) {
		if (col == 8) {
			return (row + 1);
		} else {
			return row;
		}
	}
/**
 * Ska skriva ut hela sudokut och alla dess värden
 */
	public void printSudoku() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				sb.append(board[row][col] + "  ");
			}
			sb.append('\n');
		}
		sb.append('\n');
		System.out.println(sb.toString());
	}


	/**
	 *
	 * Returnerar en string med siffran som ligger i cellen "row,col".
	 *
	 * @param row raden som cellen ligger på.
	 * @param col kolumnen som cellen ligger i.
     * @return en string med cellens värde
     */
	public String get(int row, int col){
		return Integer.toString(board[row][col]);
	}
	
}

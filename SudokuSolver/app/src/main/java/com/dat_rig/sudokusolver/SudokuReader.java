package com.dat_rig.sudokusolver;

import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;

import java.util.*;
import java.io.*;

public class SudokuReader {
	private Scanner scanner = null;
	private int counter = 0;
	private InputStream is;


	public Sudoku read(AssetManager am) {



		Sudoku table = new Sudoku();
		if (counter == 0) {
			try {
				is = am.open("SudokuBoards.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {
					int nbr = 0;
					try {
						nbr = is.read()-'0';
					} catch (IOException e) {
						e.printStackTrace();
					}


					if (nbr != 0) {
						table.put(row, col, nbr);
					}
				}
			}
			/*for (int i = 0; i < 17; i++) {
				try {
					int nothing = is.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
			counter++;
			if (counter == 4) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				counter = 0;
			}
			return table;
		}
	}
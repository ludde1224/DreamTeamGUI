package com.dat_rig.sudokusolver;

import android.content.res.AssetManager;
import android.renderscript.ScriptGroup;

import java.util.*;
import java.io.*;

public class SudokuReader {
	private int counter = 0;
	private InputStream is;

    /**
     * Läser från filen "SudokuBoards.txt" som finns i app/assets.
     *
     *
     * @param am Den AssetManager som innehåller filen "SudokuBoards.txt".
     * @return Ett sudokobräde med de inlästa siffrorna ifyllda
     */
	public Sudoku read(AssetManager am) {



		Sudoku table = new Sudoku(); //Skapa ett nytt sodukubräde
		if (counter == 0) { //Om det är första gången filen läses
			try {
				is = am.open("SudokuBoards.txt"); //Öppna inputstreamen från AssetManager
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
			for (int row = 0; row < 9; row++) {
				for (int col = 0; col < 9; col++) {//Läs för varje plats i matrisen in en siffra från filen
					int nbr = 0;
					try {
						nbr = is.read()-'0'; //Läser numret från filen och omvandlar det från unicode till int
					} catch (IOException e) {
						e.printStackTrace();
					}


					if (nbr != 0) {
						table.put(row, col, nbr); //Om numret inte är en nolla så läggs det till på platsen
                                                  //i matrisen som definieras av row och col
					}
				}
			}

			counter++; //räknar på hur mänga sudokun som lästs in
			if (counter == 4) { //Om alla fyra sudokuna från filen har lästs in
                                //stänger denna inputstreamen och ser till att allt återställs
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
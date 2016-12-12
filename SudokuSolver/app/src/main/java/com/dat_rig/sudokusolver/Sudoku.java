package com.dat_rig.sudokusolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

public class Sudoku extends AppCompatActivity {
    EditText[][] sudokuMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sudoku);

        sudokuMatrix = new EditText[9][9];

        GridLayout layout = (GridLayout) findViewById(R.id.sudokulayout);


        for(int row = 0; row < 9; row++) { //dessa två loopar skapar alla rutor med EditText-element

            for (int col = 0; col < 9; col++) {

                GridLayout.Spec rowspecs = GridLayout.spec(row, 1);
                GridLayout.Spec colspecs = GridLayout.spec(col, 1);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowspecs, colspecs);

                EditText number = new EditText(this);
                sudokuMatrix[row][col] = number;
                layout.addView(sudokuMatrix[row][col], lp);

            }

        }


    }

    public void clear(View view){

        for(int row = 0; row < 9; row++) { //test av knapp

            for (int col = 0; col < 9; col++) {
                sudokuMatrix[row][col].setText("0");
            }
        }
    }
    public void solve(View view){

        for(int row = 0; row < 9; row++) { //test av knapp

           for (int col = 0; col < 9; col++) {

                sudokuMatrix[row][col].setText(String.valueOf(row + ": " + col));

            }
            }

    }


}

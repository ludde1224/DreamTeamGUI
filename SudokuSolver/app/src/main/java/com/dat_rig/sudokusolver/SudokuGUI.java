package com.dat_rig.sudokusolver;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import static com.dat_rig.sudokusolver.R.id.messageText;
import static com.dat_rig.sudokusolver.R.id.solveButton;

public class SudokuGUI extends AppCompatActivity {
    EditText[][] sudokuMatrix;
    Sudoku board = new Sudoku();
    TextView messageBox;
    SudokuReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sudoku);
        reader = new SudokuReader();

        sudokuMatrix = new EditText[9][9];

        GridLayout layout = (GridLayout) findViewById(R.id.sudokulayout);
        messageBox = (TextView) findViewById(messageText);

        layout.setBackgroundColor(0xff000000);

        for (int row = 0; row < 9; row++) { //dessa två loopar skapar alla rutor med EditText-element

            for (int col = 0; col < 9; col++) {

                GridLayout.Spec rowspecs = GridLayout.spec(row, 1);
                GridLayout.Spec colspecs = GridLayout.spec(col, 1);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowspecs, colspecs);

                EditText number = new EditText(this);
                number.setInputType(InputType.TYPE_CLASS_NUMBER);
                number.setText("1");
                number.setBackgroundColor(0xffffffff);
                lp.setMargins(4, 4, 4, 4);
                number.setHeight(100);
                number.setWidth(100);
                number.setGravity(0x00000011);
                if(row/3 == 1 && col/3 == 1){

                }
                else if(row/3 == 1 || col/3 == 1){
                    number.setBackgroundColor(0x99999999);
                }
                sudokuMatrix[row][col] = number;
                layout.addView(sudokuMatrix[row][col], lp);

            }

        }


    }

    public void load(View v) {





        AssetManager am;
            am = getAssets();
        board = reader.read(am);


        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                sudokuMatrix[row][col].setEnabled(true);
                sudokuMatrix[row][col].setText(board.get(row, col));
                if (Integer.parseInt(sudokuMatrix[row][col].getText().toString()) != 0){
                    sudokuMatrix[row][col].setEnabled(false);
                }
            }
        }

    }

    public void solve(View v) {
        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                try {
                    board.put(row, col, Integer.parseInt(sudokuMatrix[row][col].getText().toString()));
                } catch(IllegalArgumentException e){
                    sudokuMatrix[row][col].setText("0");
                    return;
                }
            }
        }
        if (!board.solve()) {

            messageBox.setText("Unsolvable");
        } else {
            for (int row = 0; row < 9; row++) {

                for (int col = 0; col < 9; col++) {

                    sudokuMatrix[row][col].setText(board.get(row, col));
                }
            }
        }


    }

    public void check(View v) {

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                try {
                    board.put(row, col, Integer.parseInt(sudokuMatrix[row][col].getText().toString()));
                } catch(IllegalArgumentException e){
                    sudokuMatrix[row][col].setText("0");
                    return;
                }

            }
        }

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                if (!board.numberIsOk(row, col)) {
                    sudokuMatrix[row][col].setBackgroundColor(0xffff0000);
                } else {

                    sudokuMatrix[row][col].setBackgroundColor(0xffffffff);

                    if(row/3 == 1 && col/3 == 1){

                    }
                    else if(row/3 == 1 || col/3 == 1){
                        sudokuMatrix[row][col].setBackgroundColor(0x99999999);
                    }

                }
            }

        }

    }

    public void clear(View v) {
        board.clear();

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                sudokuMatrix[row][col].setText(board.get(row, col));

                sudokuMatrix[row][col].setBackgroundColor(0xffffffff);

                sudokuMatrix[row][col].setEnabled(true);

                if(row/3 == 1 && col/3 == 1){

                }
                else if(row/3 == 1 || col/3 == 1){
                    sudokuMatrix[row][col].setBackgroundColor(0x99999999);
                }

            }
        }

    }
}

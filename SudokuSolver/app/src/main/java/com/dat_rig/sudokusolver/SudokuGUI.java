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
import static com.dat_rig.sudokusolver.R.id.text;

public class SudokuGUI extends AppCompatActivity {
    EditText[][] sudokuMatrix;
    Sudoku board = new Sudoku();
    TextView messageBox;
    SudokuReader reader;


    /**
     * Skapar alla grafiska element när programmet startas.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sudoku);
        reader = new SudokuReader(); //Initialiserar den klass som hanterar inläsning av filer

        sudokuMatrix = new EditText[9][9]; //Skapar en 9*9 matris

        GridLayout layout = (GridLayout) findViewById(R.id.sudokulayout);
        messageBox = (TextView) findViewById(messageText); //Finner och låter "messageBox" peka på TextView objektet
        messageBox.setGravity(0x00000011); //Centrerar texten i messageBox
        messageBox.setTextSize(30);

        layout.setBackgroundColor(0xff000000); //Gör bakgrundsfärgen för GridLayout:en svart

        for (int row = 0; row < 9; row++) { //dessa två loopar skapar alla rutor med EditText-element

            for (int col = 0; col < 9; col++) {

                GridLayout.Spec rowspecs = GridLayout.spec(row, 1); //Dessa två bestämmer var i rutnätet varje EditText objekt ska befinna sig
                GridLayout.Spec colspecs = GridLayout.spec(col, 1);

                GridLayout.LayoutParams lp = new GridLayout.LayoutParams(rowspecs, colspecs);

                lp.setMargins(4, 4, 4, 4);

                EditText number = new EditText(this);

                number.setInputType(InputType.TYPE_CLASS_NUMBER); //Ser till att endast nummer får vara inputs
                number.setText("");
                number.setBackgroundColor(0xffffffff); //Sätter cellens bakgrundsfärg till vit.
                number.setHeight(108); // Ändrar höjd och bredd för varje cell
                number.setWidth(102);
                number.setGravity(0x00000011); //Centrerar siffran i rutorna


                if(row/3 == 1 && col/3 == 1){ //ser till att mittenregionen fortfarande är vit
                }
                else if(row/3 == 1 || col/3 == 1){ //Ändrar fyra av regionerna till gråa
                    number.setBackgroundColor(0x99999999);
                }

                sudokuMatrix[row][col] = number; //lägger till EditText-objektet i en intern matris
                layout.addView(sudokuMatrix[row][col], lp); //lägger till EditText-objektet till GUI-T med Layoutparametrarna lp

            }

        }


    }

    /**
     * Laddar in ett sudoku från en fil när knappen "Load" har klickats.
     *
     * @param v
     */
    public void load(View v) { //Laddar ett sudoku från filen som specificeras i SudokuReader


      messageBox.setText("");


        AssetManager am;         //Hämtar alla filer i assets och sparar dem i en AssetManager.
            am = getAssets();    //Dessa skickas sedan iväg till SudokuReader som läser filen
        board = reader.read(am);


        for (int row = 0; row < 9; row++) {//Itererar över varje objekt i matrisen

            for (int col = 0; col < 9; col++) {

                EditText textObj = sudokuMatrix[row][col]; //Hämtar EditText-objektet

                int number = Integer.parseInt(board.get(row, col)); //Eftersom get-metoden returnerar en sträng
                                                                    //använder vi en parseInt för att göra om denna till en int

                if (number != 0){ //Kollar om numret är insatt av load-metoden
                    textObj.setEnabled(false); //Förhindrar i så fall detta nummer från att ändras av användaren
                    textObj.setTextColor(0xffff0000); //Ändrar färgen på numret
                    textObj.setText(board.get(row, col)); //Hämtar numret från board så att användaren kan se det
                } else {
                    textObj.setText("");
                    textObj.setTextColor(0xff000000);
                    textObj.setEnabled(true);
                }
            }
        }

    }

    /**
     * Försöker lösa sudokut på spelplanen när knappen "Solve" har klickats.
     * Meddelar också spelaren om ingen lösning finns
     * @param v
     */
    public void solve(View v) { //Löser sudokut
        messageBox.setText("");

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) {

                EditText textObj = sudokuMatrix[row][col];

                String number = textObj.getText().toString();


                try {

                    if (number.equals("0")){ //Kollar om användaren har satt in en nolla
                        messageBox.setText("Number must be between 1-9 -> " + "Row: " + (row+1) + " Column: " + (col+1) );
                        return;
                    }

                    if(!number.equals("")) { //Om användaren har satt in en nummer lägger den till det till board, annars skickas en nolla till board
                        board.put(row, col, Integer.parseInt(textObj.getText().toString()));
                    } else {
                        board.put(row, col, 0);
                    }
                } catch(IllegalArgumentException e){
                    messageBox.setText("Number must be between 1-9 -> " + "Row: " + (row+1) + " Column: " + (col+1) ); //Om användaren har matat in ett nummer som inte är
                    return;                                                                                            //mellan 1-9 kommer detta hända
                }
            }
        }
        if (!board.solve()) { //Om sudokut är olösligt meddelas användaren om detta

            messageBox.setText("Unsolvable!");
        } else { //Annars visas lösningen upp och användaren meddelas om att det är löst
            for (int row = 0; row < 9; row++) {

                for (int col = 0; col < 9; col++) {

                    sudokuMatrix[row][col].setText(board.get(row, col));
                }
            }

            messageBox.setText("Solved!");
        }


    }

    /**
     * Kollar om siffrorna på brädet följer reglerna när knappen "Check" har klickats.
     * Markerar också de kolumner, rader och regioner som inte uppfyller reglernas krav.
     *
     * @param v
     */
    public void check(View v) { //Kollar om siffrorna på brädet trotsar reglerna

        messageBox.setText("");

        for (int row = 0; row < 9; row++) { //Lägger först till alla siffror till board

            for (int col = 0; col < 9; col++) {

                String number = sudokuMatrix[row][col].getText().toString();

                try {
                    if (number.equals("0")){
                        messageBox.setText("Number must be between 1-9 -> " + "Row: " + (row+1) + " Column: " + (col+1) );
                        return;
                    }

                    if(!number.equals("")) {
                        board.put(row, col, Integer.parseInt(sudokuMatrix[row][col].getText().toString()));
                    } else  {
                        board.put(row, col, 0);
                    }
                } catch(IllegalArgumentException e){
                    messageBox.setText("Number must be between 1-9 -> " + "Row: " + (row+1) + " Column: " + (col+1) );
                    return;
                }

            }
        }

        for (int row = 0; row < 9; row++) { //Kollar om siffrorna följer reglerna

            for (int col = 0; col < 9; col++) {

                EditText textObj = sudokuMatrix[row][col];

                if (!board.numberIsOk(row, col)) { //Om reglerna inte följs markerar denna metod raden, kolumnen eller regionen som bryter mot reglerna
                    textObj.setBackgroundColor(0xff0000ff);
                } else { //Om reglerna följs så ser vi till att bakgrundsfärgerna återställs

                    textObj.setBackgroundColor(0xffffffff);

                    if(row/3 == 1 && col/3 == 1){

                    }
                    else if(row/3 == 1 || col/3 == 1){
                        textObj.setBackgroundColor(0x99999999);
                    }

                }
            }

        }

    }

    /**
     * Tömmer spelplanen på siffror
     * @param v
     */
    public void clear(View v) { //Tömmer brädet på siffror

        messageBox.setText("");

        board.clear();

        for (int row = 0; row < 9; row++) {

            for (int col = 0; col < 9; col++) { //För varje plats i matrisen tas siffrorna i cellerna bort.
                                                //Färgen och rutans "Enable" återställs

                EditText textObj = sudokuMatrix[row][col];


                textObj.setTextColor(0xff000000);
                textObj.setText("");
                textObj.setBackgroundColor(0xffffffff);

                textObj.setEnabled(true);

                if(row/3 == 1 && col/3 == 1){

                }
                else if(row/3 == 1 || col/3 == 1){
                    textObj.setBackgroundColor(0x99999999);
                }

            }
        }

    }
}

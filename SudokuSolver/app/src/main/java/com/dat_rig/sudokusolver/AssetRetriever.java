package com.dat_rig.sudokusolver;

import android.content.res.AssetManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Ludwig on 2016-12-13.
 */

public class AssetRetriever {
    private AssetManager am;


    public AssetRetriever(AssetManager am){
        this.am = am;
    }

    public File getFile(){
        try {
            InputStream is = am.open("SudokuBoards.txt");
            File file = createFileFromInputStream(is);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private File createFileFromInputStream(InputStream is) {
        try {
            File f = new File("SudokuBoards");
            OutputStream os = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

            os.close();
            is.close();

            return f;
        } catch (IOException e){
            return null;
        }
    }

}

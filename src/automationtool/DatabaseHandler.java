/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automationtool;

import com.healthmarketscience.jackcess.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author stifspear
 */
public class DatabaseHandler extends JFrame {

    private static File file;
    private static Table table;

    /*
    Helper function to open and read the database
    */
    public static void readDatabase(File f) {
        try {
            file = f;
            Database db = DatabaseBuilder.open(file);
            Set<String> tables = db.getTableNames();
            ArrayList<String> stringtable = new ArrayList<>(tables);
            table = db.getTable(stringtable.get(0));
            Cursor cursor = CursorBuilder.createCursor(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Table getTable() {
        return table;
    }
}

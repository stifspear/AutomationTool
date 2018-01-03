/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automationtool;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author stifspear
 * Store name of all columns in a list
 * For each {+,-} result store the result
 * For each {R,S,MR} result store the result
 * If single place is selected display the result(easy)
 * If multiple places are selected merge the result of above query to show all the organisms
 * Objects model will be used to store results for different places for each of join later
 * For each place object will be created. Each object will have total count, positive results,
   sensitivity pattern, organisms.
 
 * Task will be divided into two parts 
  i) Count positive results
  ii) Find sensitivity patterns
 */
public class AllFilter {
    public static final String PLACE = "Place";
    public static final String ORG = "Organisms";
    public static final String SPEC = "Specimen";
    public static final String OTH = "Other";
    public static HashMap<String,Integer> getTestFilter(Table table, ArrayList<ArrayList<String>> column) {
        HashMap<String,Integer> map = new HashMap<>();
        String key;
        int count;
        for(Row row:table) {
            count = 1;
            key = doesSatisfies(row,column);
            if(key == null)
                continue;
            if(map.containsKey(key)) {
                count = map.get(key)+1;
            }
            map.put(key,count);
        }
        return map;
    }
    public static String doesSatisfies(Row row, ArrayList<ArrayList<String>> column) {
        
    }
    public static boolean hasPlace(ArrayList<String> list, Row row) {
        return list.stream().anyMatch((val) -> (val.equals(row.get(PLACE).toString())));
    }
    public static boolean hasOrganism(ArrayList<String> list, Row row) {
        return list.stream().anyMatch((val) -> (val.equals(row.get(ORG).toString())));
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automationtool;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import java.util.HashMap;

/**
 *
 * @author stifspear
 */
public class TestFilter {
    public static HashMap<String,Integer> getTestFilter(Table table, String column) {
        HashMap<String,Integer> map = new HashMap<>();
        String key;
        int count;
        for(Row row:table) {
            count = 1;
            key = row.getString(column);
            if(key == null)
                continue;
            if(map.containsKey(key)) {
                count = map.get(key)+1;
            }
            map.put(key,count);
        }
        return map;
    }
}

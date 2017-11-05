/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automationtool;

import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author stifspear
 */
public class ChartTest {
    HashMap<String,Integer> map;
    String colName;
    public ChartTest(HashMap<String,Integer> map,String colName){
        this.map = map;
        this.colName = colName;
        draw();
    }

    public void draw() {
// create a dataset...
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        map.keySet().forEach((key) -> {
            //data.addValue(key, map.get(key));
            data.addValue(map.get(key), "Classes", key);
        });
// create a chart...
        JFreeChart chart = ChartFactory.createLineChart(
                colName+" vs Count Graph", 
                colName, 
                "Count", 
                data, 
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                true);
// create and display a frame...
        ChartFrame frame = new ChartFrame(colName+" Graph", chart);
        frame.pack();
        frame.setVisible(true);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automationtool;

import com.healthmarketscience.jackcess.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author stifspear
 */
public class MainPage extends JFrame{
    private JPanel panel,pan2,filterPanel;
    private JTable table;
    private JList filterList,placeList,organismList,specimenList;
    private JLabel filterLabel,outputLabel;
    private File databaseFile;
    private JTextArea outputBox;
    private String[] filterNames;
    private JPanel tablePanel;
    private JMenuItem open,exit;
    private JButton openButton;
    private JScrollPane filterScrollPane,placeNameScrollPane,organismNameScrollPane,specimenScrollPane;
    private DefaultListModel muttableList,muttablePlaceList,muttableorganismList,muttableSpecimenList;
    private ArrayList<ArrayList<String>> filters;
    Dimension dim;
    JMenuBar menuBar;
    JMenu file;
    private JButton analyzeButton;
    public MainPage() {
        init();
    }
    /*
     * Function below creates the main page of the application.
    It uses two panel
    i) For table wtih border layout
    ii) For filter and output box
    */
    public void init() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setTitle("Main Page");
        dim = tk.getScreenSize();
        panel = new JPanel(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuBar = new JMenuBar();
        file = new JMenu("File");
        menuBar.add(file);
        open = new JMenuItem("Open");
        exit = new JMenuItem("Exit");
        /*
        Will map the close shortcut to CTRL-W
        Will exit the application
        */
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        exit.addActionListener((ActionEvent e) -> {
            if(e.getSource() == exit)
                System.exit(0);
        });
        
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
        open.addActionListener(new ClickAction());
        
        file.add(open);
        file.add(exit);
        this.setJMenuBar(menuBar);
         
        
        /* First panel */
        tablePanel = new JPanel(new BorderLayout());
        addComponent(panel, tablePanel, 0, 0, 1, 1, 7, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        /* Second panel*/
        
        pan2 = new JPanel(new GridBagLayout());
        addComponent(panel, pan2, 1, 0, 1, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        openButton = new JButton("Click to select a database");
        openButton.addActionListener(new ClickAction());
        addComponent(pan2, openButton, 0, 0, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        
        JLabel label = new JLabel("Select parameters to analyze");
        addComponent(pan2, label, 0, 1, 1, 1, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
        JPanel filterLabel = new JPanel(new GridLayout(1,4));
        JLabel placeL,organismL,otherL,specimenL;
        placeL = new JLabel("Select Places");
        organismL = new JLabel("Organisms");
        otherL = new JLabel("Other");
        specimenL = new JLabel("Specimen");
        filterLabel.add(placeL);
        filterLabel.add(organismL);
        filterLabel.add(specimenL);
        filterLabel.add(otherL);
        addComponent(pan2, filterLabel, 0, 2, 1, 1, 1, 0,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        /* Following is default value
         * It will be replaced by all the filters once database has been read
         * and Logic is implemented in fillTable function below.
         * Three filter box will be created below
        */
        filterPanel = new JPanel(new GridLayout(1,4));
        addComponent(pan2, filterPanel, 0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        muttableList = new DefaultListModel();
        muttablePlaceList = new DefaultListModel();
        muttableSpecimenList = new DefaultListModel();
        muttableorganismList = new DefaultListModel();
        filterList = new JList(muttableList);
        placeList = new JList(muttablePlaceList);
        specimenList = new JList(muttableSpecimenList);
        organismList = new JList(muttableorganismList);
        addFilter(placeList,muttablePlaceList,placeNameScrollPane);
        addFilter(organismList,muttableorganismList,organismNameScrollPane);
        addFilter(specimenList,muttableSpecimenList,specimenScrollPane);
        addFilter(filterList, muttableList, filterScrollPane);
        /* Analyze button */
        
        analyzeButton = new JButton("Analyze");
        analyzeButton.addActionListener(new ClickAction());
        addComponent(pan2, analyzeButton, 0, 4, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        
        JLabel label2 = new JLabel("Output");
        addComponent(pan2, label2, 0, 5, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
        
        
        outputBox = new JTextArea();
        
        outputBox.setEditable(false);
        outputBox.setLineWrap(true);
        JScrollPane outputScroll = new JScrollPane(outputBox, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addComponent(pan2, outputScroll, 0, 6, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
    /*
    Three filter box will be created using below function.
    Jpanel with gridlayout is created and three box is added to the panel
    */
    private void addFilter(JList list, DefaultListModel model, JScrollPane pane) {
        model.addElement("None");
        model.addElement("All");
        pane = new JScrollPane(list, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        filterPanel.add(pane);
    }
    /*
     * Function will be used to add new components to the panel
    */
    public void addComponent(JPanel pan, JComponent comp,
            int xPos, int yPos, 
            int compWidth, int compHeight, 
            double weightX, double weightY,
            int place, int stretch) {
        GridBagConstraints gridCon = new GridBagConstraints();
        gridCon.gridx = xPos;
        gridCon.gridy = yPos;
        gridCon.gridwidth = compWidth;
        gridCon.gridheight = compHeight;
        gridCon.weightx = weightX;
        gridCon.weighty = weightY;
        gridCon.insets = new Insets(2, 2, 2, 2);
        gridCon.anchor = place;
        gridCon.fill = stretch;
        pan.add(comp,gridCon);
    }
    /*
     * Table will be build after reading a file from the database
    */
    public String[] fillTable(JTable tj, Table t) {
        int size = t.getColumnCount();
        String[] columnNames = new String[size];
        int index = 0;
        ArrayList<String> list = new ArrayList<String>();
        // Items added to filter list
        for (Column col : t.getColumns()) {
            columnNames[index++] = col.getName();
            
            switch(col.getName()) {
                case "Place":
                    list = getList(t,col.getName());
                    addElementToList(muttablePlaceList,list);
                    break;
                case "Organism":
                    list = getList(t,col.getName());
                    addElementToList(muttableorganismList,list);
                    break;
                case "Specimen":
                    list = getList(t, col.getName());
                    addElementToList(muttableSpecimenList,list);
                    break;
                default :
                    muttableList.addElement(col.getName());
                    break;
                
            }
        }
        int rowCount = t.getRowCount()+1;
        Object[][] data = new Object[rowCount][size];
        System.out.println(rowCount);
        int x,y;
        x=y=0;
        for (Row row : t) {
            y = 0;
            for(Column col:t.getColumns()) {
                data[x][y++] = row.get(col.getName());
            }
            x++;
        }
        
        table = new JTable(data, columnNames);
        System.out.println(table);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane tablePane = new JScrollPane(table, 
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //addComponent(panel, tablePane, 0, 0, 1, 1, 9, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        //textArea.setEditable(true);
        
        
        tablePanel.add(tablePane);
        this.setSize(dim);
        return columnNames;
    }
    private void addElementToList(DefaultListModel model, ArrayList<String> list) {
        list.forEach((val) -> {
            model.addElement(val);
        });
    }
    private ArrayList<String> getList(Table t, String col) {
        ArrayList<String> list = new ArrayList<>();
        for(Row row:t) {
            if(row.get(col)!= null) {
                if (!list.contains(row.get(col).toString())) {
                    list.add(row.get(col).toString());
                }
            }
        }
        return list;
    }
    /*
    Below class implements action listener. When Open button is clicked it opens a dialog 
    box for selecting a file and displays the file data in table format.
    When analyze button is clicked then it will filter all the data based on the selected
    options. Output is the AND of all filters(A&&B&&C->Output) i.e common data for selected filters.
    */
    private class ClickAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == open || e.getSource() == openButton) {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showOpenDialog(MainPage.this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    databaseFile = chooser.getSelectedFile();
                    outputBox.setText("File selected is "+databaseFile.getName()+" \n");
                    DatabaseHandler.readDatabase(databaseFile);
                    Table dataBaseTable = DatabaseHandler.getTable();
                    filterNames = fillTable(table,dataBaseTable);
                }
            } else if(e.getSource() == analyzeButton) {
                ArrayList<String> filter1,filter2,filter3,filter4;
                filter1 = new ArrayList<>();
                filter2 = new ArrayList<>();
                filter3 = new ArrayList<>();
                filter4 = new ArrayList<>();
                /*
                Will get all the selected values of 4 filters
                */
                for(int va:filterList.getSelectedIndices())
                    filter1.add(muttableList.get(va).toString());
                for(int va:placeList.getSelectedIndices())
                    filter2.add(muttablePlaceList.get(va).toString());
                for(int va:organismList.getSelectedIndices())
                    filter3.add(muttableorganismList.get(va).toString());
                for(int va:specimenList.getSelectedIndices())
                    filter4.add(muttableSpecimenList.get(va).toString());
                filters.add(filter1);
                filters.add(filter2);
                filters.add(filter3);
                filters.add(filter4);
                HashMap<String,Integer> map = AllFilter.getTestFilter(DatabaseHandler.getTable(), filters);
                map.keySet().forEach((key) -> {
                    if(key != null)
                    outputBox.append(key+" "+map.get(key)+"\n");
                    
                });
            }
        }
        
    }
}
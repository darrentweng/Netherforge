//creates a generic text box to be used in the GUI
//parent class for other text box-type elements

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.ArrayList;

public class GenericTBox {
    
    //variables that describe the TBox
    private String text;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    //variables that describe placing the TBox
    private int fill;
    private int gridx;
    private int gridy;
    private double weightx;
    private double weighty;
    private int gridwidth;
    private int gridheight;

    //darkmode selector
    private boolean darkmode = false;

    public GenericTBox(String t,    //text for TBox
                       int f,       //fill for gridbagconstraints
                       int gx,   //grid x location for gridbagconstraints
                       int gy,   //grid y location for gridbagconstraints
                       double wx,   //weight in x dir for gridbagconstraints
                       double wy,   //weight in y dir for gridbagconstraints
                       int gw,   //grid width for gridbagconstraints
                       int gh    //grid height for gridbagconstraints
                      ) {

        //initializing textArea and container pane
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);

        //dark mode
        if(darkmode) {
            //set the dark mode colors
            Color darkBackgroundColor = new Color(30, 30, 30);
            Color darkForegroundColor = new Color(200, 200, 200);
            Color darkSelectionColor = new Color(75, 110, 175);
            Color darkBorderColor = new Color(20, 20, 20);

            //set dark mode colors for specific components
            textArea.setSelectionColor(darkSelectionColor);
            textArea.setSelectedTextColor(darkForegroundColor);
            scrollPane.setBackground(darkBackgroundColor);
            scrollPane.setBorder(new LineBorder(darkBorderColor));
        }

        //sets text as blank
        textArea.setText(t);

        //sets the font and font size for the text
        textArea.setFont(new Font("Courier", Font.PLAIN, 16)); //written with chatGPT

        //sets relevant variables for placing TBox
        fill = f;
        gridx = gx;
        gridy = gy;
        weightx = wx;
        weighty = wy;
        gridwidth = gw;
        gridheight = gh;

        //set some additional properties
        textArea.setEditable(true); // Make the text area editable
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setWrapStyleWord(true); // Wrap lines at word boundaries

    }

    public void appendText(String input) {
        text = text + input;
    }

    public void replaceText(String input) {
        text = input;
        updateBox();
    }

    //updates the content of the text box
    public void updateBox() {
        textArea.setText(text);
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setGBC(GridBagConstraints gbc) {
        //setting variables for the gridbagconstraints
        gbc.fill = fill;
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
    }

    //used for initializing the GUI
    public void addToPane(Container pane, GridBagConstraints gbc) {
        
        //setting variables for the gridbagconstraints
        setGBC(gbc);
        
        //adds the TBox to the inputted pane
        pane.add(scrollPane, gbc);
    }

    //overridden addToPane for non gridbaglayout based panes
    public void addToPane(Container pane) {
        //adds the TBox to the inputted pane
        pane.add(scrollPane);
    }

    //overridden addToPane for non gridbaglayout based panes
    public void addToPane(Container pane, String dir) {
        //adds the TBox to the inputted pane
        pane.add(scrollPane, dir);
    }

    //blank method used for ListTBox
    public ArrayList<String> getTextList() {
        return null;
    }
    
    //blank method used for ListTBox
    public void appendToList(String input) {
    }

    //blank method used for ListTBox
    public void replaceList(ArrayList<String> newList) {
    }

    //blank method used for CharArrayTBox
    public void setCharacterArray(char[][] array) {
    }

    //blank method used for CATBox
    public void stringSetCharArray(String[][] array) {
    }


}

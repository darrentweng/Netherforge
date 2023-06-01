import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

//written in large part by chatGPT. see ListTBox.java for implementation notes. note that chatGPT produced private/public errors. chatGPT also referenced methods that did not exist for their class
//Character Array Text Box
public class CATBox extends GenericTBox {

    //variables that describe the TBox
    private char[][] characterArray;
    private char[][] displayArray;
    private JPanel panel;
    private JTextArea textArea;
    private int fontSize;

    public CATBox(char[][] array,
                  int fill,
                  int gridx,
                  int gridy,
                  double weightx,
                  double weighty,
                  int gridwidth,
                  int gridheight) {

        super("", fill, gridx, gridy, weightx, weighty, gridwidth, gridheight);

        panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();

        //set up the font
        fontSize = 13;
        Font font = new Font("Courier", Font.PLAIN, fontSize);
        textArea.setFont(font);

        //set some additional properties
        textArea.setEditable(false); // Make the text area editable
        textArea.setLineWrap(true); // Enable line wrapping

        panel.add(textArea, BorderLayout.CENTER);

        //sets up the 2D array
        characterArray = array;

        System.out.println("CATBox.main(): setting up chararray with size " + characterArray.length + " x " + characterArray[0].length + " and content: " + arrayToString(characterArray));

        //catches a nullpointerexception with calculateFontSize if characterArray = null
        if(characterArray == null) {
            characterArray = new char[1][1];
        }
        if(displayArray == null) {
            displayArray = new char[4][4];
        }    

        //resize();
        //updateTextFromArray();

        // Add a component listener to the scroll pane
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resize();
                updateTextFromArray();
                System.out.println("CATBox.main(): component resized");
            }
        });

    }

    //centers the characterArray in the displayArray. written in part by chatGPT
    //PREREQUISITE: displayArray must be larger than characterArray. only call this method in resize()
    private void centerArray() {
        
        //store the width and height of the displayArray
        int width = displayArray[0].length;
        int height = displayArray.length;

        //'wipes' the displayArray clear
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                displayArray[i][j] = '.';
            }
        }

        //calculate horizontal and vertical center positions
        int centerX = width / 2;
        int centerY = height / 2;

        //calculate starting positions for placing the input array
        int startX = centerX - (characterArray[0].length / 2);
        int startY = centerY - (characterArray.length / 2);

        //place the input array into the centered array
        int inputY = 0;
        for (int y = startY; y < startY + characterArray.length - 1; y++) {
            int inputX = 0;
            for (int x = startX; x < startX + characterArray[0].length - 1; x++) {
                displayArray[y][x] = characterArray[inputY][inputX];
                inputX++;
            }
            inputY++;
        }

        System.out.println("CATBox.centerArray(): created displayArray with size " + displayArray.length + " x " + displayArray[0].length + " and content: " + arrayToString(displayArray));

    }

    //resizes to fit required number of columns and rows by iterating through font sizes
    public void resize() {

        //finds the minimum requried number of columns and rows for the chararray
        int reqRowNum = characterArray.length;
        int reqColNum = characterArray[0].length;

        //iterates through font sizes to find the one that can hold minimum requried rows & cols
        boolean fontCheck = true;
        int fontSizeCounter = 1;

        while(fontCheck) {
            fontCheck = checkFontSize(fontSizeCounter, reqRowNum, reqColNum);
            fontSizeCounter++;
        }

        //sets the correct font size
        Font correctFont = new Font("Courier", Font.PLAIN, fontSizeCounter-1);
        textArea.setFont(correctFont);

        //updates the displayArray to store the correct amount of spaces
        displayArray = new char[calculateRowNum(correctFont)][calculateColNum(correctFont)];
        System.out.println("CATBox.resize(): displayArray is set to " + calculateRowNum(correctFont) + " x " + calculateColNum(correctFont));

        //calls centerArray(), which centers the charArray in the displayArray
        centerArray();
        updateBox();
    }

    private int calculateRowNum(Font font) {
        FontMetrics fm = getTextArea().getFontMetrics(font);

        int panelHeight = panel.getHeight();
        int textHeight = fm.getAscent();
        
        return panelHeight / textHeight + 1;
    }

    //calculates the maximum number of columns in the char array. only works for fonts w/ uniform spacing
    private int calculateColNum(Font font) {
        FontMetrics fm = getTextArea().getFontMetrics(font);
        
        int panelWidth = panel.getWidth();
        int textWidth = fm.charWidth('.');

        return panelWidth / textWidth + 1;
    }

    //returns true if the inputed font size will allow for storage of the charArray
    //returns false if the displayArray will not have enough space for the charArray
    private boolean checkFontSize(int fontSize, int reqRowNum, int reqColNum) {
        
        System.out.println("CATBox.checkFontSize(): checking pt. " + fontSize + " with " + reqRowNum + " rows and " + reqColNum + " cols");

        Font font = new Font("Courier", Font.PLAIN, fontSize);
        return calculateRowNum(font) >= reqRowNum && calculateColNum(font) >= reqColNum;
    }

    //necessary for board to update CATBox
    public void stringSetCharArray(String[][] array) {
        setCharacterArray(convertToCharArray(array));
    }

    //helper for stringSetCharArray
    private char[][] convertToCharArray(String[][] strings) {
        int numRows = strings.length;
        int numCols = strings[0].length;
        char[][] charArray = new char[numRows][numCols];
    
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                String str = strings[i][j];
                char firstChar = str.charAt(0);
                charArray[i][j] = firstChar;
            }
        }
    
        return charArray;
    }    

    public void setCharacterArray(char[][] array) {
        characterArray = array;
        centerArray();
        updateTextFromArray();
    }

    //updates the text of the TBox from the displayArray
    private void updateTextFromArray() {
        StringBuilder builder = new StringBuilder();

        for (char[] row : displayArray) {
            for (char ch : row) {
                builder.append(ch);
            }
            builder.append("\n");
        }

        System.out.println("CATBox.updateTextFromArray: replacing textArea with: \n" + builder.toString());

        textArea.setText(builder.toString());
        updateBox();
    }

    public String arrayToString(char[][] charArray) {
        StringBuilder stringBuilder = new StringBuilder();

        for (char[] row : charArray) {
            for (char c : row) {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public void addToPane(Container pane, GridBagConstraints gbc) {
        
        //setting variables for the gridbagconstraints
        setGBC(gbc);
        
        //adds the TBox to the inputted pane
        pane.add(panel, gbc);
    }

    @Override
    public void updateBox() {
        super.updateBox();
        panel.revalidate();
        panel.repaint();
    }

}
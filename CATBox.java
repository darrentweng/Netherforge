import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

//written in large part by chatGPT. see ListTBox.java for implementation notes. note that chatGPT produced private/public errors. chatGPT also referenced methods that did not exist for their class
public class CATBox extends GenericTBox {

    //variables that describe the TBox
    private char[][] characterArray;

    public CATBox(char[][] array,
                  int fill,
                  int gridx,
                  int gridy,
                  double weightx,
                  double weighty,
                  int gridwidth,
                  int gridheight) {

        super("", fill, gridx, gridy, weightx, weighty, gridwidth, gridheight);

        //sets up the 2D array
        characterArray = array;

        //catches a nullpointerexception with calculateFontSize if characterArray = null
        if(characterArray == null) {
            characterArray = new char[1][1];
        }

        updateTextFromArray();

        //set some additional properties
        getTextArea().setEditable(false); // Make the text area uneditable
        getScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //getScrollPane().setHorizontalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Add a component listener to the scroll pane
        getScrollPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateTextFromArray();
            }
        });

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
        updateTextFromArray();
    }

    private void updateTextFromArray() {
        StringBuilder builder = new StringBuilder();

        int containerWidth = getScrollPane().getViewport().getWidth();
        int containerHeight = getScrollPane().getViewport().getHeight();

        int fontSize = calculateFontSize(containerWidth, containerHeight);
        Font font = new Font("Courier", Font.PLAIN, fontSize);
        getTextArea().setFont(font);

        for (char[] row : characterArray) {
            for (char ch : row) {
                builder.append(ch);
            }
            builder.append("\n");
        }

        getTextArea().setText(builder.toString());
    }

    private int calculateFontSize(int containerWidth, int containerHeight) {
        int maxWidth = containerWidth / characterArray[0].length * 96/72;
        int maxHeight = containerHeight / characterArray.length * 96/72;

        return Math.min(maxWidth, maxHeight);
    }

}
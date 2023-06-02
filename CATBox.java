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
    JEditorPane editorPane;

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

        //editorPane = new JEditorPane();
        //editorPane.setContentType("text/html");

        //set up the font
        fontSize = 13;
        Font font = new Font("Courier", Font.PLAIN, fontSize);
        textArea.setFont(font);
        //editorPane.setFont(font);

        //set some additional properties
        textArea.setEditable(false); // Make the text area editable
        textArea.setLineWrap(true); // Enable line wrapping
        textArea.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        textArea.setAlignmentY(JTextArea.CENTER_ALIGNMENT);

        panel.add(textArea, BorderLayout.CENTER);
        //editorPane.setEditable(false);
        //panel.add(editorPane, BorderLayout.CENTER);

        //sets up the 2D array
        characterArray = array;

        System.out.println("CATBox.main(): setting up chararray with size " + characterArray.length + " x " + characterArray[0].length + " and content: " + arrayToString(characterArray));

        //catches a nullpointerexception with calculateFontSize if characterArray = null
        if(characterArray == null) {
            characterArray = new char[1][1];
        }
        if(displayArray == null) {
            displayArray = new char[6][6];
        }    

        // Add a component listener to the scroll pane
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resize();
                updateTextFromArray();
                //Font font = new Font("Courier", Font.PLAIN, 400);
                //editorPane.setFont(font);
                //editorPane.setText(formatHTML(displayArray));
                //updateBox();
                System.out.println("CATBox.main(): component resized");
            }
        });

    }

    public JPanel getPanel() {
        return panel;
    }

    //centers the characterArray in the displayArray. written in part by chatGPT
    //PREREQUISITE: displayArray must be larger than characterArray. only call this method in resize()
    private void centerArray() {
        
        //store the width and height of the charArray
        int widthA = characterArray[0].length;
        int heightA = characterArray.length;

        //store the width and height of the displayArray
        int widthB = displayArray[0].length;
        int heightB = displayArray.length;


        //'wipes' the displayArray clear
        for(int i=0; i<heightB; i++) {
            for(int j=0; j<widthB; j++) {
                displayArray[i][j] = ' ';
            }
        }

        //calculate starting positions for the centered array
        int startX = (widthB - widthA) / 2 + 1;
        int startY = (heightB - heightA) / 2 + 1;

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

        fontSizeCounter--;

        //sets the correct font size
        Font correctFont = new Font("Courier", Font.PLAIN, fontSizeCounter);
        textArea.setFont(correctFont);
        //editorPane.setFont(correctFont);
        System.out.println("CATBox.resize(): set font size to: " + fontSizeCounter);

        //updates the displayArray to store the correct amount of spaces
        displayArray = new char[calculateRowNum(correctFont)][calculateColNum(correctFont)];
        System.out.println("CATBox.resize(): displayArray is set to " + calculateRowNum(correctFont) + " x " + calculateColNum(correctFont));

        //calls centerArray(), which centers the charArray in the displayArray
        centerArray();
        updateBox();
    }

    private int calculateRowNum(Font font) {
        FontMetrics fm = getTextArea().getFontMetrics(font);

        //double panelHeight = Main.getFrameHeight()*0.8;
        double panelHeight = Main.getFrameHeight()*0.75;
        double textHeight = fm.getHeight();
        
        return (int)(panelHeight / textHeight + 1);
    }

    //calculates the maximum number of columns in the char array. only works for fonts w/ uniform spacing
    private int calculateColNum(Font font) {
        FontMetrics fm = getTextArea().getFontMetrics(font);
        
        double panelWidth = Main.getFrameWidth()*1/3;
        double textWidth = fm.charWidth('0') * 1.2;

        return (int)(panelWidth / textWidth + 1);
    }

    //returns true if the inputed font size will allow for storage of the charArray
    //returns false if the displayArray will not have enough space for the charArray
    private boolean checkFontSize(int fontSize, int reqRowNum, int reqColNum) {
        
        System.out.println("CATBox.checkFontSize(): checking pt. " + fontSize + " with " + reqRowNum + " rows and " + reqColNum + " cols");

        Font font = new Font("Courier", Font.PLAIN, fontSize);
        return calculateRowNum(font) > reqRowNum && calculateColNum(font) > reqColNum;
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
        int numRows = displayArray.length;

        for (int i = 0; i < numRows; i++) {
            char[] row = displayArray[i];
            int numCols = row.length;

            for (int j = 0; j < numCols; j++) {
                char ch = row[j];
                builder.append(ch);
            }

            if (i < numRows - 1) {
                builder.append("\n");
            }
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
    public void addToPane(Container pane) {
        //adds the TBox to the inputted pane
        pane.add(panel);
    }

    @Override
    public void updateBox() {
        super.updateBox();
        panel.revalidate();
        panel.repaint();
    }

    private String formatHTML(char[][] paragraph) {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<style>\n");
        html.append("body { text-align: center; }\n");
        html.append("</style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<p>\n");

        int numRows = paragraph.length;
        for (int i = 0; i < numRows; i++) {
            char[] row = paragraph[i];
            int numCols = row.length;
            for (int j = 0; j < numCols; j++) {
                html.append(row[j]);
            }
            if (i != numRows - 1) {
                html.append("<br>\n");
            }
        }

        html.append("</p>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }
}
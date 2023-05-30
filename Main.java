import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    //does what it says on the tin. written by chatGPT
    private static void setDarkModeColors() {
        // Set the dark mode colors
        Color darkBackgroundColor = new Color(30, 30, 30);
        Color darkForegroundColor = new Color(200, 200, 200);
        Color darkSelectionColor = new Color(75, 110, 175);

        // Set the dark mode colors for specific Swing components
        UIManager.put("Panel.background", darkBackgroundColor);
        UIManager.put("Label.foreground", darkForegroundColor);
        UIManager.put("TextArea.background", darkBackgroundColor);
        UIManager.put("TextArea.foreground", darkForegroundColor);
        UIManager.put("TextArea.selectionBackground", darkSelectionColor);
        UIManager.put("TextArea.selectionForeground", darkForegroundColor);
        UIManager.put("Viewport.background", darkBackgroundColor);
        UIManager.put("Viewport.foreground", darkForegroundColor);
        UIManager.put("ScrollPane.background", darkBackgroundColor);
        UIManager.put("ScrollPane.foreground", darkForegroundColor);
        UIManager.put("ScrollBar.background", darkBackgroundColor);
        UIManager.put("ScrollBar.foreground", darkForegroundColor);
        UIManager.put("ScrollBar.thumb", darkForegroundColor);
        UIManager.put("ScrollBar.track", darkBackgroundColor);
    }
    
    private static void createAndShowGUI(ArrayList<GenericTBox> boxList) {

        //create and set up the window.
        JFrame frame = new JFrame("GridBagLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set up the content pane.
        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        //add TBoxes to the content pane
        for(int i=0; i<boxList.size(); i++) {
            boxList.get(i).addToPane(frame.getContentPane(), gbc);
        }

        //display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        
        
        boolean darkmode = false;

        if(darkmode) {
            setDarkModeColors();
        }    

        //this needs to be simplified
        GenericTBox center, right, bottom;
        ListTBox left;

        left = new ListTBox(null, GridBagConstraints.BOTH, 0, 0, 0.15, 0.3, 1, 1);
        center = new CATBox(null, GridBagConstraints.BOTH, 1, 0, 0.3, 0.3, 1, 1);
        right = new GenericTBox(null, GridBagConstraints.BOTH, 2, 0, 0.15, 0.3, 1, 1); 
        bottom = new ListTBox(null, GridBagConstraints.BOTH, 0, 1, 0, 0.05, 3, 1);

        ArrayList<GenericTBox> boxList = new ArrayList<GenericTBox>();

        boxList.add(left);
        boxList.add(center);
        boxList.add(right);
        boxList.add(bottom);

        SwingUtilities.invokeLater(() -> createAndShowGUI(boxList));    
        
        Scanner UI = new Scanner(System.in);
        Player p = new Player(0, 0);
        Board b = new Board(10,10,5,5, p);
        b.setBlock(1, 0, new Block());
        b.setBlock(3, 0, new Block());
        b.setMob(5,1, new Mob(5,1));
        //stores most recent user input
        b.printMob();
        String recentInput;
        System.out.println();
        System.out.println();
        b.printCoords();
        b.drawBoard();

        while(true) {
            recentInput = UI.nextLine();
            p.input(recentInput, b);
            System.out.println();
            System.out.println();
            b.printCoords();
            b.drawBoard();
            

        }


        
    }
}

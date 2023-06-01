import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    static Player p;
    static Board b;
    static ArrayList<GenericTBox> boxList;


    //does what it says on the tin. written by chatGPT. get rid of darkmode??
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
    /*
    private static void createAndShowGUI(ArrayList<GenericTBox> boxList) {

        //create and set up the window.
        JFrame frame = new JFrame("Netherforge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));

        //set up the content pane.
        frame.getContentPane().setLayout(new GridLayout(2, 1));
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(1, 0));
        //upperPanel.setPreferredSize(new Dimension((int)frame.getWidth(), (int)(frame.getHeight()*0.8)));
        upperPanel.setPreferredSize(new Dimension(100, 100));

        JPanel lowerPanel = new JPanel(new GridLayout(1, 0));
        lowerPanel.setPreferredSize(new Dimension((int)frame.getWidth(), (int)(frame.getHeight()*0.2)));

        //add TBoxes to the content pane (this is sloppy and will break if boxList is changed)
        for(int i=0; i<3; i++) {
            boxList.get(i).addToPane(upperPanel);
        }

        boxList.get(3).addToPane(lowerPanel);

        frame.getContentPane().add(upperPanel, BorderLayout.NORTH);
        //frame.getContentPane().add(new JSeparator(), BorderLayout.CENTER);
        frame.getContentPane().add(lowerPanel, BorderLayout.SOUTH);

        //display the window.
        frame.pack();
        frame.setVisible(true);
    }*/

    private static void createAndShowGUI(ArrayList<GenericTBox> boxList) {
        // Create and set up the window.
        JFrame frame = new JFrame("Netherforge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));
        //frame.setResizable(false);
    
        // Set up the content pane.
        frame.getContentPane().setLayout(new GridLayout(2, 1));
    
        // Create the upper panel and set its layout.
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(1, 3));
        upperPanel.setPreferredSize(new Dimension((int)frame.getWidth(), (int)(frame.getHeight()*0.8)));
    
        // Add TBoxes to the upper panel.
        for(int i=0; i<3; i++) {
            boxList.get(i).addToPane(upperPanel);
        }
    
        // Create the lower panel.
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.setPreferredSize(new Dimension((int)frame.getWidth(), (int)(frame.getHeight()*0.2)));
    
        // Add boxList(3) to the lower panel.
        boxList.get(3).addToPane(lowerPanel);
    
        // Add the panels to the content pane.
        frame.getContentPane().add(upperPanel, BorderLayout.NORTH);
        frame.getContentPane().add(lowerPanel, BorderLayout.CENTER);
    
        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    

    public static void main(String[] args) {
        
        boolean darkmode = false;

        if(darkmode) {
            setDarkModeColors();
        }    

        GenericTBox center, right, bottom;
        ListTBox left;

        char[][] test = {
            {'H', 'e', 'l', 'l', 'o'},
            {'W', 'o', 'r', 'l', 'd'}
        };

        left = new ListTBox(null, GridBagConstraints.BOTH, 0, 0, 0.15, 0.3, 1, 1);
        center = new CATBox(test, GridBagConstraints.BOTH, 1, 0, 0.3, 0.3, 1, 1);
        right = new GenericTBox(null, GridBagConstraints.BOTH, 2, 0, 0.15, 0.3, 1, 1); 
        bottom = new ConsoleTBox(null, GridBagConstraints.BOTH, 0, 1, 0, 0.05, 3, 1);

        boxList = new ArrayList<GenericTBox>();

        boxList.add(left);
        boxList.add(center);
        boxList.add(right);
        boxList.add(bottom);

        SwingUtilities.invokeLater(() -> createAndShowGUI(boxList));    
        
        Scanner UI = new Scanner(System.in);
        p = new Player(0, 0);
        b = new Board(10,10,5,5, p);
        b.setBlock(1, 0, new Block());
        b.setBlock(3, 0, new Block());
        b.setMob(5,1, new Mob(5,1));
        //stores most recent user input
        //b.printMob();
        //b.printCoords();
        boxList.get(1).stringSetCharArray(b.drawBoard());
        
    }

    //updates all systems. called by ConsoleTBox when cmd is inputed by user
    public static void update(String input) {
        p.input(input, b);
        b.printCoords();
        //System.out.print(b.drawBoard());
        boxList.get(1).stringSetCharArray(b.drawBoard());

    }

    

}

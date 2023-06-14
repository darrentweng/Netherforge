import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {

    static Player p;
    static Board b;
    static ArrayList<GenericTBox> boxList;
    static JFrame frame;
    static Inventory i;

    public static int getFrameWidth() {
        return frame.getWidth();
    }

    public static int getFrameHeight() {
        return frame.getHeight();
    }

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
    
    private static void createAndShowGUI(ArrayList<GenericTBox> boxList) {
        //create and set up the window
        frame = new JFrame("Netherforge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null);
    
        //create a main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        //JScrollPane container = new JScrollPane(mainPanel);
        GridBagConstraints gbc = new GridBagConstraints();

        //create panel1 and set its constraints
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 3));
    
        //add TBoxes to the upper panel.
        for(int i=0; i<3; i++) {
            boxList.get(i).addToPane(panel1);
        }

        //add panel1 and set its constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 6;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.PAGE_START;
        mainPanel.add(panel1, gbc);

        //listens for resizing of window and fixes panel sizes for left, middle, right
        frame.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int width = frame.getWidth();
                int height = panel1.getHeight();

                boxList.get(0).getScrollPane().setPreferredSize(new Dimension((int)(width*0.25), height));
                boxList.get(1).getPanel().setPreferredSize(new Dimension((int)(width*0.5), height));
                boxList.get(2).getScrollPane().setPreferredSize(new Dimension((int)(width*0.25), height));
            }
        });

        //create panel2
        JPanel panel2 = new JPanel(new GridBagLayout());

        //add boxList(3) to the lower panel.
        boxList.get(3).addToPane(panel2, gbc);

        //add panel2 and set its constraints
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.weighty = 0.25;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.anchor = GridBagConstraints.PAGE_START;
        mainPanel.add(panel2, gbc);

        //frame.getContentPane().add(container);
        frame.getContentPane().add(mainPanel);

        //display the window.
        frame.pack();
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        
        boolean darkmode = false;
        // GPT.main();
        if(darkmode) {
            setDarkModeColors();
        }    

        GenericTBox center, right, bottom;
        ListTBox left;

        char[][] test = {
            {'H', 'e', 'l', 'l', 'o'},
            {'W', 'o', 'r', 'l', 'd'}
        };

        left = new ListTBox(null, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0, 1, 1, 16);
        center = new CATBox(test, GridBagConstraints.HORIZONTAL, 1, 0, 1, 0, 1, 1);
        right = new ListTBox("test", GridBagConstraints.HORIZONTAL, 2, 0, 0, 0, 1, 1, 12); 
        bottom = new ConsoleTBox(null, GridBagConstraints.BOTH, 0, 0, 1, 1, 1, 1);

        boxList = new ArrayList<GenericTBox>();

        boxList.add(left);
        boxList.add(center);
        boxList.add(right);
        boxList.add(bottom);

        i = new Inventory(boxList.get(0));

        SwingUtilities.invokeLater(() -> createAndShowGUI(boxList));    
        
        Scanner UI = new Scanner(System.in);
        p = new Player(1, 1);
        b = new Board(16,25, 2, p);
        b.buildMapFromFile();
        b.setBlock(1, 0, new Block());
        b.setBlock(3, 0, new Block());
        b.setMob(21,9, new Mob(21,9));
        boxList.get(1).stringSetCharArray(b.drawBoard());

        boxList.get(2).appendToList("With a ringing headache, you awaken in a dimly lit dungeon, your eyes struggling to adjust to the darkness. Cold and damp, the stone walls are adorned with ancient runes, whispering secrets of forgotten ages. The stagnant air carries a scent of decay, mingled with the metallic tang of blood. Lost and disoriented, you have no recollection of how you arrived here.");
        boxList.get(2).appendToList("You see a flickering light to your southeast. Perhaps a torch you could use to better see your surroundings?");

    }

    //updates all systems. called by ConsoleTBox when cmd is inputed by user
    public static void update(String input) {
        p.input(input, b, i);
        b.printCoords();
        //System.out.print(b.drawBoard());
        boxList.get(1).stringSetCharArray(b.drawBoard());

    }

    

}

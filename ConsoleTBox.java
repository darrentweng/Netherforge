import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ConsoleTBox extends GenericTBox implements KeyListener {

    private ArrayList<String> commandHistory;
    private int historyIndex;

    public ConsoleTBox(String t, int f, int gx, int gy, double wx, double wy, int gw, int gh) {
        super(t, f, gx, gy, wx, wy, gw, gh);
        commandHistory = new ArrayList<>();
        historyIndex = 0;
        getTextArea().addKeyListener(this);
    }

    @Override
    public void addToPane(Container pane, GridBagConstraints gbc) {
        super.addToPane(pane, gbc);
        getTextArea().requestFocus(); // Set focus on the text area when added to the pane
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            e.consume(); // Consume the enter key event to prevent a line break in the text area
            String command = getTextArea().getText().trim();
            processCommand(command);
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            e.consume(); // Consume the up arrow key event to prevent cursor movement in the text area
            showPreviousCommand();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            e.consume(); // Consume the down arrow key event to prevent cursor movement in the text area
            showNextCommand();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void processCommand(String command) {
        if (!command.isEmpty()) {
            commandHistory.add(command);
            // Do something with the command (e.g., execute it)
            Main.update(command);
            // Replace the text area content with a blank string
            replaceText("");
        }
    }

    private void showPreviousCommand() {
        if (historyIndex > 0) {
            historyIndex--;
            replaceText(commandHistory.get(historyIndex));
        }
    }

    private void showNextCommand() {
        if (historyIndex < commandHistory.size() - 1) {
            historyIndex++;
            replaceText(commandHistory.get(historyIndex));
        } else if (historyIndex == commandHistory.size() - 1) {
            historyIndex++;
            replaceText(""); // Clear the text area when reaching the end of history
        }
    }

}

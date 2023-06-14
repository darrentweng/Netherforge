import java.util.*;

public class Player {
    private int x;
    private int y;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void move(int dx, int dy, Board board) {
        int newPlayerX = x + dx;
        int newPlayerY = y + dy;
        if (board.isValidMove(newPlayerX, newPlayerY)) {
            x = newPlayerX;
            y = newPlayerY;
        }
        else {
            System.out.println("Block or mob is in way");
        }
    }

    public int getPlayerX() {
        return x;
    }
    public int getPlayerY() {
        return y;
    }
    public void setPlayerX(int _x) {
        x = _x;
    }
    public void setPlayerY(int _y) {
        y = _y;
    }
    // Additional methods for player actions and attributes

    //takes player input, returns false if found no applicable inputs
    public boolean input(String _i, Board board, Inventory inventory) {
        System.out.println("Player.input(): player command recieved: " + _i);
        
        switch(_i) {
            case "w":
                move(0,-1, board);
                break;
            case "a":
                move(-1,0, board);
                break;
            case "s":
                move(0,1, board);
                break;
            case "d":
                move(1,0, board);
                break;
            case "give torch":
                inventory.addItem(new Torch());
                break;
            case "use 1":
                inventory.getItems().get(0).use(board);
            default:
                return false;
        }

        return true;

    }

}

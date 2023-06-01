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
            System.out.println("강남스타일");
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
    public boolean input(String _i, Board board) {
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
            default:
                return false;
        }

        return true;

    }

}

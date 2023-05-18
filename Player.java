public class Player {
    private int x;
    private int y;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void move(int dx, int dy, Board board) {
        int newPlayerX = x + dx;
        int newPlayerY = y + dy;
        
        // Check if the new player position is within the bounds of the board
        if (board.isValidMove(newPlayerX, newPlayerY)) {
            // Set the current player position as an empty tile on the board
            board.setTile(x, y, null);
            
            // Update the player position
            x = newPlayerX;
            y = newPlayerY;
            
            // Set the new player position on the board
            board.setTile(x, y, this);
        }
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

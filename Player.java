public class Player {
    private int x;
    private int y;
    
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void move(String dir) {
        // Code to move the player
    }
    
    // Additional methods for player actions and attributes

    //takes player input, returns false if found no applicable inputs
    public boolean input(String _i) {
        switch(_i) {
            case "w":
                move("north");
                break;
            case "a":
                move("west");
                break;
            case "s":
                move("south");
                break;
            case "d":
                move("west");
                break;
            default:
                return false;
        }

        return true;

    }

}

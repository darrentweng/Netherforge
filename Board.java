public class Board {
    private Object[][] visibleGrid;
    private Object[][] mapGrid;
    private int visibleWidth;
    private int visibleHeight;
    private int playerX;
    private int playerY;
    
    public Board(int mapWidth, int mapHeight, int visibleWidth, int visibleHeight) {
        this.visibleWidth = visibleWidth;
        this.visibleHeight = visibleHeight;
        visibleGrid = new Object[visibleHeight][visibleWidth];
        mapGrid = new Object[mapHeight][mapWidth];
    }
    
    public void initializeBoard() {
        // Code to initialize the map grid and visible grid
    }
    
    public void drawBoard() {
        int startX = playerX - visibleWidth / 2;
        int startY = playerY - visibleHeight / 2;
        
        // Adjust startX and startY if they are out of bounds
        if (startX < 0) {
            startX = 0;
        } else if (startX + visibleWidth > mapGrid[0].length) {
            startX = mapGrid[0].length - visibleWidth;
        }
        
        if (startY < 0) {
            startY = 0;
        } else if (startY + visibleHeight > mapGrid.length) {
            startY = mapGrid.length - visibleHeight;
        }
        
        // Copy the relevant portion of the mapGrid to visibleGrid
        for (int row = 0; row < visibleHeight; row++) {
            for (int col = 0; col < visibleWidth; col++) {
                visibleGrid[row][col] = mapGrid[startY + row][startX + col];
            }
        }
        
        // Code to draw the visible grid
        for (int row = 0; row < visibleGrid.length; row++) {
            for (int col = 0; col < visibleGrid[row].length; col++) {
                if (visibleGrid[row][col] == null) {
                    System.out.print(".");
                }
                else {
                    System.out.print(visibleGrid[row][col]);
                }
                
                
            }
            System.out.println();
        }
    }
    
    // Additional methods for managing the board
    public void movePlayerX(int dx) {
        int newPlayerX = playerX + dx;
        
        // Check if the new playerX is within the bounds of the mapGrid
        if (newPlayerX >= 0 && newPlayerX < mapGrid[0].length) {
            playerX = newPlayerX;
        }
    }
    
    public void movePlayerY(int dy) {
        int newPlayerY = playerY + dy;
        
        // Check if the new playerY is within the bounds of the mapGrid
        if (newPlayerY >= 0 && newPlayerY < mapGrid.length) {
            playerY = newPlayerY;
        }
    }
    public void setTile(int x, int y, Object tile) {
        // Check if the given coordinates are within the bounds of the mapGrid
        if (x >= 0 && x < mapGrid[0].length && y >= 0 && y < mapGrid.length) {
            mapGrid[y][x] = tile;
        }
    }
    public boolean isValidMove(int x, int y) {
        // Check if the given coordinates are within the bounds of the mapGrid
        if (x >= 0 && x < mapGrid[0].length && y >= 0 && y < mapGrid.length) {
            Object tile = mapGrid[y][x];
            return !(tile instanceof Block || tile instanceof Mob);
        }
        return false;
    }

}

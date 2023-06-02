public class Board {
    private String[][] visibleGrid;
    private Block[][] mapGrid;
    private Item[][] itemGrid;
    private Mob[][] mobGrid;
    //private int visibleWidth;
    //private int visibleHeight;
    private int visibleRadius;
    private Player player;

    //note the replaced visibleWidth and visibleHeight with visibleRadius
    public Board(int mapWidth, int mapHeight, int visibleRadius, Player player) {
        //this.visibleWidth = visibleWidth;
        //this.visibleHeight = visibleHeight;
        this.visibleRadius = visibleRadius;
        this.player = player;
        visibleGrid = new String[visibleRadius * 2 + 1][visibleRadius * 2 + 1];
        System.out.println("Board.Board(): visibleGrid established as " + visibleGrid.length + " x " + visibleGrid[0].length);
        mapGrid = new Block[mapHeight][mapWidth];
        itemGrid = new Item[mapHeight][mapWidth];
        mobGrid = new Mob[mapHeight][mapWidth];
    }
    
    public void initializeBoard() {
        // Code to initialize the map grid and visible grid
    }
    
    public void printCoords() {
        System.out.println("x: " + player.getPlayerX() + ",     y: " + player.getPlayerY());
    }
    
    public String[][] drawBoard() {
        int playerX = player.getPlayerX();
        int playerY = player.getPlayerY();
        int startX = playerX - visibleRadius;
        int startY = playerY - visibleRadius;
        int endX = playerX + visibleRadius;
        int endY = playerY + visibleRadius;
        
        // Adjust startX and startY if they are out of bounds
        if (startX < 0) {
            startX = 0;
        } else if (endX > mapGrid[0].length) {
            startX = mapGrid[0].length - (2 * visibleRadius);
        }
        
        if (startY < 0) {
            startY = 0;
        } else if (endY > mapGrid.length) {
            startY = mapGrid.length - (2 * visibleRadius);
        }
        
        // Copy the relevant portion of the mapGrid to visibleGrid
        for (int row = 0; row < (2 * visibleRadius + 1); row++) {
            for (int col = 0; col < (2 * visibleRadius + 1); col++) {
                
                //copies player
                if (playerX == startX + col && playerY == startY + row) {
                    visibleGrid[row][col] = "@";
                }
                //catches out of bounds, replaces with a " "
                else if (startY + row < 0 || startY + row >= mapGrid.length || startX + col < 0 || startX + col >= mapGrid[0].length) {
                    visibleGrid[row][col] = " ";
                }
                //copies mobs, items, walls, ect.
                else if (mobGrid[startY + row][startX + col] != null) {
                    visibleGrid[row][col] = mobGrid[startY + row][startX + col].toString();
                }
                else if (itemGrid[startY + row][startX + col] != null) {
                    visibleGrid[row][col] = itemGrid[startY + row][startX + col].toString();
                }
                else if (mapGrid[startY + row][startX + col] != null) {
                    visibleGrid[row][col] = mapGrid[startY + row][startX + col].toString();
            }
                else {
                    visibleGrid[row][col] = ".";
                }
        }
    }
        
        System.out.println("Board.drawBoard(): visibleGrid with content: \n" + arrayToString(visibleGrid));
        return visibleGrid;
        /*
        // Code to draw the visible grid
        for (int row = 0; row < visibleGrid.length; row++) {
            for (int col = 0; col < visibleGrid[row].length; col++) {
                 if (row == player.getPlayerY() - startY && col == player.getPlayerX() - startX) {
                    System.out.print("@");
                }
                else if (mobGrid[row-startY][col-startX] != null) {

                    System.out.print("a");
                }
                else if (itemGrid[row-startY][col-startX] != null) {
                    System.out.print(itemGrid[row-startY][col-startX]);
                }
                else if (visibleGrid[startY][startX] == null) {
                    System.out.print(".");
                }
                
                //else {
                    System.out.print(visibleGrid[row][col]);
                //}
                
                
            }
            System.out.println();
        }
        */
    }
    
    public void setBlock(int x, int y, Block tile) {
        // Check if the given coordinates are within the bounds of the mapGrid
        if (x >= 0 && x < mapGrid[0].length && y >= 0 && y < mapGrid.length) {
            mapGrid[y][x] = tile;
        }
    }
    public void setItem(int x, int y, Item tile) {
        // Check if the given coordinates are within the bounds of the mapGrid
        if (x >= 0 && x < mapGrid[0].length && y >= 0 && y < mapGrid.length) {
            itemGrid[y][x] = tile;
        }
    }
    public void setMob(int x, int y, Mob tile) {
        // Check if the given coordinates are within the bounds of the mapGrid
        if (x >= 0 && x < mapGrid[0].length && y >= 0 && y < mapGrid.length) {
            mobGrid[y][x] = tile;
        }
    }
    public boolean isValidMove(int x, int y) {
        // Check if the given coordinates are within the bounds of the mapGrid
        if (x >= 0 && x < mapGrid[0].length && y >= 0 && y < mapGrid.length) {
            Block tile = mapGrid[y][x];
            Object tileMob = mobGrid[y][x];
            return !(tile instanceof Block || tileMob instanceof Mob);
        }
        return false;
    }
    public void printMob() {
        for (int row = 0; row < mobGrid.length; row++) {
            for (int col = 0; col < mobGrid[row].length; col++) {
                if (mobGrid[row][col] != null) {
                    System.out.print(mobGrid[row][col]);
                    //System.out.println("x: " + row + ",     y: " + col);
                }
                else {
                    System.out.print(".");
                }
            }
            System.out.println();
            }
    }

    public String arrayToString(String[][] stringArray) {
        StringBuilder builder = new StringBuilder();

        int numRows = stringArray.length;

        for (int i = 0; i < numRows; i++) {
            String[] row = stringArray[i];
            int numCols = row.length;

            for (int j = 0; j < numCols; j++) {
                String ch = row[j];
                builder.append(ch);
            }

            if (i < numRows - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();

    }
}
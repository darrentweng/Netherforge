import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner UI = new Scanner(System.in);
        Board b = new Board(10,10,3,3);
        b.drawBoard();
        b.setTile(0, 0, new Block());
        b.setTile(3, 0, new Block());
        System.out.println();
        b.drawBoard();
        b.movePlayerX(3);
        System.out.println();
        b.drawBoard();
        //stores most recent user input
        /* 
        String recentInput;

        while(true) {
            recentInput = UI.nextLine();
            

        }
*/

        
    }
}

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner UI = new Scanner(System.in);
        Player p = new Player(0, 0);
        Board b = new Board(10,10,5,5, p);
        b.setBlock(1, 0, new Block());
        b.setBlock(3, 0, new Block());
        b.setMob(5,1, new Mob(5,1));
        //stores most recent user input
        b.printMob();
        String recentInput;
        System.out.println();
        System.out.println();
        b.printCoords();
        b.drawBoard();

        while(true) {
            recentInput = UI.nextLine();
            p.input(recentInput, b);
            System.out.println();
            System.out.println();
            b.printCoords();
            b.drawBoard();
            

        }


        
    }
}

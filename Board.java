import javax.swing.*;
import java.awt.*;

//make some nice comments

public class Board {

    public int rows = 8;
    public int columns = 8;
    
    public static void main(String[] arg) { 
        Board board = new Board(8, 8);
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setTitle("Hello world!"); 
        frame.setSize(900,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();

        frame.setContentPane(panel);

        Square[] spaces = new Square[64];
        gbc.ipadx = -32;
        gbc.ipady = -8;
        for(int i = 0; i < 64; i++) {  
                gbc.gridy = (i / 8);
                gbc.gridx = (i % 8);
                spaces[i] = new Square(board, panel, gbc);
        }
    }

    Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
}
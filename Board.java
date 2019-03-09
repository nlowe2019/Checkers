import javax.swing.*;
import java.awt.*;

public class Board {

    public int size;
    public static void main(String[] arg) { 

        Board board = new Board(8); 
        JFrame frame = new JFrame(); //frame setup
        frame.setTitle("Draughts"); 
        frame.setSize(870,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout()); //uses gridbaglayout
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setContentPane(panel);

        Square[] spaces = new Square[(int)Math.pow(board.size, 2)]; //creates array of squares
        gbc.ipadx = -32; //remove button padding
        gbc.ipady = -8;

        for(int i = 0; i < (int)Math.pow(board.size, 2); i++) {  
                gbc.gridy = (i / board.size); //calcs square y coordinate
                gbc.gridx = (i % board.size); //calcs square x coordinate
                spaces[i] = new Square(board, panel, gbc, board.size/2);
        }

        frame.setVisible(true);
    }

    Board(int size) {
        this.size = size;
    }
}
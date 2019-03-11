import javax.swing.*;
import java.awt.*;

public class Board {

    private int size;
    private boolean whiteTurn = true;
    private int source;
    private int dest;
    private boolean selected;
    private Square sourceSquare;
    JButton[] btn;
    Square[] spaces;
    public static void main(String[] arg) { 

        Board board = new Board(8);
    }

    Board(int size) {
        this.size = size; //sets board size
        JFrame frame = new JFrame(); //frame setup
        frame.setTitle("Draughts"); 
        frame.setSize(870,900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridBagLayout()); //uses gridbaglayout
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setContentPane(panel);

        spaces = new Square[(int)Math.pow(this.size, 2)]; //creates array of squares
        btn = new JButton[(int)Math.pow(this.size, 2)];

        gbc.ipadx = -32; //remove button padding
        gbc.ipady = -8;
        
        for(int i = 0; i < (int)Math.pow(this.size, 2); i++) {  
                gbc.gridy = (i / this.size); //calcs square y coordinate
                gbc.gridx = (i % this.size); //calcs square x coordinate
                Square s = new Square(this, panel, gbc, this.size/2);
                spaces[i] = s;
        }

        frame.setVisible(true);
    }


    //accessors and mutators...
    public boolean getTurn() {
        return whiteTurn;
    }

    public void setTurn(boolean b) {
        whiteTurn = b;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean b) {
        selected = b;
    }

    public int getSize() {
        return size;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int s) {
        source = s;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int d) {
        dest = d;
    }

    public Square getSourceSquare() {
        return sourceSquare;
    }

    public void setSourceSquare(Square s) {
        sourceSquare = s;
    }
}
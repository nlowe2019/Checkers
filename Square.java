import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Square implements ActionListener{

    private int xPos;
    private int yPos;
    private String contents = "NONE";
    private Board b;

    //images
    static ImageIcon emptyw = new ImageIcon("empty.png");
    static ImageIcon emptyb = new ImageIcon("empty2.png");
    static ImageIcon valid = new ImageIcon("selected.png");
    static ImageIcon RED = new ImageIcon("red.png");
    static ImageIcon WHITE = new ImageIcon("white.png");
    static ImageIcon REDKING = new ImageIcon("red-king.png");
    static ImageIcon WHITEKING = new ImageIcon("white-king.png");

    Square(Board board, JPanel p, GridBagConstraints gbc) {

        b = board;
        xPos = gbc.gridx; //stores squares position on grid
        yPos = gbc.gridy;
        int i = ((yPos * b.getSize()) + xPos);
        b.btn[i] = new JButton();

        if(xPos % 2 == yPos % 2)
            b.btn[i].setIcon(emptyb);
        else
            if(yPos < (b.getSize()/2)-1) {
                b.btn[i].setIcon(RED);
                contents = "RED";
            }
            else if((b.getSize() % 2 == 0 && yPos > b.getSize()/2) || (b.getSize() % 2 == 1 && yPos > b.getSize()/2+1)) {
                b.btn[i].setIcon(WHITE);
                contents = "WHITE";
            }
            else
                b.btn[i].setIcon(emptyw); 

        b.btn[i].addActionListener(this);
        p.add(b.btn[i], gbc);
    }

    //Action Listener 
    public void actionPerformed(ActionEvent e) {
        if (b.getSelected() == false) {
            select(this);
        }
        else {
            moveTo(this);
        }
    }

//- - - - - - - - - - - - - - - - - - - - - - - FUNctions - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void select(Square s) {
        if(s.contents.matches("R(.*)") && b.getTurn() == false || s.contents.matches("W(.*)") && b.getTurn() == true) { //only certain coloured pieces can be selected depending on turn
            b.setSource((s.yPos * b.getSize()) + s.xPos); //stores selected spaces as var
            b.setSelected(true);
            validSquares(b.getSource()); //highlights possible moves
        }
        else {
            System.out.println("Invalid Selection"); //prints on invalid selection
        }
    }

    public void moveTo(Square s) {
        if(s.contents == "VALID") {
            b.setDest((s.yPos * b.getSize()) + s.xPos); //stores space you are moving to
            s.contents = b.spaces[b.getSource()].contents; //replaces contents of target square with piece moved onto it
            if(s.contents == "RED") //sets image depending on which piece was moved
                b.btn[b.getDest()].setIcon(RED);
            else if(s.contents == "RKING")
                b.btn[b.getDest()].setIcon(REDKING);
            else if(s.contents == "WKING")
                b.btn[b.getDest()].setIcon(WHITEKING);
            else
                b.btn[b.getDest()].setIcon(WHITE);
            b.btn[b.getSource()].setIcon(emptyw); //sets original square image to blank
            printMove(b.getSource(), b.getDest()); //prints move
            b.spaces[b.getSource()].contents = "NONE"; //sets contents of original square to "none"
            if(b.getTurn() == true) { //changes the turn to other player
                b.setTurn(false);
                b.frame.setTitle("Draughts - Red's Turn"); //changes title of window to show whos turn it is
            }
            else {
                b.setTurn(true);
                b.frame.setTitle("Draughts - White's Turn");
            }
            b.setSelected(false);
            removeSelect();
            king(b.getDest());
            takePiece(b.getSource(), b.getDest());
        }
        else {
            b.setSelected(false); //cancels select if illegal move is attempted
            removeSelect();
            System.out.println("Invalid Move");
        }
    }

    public void printMove(int s, int d) {
        int sn = b.getSize() - s/b.getSize(); //calcs y coordinate
        int dn = b.getSize() - d/b.getSize();
        char sl = (char)(s % b.getSize() + 65); //converts x coordinates to char (0 - 7 / A - H)
        char dl = (char)(d % b.getSize() + 65);
        System.out.println(b.spaces[b.getSource()].contents + ": " + sl + sn + " to " + dl + dn); //prints last move
    }

    public void king(int d) {
        if(b.spaces[d].yPos == 0 && b.spaces[d].contents == "WHITE") { //if white destination is the top row
            b.btn[d].setIcon(WHITEKING); //replace with king piece
            b.spaces[d].contents = "WKING";
        }
        else if(b.spaces[d].yPos == b.getSize()-1 && b.spaces[d].contents == "RED") { //if red destination is bottom row
            b.btn[d].setIcon(REDKING); // replace with king piece
            b.spaces[d].contents = "RKING";
        }
    }

    public void takePiece(int s, int d) {
        if(Math.abs(b.spaces[d].yPos - b.spaces[s].yPos) == 2) { 
            b.spaces[d + ((s-d)/2)].contents = "NONE"; //removes opponent piece after jumping over it
            b.btn[d + ((s-d)/2)].setIcon(emptyw);
            endGame();
        }
    }

    public void validSquares(int s) {
        
        if(b.spaces[s].contents == "WKING" || b.spaces[s].contents == "RKING") { //move validation for king pieces
            for(int i = -2; i <= 2; i++) {
                int d = s+(b.getSize()*i)+i; //local var contains possible squares to move to
                for(int j = 0; j < 2; j++) {
                    if(d >= 0 && d <= (int)Math.pow(b.getSize(), 2)-1 && 
                        b.spaces[d].contents == "NONE" && (b.spaces[d].xPos % 2 != b.spaces[d].yPos % 2)) {
                            if((Math.abs(i) == 2 && //if king can jump across opposite coloured piece
                                (b.spaces[d + ((s-d)/2)].contents == "WHITE" && b.spaces[s].contents == "RKING" || 
                                b.spaces[d + ((s-d)/2)].contents == "RED" && b.spaces[s].contents == "WKING")) ||
                                Math.abs(i) == 1) { //or if king can move to surrounding square
                                    b.spaces[d].contents = "VALID";
                                    b.btn[d].setIcon(valid); //highlights potential moves
                            }
                    }
                    if(j == 0)
                        d = d - (2*i);
                }
            }
        }
        else { 
            for(int i = 1; i <= 2; i++) {
                int d;
                if(b.spaces[s].contents == "RED")
                    d = s+(b.getSize()*i)+i; //looks at squares below if a red piece
                else  
                    d = s-(b.getSize()*i)+i; //looks at squares above if white
                for(int j = 0; j < 2; j++) {
                    if(d >= 0 && d <= (int)Math.pow(b.getSize(), 2)-1 && 
                        b.spaces[d].contents == "NONE" && (b.spaces[d].xPos % 2 != b.spaces[d].yPos % 2)) {
                            if((i == 2 && 
                                (b.spaces[d + ((s-d)/2)].contents.matches("W(.*)") && b.spaces[s].contents.matches("R(.*)") || 
                                b.spaces[d + ((s-d)/2)].contents.matches("R(.*)") && b.spaces[s].contents.matches("W(.*)")) ||
                                i == 1)) { 
                                    b.spaces[d].contents = "VALID";
                                    b.btn[d].setIcon(valid); 
                            }
                    }
                    if(j == 0)
                        d = d - (2*i);
                }
            } 
        }
    }
    
    public void removeSelect() { //cycles through every square and reverts highlighted spaces
        for(int i = 0; i < (int)Math.pow(b.getSize(), 2); i++) {
            if(b.spaces[i].contents == "VALID") {
                b.spaces[i].contents = "NONE";
                b.btn[i].setIcon(emptyw);
            }
        }
    }

    public void endGame() {
        int w = 0;
        int r = 0;
        for(int i = 0; i < (int)Math.pow(b.getSize(), 2); i++) { //counts every piece on the board
            if(b.spaces[i].contents.matches("R(.*)"))
                r++;
            if(b.spaces[i].contents.matches("W(.*)"))
                w++;
        }
        if(w == 0 || r == 0) { //if either side has no more pieces
            for(int i = 0; i < (int)Math.pow(b.getSize(), 2); i++) {
                b.btn[i].removeActionListener(this); //removes action listener from all buttons
            }
            if(w == 0)
                b.frame.setTitle("Draughts - Red Wins"); //changes title depending on winner
            if(r == 0)
                b.frame.setTitle("Draughts - White Wins");
        }
    }
}


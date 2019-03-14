import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Square implements ActionListener{

    private int xPos;
    private int yPos;
    private String contents = "NONE";
    private Board b;

    //relevant images
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

    public void actionPerformed(ActionEvent e) {
        if (b.getSelected() == false) {
            select(this);
            validSquares(b.getSource());
        }
        else {
            moveTo(this);
        }
    }

//- - - - - - - - - - - - - - - - - - - - - - - FUNctions - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void select(Square s) {
        if(s.contents == "RED" && b.getTurn() == false || s.contents == "WHITE" && b.getTurn() == true) {
            b.setSource((s.yPos * b.getSize()) + s.xPos);
            b.setSelected(true);
        }
        else {
            System.out.println("Invalid Selection");
        }
    }

    public void moveTo(Square s) {
        if(s.contents == "VALID") {
            b.setDest((s.yPos * b.getSize()) + s.xPos);
            s.contents = b.spaces[b.getSource()].contents; 
            if(s.contents == "RED")
                b.btn[b.getDest()].setIcon(RED);
            else
                b.btn[b.getDest()].setIcon(WHITE);
            b.btn[b.getSource()].setIcon(emptyw);
            printMove(b.getSource(), b.getDest());
            b.spaces[b.getSource()].contents = "NONE";
            if(b.getTurn() == true) {
                b.setTurn(false);
                b.frame.setTitle("Draughts - Red's Turn");
            }
            else {
                b.setTurn(true);
                b.frame.setTitle("Draughts - White's Turn");
            }
            b.setSelected(false);
            removeSelect();
            king(b.getDest());
        }
        else {
            b.setSelected(false);
            removeSelect();
            System.out.println("Invalid Move");
        }
    }

    public void printMove(int s, int d) {
        int sn = b.getSize() - s/b.getSize(); //calcs y coordinate
        int dn = b.getSize() - d/b.getSize();
        char sl = (char)(s % b.getSize() + 65); //converts x coordinates to char (0 - 7/ A - H)
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

    public void validSquares(int s) {
        if(b.spaces[s].contents == "WHITE") {
            if(b.spaces[s-b.getSize()-1].contents == "NONE") {
                b.spaces[s-b.getSize()-1].contents = "VALID";
                b.btn[s-b.getSize()-1].setIcon(valid);
            }
            if(b.spaces[s-b.getSize()+1].contents == "NONE") {
                b.spaces[s-b.getSize()+1].contents = "VALID";
                b.btn[s-b.getSize()+1].setIcon(valid);
            }
            if(b.spaces[s-b.getSize()+1].contents == "RED") {
                if(b.spaces[s-b.getSize()*2+2].contents == "NONE") {
                    b.spaces[s-b.getSize()*2+2].contents = "VALID";
                    b.btn[s-b.getSize()*2+2].setIcon(valid);
                }
            }
            if(b.spaces[s-b.getSize()-1].contents == "RED") {
                if(b.spaces[s-b.getSize()*2-2].contents == "NONE") {
                    b.spaces[s-b.getSize()*2-2].contents = "VALID";
                    b.btn[s-b.getSize()*2-2].setIcon(valid);
                }
            }
        }

        if(b.spaces[s].contents == "RED") {
            if(b.spaces[s+b.getSize()-1].contents == "NONE") {
                b.spaces[s+b.getSize()-1].contents = "VALID";
                b.btn[s+b.getSize()-1].setIcon(valid);
            }
            if(b.spaces[s+b.getSize()+1].contents == "NONE") {
                b.spaces[s+b.getSize()+1].contents = "VALID";
                b.btn[s+b.getSize()+1].setIcon(valid);
            }
            if(b.spaces[s+b.getSize()+1].contents == "WHITE") {
                if(b.spaces[s+b.getSize()*2+2].contents == "NONE") {
                    b.spaces[s+b.getSize()*2+2].contents = "VALID";
                    b.btn[s+b.getSize()*2+2].setIcon(valid);
                }
            }
            if(b.spaces[s+b.getSize()-1].contents == "WHITE") {
                if(b.spaces[s+b.getSize()*2-2].contents == "NONE") {
                    b.spaces[s+b.getSize()*2-2].contents = "VALID";
                    b.btn[s+b.getSize()*2-2].setIcon(valid);
                }
            }
        }
    }

    public void removeSelect() {
        for(int i = 0; i < (int)Math.pow(b.getSize(), 2); i++) {
            if(b.spaces[i].contents == "VALID") {
                b.spaces[i].contents = "NONE";
                b.btn[i].setIcon(emptyw);
            }
        }
    }
}


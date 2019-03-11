import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Square implements ActionListener{

    private int xPos;
    private int yPos;
    private String contents = "NONE";
    private Board b;
    //private static JButton[] btn = new JButton[64];

    //relevant images
    ImageIcon emptyw = new ImageIcon("empty.png");
    ImageIcon emptyb = new ImageIcon("empty2.png");
    ImageIcon RED = new ImageIcon("red.png");
    ImageIcon WHITE = new ImageIcon("white.png");

    Square(Board board, JPanel p, GridBagConstraints gbc, int mid) {

        b = board;
        xPos = gbc.gridx; //stores squares position on grid
        yPos = gbc.gridy;
        int i = ((yPos * b.getSize()) + xPos);
        b.btn[i] = new JButton();

        if(gbc.gridx % 2 == gbc.gridy % 2)
            b.btn[i].setIcon(emptyb);
        else
            if(gbc.gridy < (b.getSize()/2)-1) {
                b.btn[i].setIcon(RED);
                contents = "RED";
            }
            else if((b.getSize() % 2 == 0 && gbc.gridy > b.getSize()/2) || (b.getSize() % 2 == 1 && gbc.gridy > b.getSize()/2+1)) {
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
            Select(this);
        }
        else {
            moveTo(this);
        }
    }

    public void Select(Square s) {
        if(s.contents == "RED" && b.getTurn() == false || s.contents == "WHITE" && b.getTurn() == true) {
            b.setSource((s.yPos * b.getSize()) + s.xPos);
            b.setSourceSquare(s);
            b.setSelected(true);
        }
        else {
            System.out.println("Invalid Selection");
        }
    }

    public void moveTo(Square s) {
        if((b.getSourceSquare().contents == "WHITE" && s.yPos == b.getSourceSquare().yPos - 1)||(b.getSourceSquare().contents == "RED" && s.yPos == b.getSourceSquare().yPos + 1)) {
            if(s.xPos == b.getSourceSquare().xPos + 1 || s.xPos == b.getSourceSquare().xPos - 1) {
                b.setDest((s.yPos * b.getSize()) + s.xPos);
                s.contents = b.getSourceSquare().contents; 
                if(s.contents == "RED")
                    b.btn[b.getDest()].setIcon(RED);
                else
                    b.btn[b.getDest()].setIcon(WHITE);
                b.btn[b.getSource()].setIcon(emptyw);
                printMove(b.getSource(), b.getDest());
                b.getSourceSquare().contents = "NONE";
                if(b.getTurn() == true) {
                    b.setTurn(false);
                }
                else {
                    b.setTurn(true);
                }
                b.setSelected(false);
            }
        }
        else {
            b.setSelected(false);
            System.out.println("Invalid Move");
        }
    }

    public void printMove(int s, int d) {
        int sn = 8 - s/b.getSize();
        int dn = 8 - d/b.getSize();
        char sl = (char)(s % b.getSize() + 65);
        char dl = (char)(d % b.getSize() + 65);
        System.out.println(b.getSourceSquare().contents + ": " + sl + sn + " to " + dl + dn);
    }
}
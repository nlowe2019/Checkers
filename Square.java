import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Square implements ActionListener{

    int xPos;
    int yPos;
    String contents = "NONE";

    static boolean selected = false;
    static Square sourceSquare;
    static int source;
    static int dest;
    static JButton[] btn = new JButton[64];

    //relevant images
    ImageIcon emptyw = new ImageIcon("empty.png");
    ImageIcon emptyb = new ImageIcon("empty2.png");
    ImageIcon RED = new ImageIcon("red.png");
    ImageIcon WHITE = new ImageIcon("white.png");

    Square(Board board, JPanel p, GridBagConstraints gbc, int mid) {

        xPos = gbc.gridx; //stores squares position on grid
        yPos = gbc.gridy;
        int i = ((yPos * 8) + xPos);
        btn[i] = new JButton();

        if(gbc.gridx % 2 == gbc.gridy % 2)
            btn[i].setIcon(emptyb);
        else
            if(gbc.gridy < mid-1) {
                btn[i].setIcon(RED);
                contents = "RED";
            }
            else if(gbc.gridy > mid) {
                btn[i].setIcon(WHITE);
                contents = "WHITE";
            }
            else
                btn[i].setIcon(emptyw); 

        btn[i].addActionListener(this);
        p.add(btn[i], gbc);
    }

    public void actionPerformed(ActionEvent e) {
        if (selected == false) {
            if(this.contents != "NONE") {
                source = (this.yPos * 8) + this.xPos;
                sourceSquare = this;
                selected = true;
            }
        }
        else {
            if (this.xPos % 2 == this.yPos % 2) {
                ;
            }
            else {
            dest = (this.yPos * 8) + this.xPos;
            String i = this.contents;
            System.out.print(i);
            this.contents = sourceSquare.contents; 
            if(this.contents == "RED")
                btn[dest].setIcon(RED);
            else
                btn[dest].setIcon(WHITE);
            btn[source].setIcon(emptyw);
            sourceSquare.contents = "NONE";
            selected = false;
            }
        }
    }
}
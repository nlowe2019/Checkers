import javax.swing.*;
import java.awt.*;

public class Square {

    int xPos;
    int yPos;
    String contents = "NONE";

    //relevant images
    ImageIcon emptyw = new ImageIcon("empty.png");
    ImageIcon emptyb = new ImageIcon("empty2.png");
    ImageIcon RED = new ImageIcon("red.png");
    ImageIcon WHITE = new ImageIcon("white.png");

    Square(Board board, JPanel p, GridBagConstraints gbc, int mid) {

        JButton space = new JButton();

        if(gbc.gridx % 2 == gbc.gridy % 2)
            space.setIcon(emptyb);
        else
            if(gbc.gridy < mid-1)
                space.setIcon(RED);
            else if(gbc.gridy > mid)
                space.setIcon(WHITE);
            else
                space.setIcon(emptyw); 

        xPos = gbc.gridx; //stores squares position on grid
        yPos = gbc.gridy;
        p.add(space, gbc);
    }
}
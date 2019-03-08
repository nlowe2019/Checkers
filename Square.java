import javax.swing.*;
import java.awt.*;

public class Square {

    int xPos;
    int yPos;
    int size = 100;
    ImageIcon emptyw = new ImageIcon("empty.png");
    ImageIcon emptyb = new ImageIcon("empty2.png");

    Square(Board board, JPanel p, GridBagConstraints gbc) {

        JButton space = new JButton(emptyw);

        if(gbc.gridx % 2 == gbc.gridy % 2)
            space.setIcon(emptyw);
        else
            space.setIcon(emptyb); 

        xPos = gbc.gridx;
        yPos = gbc.gridy;
        p.add(space, gbc);
    }
}
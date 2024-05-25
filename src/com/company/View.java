package com.company;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class View {

    private JLabel imageLabel;
    private Controller controller;

    public void create(int width, int height) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);


        imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, width, height);
        frame.add(imageLabel);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.handleMousePress(e.getX(), e.getY(), e.getButton());
            }
        });
        frame.setVisible(true);
    }
    public void setImage(BufferedImage image) {
        imageLabel.setIcon(new ImageIcon(image));
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}

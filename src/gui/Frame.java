package gui;

import javax.swing.*;

// Inherit the JFrame class, to create a frame for the window.
public class Frame extends JFrame {
    Frame() {
        this.setTitle("OSPF Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close the application, when pressing X
        this.setResizable(false);
        this.setSize(800, 600);
        this.setVisible(true);                                 // Make frame visible
    }
}

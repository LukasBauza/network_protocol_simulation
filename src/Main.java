// imports the gui package and the Frame class into this class.

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        // **** Start Menu ****
        JFrame start_menu = new JFrame();

        start_menu.setTitle("OSPF Simulation");
        start_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close the application, when pressing X
        start_menu.setResizable(false);
        start_menu.setSize(600, 300);
        start_menu.setLayout(new GridLayout(0, 1));       // rows=0, cols=1. Makes it vertical.

        JLabel welcomeLabel = new JLabel("""
                <html>
                Welcome to the OSPF simulation protocol!<br>
                Please select whether you want to create a custom network or a preconfigured network.
                </html>""", SwingConstants.CENTER);     // html is used to add line break
        welcomeLabel.setBackground(Color.lightGray);
        welcomeLabel.setOpaque(true);


        JButton customNetworkButton = new JButton("Custom Network");
        Font networkButtonFont = new Font(customNetworkButton.getFont().getName(), Font.BOLD, 20);
        customNetworkButton.setFont(networkButtonFont);
        customNetworkButton.addActionListener(e -> {
            System.out.println("Custom network button pressed.");
        });

        JButton preconfiguredNetworkButton = new JButton("Preconfigured Network");
        preconfiguredNetworkButton.setFont(networkButtonFont);
        preconfiguredNetworkButton.addActionListener(e -> {
            System.out.println("Preconfigured network button pressed.");
        });

        start_menu.add(welcomeLabel);
        start_menu.add(customNetworkButton);
        start_menu.add(preconfiguredNetworkButton);
        start_menu.setVisible(true);                                 // Make start_menu visible
    }
}
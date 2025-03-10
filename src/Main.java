// imports the gui package and the Frame class into this class.

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new MainMenuFrame();
        frame.setTitle("OSPF Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close the application, when pressing X
        frame.setResizable(false);
        frame.setSize(600, 300);
        frame.setLayout(new GridLayout(0, 1));       // rows=0, cols=1. Makes it vertical.

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

        JButton preconfiguredNetworkButton = new JButton("Preconfigured Network");
        preconfiguredNetworkButton.setFont(networkButtonFont);

        frame.add(welcomeLabel);
        frame.add(customNetworkButton);
        frame.add(preconfiguredNetworkButton);
        frame.setVisible(true);                                 // Make start_menu_frame visible

        JLabel prebuiltNetworkLabel = new JLabel("Prebuilt Network");

        JLabel customNetworkLabel = new JLabel("Custom Network");

        preconfiguredNetworkButton.addActionListener(e -> {
            System.out.println("Preconfigured network button pressed.");

            frame.remove(welcomeLabel);
            frame.remove(customNetworkButton);
            frame.remove(preconfiguredNetworkButton);

            frame.setTitle("OSPF Simulation: Prebuilt Network");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close the application, when pressing X
            frame.setResizable(false);
            frame.setSize(1000, 600);
            frame.setLayout(new GridLayout());       // rows=0, cols=1. Makes it vertical.

            frame.add(prebuiltNetworkLabel);
        });

        customNetworkButton.addActionListener(e -> {
            System.out.println("Custom network button pressed.");

            frame.remove(welcomeLabel);
            frame.remove(customNetworkButton);
            frame.remove(preconfiguredNetworkButton);

            frame.setTitle("OSPF Simulation: Custom Network");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close the application, when pressing X
            frame.setResizable(false);
            frame.setSize(1000, 600);
            frame.setLayout(new GridLayout());       // rows=0, cols=1. Makes it vertical.

            frame.add(customNetworkLabel);
        });
    }
}
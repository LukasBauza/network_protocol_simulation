// imports the gui package and the Frame class into this class.

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("OSPF Simulation");
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

        JButton[] routerButtons = getRouterJButtons();
        JButton[] pcButtons = getPCButtons();

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

            for (JButton button : routerButtons) {
                frame.add(button);
            }

            for (JButton button : pcButtons) {
                frame.add(button);
            }
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

    private static JButton[] getRouterJButtons() {
        Router[] routers = {
                new Router("R0"),
                new Router("R1"),
                new Router("R2"),
                new Router("R3"),
                new Router("R4")
        };
        return new JButton[]{
                new JButton(routers[0].getName()),
                new JButton(routers[1].getName()),
                new JButton(routers[2].getName()),
                new JButton(routers[3].getName()),
                new JButton(routers[4].getName())
        };
    }

    private static JButton[] getPCButtons() {
        PC[] pcs = {
                new PC("PC0", new IPAddress("192.168.1.1"), new SubnetMask("255.255.255.0")),
                new PC("PC1", new IPAddress("192.168.2.1"), new SubnetMask("255.255.255.0")),
                new PC("PC2", new IPAddress("192.168.3.1."), new SubnetMask("255.255.255.0")),
                new PC("PC3", new IPAddress("192.168.4.1"), new SubnetMask("255.255.255.0")),
        };
        return new JButton[]{
                new JButton("PC0"),
                new JButton("PC1"),
                new JButton("PC2"),
                new JButton("PC3")
        };
    };
}
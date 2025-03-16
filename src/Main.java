// imports the gui package and the Frame class into this class.

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        // Retrieve the only instance of the NICManager.
        NICManager nicManager = NICManager.getInstance();

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

        JButton[] routerButtons = getJButtonArray(7, "R");
        JButton[] pcButtons = getJButtonArray(3, "PC");

        JLabel customNetworkLabel = new JLabel("Custom Network");

        preconfiguredNetworkButton.addActionListener(e -> {
            System.out.println("Preconfigured network button pressed.");

            frame.remove(welcomeLabel);
            frame.remove(customNetworkButton);
            frame.remove(preconfiguredNetworkButton);

            frame.setTitle("OSPF Simulation: Prebuilt Network");
            frame.setSize(1200, 1000);
            frame.setLayout(new GridLayout());       // rows=0, cols=1. Makes it vertical.

            Line[] wires = {
                    new Line(60, 60, 180, 180, Color.BLACK),        // PC0 to R0
                    new Line(180, 180, 310, 310, Color.BLACK),      // R0 to R1
                    new Line(310, 310, 440, 440, Color.BLACK),      // R1 to R2
                    new Line(440, 440, 570, 570, Color.BLACK),      // R2 to R3
                    new Line(570, 570, 700, 700, Color.BLACK),      // R3 to PC1
                    new Line(240, 210, 730, 210, Color.BLACK),      // R0 to R5
                    new Line(320, 340, 545, 340, Color.BLACK),      // R1 to R4
                    new Line(545, 340, 850, 340, Color.BLACK),      // R4 to R6
                    new Line(760, 240, 850, 310, Color.BLACK),      // R5 to R6
                    new Line(480, 470, 570, 340, Color.BLACK),      // R2 to R4
            };

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    for (Line line : wires) {
                        line.draw(g); // Draw stored lines
                    }
                }
            };

            panel.setLayout(null);                  // No layout, for placing items with x and y coordinates.

            panel.add(pcButtons[0]);
            pcButtons[0].setBounds(new Rectangle(50, 50, 60, 60));

            panel.add(routerButtons[0]);
            routerButtons[0].setBounds(new Rectangle(180, 180, 60, 60));

            panel.add(routerButtons[1]);
            routerButtons[1].setBounds(new Rectangle(310, 310, 60, 60));

            panel.add(routerButtons[2]);
            routerButtons[2].setBounds(new Rectangle(440, 440, 60, 60));

            panel.add(routerButtons[3]);
            routerButtons[3].setBounds(new Rectangle(570, 570, 60, 60));

            panel.add(pcButtons[1]);
            pcButtons[1].setBounds(new Rectangle(700, 700, 60, 60));

            panel.add(routerButtons[4]);
            routerButtons[4].setBounds(new Rectangle(545, 310, 60, 60));

            panel.add(routerButtons[5]);
            routerButtons[5].setBounds(new Rectangle(700, 180, 60, 60));

            panel.add(routerButtons[6]);
            routerButtons[6].setBounds(new Rectangle(850, 310, 60, 60));

            for (JButton button : routerButtons) {
                panel.add(button);
            }

            for (JButton button : pcButtons) {
                panel.add(button);
            }

            frame.add(panel);
        });

        customNetworkButton.addActionListener(e -> {
            System.out.println("Custom network button pressed.");

            frame.remove(welcomeLabel);
            frame.remove(customNetworkButton);
            frame.remove(preconfiguredNetworkButton);

            frame.setTitle("OSPF Simulation: Custom Network");
            frame.setSize(1000, 600);
            frame.setLayout(new GridLayout());       // rows=0, cols=1. Makes it vertical.

            frame.add(customNetworkLabel);
        });
    }

    /**
     * Method for creating an array of JButton objects, with a name for each object.
     * @param count
     * @param name
     * @return Array of JButton objects, with size count. With the text as: name + (count - 1), for each button.
     */
    private static JButton[] getJButtonArray(int count, String name) {
        JButton[] buttons = new JButton[count];

        while (count != 0) {
            buttons[count - 1] = new JButton(name + (count - 1));
            count--;
        }
        return buttons;
    }
}
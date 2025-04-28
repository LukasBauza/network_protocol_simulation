// imports the gui package and the Frame class into this class.

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {

        //PCButton[] pcButtons = getPCButtonArray(3, "PC");
        //RouterButton[] routerButtons = getRouterButtonArray(7, "R");

        JFrame frame = new JFrame("OSPF Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Close the application, when pressing X (all frames close)
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

        JLabel customNetworkLabel = new JLabel("Custom Network");

        preconfiguredNetworkButton.addActionListener(e -> {
            System.out.println("Preconfigured network button pressed.");

            frame.remove(welcomeLabel);
            frame.remove(customNetworkButton);
            frame.remove(preconfiguredNetworkButton);

            frame.setTitle("OSPF Simulation: Prebuilt Network");
            frame.setSize(1200, 1000);
            frame.setLayout(new GridLayout());       // rows=0, cols=1. Makes it vertical.

            PreconfiguredNetworkPanel preconfiguredNetworkPanel = new PreconfiguredNetworkPanel();
            frame.add(preconfiguredNetworkPanel);
            preconfiguredNetworkPanel.setVisible(true);
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
     * @param count The amount of Button objects in the array.
     * @param name The text for the buttons + i, where is the current button being created.
     * @return Array of JButton objects, with size count. With the text as: name + (count - 1), for each button.
     */
    private static JButton[] getJButtonArray(int count, String name) {
        JButton[] buttons = new JButton[count];

        // count-- is post decrement, meaning the current count variable is used, then it is decremented.
        while (count-- > 0) {
            buttons[count] = new JButton(name + (count));
        }
        return buttons;
    }

    private static RouterButton[] getRouterButtonArray(int count, String name) {
        RouterButton[] routers = new RouterButton[count];

        // count-- is post decrement, meaning the current count variable is used, then it is decremented.
        while (count-- > 0) {
            routers[count] = new RouterButton(name + (count));
        }
        return routers;
    }

    private static PCButton[] getPCButtonArray(int count, String name, PreconfiguredNetworkPanel preconfiguredNetworkPanel) {
        PCButton[] pcButtons = new PCButton[count];

        // count-- is post decrement, meaning the current count variable is used, then it is decremented.
        while (count-- > 0) {
            pcButtons[count] = new PCButton(name + (count), preconfiguredNetworkPanel);
        }
        return pcButtons;
    }
}
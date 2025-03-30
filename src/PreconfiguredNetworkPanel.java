import javax.swing.*;
import java.awt.*;

public class PreconfiguredNetworkPanel extends JPanel {
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
            new Line(880, 340, 1030, 210, Color.BLACK),      // R6 to PC3
    };

    PCButton[] pcButtons = getPCButtonArray(3, "PC");
    RouterButton[] routerButtons = getRouterButtonArray(7, "R");

    JLabel[] pcFa00Lables = new JLabel[pcButtons.length];

    JLabel[] routerGig00Lables = new JLabel[routerButtons.length];
    JLabel[] routerGig01Lables = new JLabel[routerButtons.length];
    JLabel[] routerGig02Lables = new JLabel[routerButtons.length];

    public PreconfiguredNetworkPanel() {
        this.setLayout(null);                  // No layout, for placing items with x and y coordinates.
        addRouterAndPCButtons();
        setupPCPorts();
        setupRouterPorts();
        connectPCToRouter();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : wires) {
            line.draw(g); // Draw stored lines
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        PreconfiguredNetworkPanel panel = new PreconfiguredNetworkPanel();

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static RouterButton[] getRouterButtonArray(int count, String name) {
        RouterButton[] routers = new RouterButton[count];

        // count-- is post decrement, meaning the current count variable is used, then it is decremented.
        while (count-- > 0) {
            routers[count] = new RouterButton(name + (count));
        }
        return routers;
    }

    private static PCButton[] getPCButtonArray(int count, String name) {
        PCButton[] pcButtons = new PCButton[count];

        // count-- is post decrement, meaning the current count variable is used, then it is decremented.
        while (count-- > 0) {
            pcButtons[count] = new PCButton(name + (count));
        }
        return pcButtons;
    }

    private void setupPCPorts() {
        pcButtons[0].getPC().setPortFA00(new IPAddress("192.168.1.254"), new SubnetMask("255.255.255.0"));
        pcButtons[1].getPC().setPortFA00(new IPAddress("192.168.10.254"), new SubnetMask("255.255.255.0"));
        pcButtons[2].getPC().setPortFA00(new IPAddress("192.168.19.254"), new SubnetMask("255.255.255.0"));
    }

    private void setupRouterPorts() {
        // Setup all the router IP Address and Subnet Masks for all of their ports.
        for (int i = 0; i < routerButtons.length; i++) {
            Router router = routerButtons[i].getRouter();
            router.setPortGig00(new IPAddress("192.168.%d.1".formatted((i * 3) + 1)), new SubnetMask("255.255.255.0"));
            router.setPortGig01(new IPAddress("192.168.%d.1".formatted((i * 3) + 2)), new SubnetMask("255.255.255.0"));
            router.setPortGig02(new IPAddress("192.168.%d.1".formatted((i * 3) + 3)), new SubnetMask("255.255.255.0"));
        }
    }

    private void connectPCToRouter() {
        // Connect the router NIC to the PC NIC.
        // PC0 connection to R0 Gig00
        routerButtons[0].getRouter().getPortGig00().setConnection(pcButtons[0].getPC().getPortFA00());
        pcButtons[0].getPC().getPortFA00().setConnection(routerButtons[0].getRouter().getPortGig00());
        // PC1 connection to R3 Gig00
        routerButtons[3].getRouter().getPortGig00().setConnection(pcButtons[1].getPC().getPortFA00());
        pcButtons[1].getPC().getPortFA00().setConnection(routerButtons[3].getRouter().getPortGig00());
        // PC2 connection to R6 Gig00
        routerButtons[6].getRouter().getPortGig00().setConnection(pcButtons[2].getPC().getPortFA00());
        pcButtons[2].getPC().getPortFA00().setConnection(routerButtons[6].getRouter().getPortGig00());
    }

    private void addRouterAndPCButtons() {

        this.add(pcButtons[0]);
        pcButtons[0].setBounds(new Rectangle(50, 50, 60, 60));

        this.add(routerButtons[0]);
        routerButtons[0].setBounds(new Rectangle(180, 180, 60, 60));

        this.add(routerButtons[1]);
        routerButtons[1].setBounds(new Rectangle(310, 310, 60, 60));

        this.add(routerButtons[2]);
        routerButtons[2].setBounds(new Rectangle(440, 440, 60, 60));

        this.add(routerButtons[3]);
        routerButtons[3].setBounds(new Rectangle(570, 570, 60, 60));

        this.add(pcButtons[1]);
        pcButtons[1].setBounds(new Rectangle(700, 700, 60, 60));

        this.add(routerButtons[4]);
        routerButtons[4].setBounds(new Rectangle(545, 310, 60, 60));

        this.add(routerButtons[5]);
        routerButtons[5].setBounds(new Rectangle(700, 180, 60, 60));

        this.add(routerButtons[6]);
        routerButtons[6].setBounds(new Rectangle(850, 310, 60, 60));

        this.add(pcButtons[2]);
        pcButtons[2].setBounds(new Rectangle(1000, 180, 60, 60));

        for (JButton button : routerButtons) {
            this.add(button);
        }

        for (JButton button : pcButtons) {
            this.add(button);
        }
    }

    private void createPCFa00Labels() {
        // Set the name for all the PC labels
        for (int i = 0; i < pcFa00Lables.length; i++) {
            pcFa00Lables[i] = new JLabel("Fa 0/0");
        }
    }

    private void createRouterAllLabels() {
        // Set the name for all the Router labels
        for (int i = 0; i < routerButtons.length; i++) {
            if (i % 3 == 0) {
                routerGig00Lables[i] = new JLabel("Gig 0/0");
            } else if (i % 3 == 1) {
                routerGig01Lables[i] = new JLabel("Gig 0/1");
            } else {
                routerGig02Lables[i] = new JLabel("Gig 0/2");
            }
        }
    }
}
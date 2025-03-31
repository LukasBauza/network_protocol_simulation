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
        createPCFa00Labels();
        createRouterAllLabels();
        placePCFa00Labels();
        placeRouterAllLabels();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : wires) {
            line.draw(g); // Draw stored lines
        }
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

        pcButtons[0].setBounds(new Rectangle(50, 50, 60, 60));

        routerButtons[0].setBounds(new Rectangle(180, 180, 60, 60));

        routerButtons[1].setBounds(new Rectangle(310, 310, 60, 60));

        routerButtons[2].setBounds(new Rectangle(440, 440, 60, 60));

        routerButtons[3].setBounds(new Rectangle(570, 570, 60, 60));

        pcButtons[1].setBounds(new Rectangle(700, 700, 60, 60));

        routerButtons[4].setBounds(new Rectangle(545, 310, 60, 60));

        routerButtons[5].setBounds(new Rectangle(700, 180, 60, 60));

        routerButtons[6].setBounds(new Rectangle(850, 310, 60, 60));

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
        for (int i = 0; i < routerButtons.length; i++) {
            routerGig00Lables[i] = new JLabel("Gig 0/0");
        }

        for (int i = 0; i < routerButtons.length; i++) {
            routerGig01Lables[i] = new JLabel("Gig 0/1");
        }

        for (int i = 0; i < routerButtons.length; i++) {
            routerGig02Lables[i] = new JLabel("Gig 0/2");
        }
    }

    private void placePCFa00Labels() {
        pcFa00Lables[0].setBounds(70, 90, 60, 60);

        pcFa00Lables[1].setBounds(700, 660, 60, 60);

        pcFa00Lables[2].setBounds(1000, 220, 60, 60);

        for (JLabel label : pcFa00Lables) {
            this.add(label);
        }
    }

    private void placeRouterAllLabels() {
        // R0
        routerGig00Lables[0].setBounds(130, 150, 60, 60);
        routerGig01Lables[0].setBounds(200, 220, 60, 60);
        routerGig02Lables[0].setBounds(240, 160, 60, 60);
        // R1
        routerGig00Lables[1].setBounds(260, 280, 60, 60);
        routerGig01Lables[1].setBounds(330, 350, 60, 60);
        routerGig02Lables[1].setBounds(370, 290, 60, 60);
        // R2
        routerGig00Lables[2].setBounds(390, 410, 60, 60);
        routerGig01Lables[2].setBounds(460, 480, 60, 60);
        routerGig02Lables[2].setBounds(500, 420, 60, 60);
        // R3
        routerGig00Lables[3].setBounds(520, 540, 60, 60);
        routerGig01Lables[3].setBounds(590, 610, 60, 60);
        // R4
        routerGig00Lables[4].setBounds(500, 290, 60, 60);
        routerGig01Lables[4].setBounds(550, 350, 60, 60);
        routerGig02Lables[4].setBounds(610, 290, 60, 60);
        // R5
        routerGig00Lables[5].setBounds(650, 160, 60, 60);
        routerGig01Lables[5].setBounds(720, 220, 60, 60);
        // R6
        routerGig00Lables[6].setBounds(800, 280, 60, 60);
        routerGig01Lables[6].setBounds(800, 320, 60, 60);
        routerGig02Lables[6].setBounds(910, 290, 60, 60);

        for (JLabel label : routerGig00Lables) {
            this.add(label);
        }

        for (JLabel label : routerGig01Lables) {
            this.add(label);
        }

        for (JLabel label : routerGig02Lables) {
            this.add(label);
        }
    }
}
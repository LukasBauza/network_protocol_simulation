import javax.swing.*;
import java.awt.*;

public class PreconfiguredNetworkPanel extends JPanel {
    // Button size constants
    private static final int BUTTON_WIDTH = 60;
    private static final int BUTTON_HEIGHT = 60;

    // PC Button positions
    private static final int PC0_X = 50;
    private static final int PC0_Y = 50;
    
    private static final int PC1_X = 700;
    private static final int PC1_Y = 700;
    
    private static final int PC2_X = 1000;
    private static final int PC2_Y = 180;

    // Router Button positions
    private static final int R0_X = 180;
    private static final int R0_Y = 180;
    
    private static final int R1_X = 310;
    private static final int R1_Y = 310;
    
    private static final int R2_X = 440;
    private static final int R2_Y = 440;
    
    private static final int R3_X = 570;
    private static final int R3_Y = 570;
    
    private static final int R4_X = 545;
    private static final int R4_Y = 310;
    
    private static final int R5_X = 700;
    private static final int R5_Y = 180;
    
    private static final int R6_X = 850;
    private static final int R6_Y = 310;

    private PrePathCalculation prePathCalculation;

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
        setupConnections();
        createPCFa00Labels();
        createRouterAllLabels();
        placePCFa00Labels();
        placeRouterAllLabels();
        placeCostLabels();
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

    private PCButton[] getPCButtonArray(int count, String name) {
        PCButton[] pcButtons = new PCButton[count];

        // count-- is post decrement, meaning the current count variable is used, then it is decremented.
        while (count-- > 0) {
            pcButtons[count] = new PCButton(name + (count), this);
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

    private void setupConnections() {
        // Connect the router NIC to the PC NIC.
        // PC0 to R0 Gig00
        routerButtons[0].getRouter().getPortGig00().setConnection(pcButtons[0].getPC().getPortFA00());
        pcButtons[0].getPC().getPortFA00().setConnection(routerButtons[0].getRouter().getPortGig00());
        // PC1 to R3 Gig00
        routerButtons[3].getRouter().getPortGig00().setConnection(pcButtons[1].getPC().getPortFA00());
        pcButtons[1].getPC().getPortFA00().setConnection(routerButtons[3].getRouter().getPortGig00());
        // PC2 to R6 Gig00
        routerButtons[6].getRouter().getPortGig00().setConnection(pcButtons[2].getPC().getPortFA00());
        pcButtons[2].getPC().getPortFA00().setConnection(routerButtons[6].getRouter().getPortGig00());
        // Connect Router NIC  ot the other Router NIC.
        // R0 Gig01 to R1 Gig00
        routerButtons[0].getRouter().getPortGig01().setConnection(routerButtons[1].getRouter().getPortGig00());
        routerButtons[1].getRouter().getPortGig00().setConnection(routerButtons[0].getRouter().getPortGig01());
        // R0 Gig02 to R5 Gig00
        routerButtons[0].getRouter().getPortGig02().setConnection(routerButtons[5].getRouter().getPortGig00());
        routerButtons[5].getRouter().getPortGig00().setConnection(routerButtons[0].getRouter().getPortGig02());
        // R1 Gig01 to R2 Gig00
        routerButtons[1].getRouter().getPortGig01().setConnection(routerButtons[2].getRouter().getPortGig00());
        routerButtons[2].getRouter().getPortGig00().setConnection(routerButtons[1].getRouter().getPortGig01());
        // R1 Gig02 to R4 Gig00
        routerButtons[1].getRouter().getPortGig02().setConnection(routerButtons[4].getRouter().getPortGig00());
        routerButtons[4].getRouter().getPortGig00().setConnection(routerButtons[1].getRouter().getPortGig02());
        // R2 Gig01 to R4 Gig01
        routerButtons[2].getRouter().getPortGig01().setConnection(routerButtons[4].getRouter().getPortGig01());
        routerButtons[4].getRouter().getPortGig01().setConnection(routerButtons[2].getRouter().getPortGig01());
        // R2 Gig01 to R3 Gig00
        routerButtons[2].getRouter().getPortGig01().setConnection(routerButtons[3].getRouter().getPortGig00());
        routerButtons[3].getRouter().getPortGig00().setConnection(routerButtons[2].getRouter().getPortGig01());
        // R5 Gig01 to R6 Gig00
        routerButtons[5].getRouter().getPortGig01().setConnection(routerButtons[6].getRouter().getPortGig00());
        routerButtons[6].getRouter().getPortGig00().setConnection(routerButtons[5].getRouter().getPortGig01());
        // R4 Gig02 to R6 Gig01
        routerButtons[4].getRouter().getPortGig02().setConnection(routerButtons[6].getRouter().getPortGig01());
        routerButtons[6].getRouter().getPortGig01().setConnection(routerButtons[4].getRouter().getPortGig02());
    }

    private void addRouterAndPCButtons() {
        pcButtons[0].setBounds(new Rectangle(PC0_X, PC0_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        routerButtons[0].setBounds(new Rectangle(R0_X, R0_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        routerButtons[1].setBounds(new Rectangle(R1_X, R1_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        routerButtons[2].setBounds(new Rectangle(R2_X, R2_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        routerButtons[3].setBounds(new Rectangle(R3_X, R3_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        pcButtons[1].setBounds(new Rectangle(PC1_X, PC1_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        routerButtons[4].setBounds(new Rectangle(R4_X, R4_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        routerButtons[5].setBounds(new Rectangle(R5_X, R5_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        routerButtons[6].setBounds(new Rectangle(R6_X, R6_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

        pcButtons[2].setBounds(new Rectangle(PC2_X, PC2_Y, BUTTON_WIDTH, BUTTON_HEIGHT));

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

    private void placeCostLabels() {
        prePathCalculation = new PrePathCalculation(getRouters());

        int R0_R1_COST = prePathCalculation.getR0R1Cost();
        int R0_R5_COST = prePathCalculation.getR0R5Cost();
        int R1_R2_COST = prePathCalculation.getR1R2Cost();
        int R1_R4_COST = prePathCalculation.getR1R4Cost();
        int R2_R3_COST = prePathCalculation.getR2R3Cost();
        int R2_R4_COST = prePathCalculation.getR2R4Cost();
        int R4_R6_COST = prePathCalculation.getR4R6Cost();
        int R5_R6_COST = prePathCalculation.getR5R6Cost();

        JLabel[] costLabels = {
                new JLabel("R0 to R1: " + R0_R1_COST),
                new JLabel("R0 to R5: " + R0_R5_COST),
                new JLabel("R1 to R2: " + R1_R2_COST),
                new JLabel("R1 to R4: " + R1_R4_COST),
                new JLabel("R2 to R3: " + R2_R3_COST),
                new JLabel("R2 to R4: " + R2_R4_COST),
                new JLabel("R4 to R6: " + R4_R6_COST),
                new JLabel("R5 to R6: " + R5_R6_COST)
        };

        costLabels[0].setBounds(((R0_X + R1_X) / 2) - 50, ((R0_Y + R1_Y) / 2) + 20, 120, 60);
        costLabels[1].setBounds(((R0_X + R5_X) / 2), ((R0_Y + R5_Y) / 2) + 20, 120, 60);
        costLabels[2].setBounds(((R1_X + R2_X) / 2) - 50, ((R1_Y + R2_Y) / 2) + 20, 120, 60);
        costLabels[3].setBounds(((R1_X + R4_X) / 2), ((R1_Y + R4_Y) / 2) + 20, 120, 60);
        costLabels[4].setBounds(((R2_X + R3_X) / 2) - 50, ((R2_Y + R3_Y) / 2) + 20, 120, 60);
        costLabels[5].setBounds(((R2_X + R4_X) / 2) + 50, ((R2_Y + R4_Y) / 2) + 20, 120, 60);
        costLabels[6].setBounds(((R4_X + R6_X) / 2), ((R4_Y + R6_Y) / 2) + 20, 120, 60);
        costLabels[7].setBounds(((R5_X + R6_X) / 2) - 50, ((R5_Y + R6_Y) / 2) + 20, 120, 60);

        for (JLabel label : costLabels) {
            this.add(label);
        }
    }

    public Router[] getRouters() {
        Router[] routers = new Router[routerButtons.length];
        for (int i = 0; i < routerButtons.length; i++) {
            routers[i] = routerButtons[i].getRouter();
        }
        return routers;
    }

    public PC[] getPCs() {
        PC[] pcs = new PC[pcButtons.length];
        for (int i = 0; i < pcButtons.length; i++) {
            pcs[i] = pcButtons[i].getPC();
        }
        return pcs;
    }

    // Coordinate getter methods for devices.
    public Point getDeviceCoordinates(String deviceName) {
        switch (deviceName) {
            case "R0":
                return new Point(R0_X, R0_Y);
            case "R1":
                return new Point(R1_X, R1_Y);
            case "R2":
                return new Point(R2_X, R2_Y);
            case "R3":
                return new Point(R3_X, R3_Y);
            case "R4":
                return new Point(R4_X, R4_Y);
            case "R5":
                return new Point(R5_X, R5_Y);
            case "R6":
                return new Point(R6_X, R6_Y);
            case "PC0":
                return new Point(PC0_X, PC0_Y);
            case "PC1":
                return new Point(PC1_X, PC1_Y);
            case "PC2":
                return new Point(PC2_X, PC2_Y);
            default:
                return null;
        }
    }

    // Get button dimensions
    public Dimension getButtonDimension() {
        return new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
    }
}
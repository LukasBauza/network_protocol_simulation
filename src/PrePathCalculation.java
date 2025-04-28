public class PrePathCalculation {
    private Router[] routers = new Router[7];

    public PrePathCalculation(Router[] routers) {
        if (routers.length != 7) {
            throw new IllegalArgumentException("Router array length should be equal to 7");
        } else {
            this.routers = routers;
        }
    }

    public String getShortestPath(String startPc, String endPc) {
        // PC0, PC1
        if ((startPc.equals("PC0") && endPc.equals("PC1")) || (startPc.equals("PC1") && endPc.equals("PC0"))) {
            int paths[] = { getPc0Pc1PathCost0(), getPc0Pc1PathCost1(), getPc0Pc1PathCost2(), getPc0Pc1PathCost3() };
            switch (findShortestPathIndex(paths)) {
                case 0:
                    return "PC0--R0--R1--R2--R3--PC1";
                case 1:
                    return "PC0--R0--R1--R4--R2--R3--PC1";
                case 2:
                    return "PC0--R0--R5--R6--R4--R2--R3--PC1";
                case 3:
                    return "PC0--R0--R5--R6--R4--R1--R2--R3--PC1";
                default:
                    System.err.println("Invalid path comparison for " + startPc + " and " + endPc);
                    return "No path";
            }
        } else if ((startPc.equals("PC0") && endPc.equals("PC2")) || (startPc.equals("PC2") && endPc.equals("PC0"))) {
            int paths[] = { getPc0Pc2PathCost0(), getPc0Pc2PathCost1(), getPc0Pc2PathCost2() };
            switch (findShortestPathIndex(paths)) {
                case 0:
                    return "PC0--R0--R5--R6--PC2";
                case 1:
                    return "PC0--R0--R1--R4--R6--PC2";
                case 2:
                    return "PC0--R0--R1--R2--R4--R6--PC2";
                default:
                    System.err.println("Invalid path comparison for " + startPc + " and " + endPc);
                    return "No path";
            }
        } else if ((startPc.equals("PC1") && endPc.equals("PC2")) || (startPc.equals("PC2") && endPc.equals("PC1"))) {
            int paths[] = { getPc1Pc2PathCost0(), getPc1Pc2PathCost1(), getPc1Pc2PathCost2() };
            switch (findShortestPathIndex(paths)) {
                case 0:
                    return "PC1--R3--R2--R4--R6--PC2";
                case 1:
                    return "PC1--R3--R2--R1--R4--R6--PC2";
                case 2:
                    return "PC1--R3--R2--R1--R0--R5--R6--PC2";
                default:
                    System.err.println("Invalid path comparison for " + startPc + " and " + endPc);
                    return "No path";
            }
        }
        System.err.println("No path found between " + startPc + " and " + endPc);
        return "No path";
    }

    private int findShortestPathIndex(int paths[]) {
        int shortestIndex = 0;
        int shortestCost = paths[0];
        
        for (int i = 1; i < paths.length; i++) {
            if (paths[i] < shortestCost) {
                shortestCost = paths[i];
                shortestIndex = i;
            }
        }
        
        return shortestIndex;
    }

    // PC0 PC2 path
    // 1. R0--R5--R6
    private int getPc0Pc2PathCost0() {
        return getR0R5Cost() + getR5R6Cost();
    }

    // 2. R0--R1--R4--R6
    private int getPc0Pc2PathCost1() {
        return getR0R1Cost() + getR1R4Cost() + getR4R6Cost();
    }

    // 3. R0--R1--R2--R4--R6
    private int getPc0Pc2PathCost2() {
        return getR0R1Cost() + getR1R2Cost() + getR2R4Cost() + getR4R6Cost();
    }

    // PC0 PC1 path
    // 1. R0--R1--R2--R3
    private int getPc0Pc1PathCost0() {
        return getR0R1Cost() + getR1R2Cost() + getR2R3Cost();
    }

    // 2. R0--R1--R4--R2--R3
    private int getPc0Pc1PathCost1() {
        return getR0R1Cost() + getR1R4Cost() + getR2R4Cost() + getR2R3Cost();
    }

    // 3. R0--R5--R6--R4--R2--R3
    private int getPc0Pc1PathCost2() {
        return getR0R5Cost() + getR5R6Cost() + getR4R6Cost() + getR2R4Cost() + getR2R3Cost();
    }

    // 4. R0--R5--R6--R4--R1--R2--R3
    private int getPc0Pc1PathCost3() {
        return getR0R5Cost() + getR5R6Cost() + getR4R6Cost() + getR1R4Cost() + getR1R2Cost() + getR2R3Cost();
    }

    // PC1 PC2 path
    // 1. R3--R2--R4--R6
    private int getPc1Pc2PathCost0() {
        return getR2R3Cost() + getR2R4Cost() + getR4R6Cost();
    }

    // 2. PC1 || R3--R2--R1--R4--R6 || PC2
    private int getPc1Pc2PathCost1() {
        return getR2R3Cost() + getR1R2Cost() + getR1R4Cost() + getR4R6Cost();
    }

    // 3. PC1 || R3--R2--R1--R0--R5--R6 || PC2
    private int getPc1Pc2PathCost2() {
        return getR2R3Cost() + getR1R2Cost() + getR0R1Cost() + getR0R5Cost() + getR5R6Cost();
    }

    private int getPortCost(String name) {
        // TODO: Temporary, need to change so it will only be Fastethernet
        if (name.equals("Fastethernet 0/0") || name.equals("Fastethernet 0/1") || name.equals("Fastethernet 0/2") || name.equals("FastEthernet")) {
            return 10;
        } else {
            return 1;
        }
    }

    private int getSectionCost(Router firstRouter, int firstRouterPort, Router secondRouter, int secondRouterPort) {
        String firstRouterPortName = firstRouter.getNICList().get(firstRouterPort).getType();
        String secondRouterPortName = secondRouter.getNICList().get(secondRouterPort).getType();

        if (getPortCost(firstRouterPortName) == 10 || getPortCost(secondRouterPortName) == 10) {
            return 10;
        } else {
            return 1;
        }
    }

    public int getR0R5Cost() {
        return getSectionCost(routers[0], 2, routers[5], 0);
    }

    public int getR0R1Cost() {
        return getSectionCost(routers[0], 1, routers[1], 0);
    }

    public int getR5R6Cost() {
        return getSectionCost(routers[5], 1, routers[6], 0);
    }

    public int getR4R6Cost() {
        return getSectionCost(routers[4], 2, routers[6], 1);
    }

    public int getR1R4Cost() {
        return getSectionCost(routers[1], 2, routers[4], 0);
    }

    public int getR1R2Cost() {
        return getSectionCost(routers[1], 1, routers[2], 0);
    }

    public int getR2R4Cost() {
        return getSectionCost(routers[2], 2, routers[4], 1);
    }

    public int getR2R3Cost() {
        return getSectionCost(routers[2], 1, routers[3], 0);
    }
}
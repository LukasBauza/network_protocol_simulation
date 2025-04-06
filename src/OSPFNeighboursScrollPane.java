import javax.swing.*;

public class OSPFNeighboursScrollPane extends JScrollPane {
    JTable table;
    String[] columnNames = { "Neighbour ID", "Pri", "State", "Dead Time", "Address", "Interface" };
    Router router;

    public OSPFNeighboursScrollPane(Router router) {
        this.router = router;
        this.table = new JTable(getRows(), this.columnNames);
        super.setViewportView(table);
        setColumnWidth();
    }

    private int getConnectionCount() {
        int count = 0;
        for (NIC nic : router.getNICList()) {
            if (nic.isConnected()) {
                count++;
            }
        }
        return count;
    }

    private String[][] getRows() {
        String[][] rows = new String[getConnectionCount()][columnNames.length];
        for (int i = 0; i < getConnectionCount(); i++) {
            NIC currentNic = router.getNICList().get(i).getConnectedNIC();
            // TODO:
            // Neighbour ID
            rows[i][0] = router.getRid();
            // Pri
            rows[i][1] = Integer.toString(currentNic.getPriority());
            // State
            rows[i][2] = currentNic.getState();
            // Dead time
            rows[i][3] = currentNic.getDeadTime();
            // Address
            if (currentNic.getIpAddress() != null) {
                rows[i][4] = currentNic.getIpAddress().toString();
            }
            // Interface
            rows[i][5] = currentNic.getName();
        }
        return rows;
    }

    private void setColumnWidth() {
        // Neighbor ID
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        // Pri
        table.getColumnModel().getColumn(1).setPreferredWidth(2);
        // State
        table.getColumnModel().getColumn(2).setPreferredWidth(2);
        // Dead Time
        table.getColumnModel().getColumn(3).setPreferredWidth(5);
    }
}
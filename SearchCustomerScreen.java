import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class SearchCustomerScreen extends JFrame {
    private JTextField textFieldCustomerID;
    private JTable tableCustomer;
    private DefaultTableModel dtm;
    private JLabel labelTotalValue;
    private OrderController orderController; // Controller to interact with orders

    public SearchCustomerScreen(OrderController orderController) {
        this.orderController = orderController;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setTitle("Search Customer");

        // Back button
        JButton buttonBack = new JButton("Back");
        buttonBack.setOpaque(true);
        buttonBack.setBackground(Color.RED);
        buttonBack.setForeground(Color.WHITE);
        buttonBack.setFont(new Font(null, Font.BOLD, 20));
        buttonBack.addActionListener(evt -> dispose());

        JPanel buttonBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonBackPanel.add(buttonBack);

        // Search input
        JPanel subTopPanel = new JPanel(new GridLayout(1, 3));
        JLabel labelCustomer = new JLabel("Enter Customer ID: ");
        labelCustomer.setFont(new Font(null, Font.BOLD, 16));
        labelCustomer.setHorizontalAlignment(JLabel.CENTER);

        textFieldCustomerID = new JTextField();
        JButton buttonSearch = new JButton("Search");
        buttonSearch.setOpaque(true);
        buttonSearch.setBackground(Color.decode("#00cccb"));
        buttonSearch.setForeground(Color.WHITE);
        buttonSearch.setFont(new Font(null, Font.BOLD, 14));
        buttonSearch.addActionListener(evt -> searchCustomerOrders());

        subTopPanel.add(labelCustomer);
        subTopPanel.add(textFieldCustomerID);
        subTopPanel.add(buttonSearch);

        // Table to display customer orders
        JPanel subCenterPanel = new JPanel(new BorderLayout());
        String[] headers = new String[]{"Size", "Qty", "Amount"};
        dtm = new DefaultTableModel(headers, 0);
        tableCustomer = new JTable(dtm);
        tableCustomer.setFont(new Font(null, Font.PLAIN, 13));
        JScrollPane tablePane = new JScrollPane(tableCustomer);

        // Total label
        JPanel subBottomPanel = new JPanel(new GridLayout(1, 2));
        JLabel labelTotal = new JLabel("Total: ");
        labelTotal.setFont(new Font(null, Font.BOLD, 15));

        labelTotalValue = new JLabel("0.00");
        labelTotalValue.setFont(new Font(null, Font.BOLD, 15));
        labelTotalValue.setHorizontalAlignment(JLabel.RIGHT);

        subBottomPanel.add(labelTotal);
        subBottomPanel.add(labelTotalValue);

        subCenterPanel.add("Center", tablePane);
        subCenterPanel.add("South", subBottomPanel);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add("North", subTopPanel);
        mainPanel.add("Center", subCenterPanel);

        add("North", buttonBackPanel);
        add("Center", mainPanel);
    }

    private void searchCustomerOrders() {
        String customerId = textFieldCustomerID.getText().trim();

        if (customerId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Customer ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fetch orders from OrderController
        CustomerOrder[] orders = orderController.getOrdersByCustomerId(customerId);

        if (orders == null) {
            JOptionPane.showMessageDialog(this, "No orders found for Customer ID: " + customerId, "Error", JOptionPane.ERROR_MESSAGE);
            dtm.setRowCount(0); // Clear table
            labelTotalValue.setText("0.00");
            return;
        }

        // Populate table with orders
        dtm.setRowCount(0); // Clear table before adding new rows
        double totalAmount = 0;

        for (CustomerOrder order : orders) {
            String[] sizes = order.getAllSizes();
            int[] quantities = order.getSizeQuantities();
            double[] amounts = order.getSizeAmounts();

            for (int i = 0; i < sizes.length; i++) {
                dtm.addRow(new Object[]{sizes[i], quantities[i], String.format("%.2f", amounts[i])});
                totalAmount += amounts[i];
            }
        }

        labelTotalValue.setText(String.format("%.2f", totalAmount));
    }
}

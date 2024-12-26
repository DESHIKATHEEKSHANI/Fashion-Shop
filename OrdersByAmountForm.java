import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class OrdersByAmountForm extends JFrame {

    OrdersByAmountForm() {
        setTitle("Orders Sorted by Amount");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[][] orderData = getAllOrdersByAmountData();

        String[] columnNames = {"Order ID", "Customer ID", "Size", "QTY", "Amount", "Status"};

        JTable table = new JTable(orderData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 102, 102));
        backButton.setForeground(Color.WHITE); 
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> dispose());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(backButton, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private String[][] getAllOrdersByAmountData() {
        final int MAX_ORDERS = 100;
        
        String[] orderIDs = new String[MAX_ORDERS];
        String[] customerIDs = new String[MAX_ORDERS];
        String[] sizes = new String[MAX_ORDERS];
        int[] quantities = new int[MAX_ORDERS];
        double[] amounts = new double[MAX_ORDERS];
        String[] statuses = new String[MAX_ORDERS];
        int orderCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            String orderID = null, customerID = null, size = null, status = null;
            int qty = 0;
            double amount = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Order ID:")) {
                    if (orderID != null) {
                        orderIDs[orderCount] = orderID;
                        customerIDs[orderCount] = customerID;
                        sizes[orderCount] = size;
                        quantities[orderCount] = qty;
                        amounts[orderCount] = amount;
                        statuses[orderCount] = status;
                        orderCount++;
                    }
                    orderID = line.split(":")[1].trim();
                } else if (line.startsWith("Customer ID:")) {
                    customerID = line.split(":")[1].trim();
                } else if (line.startsWith("Size:")) {
                    size = line.split(":")[1].trim();
                } else if (line.startsWith("Quantity:")) {
                    qty = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Amount:")) {
                    amount = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("Status:")) {
                    status = line.split(":")[1].trim();
                }
            }

            if (orderID != null) {
                orderIDs[orderCount] = orderID;
                customerIDs[orderCount] = customerID;
                sizes[orderCount] = size;
                quantities[orderCount] = qty;
                amounts[orderCount] = amount;
                statuses[orderCount] = status;
                orderCount++;
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        for (int i = 0; i < orderCount - 1; i++) {
            for (int j = 0; j < orderCount - i - 1; j++) {
                if (amounts[j] < amounts[j + 1]) {
                    double tempAmount = amounts[j];
                    amounts[j] = amounts[j + 1];
                    amounts[j + 1] = tempAmount;

                    String tempOrderID = orderIDs[j];
                    orderIDs[j] = orderIDs[j + 1];
                    orderIDs[j + 1] = tempOrderID;

                    String tempCustomerID = customerIDs[j];
                    customerIDs[j] = customerIDs[j + 1];
                    customerIDs[j + 1] = tempCustomerID;

                    String tempSize = sizes[j];
                    sizes[j] = sizes[j + 1];
                    sizes[j + 1] = tempSize;

                    int tempQty = quantities[j];
                    quantities[j] = quantities[j + 1];
                    quantities[j + 1] = tempQty;

                    String tempStatus = statuses[j];
                    statuses[j] = statuses[j + 1];
                    statuses[j + 1] = tempStatus;
                }
            }
        }

        String[][] data = new String[orderCount][6];
        for (int i = 0; i < orderCount; i++) {
            data[i][0] = orderIDs[i];
            data[i][1] = customerIDs[i];
            data[i][2] = sizes[i];
            data[i][3] = String.valueOf(quantities[i]);
            data[i][4] = String.format("%.2f", amounts[i]);
            data[i][5] = statuses[i];
        }

        return data;
    }

}

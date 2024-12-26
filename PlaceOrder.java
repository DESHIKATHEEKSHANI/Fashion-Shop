import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlaceOrderFrame extends JFrame {
    private JTextField orderIdField, customerIdField, sizeField, quantityField, amountField;
    private JComboBox<String> statusComboBox;
    private JButton addButton, clearButton;
    private List orderList; // Reference to the List object

    public PlaceOrderFrame(List orderList) {
        this.orderList = orderList;

        setTitle("Place Order");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        JLabel orderIdLabel = new JLabel("Order ID:");
        orderIdField = new JTextField(15);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdField = new JTextField(15);

        JLabel sizeLabel = new JLabel("Size (e.g., M):");
        sizeField = new JTextField(10);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(10);

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);

        JLabel statusLabel = new JLabel("Status:");
        String[] statuses = {"PROCESSING", "DELIVERING", "DELIVERED"};
        statusComboBox = new JComboBox<>(statuses);

        addButton = new JButton("Add Order");
        clearButton = new JButton("Clear");

        // Layout
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.add(orderIdLabel);
        panel.add(orderIdField);
        panel.add(customerIdLabel);
        panel.add(customerIdField);
        panel.add(sizeLabel);
        panel.add(sizeField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(statusLabel);
        panel.add(statusComboBox);
        panel.add(addButton);
        panel.add(clearButton);

        add(panel, BorderLayout.CENTER);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void placeOrder() {
        try {
            String orderID = orderIdField.getText().trim();
            String customerID = customerIdField.getText().trim();
            String size = sizeField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double amount = Double.parseDouble(amountField.getText().trim());
            String status = (String) statusComboBox.getSelectedItem();

            // Validation
            if (orderID.isEmpty() || customerID.isEmpty() || size.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (quantity <= 0 || amount <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity and amount must be greater than 0!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create the order and add it to the list
            String[] sizes = {size};
            int[] quantities = {quantity};
            double[] amounts = {amount};
            CustomerOrder order = new CustomerOrder(orderID, customerID, sizes, quantities, amounts, status);
            orderList.addOrder(order);

            JOptionPane.showMessageDialog(this, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format for quantity or amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        orderIdField.setText("");
        customerIdField.setText("");
        sizeField.setText("");
        quantityField.setText("");
        amountField.setText("");
        statusComboBox.setSelectedIndex(0);
    }
}

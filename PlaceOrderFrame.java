import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class PlaceOrderFrame extends JFrame {
    private JTextField customerIDField, sizeField, qtyField, amountField;
    private JLabel orderIDLabel;
    private List orderList;
    private static final int[] T_SHIRT_PRICES = {600, 800, 900, 1000, 1100, 1200};
    private static final String[] SIZES = {"XS", "S", "M", "L", "XL", "XXL"};

    public PlaceOrderFrame(List orderList) {
        this.orderList = orderList;

        setTitle("Place Order");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        mainPanel.add(new JLabel("Order ID:"));
        orderIDLabel = new JLabel(generateOrderID());
        orderIDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(orderIDLabel);

        mainPanel.add(new JLabel("Customer ID:"));
        customerIDField = new JTextField();
        mainPanel.add(customerIDField);

        mainPanel.add(new JLabel("Size (XS/S/M/L/XL/XXL):"));
        sizeField = new JTextField();
        mainPanel.add(sizeField);

        mainPanel.add(new JLabel("Quantity:"));
        qtyField = new JTextField();
        mainPanel.add(qtyField);

        mainPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        amountField.setEditable(false);
        mainPanel.add(amountField);

        JButton placeOrderButton = new JButton("Place Order");
        placeOrderButton.setFont(new Font("Arial", Font.BOLD, 14));
        placeOrderButton.setBackground(new Color(0, 200, 200));
        placeOrderButton.setForeground(Color.WHITE);
        placeOrderButton.setPreferredSize(new Dimension(150, 40));
        placeOrderButton.addActionListener(e -> placeOrder());
        mainPanel.add(placeOrderButton);

        add(mainPanel, BorderLayout.CENTER);
    }

    private String generateOrderID() {
        int counter = OrderController.loadOrderCounter();
        return "ODR#" + String.format("%06d", counter);
    }

    private void placeOrder() {
        String customerID = customerIDField.getText();
        String size = sizeField.getText();
        if (!customerID.matches("0\\d{9}")) {
				JOptionPane.showMessageDialog(null, "Invalid Contact Number", "Error", JOptionPane.ERROR_MESSAGE);
				return;
		}
        int sizeIndex = -1;
        for (int i = 0; i < SIZES.length; i++) {
            if (SIZES[i].equalsIgnoreCase(size)) {
                sizeIndex = i;
                break;
            }
        }
        if (sizeIndex == -1) {
            JOptionPane.showMessageDialog(this, "Invalid Size");
            return;
        }
        int qty = Integer.parseInt(qtyField.getText());
        double amount = T_SHIRT_PRICES[sizeIndex] * qty;
        amountField.setText(String.valueOf(amount));

        String orderID = generateOrderID();
        CustomerOrder order = new CustomerOrder(orderID, customerID, new String[]{size}, new int[]{qty}, new double[]{amount}, "PROCESSING");
        orderList.addOrder(order);

        OrderController.saveOrderToFile(order);
        int counter = OrderController.loadOrderCounter() + 1;
        OrderController.saveOrderCounter(counter);

        JOptionPane.showMessageDialog(this, "Order placed successfully!");
        dispose();
    }
}

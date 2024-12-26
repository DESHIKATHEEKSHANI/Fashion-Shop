import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class ChangeOrderStatusForm extends JFrame {
    private JLabel labelOrderID;
    private JTextField textFieldOrderID;

    private JLabel labelCustomerIDValue;
    private JLabel labelSizeValue;
    private JLabel labelQtyValue;
    private JLabel labelAmountValue;
    private JLabel labelStatusValue;

    private JButton buttonSearch;
    private JButton buttonChangeStatus;

    private OrderController orderController;

    public ChangeOrderStatusForm() {
        //orderController = new OrderController();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 520);
        setLocationRelativeTo(null);
        setTitle("Change Order Status");

        //........
        buttonSearch.addActionListener(event -> searchOrder());
        buttonChangeStatus.addActionListener(event -> changeOrderStatus());
    }

    private void searchOrder() {
        String orderId = textFieldOrderID.getText().trim().toUpperCase();
        if (orderId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Order ID", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Order order = orderController.searchOrderById(orderId);
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Order ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        labelCustomerIDValue.setText(order.getCustomerId());
        labelSizeValue.setText(order.getSize());
        labelQtyValue.setText(String.valueOf(order.getQuantity()));
        labelAmountValue.setText(String.valueOf(order.getAmount()));
        labelStatusValue.setText(order.getStatus());

        buttonChangeStatus.setEnabled(true);
    }

    private void changeOrderStatus() {
        String orderId = textFieldOrderID.getText().trim().toUpperCase();
        boolean updated = orderController.updateOrderStatus(orderId);

        if (updated) {
            JOptionPane.showMessageDialog(this, "Order status updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            searchOrder(); // Refresh details
        } else {
            JOptionPane.showMessageDialog(this, "Order is already Delivered or not found.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SearchOrderScreen extends JFrame {

    private JLabel labelOrderID;
    private JTextField textFieldOrderID;

    private JLabel labelCustomerID;
    private JLabel labelSize;
    private JLabel labelQty;
    private JLabel labelAmount;
    private JLabel labelStatus;

    private JLabel labelCustomerIDValue;
    private JLabel labelSizeValue;
    private JLabel labelQtyValue;
    private JLabel labelAmountValue;
    private JLabel labelStatusValue;

    private JButton buttonBack;
    private JButton buttonSearch;

    public SearchOrderScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 520);
        setLocationRelativeTo(null);
        setTitle("Search Order");

        JPanel subTopPanel = new JPanel(new GridLayout(1, 3));
        subTopPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        labelOrderID = new JLabel("Enter Order ID: ");
        labelOrderID.setFont(new Font(null, Font.BOLD, 16));
        labelOrderID.setHorizontalAlignment(JLabel.CENTER);

        textFieldOrderID = new JTextField();

        buttonSearch = new JButton("Search");
        buttonSearch.setOpaque(true);
        buttonSearch.setBackground(Color.decode("#00cccb"));
        buttonSearch.setForeground(Color.WHITE);
        buttonSearch.setFont(new Font(null, Font.BOLD, 14));

        subTopPanel.add(labelOrderID);
        subTopPanel.add(textFieldOrderID);
        subTopPanel.add(buttonSearch);

        JPanel labelPanel = new JPanel(new GridLayout(5, 1, 10, 0));
        labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 50, 30));

        labelCustomerID = new JLabel("Customer ID: ");
        labelSize = new JLabel("Size: ");
        labelQty = new JLabel("QTY: ");
        labelAmount = new JLabel("Amount: ");
        labelStatus = new JLabel("Status: ");

        JLabel[] labels = new JLabel[]{labelCustomerID, labelSize, labelQty, labelAmount, labelStatus};

        for (JLabel label : labels) {
            label.setFont(new Font(null, Font.BOLD, 17));
            labelPanel.add(label);
        }

        JPanel valuePanel = new JPanel(new GridLayout(5, 1, 10, 0));
        valuePanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 50, 30));

        labelCustomerIDValue = new JLabel("");
        labelSizeValue = new JLabel("");
        labelQtyValue = new JLabel("");
        labelAmountValue = new JLabel("");
        labelStatusValue = new JLabel("");

        JLabel[] values = new JLabel[]{labelCustomerIDValue, labelSizeValue, labelQtyValue, labelAmountValue, labelStatusValue};

        for (JLabel value : values) {
            value.setFont(new Font(null, Font.BOLD, 15));
            valuePanel.add(value);
        }

        buttonBack = new JButton("Back");
        buttonBack.setOpaque(true);
        buttonBack.setBackground(Color.RED);
        buttonBack.setForeground(Color.WHITE);
        buttonBack.setFont(new Font(null, Font.BOLD, 20));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        JPanel buttonBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonBackPanel.add(buttonBack);

        topPanel.add(buttonBackPanel);
        topPanel.add(subTopPanel);

        add("North", topPanel);
        add("West", labelPanel);
        add("Center", valuePanel);

        buttonBack.addActionListener(event -> dispose());
        buttonSearch.addActionListener(event -> searchOrder());
    }

    private void searchOrder() {
        String orderId = textFieldOrderID.getText().trim().toUpperCase();

        if (orderId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Order ID", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Order ID:")) {
                    String currentOrderId = line.split(":")[1].trim();
                    if (currentOrderId.equalsIgnoreCase(orderId)) {
                        found = true;

                        labelCustomerIDValue.setText(reader.readLine().split(":")[1].trim()); 
                        labelSizeValue.setText(reader.readLine().split(":")[1].trim());
                        labelQtyValue.setText(reader.readLine().split(":")[1].trim());
                        labelAmountValue.setText(reader.readLine().split(":")[1].trim());
                        labelStatusValue.setText(reader.readLine().split(":")[1].trim().toUpperCase());

                        return; 
                    }
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Order ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

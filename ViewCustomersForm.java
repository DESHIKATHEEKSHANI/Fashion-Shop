import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ViewCustomersForm extends JFrame {
    private JTable table;

    public ViewCustomersForm() {
        setTitle("View Customers");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 102, 102));
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(80, 40));
        backButton.addActionListener(e -> dispose());
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        String[][] data = getCustomerDataFromFile();
        String[] columns = {"Customer ID", "QTY", "Total Amount"};

        table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private String[][] getCustomerDataFromFile() {
        String[] customerIDs = new String[100];
        int[] quantities = new int[100];
        double[] amounts = new double[100];
        int customerCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            String currentCustomerID = null;
            int currentQty = 0;
            double currentAmount = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Customer ID:")) {
                    currentCustomerID = line.split(":")[1].trim();
                } else if (line.startsWith("Quantity:")) {
                    currentQty = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Amount:")) {
                    currentAmount = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("--------------------------------------------------------")) {
                    if (currentCustomerID != null) {
                        boolean found = false;

                        for (int i = 0; i < customerCount; i++) {
                            if (customerIDs[i].equals(currentCustomerID)) {
                                quantities[i] += currentQty;
                                amounts[i] += currentAmount;
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            customerIDs[customerCount] = currentCustomerID;
                            quantities[customerCount] = currentQty;
                            amounts[customerCount] = currentAmount;
                            customerCount++;
                        }

                        currentCustomerID = null;
                        currentQty = 0;
                        currentAmount = 0.0;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        String[][] data = new String[customerCount][3];
        for (int i = 0; i < customerCount; i++) {
            data[i][0] = customerIDs[i];
            data[i][1] = String.valueOf(quantities[i]);
            data[i][2] = String.format("%.2f", amounts[i]);
        }

        return data;
    }

}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class AllCustomerReportsForm extends JFrame {
    private JTable table;

    public AllCustomerReportsForm() {
        setTitle("All Customer Reports");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(255, 102, 102));
        backButton.addActionListener(e -> dispose());
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        String[][] data = getAllCustomerReportData();
        String[] columns = {"Customer ID", "XS", "S", "M", "L", "XL", "XXL", "Amount"};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private String[][] getAllCustomerReportData() {
        String[] customerIDs = new String[100];
        int[][] sizeQuantities = new int[100][6]; 
        double[] amounts = new double[100];
        int customerCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            String currentCustomerID = null;
            String currentSize = null;
            int currentQty = 0;
            double currentAmount = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Customer ID:")) {
                    currentCustomerID = line.split(":")[1].trim();
                } else if (line.startsWith("Size:")) {
                    currentSize = line.split(":")[1].trim().toLowerCase();
                } else if (line.startsWith("Quantity:")) {
                    currentQty = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Amount:")) {
                    currentAmount = Double.parseDouble(line.split(":")[1].trim());
                } else if (line.startsWith("--------------------------------------------------------")) {
                    if (currentCustomerID != null && currentSize != null) {
                        boolean found = false;

                        for (int i = 0; i < customerCount; i++) {
                            if (customerIDs[i].equals(currentCustomerID)) {
                                sizeQuantities[i][getSizeIndex(currentSize)] += currentQty;
                                amounts[i] += currentAmount;
                                found = true;
                                break;
                            }
                        }

                        if (!found) {
                            customerIDs[customerCount] = currentCustomerID;
                            sizeQuantities[customerCount][getSizeIndex(currentSize)] = currentQty;
                            amounts[customerCount] = currentAmount;
                            customerCount++;
                        }

                        currentCustomerID = null;
                        currentSize = null;
                        currentQty = 0;
                        currentAmount = 0.0;
                    }
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        String[][] data = new String[customerCount][8];
        for (int i = 0; i < customerCount; i++) {
            data[i][0] = customerIDs[i]; 
            for (int j = 0; j < 6; j++) {
                data[i][j + 1] = String.valueOf(sizeQuantities[i][j]); 
            }
            data[i][7] = String.format("%.2f", amounts[i]); 
        }

        return data;
    }

    private int getSizeIndex(String size) {
        switch (size) {
            case "xs":
                return 0;
            case "s":
                return 1;
            case "m":
                return 2;
            case "l":
                return 3;
            case "xl":
                return 4;
            case "xxl":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid size: " + size);
        }
    }

}

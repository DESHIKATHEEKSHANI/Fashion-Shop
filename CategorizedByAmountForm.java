import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CategorizedByAmountForm extends JFrame {

    CategorizedByAmountForm() {
        setTitle("Best Selling Items by Amount");
        setSize(500, 400); 
        setLocationRelativeTo(null);  
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        String[][] sizeData = getAllSizeReportDataSortedByAmount();

        String[] columnNames = {"Size", "Quantity", "Total Amount"};

        JTable table = new JTable(sizeData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true); 
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(255, 102, 102));
        backButton.setForeground(Color.WHITE); 
        backButton.setFocusPainted(false);
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private String[][] getAllSizeReportDataSortedByAmount() {
        String[] sizes = {"XS", "S", "M", "L", "XL", "XXL"};
        int[] quantities = new int[6];
        double[] amounts = new double[6];

        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            String line;
            String currentSize;
            int currentQty = 0;
            double currentAmount = 0.0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Size:")) {
                    currentSize = line.split(":")[1].trim().toLowerCase();
                    if (currentSize.equals("xs")) {
                        currentSize = "XS";
                    } else if (currentSize.equals("s")) {
                        currentSize = "S";
                    } else if (currentSize.equals("m")) {
                        currentSize = "M";
                    } else if (currentSize.equals("l")) {
                        currentSize = "L";
                    } else if (currentSize.equals("xl")) {
                        currentSize = "XL";
                    } else if (currentSize.equals("xxl")) {
                        currentSize = "XXL";
                    }
                    
                    int sizeIndex = getSizeIndex(currentSize);
                    quantities[sizeIndex] += currentQty;
                    amounts[sizeIndex] += currentAmount;
                } else if (line.startsWith("Quantity:")) {
                    currentQty = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("Amount:")) {
                    currentAmount = Double.parseDouble(line.split(":")[1].trim());
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        sortSizesByAmountDescending(sizes, quantities, amounts);

        String[][] data = new String[6][3];
        for (int i = 0; i < 6; i++) {
            data[i][0] = sizes[i];
            data[i][1] = String.valueOf(quantities[i]);
            data[i][2] = String.format("%.2f", amounts[i]);
        }

        return data;
    }

    private void sortSizesByAmountDescending(String[] sizes, int[] quantities, double[] amounts) {
        for (int i = 0; i < amounts.length - 1; i++) {
            for (int j = i + 1; j < amounts.length; j++) {
                if (amounts[i] < amounts[j]) {
                    double tempAmount = amounts[i];
                    amounts[i] = amounts[j];
                    amounts[j] = tempAmount;

                    int tempQty = quantities[i];
                    quantities[i] = quantities[j];
                    quantities[j] = tempQty;

                    String tempSize = sizes[i];
                    sizes[i] = sizes[j];
                    sizes[j] = tempSize;
                }
            }
        }
    }

    private int getSizeIndex(String size) {
        switch (size) {
            case "XS":
                return 0;
            case "S":
                return 1;
            case "M":
                return 2;
            case "L":
                return 3;
            case "XL":
                return 4;
            case "XXL":
                return 5;
            default:
                throw new IllegalArgumentException("Invalid size: " + size);
        }
    }

}

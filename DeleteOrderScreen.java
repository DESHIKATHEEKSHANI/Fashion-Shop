import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DeleteOrderScreen extends JFrame {
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
    private JButton buttonDelete;

    public DeleteOrderScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 520);
        setLocationRelativeTo(null);
        setTitle("Delete Order");

        JPanel subTopPanel = new JPanel(new GridLayout(1, 3));
        subTopPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        labelOrderID = new JLabel("Enter Order ID: ");
        labelOrderID.setFont(new Font(null, Font.BOLD, 16));
        labelOrderID.setHorizontalAlignment(JLabel.CENTER);

        textFieldOrderID = new JTextField();

        buttonDelete = new JButton("Delete");
        buttonDelete.setOpaque(true);
        buttonDelete.setBackground(Color.RED);
        buttonDelete.setForeground(Color.WHITE);
        buttonDelete.setFont(new Font(null, Font.BOLD, 14));

        subTopPanel.add(labelOrderID);
        subTopPanel.add(textFieldOrderID);
        subTopPanel.add(buttonDelete);

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
        buttonBack.setBackground(Color.PINK);
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
        buttonDelete.addActionListener(event -> deleteOrder());
    }
    
	private void deleteOrder() {
		String orderId = textFieldOrderID.getText().trim();

		if (orderId.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter Order ID", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			File file = new File("orders.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			File tempFile = new File("temp_orders.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String line;
			boolean found = false; 
			boolean isCurrentOrder = false; 

			while ((line = reader.readLine()) != null) {
				if (line.startsWith("Order ID:")) {
					if (line.contains(orderId)) {
						found = true;
						isCurrentOrder = true;

						labelCustomerIDValue.setText(reader.readLine().split(":")[1].trim());
						labelSizeValue.setText(reader.readLine().split(":")[1].trim());
						labelQtyValue.setText(reader.readLine().split(":")[1].trim());
						labelAmountValue.setText(reader.readLine().split(":")[1].trim());
						labelStatusValue.setText(reader.readLine().split(":")[1].trim().toUpperCase());

						reader.readLine();
						continue; 
					} else {
						isCurrentOrder = false;
					}
				}

				if (!isCurrentOrder) {
					writer.write(line);
					writer.newLine();
				}
			}
			reader.close();
			writer.close();

			if (!found) {
				JOptionPane.showMessageDialog(this, "Order ID not found", "Error", JOptionPane.ERROR_MESSAGE);
				tempFile.delete(); 
				return;
			}

			int confirmation = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to delete this order?",
					"Delete Confirmation", JOptionPane.YES_NO_OPTION);

			if (confirmation == JOptionPane.YES_OPTION) {
				if (file.delete() && tempFile.renameTo(file)) {
					JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Error updating file.", "Error", JOptionPane.ERROR_MESSAGE);
				}

				labelCustomerIDValue.setText("");
				labelSizeValue.setText("");
				labelQtyValue.setText("");
				labelAmountValue.setText("");
				labelStatusValue.setText("");
			} else {
				tempFile.delete(); 
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error handling the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}

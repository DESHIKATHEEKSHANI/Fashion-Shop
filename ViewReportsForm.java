import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.border.*;
import java.io.*;

public class ViewReportsForm extends JFrame {

    public ViewReportsForm() {
        setTitle("View Reports");
        setSize(600, 300); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.LIGHT_GRAY);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.PINK);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(80, 40));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        JButton viewCustomersButton = new JButton("View Customers");
        viewCustomersButton.setBackground(Color.GREEN);
        viewCustomersButton.setPreferredSize(new Dimension(130, 40));
        viewCustomersButton.addActionListener(e -> new ViewCustomersForm());

        JButton bestInCustomersButton = new JButton("Best in Customers");
        bestInCustomersButton.setBackground(Color.GREEN);
        bestInCustomersButton.setPreferredSize(new Dimension(140, 40));
		bestInCustomersButton.addActionListener(e -> new BestInCustomersForm());
		
        JButton allCustomersButton = new JButton("All Customers");
        allCustomersButton.setBackground(Color.GREEN);
        allCustomersButton.setPreferredSize(new Dimension(120, 40));
        allCustomersButton.addActionListener(e -> new AllCustomerReportsForm());

        JButton categorizedByQtyButton = new JButton("Categorized By QTY");
        categorizedByQtyButton.setBackground(Color.BLUE);
        categorizedByQtyButton.setForeground(Color.WHITE);
        categorizedByQtyButton.setPreferredSize(new Dimension(160, 40));
        categorizedByQtyButton.addActionListener(e -> new CategorizedByQtyForm());

        JButton categorizedByAmountButton = new JButton("Categorized By Amount");
        categorizedByAmountButton.setBackground(Color.BLUE);
        categorizedByAmountButton.setForeground(Color.WHITE);
        categorizedByAmountButton.setPreferredSize(new Dimension(180, 40));
        categorizedByAmountButton.addActionListener(e -> new CategorizedByAmountForm());
        
        JButton ordersByAmountButton = new JButton("Orders By Amount");
        ordersByAmountButton.setBackground(Color.GRAY);
        ordersByAmountButton.setForeground(Color.WHITE);
        ordersByAmountButton.setPreferredSize(new Dimension(160, 40));
        ordersByAmountButton.addActionListener(e -> new OrdersByAmountForm());

        JButton allOrdersButton = new JButton("All Orders");
        allOrdersButton.setBackground(Color.GRAY);
        allOrdersButton.setForeground(Color.WHITE);
        allOrdersButton.setPreferredSize(new Dimension(120, 40));
        allOrdersButton.addActionListener(e -> new AllOrdersForm());

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(viewCustomersButton, gbc);

        gbc.gridy = 1;
        mainPanel.add(bestInCustomersButton, gbc);

        gbc.gridy = 2;
        mainPanel.add(allCustomersButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(categorizedByQtyButton, gbc);

        gbc.gridy = 1;
        mainPanel.add(categorizedByAmountButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        mainPanel.add(ordersByAmountButton, gbc);

        gbc.gridy = 1;
        mainPanel.add(allOrdersButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}

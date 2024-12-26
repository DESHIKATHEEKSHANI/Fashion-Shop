import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;
import javax.swing.border.*;
import java.io.*;

public class HomePage extends JFrame {

    public HomePage(List orderList) {
        setTitle("Fashion Shop");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        JLabel headerLabel = new JLabel("Fashion Shop", JLabel.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(50, 100, 255));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton searchButton = createStyledButton("Search", 200, 50);
        JButton statusButton = createStyledButton("Status", 200, 50);
        JButton reportsButton = createStyledButton("Reports", 200, 50);
        JButton deleteButton = createStyledButton("Delete", 200, 50);

        buttonPanel.add(searchButton);
        buttonPanel.add(statusButton);
        buttonPanel.add(reportsButton);
        buttonPanel.add(deleteButton);
        
        searchButton.addActionListener(e -> showSearchOptions());
        statusButton.addActionListener(e -> new ChangeOrderStatusForm().setVisible(true));
        deleteButton.addActionListener(e -> new DeleteOrderScreen().setVisible(true));
        reportsButton.addActionListener(e -> new ViewReportsForm().setVisible(true));

        JPanel placeOrderPanel = new JPanel();
        placeOrderPanel.setBackground(Color.WHITE);
        JButton placeOrderButton = createStyledButton("Place Order", 200, 80);
        placeOrderButton.setBackground(new Color(0, 200, 200));
        placeOrderButton.setForeground(Color.BLACK);
        placeOrderButton.setFont(new Font("Arial", Font.BOLD, 18));
        placeOrderPanel.add(placeOrderButton);

        placeOrderButton.addActionListener(e -> new PlaceOrderFrame(orderList).setVisible(true));

        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.setBackground(Color.WHITE);
        westPanel.add(buttonPanel, BorderLayout.NORTH);
        westPanel.add(placeOrderPanel, BorderLayout.SOUTH);

        mainPanel.add(westPanel, BorderLayout.WEST);

        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("D:\\ICD113\\OOP-CRUD\\Images\\fashionshop.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(imageLabel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("Copyrights \u00A9 iCET 2023", JLabel.CENTER);
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        mainPanel.add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }
    
    private void showSearchOptions() {
        Object[] options = {"Search Customer", "Search Order", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Please select the option",
                "Search Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

       if (choice == 0) {
           // new SearchCustomerScreen().setVisible(true); 
        } else if (choice == 1) {
            new SearchOrderScreen().setVisible(true); 
        }
    }
}


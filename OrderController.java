import java.io.*;

public class OrderController {
	private List orderList;
    private static final String COUNTER_FILE = "counter.txt";
    
    public OrderController(List orderList) {
        this.orderList = orderList; 
    }
    
    public List getOrderList() {
        return orderList;
    }

    public static int loadOrderCounter() {
        File file = new File(COUNTER_FILE);
        int orderCounter = 1;
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null) {
                    orderCounter = Integer.parseInt(line.trim());
                }
            } catch (IOException e) {
            }
        }
        return orderCounter;
    }

    public static void saveOrderCounter(int orderCounter) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COUNTER_FILE))) {
            writer.write(String.valueOf(orderCounter));
        } catch (IOException e) {
        }
    }

    public static void saveOrderToFile(CustomerOrder order) {
        String fileName = "orders.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("Order ID: " + order.getOrderID() + "\n");
            writer.write("Customer ID: " + order.getCustomerID() + "\n");
            writer.write("Size: " + order.getAllSizes()[0] + "\n");
            writer.write("Quantity: " + order.getSizeQuantities()[0] + "\n");
            writer.write("Amount: " + order.getTotalAmount() + "\n");
            writer.write("Status: " + order.getStatus() + "\n");
            writer.write("--------------------------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public CustomerOrder[] getOrdersByCustomerId(String customerId) {
        int count = 0;
        List.Node current = orderList.head;

        while (current != null) {
            if (current.order.getCustomerID().equalsIgnoreCase(customerId)) {
                count++;
            }
            current = current.next;
        }

        if (count == 0) {
            return null; 
        }

        CustomerOrder[] orders = new CustomerOrder[count];
        int index = 0;
        current = orderList.head;

        while (current != null) {
            if (current.order.getCustomerID().equalsIgnoreCase(customerId)) {
                orders[index++] = current.order;
            }
            current = current.next;
        }

        return orders;
    }
}


public class CustomerOrder {
    private String orderID;
    private String customerID;
    private String[] sizes;  
    private int[] quantities;  
    private double[] amounts;  
    private String status;  
    

    public CustomerOrder(String orderID, String customerID, String[] sizes, int[] quantities, double[] amounts, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.sizes = sizes;
        this.quantities = quantities;
        this.amounts = amounts;
        this.status = status;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String[] getAllSizes() {
        return sizes;
    }

    public int[] getSizeQuantities() {
        return quantities;
    }

    public double[] getSizeAmounts() {
        return amounts;
    }

    public double getTotalAmount() {
        double total = 0;
        for (double amount : amounts) {
            total += amount;
        }
        return total;
    }
    
    public int getTotalQuantity() {
		int totalQuantity = 0;
		for (int quantity : quantities) {
			totalQuantity += quantity;
		}
		return totalQuantity;
	}


    public String getStatus() {
        return status; 
    }

    public void setStatus(String status) {
        this.status = status; 
    }
}


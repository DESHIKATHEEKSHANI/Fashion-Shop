class List {
    class Node {
        public CustomerOrder order;
        public Node next;

        public Node(CustomerOrder order) {
            this.order = order;
            this.next = null;
        }
    }

    public Node head;

    public List() {
        this.head = null;
    }

    public void add(CustomerOrder order) {
        Node newNode = new Node(order);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public boolean isEmpty() {
        return head == null;
    }
    
    public void displayOrders() {
        Node temp = head;
        while (temp != null) {
            CustomerOrder order = temp.order;
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Customer ID: " + order.getCustomerID());
            System.out.println("Total Amount: " + order.getTotalAmount());
            System.out.println("Status: " + order.getStatus());
            System.out.println("-----------------------------");
            temp = temp.next;
        }
    }
}

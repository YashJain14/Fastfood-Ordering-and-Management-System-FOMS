
package db;

import orders.*;
import java.util.HashMap;
import java.util.List;
/**
Represents a list of customer orders.
This class maintains a map of orders by order ID and a map of orders by branch ID. It provides methods to access orders, update order status, create new orders, and retrieve lists of orders based on different criteria.
*/
class OrderList {

    private HashMap<Integer, Order> orders;
    private HashMap<Integer, List<Order>> branchOrders;

    public OrderList() {
        orders = new HashMap<>();
        branchOrders = new HashMap<>();

    }

    

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public HashMap<Integer, List<Order>> getBranchOrders() {
        return branchOrders;
    }

    public HashMap<Integer, Order> dumpList(){
        return orders;
    }

}
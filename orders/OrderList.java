

package orders;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
/**
Stores and manages a collection of customer orders.
This class maintains a list of orders and provides methods to access orders, create new orders, update order status, and retrieve lists of orders based on different criteria. It also handles serialization for persistent storage of order data.
*/
public class OrderList implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient AtomicInteger lastId;
    private HashMap<Integer, Order> orders;
    private HashMap<Integer, List<Order>> branchOrders;

    public OrderList() {
        lastId = new AtomicInteger(0);
        orders = new HashMap<>();
        branchOrders = new HashMap<>();
    }

    public OrderState getOrderStatus(int orderId) {
        Order order = orders.get(orderId);
        return order != null ? order.getStatus() : null;
    }

    public void setOrderStatus(int orderId, OrderState newState) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(newState);
        }
    }

    public int createOrder(Cart cart, boolean isTakeaway, int branchId) {
        int orderId = lastId.incrementAndGet();
        Order newOrder = new Order(branchId, isTakeaway, cart);
        orders.put(orderId, newOrder);

        List<Order> branchOrderList = branchOrders.computeIfAbsent(branchId, k -> new ArrayList<>());
        branchOrderList.add(newOrder);

        return orderId;
    }

    public Order getOrder(int orderId) {
        return orders.get(orderId);
    }

    public List<Order> getUnfinishedOrders(int branchId) {
        List<Order> branchOrderList = branchOrders.getOrDefault(branchId, new ArrayList<>());
        return branchOrderList.stream()
                              .filter(order -> order.getStatus() != OrderState.COMPLETED)
                              .collect(Collectors.toList());
    }

    public void writeDB(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
        }
    }

    public static OrderList readDB(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            OrderList orderList = (OrderList) in.readObject();
            orderList.lastId = new AtomicInteger(orderList.orders.keySet().stream().max(Integer::compare).orElse(0));
            return orderList;
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        lastId = new AtomicInteger(orders.keySet().stream().max(Integer::compare).orElse(0));
    }
}

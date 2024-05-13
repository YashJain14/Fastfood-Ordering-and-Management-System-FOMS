
package db;
import orders.*;
import java.util.*;

import java.io.IOException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
/**
Manages the database operations related to customer orders.
This class interacts with the OrderList class to create new orders, retrieve orders, get lists of orders (all, unfinished, next), and handle database read/write operations for order data.
*/

public class OrderListController implements interfaces.DBController{
    // private static OrderList orderList = new OrderList();
    private OrderList orderList = new OrderList();
    private BranchDBController branchDBController; 

    public OrderListController(BranchDBController branchDBController){
        this.branchDBController = branchDBController;
    }


    public int createOrder(Cart cart, boolean isTakeaway, int branchId) {
        Order newOrder = new Order(branchId, isTakeaway, cart);

        this.addOrder(newOrder.getOrderId(), newOrder);

        return newOrder.getOrderId();
    }



    public boolean addOrder(int orderId, Order order){

        if (orderList.getOrder(orderId) != null){
            return false;
        }

        orderList.dumpList().put(orderId, order);

        List <Order> orders = orderList.getBranchOrders().get(order.getBranchId());

        if (orders == null){
            orderList.getBranchOrders().put(order.getBranchId(), new ArrayList<>());
            orders = orderList.getBranchOrders().get(order.getBranchId());
        }
        orders.add(order);


        return true;


    }

    public Order getOrder(int orderId) {
        return orderList.getOrder(orderId);
    }

    public List<Order> getAllOrders(){
        List<Order> orders = new ArrayList<>(orderList.dumpList().values());
        return orders;
    }

    public Order[] getUnfinishedOrders(int branchId) {
        List<Order> branchOrderList = orderList.getBranchOrders().get(branchId);
        return branchOrderList.stream()
                .filter(order -> order.getStatus().equals(OrderState.NEW_ORDER)) // Changed from getOrderState() to getStatus()
                .toArray(Order[]::new);
    }
    
    public Order getNextOrder(int branchId) {
        List<Order> branchOrderList = orderList.getBranchOrders().get(branchId);
        return branchOrderList.stream()
                .filter(order -> order.getStatus().equals(OrderState.NEW_ORDER)) // Changed from getOrderState() to getStatus()
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean writeDB(String filename) {

        HashMap<Integer, Order> orders = orderList.dumpList();
        String[] line = new String[7];

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Order order : orders.values()) {
                line[0] = Integer.toString(order.getBranchId());
                line[1] = Integer.toString(order.getOrderId());
                line[2] = order.getTakeaway() ? "1" : "0";


                line[3] = order.getStatus().name();

                line[4] = Double.toString(order.getPrice());

                List <String> itemStrings = new ArrayList<>();
               


                for( OrderItem orderItem: order.getOrderItems()) {
                	String[] itemDetails = new String[3]; 
                    
                    itemDetails[0] = Integer.toString(orderItem.getId());


                    itemDetails[1] = Integer.toString(orderItem.getQuantity());

                    itemDetails[2] = String.join(";", orderItem.getCustomisation());

                    if (itemDetails[2] == ""){
                        itemDetails[2] = "None";
                    }


                    itemStrings.add(String.join("-", itemDetails));
                
                }

                line[5] = String.join(":", itemStrings);

                if (order.getStatus() != OrderState.NEW_ORDER){
                    line[6] = order.getCancelTime().toString();
                }

                else{
                    line[6] = "";
                }

                for (String data : line){
                    bw.write(data);
                    bw.write(",");
                }
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean readDB(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            
            String line;


            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int branchId = Integer.parseInt(parts[0].trim());

                

                branch_management.Menu menu = branchDBController.getBranchById(branchId).getMenu();


                int orderId = Integer.parseInt(parts[1].trim());

                boolean isTakeAway = parts[2].trim() == "1";

                OrderState orderState = OrderState.valueOf(parts[3].trim());
                double price = Double.parseDouble(parts[4].trim());

                String itemStrings = parts[5].trim();

                String[] itemStringArr = itemStrings.split(":");
                List<OrderItem> orderItems = new ArrayList<>();

                for (String itemString : itemStringArr){
                    String[] itemDetails = itemString.split("-");

                    

                    branch_management.MenuItem item = menu.findItemByNo(Integer.parseInt(itemDetails[0].trim()));



                    int quantity = Integer.parseInt(itemDetails[1].trim());


                    
                     List <String> customisations = Arrays.asList(itemDetails[2].split(";"));
                    


                    OrderItem orderItem = new OrderItem(item, customisations, quantity);


                    orderItems.add(orderItem);

                }
                

                Order order = new Order(branchId, orderId, isTakeAway, orderState, price, orderItems);
                
                if (orderState != OrderState.NEW_ORDER){
                    String cancelTime = parts[6].trim();
                  order.setCancelTime(cancelTime);
                }
                
                this.addOrder(orderId, order);
            }



            reader.close();
            return true;
        } catch (IOException  e) {
            e.printStackTrace();
            return false;
        }   

    }

}
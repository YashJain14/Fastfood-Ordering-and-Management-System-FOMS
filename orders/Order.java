
package orders;

import java.util.List;

import java.time.LocalDateTime;


/**
Represents a customer order.
Each order has attributes such as order ID, branch ID, takeaway status, a list of order items, the current order status, the total price, and the cancellation time (if applicable). The class provides methods to manage the order status, retrieve order details, and calculate the total price.
*/

public class Order {
	
	
    private static int highestId = 0;
	private int orderId;
    private boolean isTakeaway;
    private List<OrderItem> orderItems;
    private OrderState status;
    private double totalPrice;
	private int branchId;

	private LocalDateTime cancellationTime;
   
    
	public Order(int branchId, boolean isTakeaway, Cart cart) {
		this.orderId = highestId++;
		this.isTakeaway = isTakeaway;
		this.orderItems = cart.getItems(); 
		this.status = (OrderState.NEW_ORDER);
		this.totalPrice = cart.getTotalCost();
		this.branchId = branchId;
		
    }

	public Order(int branchId,int id,  boolean isTakeAway,OrderState orderState, double price, List <OrderItem> orderItems){
		this.branchId = branchId;
		this.isTakeaway = isTakeAway;
		this.totalPrice = price;
		this.orderId = id;
		this.status = orderState;

		if (id >= highestId){
			highestId = id + 1;
		}
		this.orderItems = orderItems;

	}

	public LocalDateTime getCancelTime(){
		return cancellationTime;
	}

	public void setCancelTime(String time){
		this.cancellationTime = LocalDateTime.parse(time);

	}



	public void checkOrderStatus() {
		if (cancellationTime != null && LocalDateTime.now().isAfter(cancellationTime) && this.status == OrderState.READY_TO_PICKUP) {
			this.setStatus(OrderState.CANCELLED);
		}
	}
	


	
	
	// mark order as ready 
	public void markReady() {
		this.status = OrderState.READY_TO_PICKUP; 
		
		this.cancellationTime = LocalDateTime.now().plusMinutes(3);
	}
		
	// return List of Order Items
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

	
	public int getOrderId() {
		return orderId;
	}
	
	
	public boolean getTakeaway() {
		return isTakeaway;
	}
	
	
	public OrderState getStatus() {
		return status;
	}
	
	
	public void setStatus(OrderState status) {
		this.status = status;
	}
	
	public double getPrice() {
		return this.totalPrice;
	}

	public int getBranchId(){
		return this.branchId;
	}
}


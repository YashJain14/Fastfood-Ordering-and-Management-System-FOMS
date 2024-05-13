

package db;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import orders.Order;
/**
Periodically checks the status of orders and handles cancellations.
This class runs as a background task and checks the status of all orders at regular intervals. If an order has been in the "READY_TO_PICKUP" state for longer than the allowed time, it is automatically cancelled.
*/
public class OrderChecker extends TimerTask {
    private static OrderChecker instance;
    private OrderListController orderListController;
    private Timer timer;

    private OrderChecker(OrderListController orderListController) {
        this.orderListController = orderListController;
    }

    public static synchronized OrderChecker getInstance(OrderListController orderListController) {
        if (instance == null) {
            instance = new OrderChecker(orderListController);
            instance.startChecker();
        }
        return instance;
    }

    private void startChecker() {
        this.timer = new Timer(true);
        this.timer.scheduleAtFixedRate(this, 0, 60000); // Check every minute
    }

    @Override
    public void run() {
        List <Order> orders = orderListController.getAllOrders();
        for (Order order : orders) {
            order.checkOrderStatus();
        }
    }

    public void stopChecker() {
        if (this.timer != null) {
            this.timer.cancel();  // This stops the timer and cancels scheduled tasks
        }
    }
}

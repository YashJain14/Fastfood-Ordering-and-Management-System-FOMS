

package orders;

import javax.swing.*;

import interfaces.PaymentDetails;
import interfaces.PaymentMethod;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
Implements the PayNow payment method.
This class displays a QR code for PayNow payments and allows the user to confirm the payment.
*/
public class PaynowPayment implements PaymentMethod{

    // Adding a boolean to track if the close button was explicitly clicked
    private static boolean paymentSuccessful = false;

    
    @Override
    public boolean processPayment(double amount, PaymentDetails p) {
        // Assuming p is correctly an instance of PaynowDetails, which it should be.
        if (!(p instanceof PaynowDetails)) {
            System.out.println("Invalid payment details provided.");
            return false;
        }
    	
    	
    	JFrame frame = new JFrame("Payment Required");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);


    	
        JDialog dialog = new JDialog(frame, "Pay Now", true);
        
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Control the window closing manually
        dialog.setSize(600, 600);
        dialog.setLayout(new BorderLayout());

        try {
        
	        ImageIcon icon = new ImageIcon(PaynowPayment.class.getResource("/resources/paynow_qr_code.jpg"));
	        JLabel label = new JLabel(icon);
	        dialog.add(label, BorderLayout.CENTER);
        }
	    catch(Exception NullPointerException) {
	    	System.out.println("Paynow service is down. Please choose another payment method.");
	    	return false;
	    }
        
        

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton closeButton = new JButton("Close");
        
        try {
        	closeButton.setIcon(new ImageIcon(PaynowPayment.class.getResource("/resources/pay_button.jpg")));
	        closeButton.setPreferredSize(new Dimension(255, 50));  // Explicitly set the button size
	        closeButton.setMargin(new Insets(0, 0, 0, 0));
	        closeButton.setBorderPainted(false);
        }
        catch(Exception NullPointerException) {
        	closeButton.setText("Payment made");
        	closeButton.setFont(new Font("Arial", Font.BOLD, 28));
        }
        
        
        
        closeButton.addActionListener(e -> {
            paymentSuccessful = true;
            dialog.dispose();
            frame.dispose();
        });
        bottomPanel.add(closeButton);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                paymentSuccessful = false; // Set to false if the window is closed without clicking the button
                dialog.dispose();
                frame.dispose();
            }
        });

        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true); // This will block until the dialog is closed
        
     

        return paymentSuccessful; // Return true if close button was clicked, false otherwise
    }
}

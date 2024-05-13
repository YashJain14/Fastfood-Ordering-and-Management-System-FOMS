

package db;


import interfaces.*;
import java.io.*;
import orders.PaymentMethodEnum;
/**
Controls the database operations for available payment methods.
This class interacts with the PaymentMethodDB to read and write data about which payment methods are enabled or disabled for the application.
*/
public class PaymentMethodDBController implements DBController{
    private static PaymentMethodDB paymentMethodDB = new PaymentMethodDB();

    @Override
    public boolean readDB(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                if(line.trim().matches("[,\\s]*")) break;
                String[] values = line.split(",");

                paymentMethodDB.getAvailablePayment().put(values[0], Integer.parseInt(values[1]));
            }}catch (IOException e){
                return false;
            }

            return true;

    }

    @Override
    public boolean writeDB(String filename){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            // String[] line = new String[6];

            
            for (String paymentString : paymentMethodDB.getAvailablePayment().keySet()) {
                PaymentMethodEnum paymentMethod = PaymentMethodEnum.valueOf(paymentString);

                switch (paymentMethod) {
                    case PaynowPayment:
                        bw.write("PaynowPayment");
                        break;
                    case CreditCardPayment:
                        bw.write("CreditCardPayment");
                        break;
                    default:
                        bw.write("PayPal"); 
                        break;
                }
                bw.write(",");
                bw.write(this.isAvailable(paymentMethod) ? "1" : "0");
                bw.newLine();

            }
        }catch(IOException e){
            e.printStackTrace();
            return false;

        }

        return true;



    }

    public boolean isAvailable(PaymentMethodEnum paymentMethodEnum){
        return paymentMethodDB.getAvailablePayment().get(paymentMethodEnum.toString()) == 1;
    }


    public void setAvailable(PaymentMethodEnum paymentMethodEnum, int status){
        paymentMethodDB.getAvailablePayment().put(paymentMethodEnum.toString(), status);
    }




    
}

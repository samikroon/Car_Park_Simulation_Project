package logic;

/**
 * Created by samikroon on 4/11/16.
 */
public class PassHoldersPayment {
    private double parkingHolderRevenue;
    private double price = 6.50;

    public PassHoldersPayment() {
        parkingHolderRevenue = 0;
    }

    public void automaticPayment() {
        parkingHolderRevenue += price;
    }

    public double getParkingHolderRevenue() {
        return parkingHolderRevenue;
    }

    public double getPrice() {
        return price;
    }



}

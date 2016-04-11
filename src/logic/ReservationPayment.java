package logic;

/**
 * Created by samikroon on 4/11/16.
 */
public class ReservationPayment {
    private double revenueFromReservation;
    private double reservationPrice = 1.00;

    public ReservationPayment(double beginReservationRevenue) {
        revenueFromReservation = beginReservationRevenue;
    }

    public void addReservationPayment() {
        revenueFromReservation += reservationPrice;
    }

    public double getRevenueFromReservation() {
        return revenueFromReservation;
    }
}

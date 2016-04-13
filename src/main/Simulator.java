package main;

import logic.*;
import view.SimulatorView;


import java.util.Random;

public class Simulator {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;
    private PassHoldersPayment passHoldersPayment;
    private ReservationPayment reservationPayment;



    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;
    private boolean run = true;

    private int carsNormalInside = 0;
    private int carsHoldersInside = 0;
    private int carsPayed = 0;
    private int carsReservedInside = 0;
    private int emptyReservedSpots = 0;


    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 3; // number of cars that can leave per minute

    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        passHoldersPayment = new PassHoldersPayment(0);
        reservationPayment = new ReservationPayment(0);
        simulatorView = new SimulatorView(3, 6, 30, this);

    }

    public void run() {
        for (int i = 0; i < 10000 && run; i++) {
            tick();
        }
    }

    public void runSteps(int steps) {
        for (int i = 0; i < steps && run; i++) {
            tick();
        }
        simulatorView.getButtonController().setAlreadyRunning(false);
        simulatorView.getButtonController().getPerformerThread().interrupt();
    }

    /**
     * Created by machiel 4/6/16
     * De methode runFalse geeft aan de boolean run de waarde False.
     * Door aan de boolean run de waarde false te geven
     * zullen de methode's run en runSteps hun for loop niet verder door lopen.
     */
    public void runFalse(){
        this.run = false;
    }
    /**
     * Created by machiel 4/6/16
     * De methode runTrue geeft aan de boolean run de waarde true.
     * Deze waarde moet true zijn als methode's run en runsteps wilt uit voeren.
     */
    public void runTrue(){
        this.run = true;
    }

    private void tick() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDayArrivals
                : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);


        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            boolean passHolder = random.nextDouble() <= 0.2;
            boolean reservation = random.nextDouble() <=0.15;
            Car car = new AdHocCar();
            if (passHolder) {car.setPassHolder(true);}
            if (reservation) {car.setReservedSpot(true); car.setWasReservedSpot(false);}
            entranceCarQueue.addCar(car);
        }

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Find a space for this car.
            Location freeLocation = simulatorView.getFirstFreeLocation();
            if (freeLocation != null) {
                simulatorView.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                int minutesTillArrival;
                if (car.isReservedSpot()) {
                    minutesTillArrival = (int) (15 + random.nextFloat() * 10 * 30);
                    stayMinutes = stayMinutes + minutesTillArrival;
                    car.setMinutesTillArrival(minutesTillArrival);
                    emptyReservedSpots++;
                    reservationPayment.addReservationPayment();
                }
                car.setMinutesLeft(stayMinutes);
                if (car.isPassHolder() && !car.isReservedSpot()) {
                    carsHoldersInside++;
                } else if (!car.isReservedSpot()){
                    carsNormalInside++;
                }
            }
        }


        // Perform car park tick.
        simulatorView.tick();

        // Add leaving cars to the exit queue.
        while (true) {
            Car car = simulatorView.getFirstLeavingCar();
            if (car == null) {
                break;
            }
            if (car.getMinutesLeft() > 0 && car.isReservedSpot()) {
                if (car.isPassHolder()) {
                    carsHoldersInside++;
                } else {
                    carsNormalInside++;
                }
                carsReservedInside++;
                emptyReservedSpots--;
                car.setReservedSpot(false);
                car.setWasReservedSpot(true);
            } else if (car.isPassHolder()) {
                simulatorView.removeCarAt(car.getLocation());
                exitCarQueue.addCar(car);
                passHoldersPayment.automaticPayment();
            } else {
                car.setIsPaying(true);
                paymentCarQueue.addCar(car);
            }
        }

        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            simulatorView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
            carsPayed++;

        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            if (car.isPassHolder()) {
                carsHoldersInside--;
            } else {
                carsNormalInside--;
            }
            if (car.isReservedSpot()) {
                carsReservedInside--;
            }
            // Bye!
        }

        // Update the car park view.

        simulatorView.updateView();

        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }

    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    public CarQueue getPaymentCarQueue() {
        return paymentCarQueue;
    }

    public int getCarsNormalInside() {
        return carsNormalInside;
    }

    public int getCarsHoldersInside() {
        return carsHoldersInside;
    }

    public int getCarsPayed() {
        return carsPayed;
    }

    public int getCarsReservedInside() {
        return carsReservedInside;
    }

    public int getEmptyReservedSpots() {
        return emptyReservedSpots;
    }

    public void setWeekDayArrivals(int arrivals) {
        this.weekDayArrivals = arrivals;
    }

    public void setWeekendArrivals(int arrivals) {
        this.weekendArrivals = arrivals;
    }

    public ReservationPayment getReservationPayment() {
        return reservationPayment;
    }

    public PassHoldersPayment getPassHoldersPayment() {
        return passHoldersPayment;
    }

    public SimulatorView getSimulatorView() {
        return simulatorView;
    }
}

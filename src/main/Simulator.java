package main;

import logic.AdHocCar;
import logic.Car;
import logic.CarQueue;
import logic.Location;
import view.InfoView;
import view.SimulatorView;


import java.util.Random;

public class Simulator {

    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;



    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;
    private boolean run = true;

    private int carsLeft = 0;
    private int carsPayed = 0;
    private int carsInside = 0;


    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute

    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
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
    }

    /**
     * Created by machiel 4/6/16
     * De methode runFalse geeft aan de boolean run de waarde False.
     * Door aan de boolean run de waarde false te geven
     * zullen de methode's run en runSteps hun for loop niet verder door lopen.
     */
    public void runFalse(){
        this.run = false;
        System.out.println("runFalse methode");
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
        System.out.println("simulator-tick");
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
            Car car = new AdHocCar();
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
                car.setMinutesLeft(stayMinutes);
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
            car.setIsPaying(true);
            paymentCarQueue.addCar(car);
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

        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
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

    public CarQueue getEntranceCarQueue() {
        return entranceCarQueue;
    }

    public CarQueue getPaymentCarQueue() {
        return entranceCarQueue;
    }

    public CarQueue getExitCarQueue() {
        return exitCarQueue;
    }
}

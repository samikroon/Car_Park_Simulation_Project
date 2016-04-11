package logic;



import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private Queue<Car> queue = new LinkedList<>();
    private int numberOfCars;

    public boolean addCar(Car car) {
        numberOfCars--;
        return queue.add(car);
    }

    public Car removeCar() {
        numberOfCars++;
        return queue.poll();
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }


}

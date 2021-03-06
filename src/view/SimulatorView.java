package view;

import controller.ButtonController;
import logic.Car;
import logic.Location;
import main.Simulator;

import javax.swing.*;
import java.awt.*;

public class SimulatorView extends AbstractView {
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;
    private ButtonController buttonController;
    private InfoView infoView;
    private RevenueView revenueView;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane2;
    private JScrollPane scrollPane3;
    private JScrollPane scrollPane4;
    private JSplitPane pane;
    private JSplitPane pane2;
    private JSplitPane pane3;
    private BarChart pieChart;
    private LegendaView legenda;



    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces, Simulator simulator) {
        super(simulator);
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView();
        buttonController = new ButtonController(simulator);

        Container contentPane = getContentPane();
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        contentPane.add(carParkView, BorderLayout.WEST);
        //contentPane.add(population, BorderLayout.SOUTH);

        JButton stepForward = new JButton("one step");
        stepForward.addActionListener(buttonController);

        JButton stepHundredForward = new JButton("hundred steps");
        stepHundredForward.addActionListener(buttonController);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(buttonController);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(buttonController);
        
        JButton terminate = new JButton("Exit Application");
        terminate.addActionListener(buttonController);

        JButton save = new JButton("Save stats to file");
        save.addActionListener(buttonController);

        JButton editIncomingCars = new JButton("Edit incoming cars");
        editIncomingCars.addActionListener(buttonController);

      

        JMenuBar stepBar = new JMenuBar();
        stepBar.add(stepForward);
        stepBar.add(stepHundredForward);
        stepBar.add(startButton);
        stepBar.add(pauseButton);

        contentPane.add(stepBar, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(terminate);
        menuBar.add(save);
        menuBar.add(editIncomingCars);
        contentPane.add(menuBar, BorderLayout.NORTH);

        infoView = new InfoView(simulator);
        revenueView = new RevenueView(simulator);
        pieChart = new BarChart(simulator);
        legenda = new LegendaView(simulator);
        scrollPane = new JScrollPane(infoView.getTable());
        scrollPane2 = new JScrollPane(revenueView.getTable());
        pieChart.setDataset();
        scrollPane3 = new JScrollPane(pieChart.createChart());
        scrollPane4 = new JScrollPane(legenda.getTable());
        pane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane3, scrollPane4);
        pane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, scrollPane2);
        //pane2.setResizeWeight(1.0);
        pane3.setResizeWeight(0.4);
        pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pane3, pane2);
        pane.setResizeWeight(0.7);
        contentPane.add(pane, BorderLayout.EAST);


        pack();
        setVisible(true);

        updateView();
    }





    public void updateView() {
        infoView.updateData();
        revenueView.updateRevenueView();
        scrollPane.setViewportView(infoView.getTable());
        scrollPane2.setViewportView(revenueView.getTable());
        updatePieChart();
        carParkView.updateView();

    }

    public void updatePieChart() {
        pieChart.removeAll();
        pieChart.revalidate();
        pieChart.setDataset();
        pieChart.repaint();

    }
    
    public int getNumberOfFloors() {
            return numberOfFloors;
        }
    
    public int getNumberOfRows() {
            return numberOfRows;
        }
    
    public int getNumberOfPlaces() {
            return numberOfPlaces;
        }
    
    public Car getCarAt(Location location) {
            if (!locationIsValid(location)) {
                return null;
            }
            return cars[location.getFloor()][location.getRow()][location.getPlace()];
        }
    
    public boolean setCarAt(Location location, Car car) {
            if (!locationIsValid(location)) {
                return false;
            }
            Car oldCar = getCarAt(location);
            if (oldCar == null) {
                cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
                car.setLocation(location);
                return true;
            }
            return false;
        }

    public Car removeCarAt(Location location) {
            if (!locationIsValid(location)) {
                return null;
            }
            Car car = getCarAt(location);
            if (car == null) {
                return null;
            }
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            car.setLocation(null);
            return car;
        }

    public RevenueView getRevenueView() {
            return revenueView;
        }

    public ButtonController getButtonController() {
        return buttonController;
    }



    
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }
    
    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    } else if (car != null && car.getMinutesTillArrival() <= 0 && !car.getIsPaying() && car.isReservedSpot()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }
    
    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }
    
    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }
    
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(1200, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            Color color;
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        if(car == null) {
                            color = Color.white;
                        } else if (car.getWasReservedSpot()) {
                            color = Color.black;
                        } else if (car.isReservedSpot()) {
                            color = Color.orange;
                        } else if (car.isPassHolder()) {
                            color = Color.cyan;
                        } else if (!car.isPassHolder()) {
                            color = Color.red;
                        } else {
                            color = Color.white;
                        }
                        drawPlace(graphics, location, color);
                    }
                }
            }

            repaint();

        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }
}

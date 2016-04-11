package logic;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private int minutesTillArrival;
    private boolean isPaying;
    private boolean isPassHolder;
    private boolean reservedSpot;
    private boolean wasReservedSpot;


    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public int getMinutesTillArrival() {
        return minutesTillArrival;
    }

    public void setMinutesTillArrival(int minutesTillArrival) {
        this.minutesTillArrival = minutesTillArrival;
    }

    public boolean getIsPaying() {
        return isPaying;
    }

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public void setPassHolder(boolean passHolder) {
        isPassHolder = passHolder;
    }

    public boolean isPassHolder() {
        return isPassHolder;
    }

    public void setReservedSpot(boolean reservedSpot) {
        this.reservedSpot = reservedSpot;
    }

    public boolean isReservedSpot() {
        return reservedSpot;
    }

    public void setWasReservedSpot(boolean wasReservedSpot) {
        this.wasReservedSpot = wasReservedSpot;
    }

    public boolean getWasReservedSpot() {
        return wasReservedSpot;
    }

    public void tick() {
        minutesLeft--;
        if(reservedSpot) {minutesTillArrival--;}
    }

}

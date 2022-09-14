public class Elevator implements ElevatorService {

    private final int MAX_PASSENGERS = 5;
    private final int[] passengers = new int[MAX_PASSENGERS];
    private int floorStop = 1;
    private final int maxFloor;
    private boolean route = true;

    public Elevator(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    private int ElevatorIsFull() {
        int result;
        if (route) {
            int min = maxFloor + 1;
            for (int i : passengers)
                if (i != 0 && i < min) min = i;
            result = (min != maxFloor + 1) ? min : 0;
        } else {
            int max = 0;
            for (int i : passengers)
                if (i > max)
                    max = i;
            result = max;
        }
        return result;
    }

    public boolean isFull() {
        boolean isElevatorFull = true;
        for (int i = 0; i < MAX_PASSENGERS; i++)
            if (passengers[i] == 0) {
                isElevatorFull = false;
                break;
            }
        return !isElevatorFull;
    }
    public boolean isEmpty() {
        for (int i : passengers)
            if (i != 0) return false;
        return true;
    }
    public void move() {
        this.correctRoute();
        int nextFloor;
        if (this.isFull()) {
            nextFloor = route ? floorStop + 1 : floorStop - 1;
        } else nextFloor = ElevatorIsFull();
        floorStop = nextFloor;
    }
    public void correctRoute() {
        if (floorStop == 1) route = true;
        else if (floorStop == maxFloor) route = false;
    }
    public void humanIn(int passengerFloor) {
        for (int i = 0; i < MAX_PASSENGERS; i++)
            if (passengers[i] == 0) {
                passengers[i] = passengerFloor;
                return;
            }
    }
    public int humanOut() {
        int removeHumansCount = 0;
        for (int i = 0; i < MAX_PASSENGERS; i++)
            if (passengers[i] == floorStop) {
                passengers[i] = 0;
                removeHumansCount++;
            }
        return removeHumansCount;
    }
    public boolean isRoute() {
        return route;
    }
    public void setWhere(boolean where) {
        this.route = where;
    }
    public int getFloor() {
        return floorStop;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int passenger : passengers) {
            if (passenger != 0)
                stringBuilder
                        .append("| ")
                        .append(passenger)
                        .append(" | ");
        }
        if (stringBuilder.length() > 0) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
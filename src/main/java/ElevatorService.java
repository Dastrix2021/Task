public interface ElevatorService {

    void humanIn(int passengerFloor);
    int humanOut();
    boolean isEmpty();
    boolean isFull();
    void move();
    void correctRoute();
    boolean isRoute();
    int getFloor();
    void setWhere(boolean where);
}

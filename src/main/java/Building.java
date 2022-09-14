import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Building {
    private final int floors;
    private final ElevatorService elevator;
    private final List<Integer>[] humansOnFloor;

    public Building(){
        this.floors = getRandomNumber(5, 20);
        elevator = new Elevator(floors);
        humansOnFloor = new List[floors];
        randomHumans();
    }
    public void start(int steps){
        for(int i = 1; i <= steps; i++){
            int removedPassengers = this.removeHumanFromLift();
            if(elevator.isEmpty())
                elevator.setWhere(this.getElevatorRoute());

            int addedPassengers = this.addHumansToElevator();

            if(removedPassengers == 0 && addedPassengers == 0) i--;
            else {
                createRandomPassengers(removedPassengers);
                this.showInformation(i, removedPassengers, addedPassengers);
            }
            elevator.move();
        }
    }
    public static int getRandomNumber(int min, int max) {
        return (new Random())
                .ints(min, max)
                .findFirst()
                .getAsInt();
    }
    private int addHumansToElevator(){
        elevator.correctRoute();
        ArrayList<Integer> indexesToDelete = new ArrayList<>();
        for(int i = 0; i < humansOnFloor[elevator.getFloor()-1].size() && elevator.isFull(); i++){
            if(elevator.isRoute()){
                if(humansOnFloor[elevator.getFloor() - 1].get(i) > elevator.getFloor()){
                    indexesToDelete.add(i);
                    elevator.humanIn(humansOnFloor[elevator.getFloor() - 1].get(i));
                }
            } else {
                if(humansOnFloor[elevator.getFloor() - 1].get(i) < elevator.getFloor()){
                    indexesToDelete.add(i);
                    elevator.humanIn(
                            humansOnFloor[elevator.getFloor()-1].get(i));
                }
            }
        }
        if (indexesToDelete.size() > 0) {
            humansOnFloor[elevator.getFloor() - 1].subList(0, indexesToDelete.size()).clear();
        }
        return indexesToDelete.size();
    }
    private int removeHumanFromLift(){
        return elevator.humanOut();
    }
    private void randomHumans(){
        for(int i = 0; i < floors; i++){
            humansOnFloor[i] = fillFloor(i + 1);
        }
    }
    private List<Integer> fillFloor(int currentFloor){
        ArrayList<Integer> floor = new ArrayList<>();
        int passInTheFloor = getRandomNumber(0, 10);
        for(int j = 1; j < passInTheFloor; j++) {
            floor.add(createRandomHumans(currentFloor));
        }
        return floor;
    }
    private int createRandomHumans(int currentFloor){
        int passengerTargetFloor = currentFloor;
        while(passengerTargetFloor == currentFloor)
            passengerTargetFloor = getRandomNumber(0, floors) + 1;

        return passengerTargetFloor;
    }
    private void createRandomPassengers(int count){
        for(int j = 0; j < count; j++)
            this.humansOnFloor[elevator.getFloor() - 1].add(
                    createRandomHumans(elevator.getFloor()));
    }
    private boolean getElevatorRoute(){
        if(elevator.getFloor() == 1)
            return true;
        else if (elevator.getFloor() == floors)
            return false;
        else {
            int Up = 0;
            for(int i = 0; i < humansOnFloor[elevator.getFloor() - 1].size(); i++)
                if(humansOnFloor[elevator.getFloor()-1].get(i) > elevator.getFloor())
                    Up++;
            return humansOnFloor[elevator.getFloor() - 1].size() - Up < Up;
        }
    }
    private void showInformation(int step, int removedPassengers, int addedPassengers){
        System.out.println("______________________ Step " + step + " ______________________");
        System.out.print(this);
        System.out.println("Out: " + removedPassengers + " In: " + addedPassengers + "\n");
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = floors - 1; i >= 0; i--){
            if(elevator.getFloor() != i + 1)
                sb.append(" |: ")
                        .append(i + 1)
                        .append(" :|   ")
                        .append(humansOnFloor[i].toString())
                        .append("\n");
            else
                sb.append(" |: ")
                        .append(i + 1)
                        .append(" :|   ")
                        .append(humansOnFloor[i].toString())
                        .append("      <-- [__ ")
                        .append(elevator).append(" __]\n");
        }
        return sb.toString();
    }
}

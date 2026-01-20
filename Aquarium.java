import java.util.*;

public class Aquarium{
    // The list of all tanks in the aquarium; guaranteed never to be null
    private ArrayList<Tank> tanks;

    public Aquarium(){
        this.tanks = new ArrayList<Tank>();
    }


    /**
     * Returns a tank in this aquarium with a temperature fishy can tolerate and
     * that does not contain a fish that is not compatible with fishy. Returns
     * null if there is no such tank in this aquarium.
     * Postcondition: The state of this aquarium is unchanged.
     * @param fishy the fish to be checked
     * @return a suitable tank for fishy or null if no such tank exists
     */
    private Tank findTank(Fish fishy){
        for (Tank tank : tanks) {
            int tankTemp = tank.temp();
            if (tankTemp >= fishy.minTemp() && tankTemp <= fishy.maxTemp()) {
                boolean compatible = true;
                for (Fish existingFish : tank.getFish()) {
                    if (!fishy.isCompatible(existingFish)) {
                        compatible = false;
                        break;
                    }
                }
                if (compatible) {
                    return tank;
                }
            }
        }
        return null;
    }

    /**
     * Adds each fish in fishes to a suitable tank in this aquarium if such a
     * tank exists. Each fish should be added to at most 1 tank.
     * @param fishes the list of fish to add
     * @return a list of the fish in fishes that could not be added
     */
    public ArrayList<Fish> addFish(ArrayList<Fish> fishes){
        ArrayList<Fish> notAdded = new ArrayList<Fish>();
        for (Fish fish : fishes) {
            Tank suitableTank = findTank(fish);
            if (suitableTank != null) {
                suitableTank.addFish(fish);
            } else {
                notAdded.add(fish);
            }
        }
        return notAdded;
    }

    /**
     * Adds fishTank to this aquarium if a suitable position can be found. The
     * temperature of fishTank can be no more than 5 degrees different (lower or
     * higher) than each of any adjacent tanks.
     * Postcondition: the order of the other tanks in the aquarium relative to each other is not changed
     * @param fishTank the tank to add
     * @return true if fishTank was added, false otherwise
     */
    public boolean addTank(Tank fishTank){
        if (tanks.isEmpty()) {
            tanks.add(fishTank);
            return true;
        }
        
        int newTemp = fishTank.temp();
        
        for (int i = 0; i <= tanks.size(); i++) {
            boolean canAdd = true;
            
            if (i > 0) {
                int leftTemp = tanks.get(i - 1).temp();
                if (Math.abs(newTemp - leftTemp) > 5) {
                    canAdd = false;
                }
            }
            
            if (i < tanks.size()) {
                int rightTemp = tanks.get(i).temp();
                if (Math.abs(newTemp - rightTemp) > 5) {
                    canAdd = false;
                }
            }
            
            if (canAdd) {
                tanks.add(i, fishTank);
                return true;
            }
        }
        
        return false;
    }
}
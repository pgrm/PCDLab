/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session3;

/**
 *
 * @author Peter
 */
public class CountingPrisoner extends Prisoner {

    private final Prisoner[] prisoners;
    private int countOfPrisoners = 1;
    private int goalForCountOfPrisoners;

    public CountingPrisoner(Prisoner[] prisoners) {
        this.goalForCountOfPrisoners = 2 * (prisoners.length - 1);
        this.prisoners = prisoners;
    }

    @Override
    protected void inTheRoom() {
        if (room.isLightTurnedOn()) {
            room.turnOffLight();
            countPrisoners();
        }
    }

    private void countPrisoners() {
        countOfPrisoners++;
        System.out.println(countOfPrisoners + " prisoners counted so far.");

        if (allPrisonersHaveBeenInTheRoom()) {
            freeAllPrisoners();
        }
    }

    private boolean allPrisonersHaveBeenInTheRoom() {
        return countOfPrisoners >= goalForCountOfPrisoners; // >= is just to be on the safe side
    }

    private void freeAllPrisoners() {
        for (Prisoner prisoner : prisoners) {
            prisoner.youAreFree();
        }
    }
}

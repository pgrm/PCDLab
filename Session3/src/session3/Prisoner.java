/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session3;

/**
 *
 * @author Peter
 */
public class Prisoner implements Runnable {

    protected final Room room;
    protected int counter = 0;
    private boolean prisonerIsFree = false;

    public Prisoner() {
        room = Room.getInstance();
    }

    @Override
    public void run() {
        while (!prisonerIsFree) {
            synchronized (room) { // entering the room
                inTheRoom();
            } // exitin the room
        }
    }

    protected void inTheRoom() {
        if (counter < 2) {
            if (!room.isLightTurnedOn()) {
                room.turnOnLight();
                counter++;
            }
        }
    }

    protected void youAreFree() {
        prisonerIsFree = true;
    }
}

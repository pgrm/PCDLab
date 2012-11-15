/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session3;

/**
 *
 * @author Peter
 */
public class Room {

    private static Room instance;
    private boolean lightTurnedOn;

    public static synchronized Room getInstance() {
        if (instance == null) {
            instance = new Room();
        }
        return instance;
    }

    public static synchronized Room initializeInstance(boolean initiallyLightTurnedOn) {
        if (instance == null) {
            getInstance().lightTurnedOn = initiallyLightTurnedOn;
        } else {
            throw new IllegalStateException("Room has been already created. "
                    + "Call initialize before getInstance and call it only once.");
        }

        return instance;
    }

    public boolean isLightTurnedOn() {
        return lightTurnedOn;
    }

    public void turnOffLight() {
        if (lightTurnedOn) {
            lightTurnedOn = false;
        } else {
            throw new IllegalStateException("The light is already turned off.");
        }
    }

    public void turnOnLight() {
        if (!lightTurnedOn) {
            lightTurnedOn = true;
        } else {
            throw new IllegalStateException("The light is already turned on.");
        }
    }
}

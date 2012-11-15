/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session3;

/**
 *
 * @author Peter
 */
public class Main {

    private final int numOfPrisoners = 50;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        Prisoner[] prisoners = new Prisoner[numOfPrisoners];

        for (int i = 1; i < prisoners.length; i++) {
            prisoners[i] = new Prisoner();
            new Thread(prisoners[i]).start();
        }

        prisoners[0] = new CountingPrisoner(prisoners);
        new Thread(prisoners[0]).start();
    }
}

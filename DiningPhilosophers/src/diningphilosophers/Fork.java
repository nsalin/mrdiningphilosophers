/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diningphilosophers;

/**
 *
 * @author Rafael
 */
public class Fork {

    private boolean inUse = true;

    public synchronized boolean isDirty() {
        return !inUse;
    }

    public synchronized void get() {
        while( inUse )
        {
            try {
                wait();
            } catch( InterruptedException e ) {}
        }
        inUse = true;
        notifyAll();
    }

    public synchronized void give() {
        inUse = false;
        notifyAll();
        while( !inUse )
        {
            try {
                wait();
            } catch( InterruptedException e ) {}
        }
    }

}

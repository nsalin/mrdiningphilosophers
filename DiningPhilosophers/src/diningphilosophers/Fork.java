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

    private boolean is_dirty = true;
    private int fork_id;
    private boolean side;
    DiningTable Table;

    Fork( int t_fork_id, boolean init_side, DiningTable t_Table ) {
        fork_id = t_fork_id;
        side = init_side;
        Table = t_Table;
    }

    public synchronized void get() {
        while( !is_dirty )
        {
            try {
                wait();
            } catch( InterruptedException e ) {}
        }
        is_dirty = false;
        side = !side;
        Table.updateFork( fork_id, side, is_dirty );
        notifyAll();
    }

    public synchronized void give() {
        is_dirty = true;
        Table.updateFork( fork_id, side, is_dirty );
        notifyAll();
        while( is_dirty )
        {
            try {
                wait();
            } catch( InterruptedException e ) {}
        }
    }

}

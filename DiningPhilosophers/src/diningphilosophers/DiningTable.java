/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diningphilosophers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class DiningTable {

    private static final boolean THINKING = false;
    private static final boolean EATING = true;
    private static final boolean LEFT = false;
    private static final boolean RIGHT = true;
    private static final boolean CLEAN = false;
    private static final boolean DIRTY = true;
    private static final int FIRST = 1;
    private static final int NORMAL = 2;
    private static final int LAST = 3;

    DiningPhilosophersView Window;
    List<Philosopher> philosophers;
    String states = "";

    DiningTable( DiningPhilosophersView t_Window ) {
        Window = t_Window;
    }

    public void startSimulation( int philosophers_number ) {
        int last_id = philosophers_number - 1;

        List<Fork> forks = new ArrayList<Fork>();

        boolean side = RIGHT;
        for( int id = 0; id < philosophers_number; id++ ) {
            forks.add( new Fork( id, side, this ) );

            if (side == RIGHT)
                states = states + "T<-d  ";
            else
                states = states + "T  d->";

            side = !side;
        }

        philosophers = new ArrayList<Philosopher>();

        int id = 0;

        if (philosophers_number % 2 == 1) {
            Philosopher FirstPhilosopher = new Philosopher( 0, NORMAL, this );
            philosophers.add(FirstPhilosopher);

            Philosopher SecondPhilosopher = new Philosopher( 1, LAST, this );
            philosophers.add(SecondPhilosopher);

            id = 2;
        }

        side = RIGHT;
        for(; id < philosophers_number; id++ ) {
            if (side == RIGHT) {
                Philosopher philosopher = new Philosopher( id, FIRST, this );
                philosophers.add(philosopher);
            }
            else {
                Philosopher philosopher = new Philosopher( id, LAST, this );
                philosophers.add(philosopher);
            }
            side = !side;
        }

        Philosopher p = philosophers.get(0);
        p.setLeftFork( forks.get( 0 ) );
        p.setRightFork( forks.get( last_id ) );

        for( id = 1; id < philosophers_number; id++ ) {
            p = philosophers.get(id);
            p.setLeftFork( forks.get( id ) );
            p.setRightFork( forks.get( id - 1 ) );
        }


        for( Philosopher philosopher : philosophers ) {
            philosopher.start();
        }
    }

    public void endSimulation() {
        for( Philosopher philosopher : philosophers ) {
            philosopher.interrupt();
        }
    }

    private synchronized void updateString( int index, char c ) {
        states = states.substring( 0, index ) + c + states.substring( index+1 );
    }

    private synchronized void updateStatesField() {
        Window.updateStatesField(states);
    }

    public void updatePhilosopher( int philosopher_id, boolean philosopher_state ) {
        int index = philosopher_id * 6;

        char c;
        if( philosopher_state == EATING ) {
            c = 'E';
        } else {
            c = 'T';
        }

        updateString( index, c );
        updateStatesField();
    }

    public void updateFork( int fork_id, boolean fork_side, boolean fork_state ) {
        int index = fork_id * 6 + 3;

        char c;
        if( fork_state == DIRTY ) {
            c = 'd';
        } else {
            c = 'c';
        }
        updateString( index, c );

        if( fork_side == LEFT ) {
            updateString( index-1, ' ' );
            updateString( index-2, ' ' );
            updateString( index+1, '-' );
            updateString( index+2, '>' );
        } else {
            updateString( index-1, '-' );
            updateString( index-2, '<' );
            updateString( index+1, ' ' );
            updateString( index+2, ' ' );
        }
    }
}

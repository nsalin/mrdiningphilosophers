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

        for( int id = 0; id < last_id; id++ ) {
            forks.add( new Fork( id, RIGHT, this ) );
            states = states + "T<-d  ";
        }
        forks.add( new Fork( last_id, LEFT, this ) );
        states = states + "T  d->";

        philosophers = new ArrayList<Philosopher>();

        Philosopher FirstPhilosopher = new Philosopher( 0, FIRST, this );
        philosophers.add(FirstPhilosopher);
        FirstPhilosopher.setLeftFork( forks.get(0) );
        FirstPhilosopher.setRightFork( forks.get(last_id) );

        for( int id = 1; id < last_id; id++ ) {
            Philosopher philosopher = new Philosopher( id, NORMAL, this );
            philosophers.add(philosopher);
            philosopher.setLeftFork( forks.get( id ) );
            philosopher.setRightFork( forks.get( id - 1 ) );
        }

        Philosopher LastPhilosopher = new Philosopher( last_id, LAST, this );
        philosophers.add(LastPhilosopher);
        LastPhilosopher.setLeftFork( forks.get( last_id ) );
        LastPhilosopher.setRightFork( forks.get( last_id - 1 ) );

        for( Philosopher philosopher : philosophers ) {
            philosopher.start();
        }
    }

    public void endSimulation() {
        for( Philosopher philosopher : philosophers ) {
            philosopher.interrupt();
        }
    }

    private void updateString( int index, char c ) {
        states = states.substring( 0, index ) + c + states.substring( index+1 );
    }

    private void updateStatesField() {
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

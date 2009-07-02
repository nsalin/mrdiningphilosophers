/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diningphilosophers;

/**
 *
 * @author Rafael
 */
public class Philosopher extends Thread {

    private static final boolean THINKING = false;
    private static final boolean EATING = true;

    private Fork LeftFork;
    private Fork RightFork;
    private boolean dining_state = THINKING;
    private int init_mode = 0;

    Philosopher( int t_init_mode ) {
        init_mode = t_init_mode;
    }

    public synchronized boolean isEating() {
        return dining_state;
    }

    public synchronized void setDiningState( boolean new_state ) {
        dining_state = new_state;
    }

    public void setLeftFork( Fork t_LeftFork ) {
        LeftFork = t_LeftFork;
    }

    public void setRightFork( Fork t_RightFork ) {
        RightFork = t_RightFork;
    }

    public void run() {
        if( init_mode == 1 ) {
            setDiningState(THINKING);
            GiveAndGetForkThread GiveAndGetLeftFork = new GiveAndGetForkThread( LeftFork );
            GetForkThread GetRightFork = new GetForkThread( RightFork );
            GiveAndGetLeftFork.start();
            GetRightFork.start();
            try {
                GiveAndGetLeftFork.join();
            } catch( InterruptedException e ) {}
            try {
                GetRightFork.join();
            } catch( InterruptedException e ) {}

            setDiningState(EATING);
            eat();
        }

        if( init_mode == 2 ) {
            setDiningState(THINKING);
            GetForkThread GetLeftFork = new GetForkThread( LeftFork );
            GetForkThread GetRightFork = new GetForkThread( RightFork );
            GetLeftFork.start();
            GetRightFork.start();
            try {
                GetLeftFork.join();
            } catch( InterruptedException e ) {}
            try {
                GetRightFork.join();
            } catch( InterruptedException e ) {}

            setDiningState(EATING);
            eat();
        }

        while( true ) {
            setDiningState(THINKING);
            GiveAndGetForkThread GiveAndGetLeftFork = new GiveAndGetForkThread( LeftFork );
            GiveAndGetForkThread GiveAndGetRightFork = new GiveAndGetForkThread( RightFork );
            GiveAndGetLeftFork.start();
            GiveAndGetRightFork.start();
            try {
                GiveAndGetLeftFork.join();
            } catch( InterruptedException e ) {}
            try {
                GiveAndGetRightFork.join();
            } catch( InterruptedException e ) {}

            setDiningState(EATING);
            eat();
        }
    }

    private void eat() {
        // TODO
    }
}

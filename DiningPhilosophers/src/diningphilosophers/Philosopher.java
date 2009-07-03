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

    private int philosopher_id;
    private int init_mode;
    DiningTable Table;
    private Fork LeftFork;
    private Fork RightFork;

    Philosopher( int t_philosopher_id, int t_init_mode, DiningTable t_Table ) {
        philosopher_id = t_philosopher_id;
        init_mode = t_init_mode;
        Table = t_Table;
    }

    public void setLeftFork( Fork t_LeftFork ) {
        LeftFork = t_LeftFork;
    }

    public void setRightFork( Fork t_RightFork ) {
        RightFork = t_RightFork;
    }

    public void run() {
        if( init_mode == 2 ) {
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

            eat();
        }

        if( init_mode == 3 ) {
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

            eat();
        }

        while( true ) {
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

            eat();

            if( interrupted() ){
                return;
            }
        }
    }

    private void eat() {
        Table.updatePhilosopher( philosopher_id, true );

        long eating_time = (long) (1000 + Math.random()*2000);
        try {
            sleep(eating_time);
        } catch( InterruptedException e ) {}
        
        Table.updatePhilosopher( philosopher_id, false );
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package diningphilosophers;

/**
 *
 * @author Rafael
 */
public class GetForkThread extends Thread {
    
    private Fork m_Fork;

    GetForkThread( Fork t_Fork ) {
        m_Fork = t_Fork;
    }

    public void run() {
        m_Fork.get();
    }

}

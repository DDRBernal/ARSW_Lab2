package arsw.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JButton;

public class MainCanodromo {

    private static Galgo[] galgos;

    private static Canodromo can;

    private static RegistroLlegada reg = new RegistroLlegada();

    private static Object lock ;

    private static AtomicBoolean paused;

    public static void main(String[] args) {
        paused = new AtomicBoolean(false);
        lock = new Object();
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);
        //Acción del botón start
        can.setStartAction(
                new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    //como acción, se crea un nuevo hilo que cree los hilos
                    //'galgos', los pone a correr, y luego muestra los resultados.
                    //La acción del botón se realiza en un hilo aparte para evitar
                    //bloquear la interfaz gráfica.
                    ((JButton) e.getSource()).setEnabled(false);
                        new Thread(() -> {
                            for (int i = 0; i < can.getNumCarriles(); i++) {
                                //crea los hilos 'galgos'
                                galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                                //inicia los hilos
                                galgos[i].start();
                            }
                            for (Galgo g : galgos) {
                                try {
                                    g.join();
                                } catch (InterruptedException exe) {
                                }
                            }
                            can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                            System.out.println("El ganador fue:" + reg.getGanador());
                        }).start();
                    }
                }
        );
        can.setStopAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pauseAllThreads();
                    }
                }
        );
        can.setContinueAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resume();
                    }
                }
        );
    }

    public static void pauseAllThreads() {
        synchronized (lock){
            if(!paused.get()) {
                paused.set(true);
                System.out.println("Carrera pausada!");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public static void resume() {
        synchronized (Galgo.getPauseLock()) {
            System.out.println("Carrera reanudada!");
            paused.set(false);
            Galgo.pauseLockNotify();
        }
    }

    public static boolean getPaused(){
        return paused.get();
    }

    public static Object getLock(){
        return lock;
    }

    public static void notifyThreadsLock(){
        lock.notifyAll();
    }

}

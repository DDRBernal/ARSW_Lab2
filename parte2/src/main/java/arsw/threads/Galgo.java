package arsw.threads;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private static AtomicBoolean paused = new AtomicBoolean(false);
	private static Object pauseLock = new Object();

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {
				if (MainCanodromo.getPaused()) {
					pause();
				} else {
					while (paused.get()) {
						paused.wait();
					}
					Thread.sleep(100);
					carril.setPasoOn(paso++);
					carril.displayPasos(paso);
					if (paso == carril.size()) {
						carril.finish();
						int ubicacion = regl.getUltimaPosicionAlcanzada();
						regl.setUltimaPosicionAlcanzada(ubicacion + 1);
						System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
						if (ubicacion == 1) {
							regl.setGanador(this.getName());
						}
					}
				}
		}
	}

	public static Object getPauseLock(){
		return pauseLock;
	}

	public static void pauseLockNotify(){
		pauseLock.notifyAll();
	}

	public synchronized void pause(){
		synchronized (MainCanodromo.getLock()) {
			MainCanodromo.notifyThreadsLock();
		}
		synchronized (pauseLock) {
			if(MainCanodromo.getPaused()){
				try {
					pauseLock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		this.notifyAll();
	}

	@Override
	public void run() {
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

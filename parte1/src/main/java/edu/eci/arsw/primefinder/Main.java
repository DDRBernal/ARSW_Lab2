package edu.eci.arsw.primefinder;

import java.util.Timer;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		PrimeFinderThread pft = new PrimeFinderThread(0, 10000000);

		PrimeFinderThread pft2 = new PrimeFinderThread(10000000, 20000000);

		PrimeFinderThread pft3 = new PrimeFinderThread(20000000, 30000000);

		pft.start();
		pft2.start();
		pft3.start();

		pft.sleep(5000);
		//pft2.sleep(5000);
		//pft3.sleep(5000);

		pft.wait();
		pft2.wait();
		pft3.wait();

	    //Check if the thread is sleeping
		while (pft.getState() == "TIMED_WAITING"){

		}


	}
	
}

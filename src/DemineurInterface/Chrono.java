package DemineurInterface;

import java.util.TimerTask;

public class Chrono extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
			
		
		if ((DemTest.compteur >= 0) && (DemTest.ccases < DemTest.ncases) && (DemTest.nmines == DemTest.mines)) {
			DemTest.compteur ++;
//	Affiche chrono
			DemTest.ttimer.setText (Integer.toString (DemTest.compteur));
			
	         if (DemTest.compteur >= 180 ) {
	        	 DemTest.tnmines.setText ("perdu");
	        	 DemTest.compteur = -1;
	        	 DemTest.ccases = DemTest.ncases;

	         }
	      	
	     }

	}

}

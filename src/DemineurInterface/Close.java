package DemineurInterface;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


	
	//Fermeture de la fenêtre
	public final class Close extends WindowAdapter {
		public void windowClosing (WindowEvent e) {
			System.exit (0) ;
		}
	}


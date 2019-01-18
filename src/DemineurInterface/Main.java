package DemineurInterface;

import java.awt.Frame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int largeur = 800;
		int hauteur = 700;

		
	//Démarrage
		DemTest dem = new DemTest();
		dem.init();

		
	//Création de la frame
		Frame f = new Frame ("Démineur") ;
		f.addWindowListener (new Close ()) ;
		f.add (dem) ;
		f.setTitle ("Jeu de démineur") ;
		f.setSize (largeur, hauteur) ;
		f.setVisible (true);

	}

}

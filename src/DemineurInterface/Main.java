package DemineurInterface;

import java.awt.Frame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int largeur = 800;
		int hauteur = 700;

		
	//D�marrage
		DemTest dem = new DemTest();
		dem.init();

		
	//Cr�ation de la frame
		Frame f = new Frame ("D�mineur") ;
		f.addWindowListener (new Close ()) ;
		f.add (dem) ;
		f.setTitle ("Jeu de d�mineur") ;
		f.setSize (largeur, hauteur) ;
		f.setVisible (true);

	}

}

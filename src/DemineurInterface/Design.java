package DemineurInterface;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Design extends Canvas implements MouseListener{
	
	 static final long serialVersionUID = 210712L;
	 Image img;
	 Graphics g;
	 int w, h;
	 boolean gagne;
	
	 
	 public Design () {
	     addMouseListener (this);
	 }
	
	 public void update (Graphics g) {
	     paint (g) ;
	 }
	
	
	 public void paint (Graphics g1) {
	     if (img == null) {
	//premier graphique : on initialise
	         w = getSize().width;
	         h = getSize().height;
	         img = createImage (w, h);
	         g = img.getGraphics ();
	  }
	     
	     
	
	//on dessine sur img
	     g.setColor (Color.LIGHT_GRAY);
	     g.fillRect (0, 0, w, h);
	     g.setColor (Color.BLACK);
	     DemTest.pas = Math.min ((w - 20) / DemTest.largeur, (h - 20) / DemTest.hauteur);
	     for (int i = 0; i < DemTest.largeur; i++)
	         for (int j = 0; j < DemTest.hauteur; j++)
	             g.drawRect (10 + i * DemTest.pas, 10 + j * DemTest.pas, DemTest.pas, DemTest.pas);
	     gagne = true;
	     for (int i = 0; i < DemTest.largeur; i++)
	         for (int j = 0; j < DemTest.hauteur; j++) {
	                 int grilleij = DemTest.grille [i] [j];
	//dessin d'un cercle rouge, emplacement défini par l'utilisateur
	                 if (grilleij >= 15) {
	                     g.setColor (Color.MAGENTA);
	                     g.drawOval (11 + i * DemTest.pas, 11 + j * DemTest.pas, DemTest.pas - 2, DemTest.pas - 2);
	                 }
	//case à contenu encore inconnu : couleur orange
	                 else if (grilleij >= 0) {
	                     g.setColor (Color.ORANGE);
	                     g.fillRect (11 + i * DemTest.pas, 11 + j * DemTest.pas, DemTest.pas - 2, DemTest.pas - 2);
	                 }
	//case contenant une mine
	                 else if (DemTest.grille [i] [j] == -10) {
	                     g.setColor (Color.RED);
	                     g.fillOval (11 + i * DemTest.pas, 11 + j * DemTest.pas, DemTest.pas - 2, DemTest.pas - 2);
	                     gagne = false;
	                 }
	//on affiche le nombre de mines à proximité
	                 else if (DemTest.grille [i] [j] != -9) {
	                     g.setColor (Color.BLACK);
	                     g.drawString (Integer.toString (- grilleij), 10 + i * DemTest.pas + DemTest.pas / 2, 10 + j * DemTest.pas + DemTest.pas / 2);
	                 }
	             }
	//on bascule img dans la fenêtre graphique
	     g1.drawImage (img, 0, 0, this);
	     if ((DemTest.ccases >= DemTest.ncases) || (DemTest.nmines != DemTest.mines )) {
	    	 DemTest.tnmines.setText ((gagne) ? "gagné" : "perdu");
	    	 DemTest.ccases = DemTest.ncases;
	     	       
	     }
	 }
	
	/*
	* Si la case contient le nombre de mines à proximité on remplace ce nombre par son opposé
	* Si la case est blanche, il n'y a aucune mine dans les cases adjacentes, on détecte donc toutes
	* les cases blanches à partir de la case active par une recherche récursive
	*/
	
	 private void exploreCaseBlanche (int x, int y) {
	     if ((x >= 0) && (x < DemTest.largeur) && (y >= 0) && (y < DemTest.hauteur) && (DemTest.grille [x] [y] < 15)) {
	         if (DemTest.grille [x] [y] > 0) {
	             if (DemTest.grille [x] [y] == 10) {
	            	 DemTest.nmines --;
	            	 DemTest.tnmines.setText (Integer.toString (DemTest.nmines));
	             }
	             DemTest.grille [x] [y] = - DemTest.grille [x] [y];
	             DemTest.ccases ++;
	         }
	         else if (DemTest.grille [x] [y] == 0) {
	        	 DemTest.grille [x] [y] = -9;
	        	 DemTest.ccases ++;
	             exploreCaseBlanche (x - 1, y - 1);
	             exploreCaseBlanche (x - 1, y);
	             exploreCaseBlanche (x - 1, y + 1);
	             exploreCaseBlanche (x, y - 1);
	             exploreCaseBlanche (x, y + 1);
	             exploreCaseBlanche (x + 1, y - 1);
	             exploreCaseBlanche (x + 1, y);
	             exploreCaseBlanche (x + 1, y + 1);
	         }
	     }
	 }
	 
	
	/*
	* Réponse à l'action sur un bouton de la souris
	*
	*/
	
	 public void mousePressed (MouseEvent e) {
	//Si ce n'est pas déjà fait on déclenche le compteur
	     if (DemTest.compteur < 0)
	    	 DemTest.compteur = 0;
	//Si c'est fini on bloque l'action sur le bouton de la souris
	     if (DemTest.ccases < DemTest.ncases) {
	//On récupère les coordonnées de la case
	         int x = (e.getX () - 10) / DemTest.pas;
	         int y = (e.getY () - 10) / DemTest.pas;
	         if (e.getButton () == MouseEvent.BUTTON1)
	             exploreCaseBlanche (x, y);
	         else {
	//Bouton 2 pressé : on positionne ou on retire un drapeau pour indiquer une mine
	             if ((x >= 0) && (x < DemTest.largeur) && (y >= 0) && (y < DemTest.hauteur) && (DemTest.grille [x] [y] >= 0)) {
	                 if (DemTest.grille [x] [y] >= 15) {
	                	 DemTest.grille [x] [y] -= 15;
//	                     nmines ++;
	                     if (DemTest.grille [x] [y] == 10)
	                    	 DemTest.ccases --;
	                     if (DemTest.rmines < 10)
	                    	 DemTest.rmines ++;
	                 }
	                 else {
	              	   if (DemTest.rmines != 0) {
		                       if (DemTest.grille [x] [y] == 10)
		                    	   DemTest.ccases ++;
		                	  
		                       DemTest.grille [x] [y] += 15;
		                       DemTest.rmines --;
		
	              	   }
	                 }

	                 DemTest.rnmines.setText (Integer.toString (DemTest.rmines));
	             }
	         }
	     repaint ();
	     }
	 }
	
	 public void mouseExited (MouseEvent e) { }
	 public void mouseEntered (MouseEvent e) { }
	 public void mouseReleased (MouseEvent e) { }
	 public void mouseClicked (MouseEvent e) { }
	
	
}

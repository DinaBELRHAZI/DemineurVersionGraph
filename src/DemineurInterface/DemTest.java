package DemineurInterface;



import java.awt.* ;
import java.awt.event.* ;
import java.util.* ;




//import javax.swing.JLabel;

public class DemTest extends java.applet.Applet implements ActionListener {


 /**
	 * 
	 */
 private static final long serialVersionUID = 1L;

 static TextField tlargeur, thauteur, tmines, ttimer, tnmines, rnmines;
 static int hauteur, largeur, mines, pas, nmines, rmines;
 Button Valider;
 Random rnd;
 static int [] [] grille;
 Design design;
 Timer timer;
 Chrono crono;
 static int compteur, ccases, ncases;

 
/*
* convertit le contenu d'un champ de texte en nombre entier
* retourne n en cas d'échec
*/

 private int parse (TextField tf, int n) {
     int n1 = n;
     try {
         n1 = Integer.parseInt (tf.getText ());
         if (n1 <= 0)
             n1 = n;
     }
     catch (NumberFormatException nfe) { }
     tf.setText (Integer.toString (n1));
     return n1;
 }

/*
* initialisation de la grille ; grille [l] [h] = 10 pour une mine déposée à la case de coordonnées l, h.
* renseigne les cases adjacentes en faisant figurer le nombre de mines (de 1 à 8) voisines de la case de coordonnées l, h.
* 0 (valeur par défaut) si pas de mines proches de la case.
*/

 private void initgrille (int largeur, int hauteur, int mines) {
     grille = new int [largeur] [hauteur];
     for (int i = 0; i < mines; i ++) {
         int l, h;
//choix au hasard d'une case libre pour y déposer une mine
         do {
             l = rnd.nextInt (largeur);
             h = rnd.nextInt (hauteur);
         }
         while (grille [l] [h] == 10);
         grille [l] [h] = 10;
     }
     
     for (int i = 0; i < largeur; i++) {
         for (int j = 0; j < hauteur; j++) {
             if (grille [i] [j] != 10) {
//On met à jour les cases voisines de la case contenant la mine
                 if ((i > 0) && (j > 0) && (grille [i - 1] [j - 1] == 10))
                     grille [i] [j] ++;
                 if ((i > 0) && (grille [i - 1] [j] == 10))
                     grille [i] [j] ++;
                 if ((i > 0) && (j < hauteur - 1) && (grille [i - 1] [j + 1] == 10))
                     grille [i] [j] ++;
                 if ((j > 0) && (grille [i] [j - 1] == 10))
                     grille [i] [j] ++;
                 if ((j < hauteur - 1) && (grille [i] [j + 1] == 10))
                     grille [i] [j] ++;
                 if ((i < largeur - 1) && (j > 0) && (grille [i + 1] [j - 1] == 10))
                     grille [i] [j] ++;
                 if ((i < largeur - 1) && (grille [i + 1] [j] == 10))
                     grille [i] [j] ++;
                 if ((i < largeur - 1) && (j < hauteur - 1) && (grille [i + 1] [j + 1] == 10))
                     grille [i] [j] ++;
             }
         }
     }
 }

/*
* initialisation de l'applet
*
*/

 public void init () {
     setFont (new Font ("Arial, serif", Font.BOLD, 20));
     setLayout (new BorderLayout ());
//on met en place les objets (champs de saisie, bouton...)
     Panel p = new Panel ();
     p.setBackground (Color.orange);
     add (p, BorderLayout.NORTH);
     p.add (new Label ("Largeur :"));
     p.add (tlargeur = new TextField ("10", 3));
     p.add (new Label ("Hauteur : "));
     p.add (thauteur = new TextField ("10", 3));
     p.add (new Label ("Mines :"));
     p.add (tmines = new TextField ("10", 3));
     p.add (Valider = new Button (" Valider"));
     Valider.setBackground(Color.lightGray); 
     Valider.addActionListener (this);
     p = new Panel ();
     p.setBackground (Color.orange);
     add (p, BorderLayout.SOUTH);
     p.add (new Label ("Mines restantes :"));    
     p.add (rnmines = new TextField ("10", 2));
     p.add (new Label ("Résultat :"));    
     p.add (tnmines = new TextField (" ", 5));
     p.add (new Label ("Temps écoulé :"));
     p.add (ttimer = new TextField ("10", 3));
     
     add (design = new Design (), BorderLayout.CENTER);

     //on met en place le générateur de nombres aléatoires
     rnd = new Random ();
     init1 ();
   //on met en place le timer
     crono = new Chrono ();
     timer = new Timer ();
     timer.schedule (crono, 0, 1000);
     crono.run();
 }

/*
* initialisation : installation de l'applet ou appui sur le bouton Valider
*
*/



private void init1 () {
//on récupère le nombre de cases en largeur, en hauteur et le nombre de mines
     int l = largeur;
     int h = hauteur;
     int m = mines;
     largeur = parse (tlargeur, largeur);
     hauteur = parse (thauteur, hauteur);
     mines = parse (tmines, mines);
//trop de mines => on remet les données initiales
     if (mines >= largeur * hauteur) {
         largeur = l;
         hauteur = h;
         mines = m;
         tlargeur.setText (Integer.toString (largeur));
         thauteur.setText (Integer.toString (hauteur));
         tmines.setText (Integer.toString (mines));
     }
//initialisation de la grille
     initgrille (largeur, hauteur, mines);
//initialisation du compteur du timer
     compteur = -1;
     ttimer.setText ("0");
     ccases = 0;
     ncases = hauteur * largeur;
//initialisation de nmines qui contient le nombre de mines à trouver
     nmines = mines;
     tnmines.setText (" ");
     rmines = mines;
     rnmines.setText (Integer.toString (rmines));

  
 }

/*
* réponse à l'appui sur le bouton Valider
*
*/

 public void actionPerformed (ActionEvent e) {
     if (e.getSource () == Valider) {
         init1 ();
         design.repaint ();
         
         
     }
 }


}
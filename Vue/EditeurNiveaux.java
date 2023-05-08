package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import Modele.Ball;
import Modele.Niveau;
import Modele.Pegs;

public class EditeurNiveaux extends JPanel {

    // Controleur
    private Controleur controleur;
    int width;
    int height;

    // Court
    Court court;
    private int courtWidth;
    private int courtHeight;
    Niveau niveauCree;
    CasePeg caseActive;
    ArrayList<Pegs> pegsSelectionnes;
    boolean enModif;

    JSlider sliderPegSelectionne;
    BoutonCouleur bleu;
    BoutonCouleur rouge;
    BoutonCouleur violet;
    BoutonCouleur vert;
    JButton croix;
    JButton modif;
    int valeurAlignement;
    int valeurMouvement;
    int valeurVitesse;
    int largeurBouton;
    CasePeg casePegBleu;
    CasePeg casePegRouge;
    CasePeg casePegViolet;
    CasePeg casePegVert;
    JTextField nomNiveau;
    JCheckBox uniforme;
    JMenuBar menuBar;

    EditeurNiveaux(Controleur controleur) {
        this.controleur = controleur;
        width = controleur.getWidth();
        height = controleur.getHeight();
        setLayout(null);
        setSize(width, height);

        niveauCree = new Niveau("enAttente");
        niveauCree.isCampagne(false);
        pegsSelectionnes = new ArrayList<>();

        // JMenuBar menuBar
        menuBar = new JMenuBar();
        int heightMenuBar = 30;
        menuBar.setBounds(0, 0, width, heightMenuBar);
        add(menuBar);

        // Court
        courtWidth = width * 5/6;
        courtHeight = height * 5/6;
        court = new Court(courtWidth, courtHeight, niveauCree);
        court.activerModeEditeur(this);
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds(0, heightMenuBar, courtWidth, courtHeight);
        court.setVisible(true);
        add(court, BorderLayout.CENTER);

        largeurBouton = courtHeight * 1/16;

        // JMenu Editeur
        JMenu menuEditeur = new JMenu("Editeur");
        menuEditeur.setBounds(0, 0, 100, heightMenuBar);
        menuBar.add(menuEditeur);

        // JButton back
        JButton back = new JButton("Back");
        menuEditeur.add(back);
        back.addActionListener(e -> {menuEditeur.getPopupMenu().setVisible(false); controleur.launchMenu();});

        // JButton newLevel
        JButton newLevel = new JButton("New");
        newLevel.addActionListener(e -> reset());
        menuEditeur.add(newLevel);

        // JButton save
        JButton save = new JButton("Save");
        save.setEnabled(false);
        menuEditeur.add(save);
        save.addActionListener(e-> niveauCree.save(courtWidth, courtHeight));

        // JMenu Niveau
        JMenu menuNiveau = new JMenu("Niveau");
        menuNiveau.setBounds(100, 0, 100, heightMenuBar);
        menuBar.add(menuNiveau);

        // JCheckBox campagne
        JCheckBox campagne = new JCheckBox("Campagne", false);
        campagne.setBounds((width - courtWidth) + 5, courtHeight * 1/16 + 20, courtHeight * 2/16, courtHeight * 1/16);
        campagne.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {           
                if (e.getStateChange()==1) niveauCree.isCampagne(true);
                else niveauCree.isCampagne(false);
            }    
            });   
        menuNiveau.add(campagne);

        // JTextField nomNiveau
        String[] placeHolders = new String[]{" Nom du niveau", " Nom déjà utilisé"};
        nomNiveau = new JTextField(placeHolders[0]);
        nomNiveau.setForeground(Color.GRAY);
        nomNiveau.addFocusListener(new FocusListener() {
            String currentHolder;
            public void focusGained(FocusEvent e) {
                for (String string : placeHolders) {
                    if (nomNiveau.getText().equals(string)) {
                        nomNiveau.setForeground(Color.BLACK);
                        currentHolder = nomNiveau.getText();
                        nomNiveau.setText("");
                    }
                }
            }
            public void focusLost(FocusEvent e) {
                if (nomNiveau.getText().isEmpty()) {
                    nomNiveau.setForeground(Color.GRAY);
                    nomNiveau.setText(currentHolder);
                }
            }
        });
        menuNiveau.add(nomNiveau);

        // JButton save Nom
        JButton saveNom = new JButton("Valider nom");
        menuNiveau.add(saveNom);
        saveNom.addActionListener(e -> {
            if (!Niveau.getAllNameNiveau().contains(nomNiveau.getText())
                && !nomNiveau.getText().equals(placeHolders[0])
                && !nomNiveau.getText().equals(placeHolders[1])) {
                niveauCree.setNom(nomNiveau.getText());
                save.setEnabled(true);
            }
            else nomNiveau.setText(placeHolders[1]);
        });

        // JMenu alignement
        JMenu alignement = new JMenu("Alignement");
        ButtonGroup groupAlignement = new ButtonGroup();
        alignement.setBounds(200, 0, 100, heightMenuBar);
        menuBar.add(alignement);

        // RadioButtonValue aucun
        RadioButtonValue aucunAlignement = new RadioButtonValue("aucun", 0, true);
        aucunAlignement.setSelected(true);
        alignement.add(aucunAlignement);
        groupAlignement.add(aucunAlignement);

        // RadioButtonValue horizontal
        RadioButtonValue horizontal = new RadioButtonValue("horizontal", 1, true);
        alignement.add(horizontal);
        groupAlignement.add(horizontal);

        // RadioButtonValue vertical
        RadioButtonValue vertical = new RadioButtonValue("vertical", 2, true);
        alignement.add(vertical);
        groupAlignement.add(vertical);

        // RadioButtonValue diagonal
        RadioButtonValue diagonal = new RadioButtonValue("diagonal", 3, true);
        alignement.add(diagonal);
        groupAlignement.add(diagonal);
        JMenu circulaire = new JMenu("circulaire");
        alignement.add(circulaire);

        // RadioButtonValue centre
        RadioButtonValue centre = new RadioButtonValue("autour du centre", 4, true);
        circulaire.add(centre);
        groupAlignement.add(centre);

        // RadioButtonValue inscrit
        RadioButtonValue inscrit = new RadioButtonValue("inscrit au rectangle",5, true);
        circulaire.add(inscrit);
        groupAlignement.add(inscrit);

        // JCheckBox uniforme
        uniforme = new JCheckBox("uniforme");
        alignement.add(uniforme);

        // JMenu mouvement
        JMenu menuMouvement = new JMenu("Fct de mouvement");
        ButtonGroup groupMouvement = new ButtonGroup();
        menuMouvement.setBounds(300, 0, 150, heightMenuBar);
        menuBar.add(menuMouvement);

        // RadioButtonValue aucunMouvement
        RadioButtonValue aucunMouvement = new RadioButtonValue("Aucun", 0, false);
        aucunMouvement.setSelected(true);
        groupMouvement.add(aucunMouvement);
        menuMouvement.add(aucunMouvement);

        // RadioButtonValue traverséeGD
        RadioButtonValue traverseeGD = new RadioButtonValue("Traversée gauche-droite", 1, false);
        groupMouvement.add(traverseeGD);
        menuMouvement.add(traverseeGD);

        // RadioButtonValue traverséeGD
        RadioButtonValue traverseeDG = new RadioButtonValue("Traversée droite-gauche", 2, false);
        groupMouvement.add(traverseeDG);
        menuMouvement.add(traverseeDG);

        // RadioButtonValue rotationCentrale
        RadioButtonValue rotationCentrale = new RadioButtonValue("Rotation autour du centre", 3, false);
        groupMouvement.add(rotationCentrale);
        menuMouvement.add(rotationCentrale);
        menuMouvement.addSeparator();

        // JLabel labelSpeedPegs
        JLabel labelSpeedPegs = new JLabel("Vitesse des pegs :");
        menuMouvement.add(labelSpeedPegs);

        // JSlider speedPegs
        valeurVitesse = 100;
        JSlider speedPegs = new JSlider(50, 300, 100);
        speedPegs.addChangeListener(e -> valeurVitesse = speedPegs.getValue());
        menuMouvement.add(speedPegs);

        // JPanel panelCasesPeg
        JPanel panelCasesPeg = new JPanel();
        panelCasesPeg.setLayout(null);
        panelCasesPeg.setBounds(courtWidth, heightMenuBar, width - courtWidth, courtHeight);
        add(panelCasesPeg);

        // case Peg bleu
        casePegBleu = new CasePeg(width-courtWidth, courtHeight * 1/4, 1);
        casePegBleu.setBounds(0, 0, width - courtWidth, courtHeight * 1/4);
        panelCasesPeg.add(casePegBleu);

        // case Peg Rouge
        casePegRouge = new CasePeg(width-courtWidth, courtHeight * 1/4, 2);
        casePegRouge.setBounds(0, courtHeight * 1/4, width - courtWidth, courtHeight * 1/4);
        panelCasesPeg.add(casePegRouge);

        // case Peg Violet
        casePegViolet = new CasePeg(width-courtWidth, courtHeight * 1/4, 3);
        casePegViolet.setBounds(0, courtHeight * 1/2, width - courtWidth, courtHeight * 1/4);
        panelCasesPeg.add(casePegViolet);

        // case peg Vert
        casePegVert = new CasePeg(width-courtWidth, courtHeight * 1/4, 4);
        casePegVert.setBounds(0, courtHeight * 3/4, width - courtWidth, courtHeight * 1/4);
        panelCasesPeg.add(casePegVert);

        // JPanel panelBoutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBounds(0, courtHeight + heightMenuBar, width, height - courtHeight - heightMenuBar);
        panelBoutons.setLayout(null);
        add(panelBoutons);

        // slider peg selectionné
        sliderPegSelectionne = new JSlider(5, 60, 25);
        sliderPegSelectionne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sliderPegSelectionne.setBounds(0, 0, width - courtWidth, largeurBouton);
        sliderPegSelectionne.addChangeListener(e -> {
            for (Pegs peg: pegsSelectionnes) {
                peg.setRadius(sliderPegSelectionne.getValue());
            }
            court.setPegs(court.clonePegs(niveauCree.getPegs()));
            court.repaint();
        });
        panelBoutons.add(sliderPegSelectionne);

        // BoutonCouleur bleu
        bleu = new BoutonCouleur(1);
        bleu.setLocation(width - courtWidth, 0);
        panelBoutons.add(bleu);

        // BoutonCouleur rouge
        rouge = new BoutonCouleur(2);
        rouge.setLocation(width - courtWidth + largeurBouton, 0);
        panelBoutons.add(rouge);

        // BoutonCouleur violet
        violet = new BoutonCouleur(3);
        violet.setLocation(width - courtWidth + 2*largeurBouton, 0);
        panelBoutons.add(violet);

        // BoutonCouleur vert
        vert = new BoutonCouleur(4);
        vert.setLocation(width - courtWidth + 3*largeurBouton, 0);
        panelBoutons.add(vert);

        // JButton croix
        croix = new JButton("supp");
        croix.setBounds(width - courtWidth + 4*largeurBouton, 0, largeurBouton, largeurBouton);
        croix.addActionListener(e -> {
            for (Pegs peg : pegsSelectionnes) {
                niveauCree.getPegs().remove(peg);
            }
            pegsSelectionnes.clear();
            court.setPegs(court.clonePegs(niveauCree.getPegs()));
            court.repaint();
        });
        panelBoutons.add(croix);

        // Bouton pause
        JButton pause = new JButton("||");
        pause.setBounds(courtWidth - 2*largeurBouton, 0, largeurBouton, largeurBouton);
        panelBoutons.add(pause);
        pause.setEnabled(false);

        // Bouton resume
        JButton resume = new JButton("▶");
        resume.setBounds(courtWidth - largeurBouton, 0, largeurBouton, largeurBouton);
        resume.addActionListener(e -> {
            court.setEnPause(false);
            court.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            court.animate();
            resume.setEnabled(false);
            pause.setEnabled(true);
            caseActive.unclicked();
        });
        panelBoutons.add(resume);

        pause.addActionListener(e -> {
            court.setEnPause(true);
            // Remet en place les pegs originels.
            court.setPegs(court.clonePegs(niveauCree.getPegs()));
            court.getToucherPegs().clear();
            // Enlève les balles toujours sur le court
            for (Ball ball: court.getBalls()) {
                if (ball.isPresent()) ball.setPresent(false);
            }
            court.repaint();
            resume.setEnabled(true);
            pause.setEnabled(false);
            casePegBleu.mousePressed(null);
        });

        //JButton modif
        modif = new JButton("modif");
        modif.setBounds(courtWidth - 3*largeurBouton, 0, largeurBouton, largeurBouton);
        modif.addActionListener(e -> {
            pause.doClick();
            enModif = true;
            caseActive.unclicked();
            court.setCursor(new Cursor(Cursor.HAND_CURSOR));
        });
        // TODO ajout icon
        // Icon icon = new ImageIcon(ImageImport.getImage("curseurMain.jpg"));
        // modif.setIcon(icon);
        panelBoutons.add(modif);

        // Réglages par défaut
        casePegBleu.mousePressed(null);
    }

    public void boutonsModifActifs(boolean activer) {
        sliderPegSelectionne.setEnabled(activer);
        bleu.setEnabled(activer);
        rouge.setEnabled(activer);
        violet.setEnabled(activer);
        vert.setEnabled(activer);
        croix.setEnabled(activer);
    }

    public void reset() {
        niveauCree = new Niveau("enAttente");
        court.setNiveau(niveauCree);
        court.getPegs().clear();
        court.getToucherPegs().clear();
        court.repaint();
        casePegBleu.sliderRayonPeg.setValue(25);
        casePegRouge.sliderRayonPeg.setValue(25);
        casePegViolet.sliderRayonPeg.setValue(25);
        casePegVert.sliderRayonPeg.setValue(25);
        nomNiveau.setText(" Nom du niveau");
    }


    public class CasePeg extends JPanel implements MouseInputListener{

        int largeur, hauteur, couleur;
        Pegs peg; // Le peg représenté dans la case.
        Pegs modeleActuel; // Le peg utilisé en preview sur le court.
        JSlider sliderRayonPeg;

        public CasePeg(int largeur, int hauteur, int couleur) {
            this.largeur = largeur;
            this.hauteur = hauteur;
            this.couleur = couleur;
            peg = new Pegs(largeur/2, hauteur*3/4/2, 25, couleur);
            modeleActuel = new Pegs(-100, -100, peg.getRadius(), couleur);

            setLayout(null);
            sliderRayonPeg = new JSlider(5, 60, 25);
            sliderRayonPeg.setBounds(0, hauteur*3/4, largeur, hauteur*1/4);
            sliderRayonPeg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sliderRayonPeg.addChangeListener(e -> {
                peg.setRadius(sliderRayonPeg.getValue());
                paint(this.getGraphics());
                modeleActuel.setRadius(sliderRayonPeg.getValue());
            });
            add(sliderRayonPeg);

            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(ImageImport.getImage(peg.getImageString()), (int) peg.getX()-peg.getRadius(), (int) peg.getY()-peg.getRadius(), peg.getDiametre(), peg.getDiametre(), this);
        }

        public void unclicked() {
            setBackground(UIManager.getColor("Panel.background")); // Rend au panel son background originel.
        }

        public void mousePressed(MouseEvent e) {
            if (caseActive != null) caseActive.unclicked();
            caseActive = this;
            enModif = false;
            boutonsModifActifs(false);
            court.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            setBackground(Color.LIGHT_GRAY);
        }

        public void mouseClicked(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseDragged(MouseEvent e) {}
        public void mouseMoved(MouseEvent e) {}
    }

    public class BoutonCouleur extends JButton {

        int couleur;

        public BoutonCouleur(int couleur) {
            this.couleur = couleur;
            addActionListener(e -> {
                for (Pegs peg : pegsSelectionnes) {
                    peg.setCouleur(couleur);
                    peg.setImageString(Pegs.intColorToString(couleur));
                }
                court.setPegs(court.clonePegs(niveauCree.getPegs()));
                court.repaint();
            });
            Icon icon = new ImageIcon(ImageImport.getImage(Pegs.intColorToString(couleur)));
            setIcon(icon);
            setSize(largeurBouton, largeurBouton);
        }
    }

    // Classe conçue pour les radioButton des menus alignement et mouvement.
    public class RadioButtonValue extends JRadioButtonMenuItem {

        public RadioButtonValue(String text, int value, boolean alignement) {
            super(text);
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (RadioButtonValue.this.isSelected()) {
                        if (alignement) valeurAlignement = value;
                        else valeurMouvement = value;
                        if (modif != null) modif.doClick();
                    }
                }
            });
        }
    }
}

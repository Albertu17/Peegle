package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;

import Modele.Ball;
import Modele.Niveau;
import Modele.Pegs;
import Vue.Menu.BoutonMenu;

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
    int largeurBouton;

    EditeurNiveaux(Controleur controleur) {
        this.controleur = controleur;
        width = controleur.getWidth();
        height = controleur.getHeight();
        setLayout(null);
        setSize(width, height);

        niveauCree = new Niveau("enAttente");
        niveauCree.isCampagne(false);
        pegsSelectionnes = new ArrayList<>();

        // Court
        courtWidth = width * 5/6;
        courtHeight = height * 5/6;
        court = new Court(courtWidth, courtHeight, niveauCree);
        court.activerModeEditeur(this);
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds(0, 0, courtWidth, courtHeight);
        court.setVisible(true);
        add(court, BorderLayout.CENTER);

        largeurBouton = courtHeight * 1/16;

        // case Peg bleu
        CasePeg casePegBleu = new CasePeg(width-courtWidth, courtHeight * 1/4, 1);
        casePegBleu.setBounds(courtWidth, 0, width - courtWidth, courtHeight * 1/4);
        add(casePegBleu);

        // case Peg Rouge
        CasePeg casePegRouge = new CasePeg(width-courtWidth, courtHeight * 1/4, 2);
        casePegRouge.setBounds(courtWidth, courtHeight * 1/4, width - courtWidth, courtHeight * 1/4);
        add(casePegRouge);

        // case Peg Violet
        CasePeg casePegViolet = new CasePeg(width-courtWidth, courtHeight * 1/4, 3);
        casePegViolet.setBounds(courtWidth, courtHeight * 1/2, width - courtWidth, courtHeight * 1/4);
        add(casePegViolet);

        // case peg Vert
        CasePeg casePegVert = new CasePeg(width-courtWidth, courtHeight * 1/4, 4);
        casePegVert.setBounds(courtWidth, courtHeight * 3/4, width - courtWidth, courtHeight * 1/4);
        add(casePegVert);

        // JPanel panelBoutons
        JPanel panelBoutons = new JPanel();
        panelBoutons.setBounds(0, courtHeight, width, height - courtHeight);
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

        // JComboBox alignement
        String[] values = {"Aucun", "Haut-Bas", "Bas-Haut", "Vertical", "Horizontal"};
        JComboBox<String> comboBoxAlignement = new JComboBox<String>(values);
        comboBoxAlignement.setBounds(width - courtWidth + 6*largeurBouton, 0, 3*largeurBouton, largeurBouton);
        panelBoutons.add(comboBoxAlignement);

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
        });
        // TODO ajout icon
        // Icon icon = new ImageIcon(ImageImport.getImage("curseurMain.jpg"));
        // modif.setIcon(icon);
        panelBoutons.add(modif);

        // JTextField nomNiveau
        String[] placeHolders = new String[]{" Nom du niveau", " Nom déjà utilisé"};
        JTextField nomNiveau = new JTextField();
        nomNiveau.setText(placeHolders[0]);
        nomNiveau.setBounds(5, courtHeight * 1/16 + 20, (width - courtWidth) - courtHeight * 1/16, courtHeight * 1/16);
        nomNiveau.addFocusListener(new FocusPlaceholder(nomNiveau, placeHolders));
        panelBoutons.add(nomNiveau);

        // JButton save Nom
        JButton saveNom = new JButton("OK");
        saveNom.setBounds((width - courtWidth) - courtHeight * 1/16 + 5, courtHeight * 1/16 + 20, courtHeight * 1/16, courtHeight * 1/16);
        panelBoutons.add(saveNom);

        // JCheckBox campagne
        JCheckBox campagne = new JCheckBox("Campagne", true);
        campagne.setBounds((width - courtWidth) + 5, courtHeight * 1/16 + 20, courtHeight * 2/16, courtHeight * 1/16);
        campagne.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {           
                if (e.getStateChange()==1) niveauCree.isCampagne(true);
                else niveauCree.isCampagne(false);
            }    
         });   
        panelBoutons.add(campagne);

        // JButton back
        JButton back = new BoutonMenu("Back", (width - courtWidth)/2, (height - courtHeight)/2);
        // back.setBounds(courtWidth, 0, (width - courtWidth)/2, (height - courtHeight)/2);
        back.setLocation(courtWidth, 0);
        panelBoutons.add(back);
        back.addActionListener(e -> controleur.launchMenu());

        // JButton newLevel
        JButton newLevel = new BoutonMenu("New",(width - courtWidth)/2, (height - courtHeight)/2);
        // newLevel.setBounds(courtWidth, (height - courtHeight)/2, (width - courtWidth)/2, (height - courtHeight)/2);
        newLevel.setLocation(courtWidth, (height - courtHeight)/2);
        panelBoutons.add(newLevel);
        newLevel.addActionListener(e -> {
            controleur.editeurNiveaux = null;
            controleur.launchEditeurNiveaux();
        });

        // JButton save
        JButton save = new BoutonMenu("Save", (width - courtWidth)/2, height - courtHeight);
        save.setEnabled(false);
        // save.setBounds(courtWidth + (width - courtWidth)/2, 0, (width - courtWidth)/2, height - courtHeight);
        save.setLocation(courtWidth + (width - courtWidth)/2, 0);
        panelBoutons.add(save);
        save.addActionListener(e->niveauCree.save(courtWidth, courtHeight));

        saveNom.addActionListener(e -> {
            if (!Niveau.getAllNameNiveau().contains(nomNiveau.getText())
             && !nomNiveau.getText().equals(placeHolders[0])
             && !nomNiveau.getText().equals(placeHolders[1])) {
                niveauCree.setNom(nomNiveau.getText());
                save.setEnabled(true);
            }
            else nomNiveau.setText(placeHolders[1]);
        });

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


    public class CasePeg extends JPanel implements MouseInputListener{

        int largeur, hauteur, couleur;
        Pegs peg; // Le peg représenté dans la case.
        Pegs modeleActuel; // Le peg utilisé en preview sur le court.

        public CasePeg(int largeur, int hauteur, int couleur) {
            this.largeur = largeur;
            this.hauteur = hauteur;
            this.couleur = couleur;
            peg = new Pegs(largeur/2, hauteur*3/4/2, 25, couleur);
            modeleActuel = new Pegs(0, 0, peg.getRadius(), couleur);

            setLayout(null);
            JSlider sliderRayonPeg = new JSlider(5, 60, 25);
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
            g2d.drawImage(ImageImport.getImage(peg.getImageString()), peg.getX()-peg.getRadius(), peg.getY()-peg.getRadius(), peg.getDiametre(), peg.getDiametre(), this);
        }

        public void unclicked() {
            setBackground(UIManager.getColor("Panel.background")); // Rend au panel son background originel.
        }

        public void mousePressed(MouseEvent e) {
            if (caseActive != null) caseActive.unclicked();
            caseActive = this;
            enModif = false;
            boutonsModifActifs(false);
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

    private class FocusPlaceholder implements FocusListener {
        JTextField field; 
        String[] placeHolders;
        String currentHolder;

        FocusPlaceholder(JTextField field, String[] placeHolders) {
            this.field = field ;
            this.placeHolders = placeHolders.clone() ;
            field.setForeground(Color.GRAY);
        }

        public void focusGained(FocusEvent e) {
            for (String string : placeHolders) {
                if (field.getText().equals(string)) {
                    field.setForeground(Color.BLACK);
                    currentHolder = field.getText();
                    field.setText("");
                }
            }
        }

        public void focusLost(FocusEvent e) {
            if (field.getText().isEmpty()) {
                field.setForeground(Color.GRAY);
                field.setText(currentHolder);
            }
        }
    }
}

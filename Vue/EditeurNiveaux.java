package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;

import Modele.Niveau;
import Modele.Pegs;

public class EditeurNiveaux {

    public Controleur controleur;
    Container fond;

    private int width;
    private int height;

    // Court
    Court court;
    private int courtWidth;
    private int courtHeight;

    Niveau niveauCree;
    CasePeg caseActive;
    Pegs pegSelectionne;
    boolean enModif;

    JSlider sliderPegSelectionne;
    BoutonCouleur bleu;
    BoutonCouleur rouge;
    BoutonCouleur violet;
    BoutonCouleur vert;
    JButton croix;
    JButton modif;

    // TODO: imageicon boutons, rebond sur pegs (chmt l 152 BALL),fix bug sceau qui disparaît

    EditeurNiveaux(Controleur c) {

        this.controleur = c;
        fond = c.container;
        width = controleur.getWidth();
        height = controleur.getHeight();

        niveauCree = new Niveau("enAttente");

        // Court
        courtWidth = width * 5/6;
        courtHeight = height * 5/6;
        court = new Court(courtWidth, courtHeight, niveauCree);
        court.editMode = true;
        court.eN = this;
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds(0, 0, courtWidth, courtHeight);
        court.setVisible(true);
        fond.add(court, BorderLayout.CENTER);

        // case Peg bleu
        CasePeg casePegBleu = new CasePeg(width-courtWidth, courtHeight * 1/4, 1);
        casePegBleu.setBounds(courtWidth, 0, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegBleu);

        // case Peg Rouge
        CasePeg casePegRouge = new CasePeg(width-courtWidth, courtHeight * 1/4, 2);
        casePegRouge.setBounds(courtWidth, courtHeight * 1/4, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegRouge);

        // case Peg Violet
        CasePeg casePegViolet = new CasePeg(width-courtWidth, courtHeight * 1/4, 3);
        casePegViolet.setBounds(courtWidth, courtHeight * 1/2, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegViolet);

        // case peg Vert
        CasePeg casePegVert = new CasePeg(width-courtWidth, courtHeight * 1/4, 4);
        casePegVert.setBounds(courtWidth, courtHeight * 3/4, width - courtWidth, courtHeight * 1/4);
        fond.add(casePegVert);

        // Bouton pause
        JButton pause = new JButton("||");
        pause.setBounds(courtWidth - 100, courtHeight, 50, 50);
        fond.add(pause);

        // Bouton resume
        JButton resume = new JButton("|>");
        resume.setBounds(courtWidth - 50, courtHeight, 50, 50);
        resume.addActionListener(e -> {
            court.editMode = false;
            court.animate();
            resume.setEnabled(false);
            pause.setEnabled(true);
            caseActive.unclicked();
        });
        fond.add(resume);

        pause.addActionListener(e -> {
            court.editMode = true;
            resume.setEnabled(true);
            pause.setEnabled(false);
            casePegBleu.mouseClicked(null);
        });

        // slider peg selectionné
        sliderPegSelectionne = new JSlider(10, 110, 50);
        sliderPegSelectionne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sliderPegSelectionne.setBounds(0, courtHeight, width - courtWidth, courtHeight * 1/16);
        sliderPegSelectionne.addChangeListener(e -> {
            pegSelectionne.setRadius(sliderPegSelectionne.getValue());
            court.repaint();
        });
        fond.add(sliderPegSelectionne);

        // BoutonCouleur bleu
        bleu = new BoutonCouleur(1);
        bleu.setBounds(width - courtWidth, courtHeight, courtHeight * 1/16, courtHeight * 1/16);
        fond.add(bleu);

        // BoutonCouleur rouge
        rouge = new BoutonCouleur(2);
        rouge.setBounds(width - courtWidth + courtHeight * 1/16, courtHeight, courtHeight * 1/16, courtHeight * 1/16);
        fond.add(rouge);

        // BoutonCouleur violet
        violet = new BoutonCouleur(3);
        violet.setBounds(width - courtWidth + courtHeight * 1/8, courtHeight, courtHeight * 1/16, courtHeight * 1/16);
        fond.add(violet);

        // BoutonCouleur vert
        vert = new BoutonCouleur(4);
        vert.setBounds(width - courtWidth + courtHeight * 3/16, courtHeight, courtHeight * 1/16, courtHeight * 1/16);
        fond.add(vert);

        // JButton croix
        croix = new JButton("supp");
        croix.setBounds(width - courtWidth + courtHeight * 1/4, courtHeight, courtHeight * 1/16, courtHeight * 1/16);
        croix.addActionListener(e -> niveauCree.getPegs().remove(pegSelectionne));
        fond.add(croix);

        //JButton modif
        modif = new JButton("modif");
        modif.setBounds(width - courtWidth + courtHeight * 5/16, courtHeight, courtHeight * 1/16, courtHeight * 1/16);
        modif.addActionListener(e -> {
            enModif = true;
            boutonsModifActifs(true);
            caseActive.unclicked();
            pause.doClick();
        });
        // TODO ajout icon
        // Icon icon = new ImageIcon(ImageImport.getImage("curseurMain.jpg"));
        // modif.setIcon(icon);
        fond.add(modif);

        // JTextField nomNiveau
        String[] placeHolders = new String[]{" Nom du niveau", " Nom déjà utilisé"};
        JTextField nomNiveau = new JTextField();
        nomNiveau.setText(placeHolders[0]);
        nomNiveau.setBounds(5, courtHeight + courtHeight * 1/16 + 20, (width - courtWidth) - courtHeight * 1/16 - 10, courtHeight * 1/16);
        nomNiveau.addFocusListener(new FocusPlaceholder(nomNiveau, placeHolders));
        fond.add(nomNiveau);

        // JButton save Nom
        JButton saveNom = new JButton("OK");
        saveNom.setBounds((width - courtWidth) - courtHeight * 1/16 + 5, courtHeight + courtHeight * 1/16 + 20, courtHeight * 1/16, courtHeight * 1/16);
        fond.add(saveNom);

        // JButton save
        JButton save = new JButton("Save");
        save.setEnabled(false);
        save.setBounds(courtWidth, courtHeight, width - courtWidth, height - courtHeight);
        save.addActionListener(e -> {
            try {
                niveauCree.save(courtWidth, courtHeight);
            } catch (FileNotFoundException e1) {
                System.out.println(e1.getMessage());
            }
        });
        fond.add(save);

        saveNom.addActionListener(e -> {
            if (true) {
                niveauCree.setNom(saveNom.getText()); // TODO Ajout test nom déjà utilisé
                save.setEnabled(true);
            }
            else nomNiveau.setText(placeHolders[1]);
        });

        // Réglages par défaut
        casePegBleu.mouseClicked(null);
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

        int largeur, hauteur;
        Pegs peg;
        int x, y, radius, couleur;

        public CasePeg(int largeur, int hauteur, int couleur) {
            this.largeur = largeur;
            this.hauteur = hauteur;
            this.couleur = couleur;
            peg = new Pegs(largeur/2, hauteur*3/4/2, 50, couleur);
            radius = peg.getRadius();
            x = peg.getX();
            y = peg.getY();

            setLayout(null);
            JSlider sliderTaillPeg = new JSlider(10, 110, 50);
            sliderTaillPeg.setBounds(0, hauteur*3/4, largeur, hauteur*1/4);
            sliderTaillPeg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sliderTaillPeg.addChangeListener(e -> {
                peg.setRadius(sliderTaillPeg.getValue());
                paint(this.getGraphics());
            });
            add(sliderTaillPeg);

            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void paint(Graphics g) {
            super.paint(g);
            radius = peg.getRadius();
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(ImageImport.getImage(peg.getImageString()), x-radius/2, y-radius/2, radius, radius, this);
        }

        public void mouseClicked(MouseEvent e) {
            if (caseActive != null) caseActive.unclicked();
            caseActive = this;
            enModif = false;
            boutonsModifActifs(false);
            setBackground(Color.LIGHT_GRAY);
        }

        public void unclicked() {
            setBackground(UIManager.getColor("Panel.background"));
        }

        public void mousePressed(MouseEvent e) {}
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
                pegSelectionne.setCouleur(couleur);
                pegSelectionne.setImageString(Pegs.intColorToString(couleur));
                court.repaint();
            });
            Icon icon = new ImageIcon(ImageImport.getImage(Pegs.intColorToString(couleur)));
            setIcon(icon);
        }
    }

    private class FocusPlaceholder implements FocusListener{
        JTextField field; 
        String[] placeHolders;
        String currentHolder;

        FocusPlaceholder(JTextField field, String[] placeHolders){
            this.field = field ;
            this.placeHolders = placeHolders.clone() ;
            field.setForeground(Color.GRAY);
        }

        public void focusGained(FocusEvent e) {
            for (String string : placeHolders) {
                if (field.getText().equals(string)) {
                    currentHolder = field.getText();
                    field.setText("");
                    field.setForeground(Color.BLACK);
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

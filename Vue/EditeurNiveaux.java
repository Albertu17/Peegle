package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.time.format.DecimalStyle;

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

public class EditeurNiveaux extends JPanel {

    // Court
    Court court;
    private int courtWidth;
    private int courtHeight;
    private Controleur controleur ;
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

    // TODO: imageicon boutons, rebond sur pegs (chmt l 152 BALL)

    EditeurNiveaux(Controleur controleur) {
        this.controleur = controleur ;
        int width = controleur.getWidth() ;
        int height = controleur.getHeight() ;
        setLayout(null);

        niveauCree = new Niveau("enAttente");
        niveauCree.isCampagne(true);

        // Court
        courtWidth = width * 5/6;
        courtHeight = height * 5/6;
        court = new Court(courtWidth, courtHeight, niveauCree);
        court.activerModeEditeur(this);
        court.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        court.setBounds(0, 0, courtWidth, courtHeight);
        court.setVisible(true);
        add(court, BorderLayout.CENTER);

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

        // Bouton pause
        JButton pause = new JButton("||");
        pause.setBounds(courtWidth - 100, 0, 50, 50);
        panelBoutons.add(pause);
        pause.setEnabled(false);

        // Bouton resume
        JButton resume = new JButton("|>");
        resume.setBounds(courtWidth - 50, 0, 50, 50);
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
            court.repaint();
            resume.setEnabled(true);
            pause.setEnabled(false);
            casePegBleu.mousePressed(null);
        });

        // slider peg selectionné
        sliderPegSelectionne = new JSlider(10, 110, 50);
        sliderPegSelectionne.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        sliderPegSelectionne.setBounds(0, 0, width - courtWidth, courtHeight * 1/16);
        sliderPegSelectionne.addChangeListener(e -> {
            pegSelectionne.setRadius(sliderPegSelectionne.getValue());
            court.setPegs(court.clonePegs(niveauCree.getPegs()));
            court.repaint();
        });
        panelBoutons.add(sliderPegSelectionne);

        // BoutonCouleur bleu
        bleu = new BoutonCouleur(1);
        bleu.setBounds(width - courtWidth, 0, courtHeight * 1/16, courtHeight * 1/16);
        panelBoutons.add(bleu);

        // BoutonCouleur rouge
        rouge = new BoutonCouleur(2);
        rouge.setBounds(width - courtWidth + courtHeight * 1/16, 0, courtHeight * 1/16, courtHeight * 1/16);
        panelBoutons.add(rouge);

        // BoutonCouleur violet
        violet = new BoutonCouleur(3);
        violet.setBounds(width - courtWidth + courtHeight * 1/8, 0, courtHeight * 1/16, courtHeight * 1/16);
        panelBoutons.add(violet);

        // BoutonCouleur vert
        vert = new BoutonCouleur(4);
        vert.setBounds(width - courtWidth + courtHeight * 3/16, 0, courtHeight * 1/16, courtHeight * 1/16);
        panelBoutons.add(vert);

        // JButton croix
        croix = new JButton("supp");
        croix.setBounds(width - courtWidth + courtHeight * 1/4, 0, courtHeight * 1/16, courtHeight * 1/16);
        croix.addActionListener(e -> {
            niveauCree.getPegs().remove(pegSelectionne);
            court.setPegs(court.clonePegs(niveauCree.getPegs()));
            court.repaint();
        });
        panelBoutons.add(croix);

        //JButton modif
        modif = new JButton("modif");
        modif.setBounds(width - courtWidth + courtHeight * 5/16, 0, courtHeight * 1/16, courtHeight * 1/16);
        modif.addActionListener(e -> {
            enModif = true;
            caseActive.unclicked();
            pause.doClick();
        });
        // TODO ajout icon
        // Icon icon = new ImageIcon(ImageImport.getImage("curseurMain.jpg"));
        // modif.setIcon(icon);
        panelBoutons.add(modif);

        // JTextField nomNiveau
        String[] placeHolders = new String[]{" Nom du niveau", " Nom déjà utilisé"};
        JTextField nomNiveau = new JTextField();
        nomNiveau.setText(placeHolders[0]);
        nomNiveau.setBounds(5, courtHeight * 1/16 + 20, (width - courtWidth) - courtHeight * 1/16 - 10, courtHeight * 1/16);
        nomNiveau.addFocusListener(new FocusPlaceholder(nomNiveau, placeHolders));
        panelBoutons.add(nomNiveau);

        // JButton save Nom
        JButton saveNom = new JButton("OK");
        saveNom.setBounds((width - courtWidth) - courtHeight * 1/16 + 5, courtHeight * 1/16 + 20, courtHeight * 1/16, courtHeight * 1/16);
        panelBoutons.add(saveNom);

        // JButton save
        JButton save = new JButton("Save");
        save.setEnabled(false);
        save.setBounds(courtWidth, 0, width - courtWidth, height - courtHeight);
        panelBoutons.add(save);

        save.addActionListener(e->{
            niveauCree.save(courtWidth, courtHeight);
        });

        saveNom.addActionListener(e -> {
            if ( ! Niveau.getAllNameNiveau().contains(nomNiveau.getText()) && ! nomNiveau.getText().equals(placeHolders[0]) && ! nomNiveau.getText().equals(placeHolders[1])) { // TODO Ajout test nom déjà utilisé
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

        int largeur, hauteur;
        Pegs peg;
        int x, y, radius, couleur;
        Pegs modeleActuel;

        public CasePeg(int largeur, int hauteur, int couleur) {
            this.largeur = largeur;
            this.hauteur = hauteur;
            this.couleur = couleur;
            peg = new Pegs(largeur/2, hauteur*3/4/2, 50, couleur);
            radius = peg.getRadius();
            x = peg.getX();
            y = peg.getY();
            modeleActuel = new Pegs(0, 0, radius, couleur);

            setLayout(null);
            JSlider sliderTaillPeg = new JSlider(10, 110, 50);
            sliderTaillPeg.setBounds(0, hauteur*3/4, largeur, hauteur*1/4);
            sliderTaillPeg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            sliderTaillPeg.addChangeListener(e -> {
                peg.setRadius(sliderTaillPeg.getValue());
                paint(this.getGraphics());
                modeleActuel.setRadius(sliderTaillPeg.getValue());
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
                pegSelectionne.setCouleur(couleur);
                pegSelectionne.setImageString(Pegs.intColorToString(couleur));
                court.setPegs(court.clonePegs(niveauCree.getPegs()));
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

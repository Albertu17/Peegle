package Vue;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class ImageImport {
    private static HashMap<String, BufferedImage> allimage ;
    private static String pathDossierImage = "Vue/Image/" ;
    private static boolean importFini = false ;

    // TODO a supprimer apres dev
    public static void printAllkey(){
       for (String name : allimage.keySet()) {
            System.out.println(name);
       }
    }
    
    /**
     * @description Lance l'import de toute les images du dossier image (ainsi que ses sous dossier)
     * Les images sont récuperable avec la fonction statique getImage(path) ;
     * @param Thread indique si l'import des images doit se faire en arriere plan 
     * @author Thibault
     * 
     */
    public static void setImage(Boolean Thread){
        allimage = new HashMap<>() ;

        if (Thread){
            Thread t = new Thread(){ 
                public void run(){
                    try {
                        scanFile(new File(pathDossierImage), "") ;
                        importFini = true ;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            } ;
            t.start() ;
        }else{
            try {
                scanFile(new File(pathDossierImage), "") ;
                importFini = true ;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        
    }

    /**
     * @description Relance l'import de toute les images du dossier image (ainsi que ses sous dossier)
     * Les images sont récuperable avec la fonction statique getImage(path) ;
     * @param pathFolder dossier à reimporter
     * @author Thibault
     * 
     */
    public static void reloadImageFile(String pathFolder){
        if (allimage == null){
            setImage(false);
            return  ;
        }

        importFini = false ; // fait attendre tout appel aux fonctions get image
        try {
            scanFile(new File(pathDossierImage +pathFolder), pathFolder+"/") ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        importFini = true ;
        
    }

    private static void scanFile(File parent, String path) throws IOException {
        for (File f : parent.listFiles() ){
            if (f.isDirectory()){
                scanFile(f, path + f.getName() + "/");
            }else{
                allimage.put( path + f.getName(), ImageIO.read(f)) ;
                // System.out.println( path + f.getName() +":"+ (allimage.get(path + f.getName()) == null ? "null" : "good") );
            }
        }
    }

    /**
     *
     * @param path chemin de l'image (chemin relatif depuis Le dossier "/Image") 
     * @author Thibault
     * @return l'image correspondante 
     * 
     */
    public static BufferedImage getImage(String path){
        return getImage(path, 100) ;
    }
    

    /**
     *
     * @param path chemin de l'image (chemin relatif depuis Le dossier "/Image") 
     * @param pourcentage (0-100) resize l'image en pourcenatge de la taille original 
     *  
     * @author Thibault
     * @return l'image correspondante 
     * 
     */
    public static BufferedImage getImage(String path, int pourcentage){
        
        while(! importFini){System.out.print("");} ;  //attend  que l'import des images par le thread soit fini
        
        BufferedImage image = allimage.get(path) ;

        if (pourcentage != 100){
            int width = (int) ((image.getWidth()*pourcentage)/100.0 );
            int heigth = (int) ((image.getHeight()*pourcentage)/100.0 );
            // resize de l'image :
            BufferedImage resizedImage = new BufferedImage(width, heigth, image.getType());
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, width, heigth, null);
            graphics2D.dispose();
            image = resizedImage;
        }
        
        return image ;
    }

    /**
     *
     * @param path chemin de l'image (chemin relatif depuis Le dossier "/Image") 
     * @param width nouvelle largeur 
     * @param heigth nouvelle hauteur
     *  
     * @author Thibault
     * @return l'image correspondante 
     * 
     */
    public static BufferedImage getImage(String path, int width, int heigth){

        while(! importFini) {System.out.print("");};  //attend  que l'import des images par le thread soit fini
        // laisser le print sinon ça ne fonctionne pas 

        BufferedImage image = allimage.get(path) ;

        // resize de l'image :
        BufferedImage resizedImage = new BufferedImage(width, heigth, image.getType());
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, heigth, null);
        graphics2D.dispose();
        image = resizedImage;
        
        return image ;
    }
    

}

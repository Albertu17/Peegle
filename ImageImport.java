import java.util.HashMap;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;


public class ImageImport {
    private static HashMap<String, BufferedImage> allimage ;
    private static String pathDossierImage = "Image/" ;
    private static boolean importFini = false ;

    

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

    private static void scanFile(File parent, String path) throws IOException {
        for (File f : parent.listFiles() ){
            if (f.isDirectory()){
                scanFile(f, path + f.getName() + "/");
            }else{
                allimage.put( path + f.getName(), ImageIO.read(f)) ;
                System.out.println( path + f.getName() +":"+ (allimage.get(path + f.getName()) == null ? "null" : "good") );
            }
        }



    }

    public static BufferedImage getImage(String path){
        return getImageResize(path, 100) ;
    }
    
    public static BufferedImage getImageResize(String path, int pourcentage){
        BufferedImage image = null ;
        do {
            if (importFini){
                image = allimage.get(path) ;
                // TODO faut-il supprimer l'image du hashmap apres ?
                if (pourcentage != 0){
                    // TODO faire le resize
                }
            }
        }

        while(! importFini) ;
        
        return image ;
    }
    
    public static void main(String[] args) {
        ImageImport.setImage(true) ;
    }
}

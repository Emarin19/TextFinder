package cr.ac.tec.TextFinder;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Technological Institute of Costa Rica
 * Computer Engineering
 * Course: de Algoritmos y estructuras de datos I
 * Project II: TextFinder
 * JDK 11
 * Description: Class which purpose is to open a selected file in a precise place
 * @author Emanuel Marín Gutiérrez
 * @since October 2019
 */
public class FileOpener {
    private File file;
    public FileOpener(File file){
        this.file = file;
    }
    public void openFile(){
        try{
            Desktop.getDesktop().open(file);
        }catch (IOException ex){
            System.out.println("File not found");
        }
    }
}

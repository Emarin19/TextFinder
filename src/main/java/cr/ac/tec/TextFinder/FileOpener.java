package cr.ac.tec.TextFinder;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    public void readFile(){
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
            XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
            System.out.println(extractor.getText());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (InvalidFormatException ex){
            System.out.println("Formato inválido");
        } catch (IOException ex){
            System.out.println("Can´t be readed");
        }
    }
}

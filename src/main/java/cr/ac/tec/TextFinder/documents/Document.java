package cr.ac.tec.TextFinder.documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Document {
    private File file;
    private int date;

    public Document(File file){
        this.file = file;
    }

    public String getName(){
        return file.getName();
    }

    public int getSize(){
        return (int) (file.length()/1024);
    }

    public int getDate(){
        date = 0;
        BasicFileAttributes attributes;
        try{
            attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime time = attributes.creationTime();
            String pattern = "yyyyMMdd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String formatted = simpleDateFormat.format(new Date(time.toMillis()));
            date = Integer.parseInt(formatted);
        }catch(IOException | NumberFormatException ex){
            System.out.println("Date no found");
        }
        return date;
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

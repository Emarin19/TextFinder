package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.util.Collections.BinaryTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Technological Institute of Costa Rica
 * Computer Engineering
 * Course: de Algoritmos y estructuras de datos I
 * Project II: TextFinder
 * JDK 11
 * Description: It contains all the information of a particular file and a binary tree of itself
 * @author Emanuel Marín Gutiérrez, José Morales Vargas
 * @since October 2019
 */
public class Document extends AnchorPane {
    private File file;
    private BinaryTree tree;
    private DocumentType type;
    public Document(File file){
        this.file = file;
        //setTree(); ------------ comentado en trabajo
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("GateNode.fxml")
        );
    }
    public String getName(){
        return file.getName();
    }
    public int getSize(){
        return (int) (file.length()/1024);
    }
    public int getDate(){
        int date = 0;
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
    public BinaryTree getTree(){
        return tree;
    }
    public void setTree(BinaryTree binaryTree) {
        this.tree = binaryTree;
        /*if(file.getName().endsWith(".txt")){
            tree = ParserFacade.parse(DocumentType.TXT, file).getTree();
        }
        else if(file.getName().endsWith(".docx")){
            tree = ParserFacade.parse(DocumentType.DOC, file).getTree();
        }
        else if(file.getName().endsWith(".pdf")){
            tree = ParserFacade.parse(DocumentType.PDF, file).getTree();
        }
        else{}*/

    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    @FXML
    public void initialize(){
    }
}

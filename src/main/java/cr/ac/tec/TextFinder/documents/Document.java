package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.TextFinder.FileListManager;
import cr.ac.tec.util.Collections.BinaryTree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import javax.swing.text.html.StyleSheet;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
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
    public Label lblTamano;
    public Label lblFecha;
    public Label lblNombre;
    public ImageView TypeIcon;
    public Button closeBtn;

    public Document(File file){
        this.file = file;
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("Document.fxml")
        );
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
        builGraphicElements();

    }
    private void builGraphicElements(){
        getStylesheets().add(getClass().getResource("Document.css").toExternalForm());
        TypeIcon.setPreserveRatio(true);
        TypeIcon.setSmooth(true);
        lblNombre.setText(file.getName());
        Long size = file.length()/1024;
        lblTamano.setText(size.toString() + "KB");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        lblFecha.setText(sdf.format(file.lastModified()));

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
    public String getDateString(){
        String date = "";
        BasicFileAttributes attributes;
        try{
            attributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime time = attributes.creationTime();
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            date = simpleDateFormat.format(new Date(time.toMillis()));
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
        TypeIcon.setImage(new Image(this.getClass().getResourceAsStream(type.pathToImage)));
        this.type = type;
    }

    @FXML
    public void initialize(){
        closeBtn.setOnAction(onA -> {
            FileListManager.getInstance().deleteDocument(this);
            //se autoremueve
            VBox parent = (VBox) this.getParent();
            parent.getChildren().remove(this);
        });

    }

    public void getContext(String word_phrase){
        if(type == DocumentType.TXT){
            TxtParser.getContext(this, word_phrase);
        }
        else if(type == DocumentType.DOC){
            DocParser.getContext(this, word_phrase);
        }
        else if(type == DocumentType.PDF){
            PdfParser.getContext(this, word_phrase);
        }
    }
}

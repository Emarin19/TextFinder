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
        setStyle("-fx-background-color: white" +
                "" +
                "" +
                "");
        lblNombre.setText("Nombre: " +file.getName());
        Long size = file.length()/1024;
        lblTamano.setText("Espacio: " + size.toString() + "KB");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
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
        System.out.println(type.pathToImage);
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
}

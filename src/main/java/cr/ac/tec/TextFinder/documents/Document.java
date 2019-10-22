package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.util.Collections.BinaryTree;
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
 * @author Emanuel Marín Gutiérrez
 * @since October 2019
 */
public class Document {
    private File file;
    private BinaryTree tree;
    public Document(File file){
        this.file = file;
        setTree();
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
    private void setTree() {
        if(file.getName().endsWith(".txt")){
            tree = ParserFactory.getParser(DocumentType.TXT, file).getTree();
        }
        else if(file.getName().endsWith(".docx")){
            tree = ParserFactory.getParser(DocumentType.DOC, file).getTree();
        }
        else if(file.getName().endsWith(".pdf")){
            tree = ParserFactory.getParser(DocumentType.PDF, file).getTree();
        }
        else{}
    }
    public String getContext(String word_phrase){
        String result = null;
        if(file.getName().endsWith(".txt")){
            result = TxtParser.getContext(tree, file, word_phrase);
        }
        else if(file.getName().endsWith(".docx")){
            result = DocParser.getContext(file, word_phrase);
        }
        else if(file.getName().endsWith(".pdf")){
            result = PdfParser.getContext(file, word_phrase);
        }
        return result;
    }
}

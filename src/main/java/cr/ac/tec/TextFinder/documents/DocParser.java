package cr.ac.tec.TextFinder.documents;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.openxml4j.opc.OPCPackage;
import cr.ac.tec.util.Collections.List.TecList;
import cr.ac.tec.util.Collections.BinaryTree;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.util.Pair;
import java.util.List;
import java.io.File;

/**
 * Technological Institute of Costa Rica
 * Computer Engineering
 * Course: de Algoritmos y estructuras de datos I
 * Project II: TextFinder
 * JDK 11
 * Description: Create a binary tree from all the words in the document
 * @author Emanuel Marín Gutiérrez
 * @since October 2019
 */
public class DocParser implements TextFileParser {
    private final File file;
    private final BinaryTree tree;
    private Pair<String, TecList> value;

    public DocParser(File file) {
        this.file = file;
        this.tree = new BinaryTree();
        setTree();
    }

    private void setTree(){
        try{
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
            String delimiters = ".,;:(){}[]/´ ";
            int numparagraph = 1;
            int position = 0;
            for(XWPFParagraph paragraph : paragraphList){
                StringTokenizer stk = new StringTokenizer(paragraph.getParagraphText(), delimiters);
                while(stk.hasMoreTokens()){
                    String word = Normalizer
                            .normalize(stk.nextToken(), Normalizer.Form.NFD)
                            .replaceAll("[^\\p{ASCII}]", "");
                    TecList list = new TecList();
                    list.addAll(numparagraph,position);
                    value = new Pair<String, TecList>(word,list);
                    tree.insert(value);
                    position++;
                }
                position = 0;
                numparagraph++;
            }
            fis.close();
            docx.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Read error");
        } catch (InvalidFormatException e){
            System.out.println("Incorrect format");;
        }
    }

    @Override
    public BinaryTree getTree() {
        return tree;
    }

    public static String getContext(BinaryTree tree, File file, String word_phrase){
        return "Hola";
    }
}

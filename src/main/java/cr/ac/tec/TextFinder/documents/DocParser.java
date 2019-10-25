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
    private static DocParser instance;
    private DocParser() {
    }
    public static DocParser getInstance(){
        if(instance == null)
            instance = new DocParser();
        return instance;
    }
    public Document parseDocument(File file) {
        Document parsedDoc = new Document(file);
        parsedDoc.setType(DocumentType.DOC);
        generateTree(parsedDoc);
        return parsedDoc;
    }

    private void generateTree(Document fileToParse){
        try{
            Pair<String, TecList> value;
            BinaryTree tree = new BinaryTree();
            FileInputStream fis = new FileInputStream(fileToParse.getFile());
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
                    list.add(new Pair<Integer,Integer>(numparagraph,position));
                    value = new Pair<String, TecList>(word,list);
                    tree.insert(value);
                    position++;
                }
                position = 0;
                numparagraph++;
            }
            fis.close();
            docx.close();
            fileToParse.setTree(tree);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Read error");
        } catch (InvalidFormatException e){
            System.out.println("Incorrect format");;
        }
    }

    public static void getContext(Document doc, String word_phrase){
        String[] sentence = word_phrase.split(" ");
        if(sentence.length == 1)
            word(doc, word_phrase);
        else
            phrase(doc, word_phrase);
    }

    private static void word(Document doc, String word) {
        BinaryTree tree = doc.getTree();
        File file = doc.getFile();
        TecList list = tree.searchNode(word).getValue();
        String context = "";
        Pair value;
        int line;
        int prevLine = 0;
        try{
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
            int numLines = 1;
            String sline;
            for (int i=0; i<list.size(); i++){
                value = (Pair) list.get(i);
                line = (int) value.getKey();
                if(prevLine!=line){
                    while (numLines!=line){
                        numLines++;
                    }
                    context = paragraphList.get(numLines-1).getText();
                    //SearchResult(doc, context, value)
                    System.out.println(paragraphList.get(numLines-1).getText());
                    prevLine = line;
                }
            }
        } catch (Exception ex){ return; }
    }
    private static void phrase(Document doc, String phrase) {
        String[] sentence = phrase.split(" ");
        File file = doc.getFile();
        BinaryTree tree = doc.getTree();
        TecList list = tree.searchNode(sentence[0]).getValue();
        try{
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
            String context = "";
            int numLines = 1;
            int prevLine = 0;
            Pair value;
            int line;
            String sline;
            for (int i=0; i<list.size(); i++){
                value = (Pair) list.get(i);
                line = (int) value.getKey();
                boolean exist = false;
                if(prevLine!=line){
                    while (numLines!=line){
                        numLines++;
                    }
                    exist = verify(paragraphList.get(numLines-1).getText(), sentence, 1);
                    if(exist){
                        context = paragraphList.get(numLines-1).getText();
                        //SearchResult(doc, context, value)
                        System.out.println(context);
                        prevLine = line;
                    }
                }
            }
        } catch (Exception ex){ return; }
    }
    private static boolean verify(String line, String[] sentence, int pos) {
        boolean result = false;
        String delimiters = ".,;:(){}[]/´ ";
        StringTokenizer stk = new StringTokenizer(line, delimiters);
        while (stk.hasMoreTokens()){
            if (stk.nextToken().equalsIgnoreCase(sentence[0])){
                break;
            }
        }

        while (stk.hasMoreTokens()){
            if (pos<sentence.length){
                if(stk.nextToken().equalsIgnoreCase(sentence[pos])){
                    result = true;
                    pos++;
                }
                else {
                    result = false;
                    break;
                }
            }
            else { break; }
        }
        return result;
    }
}
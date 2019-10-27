package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.TextFinder.FileListManager;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.openxml4j.opc.OPCPackage;
import cr.ac.tec.util.Collections.List.TecList;
import cr.ac.tec.util.Collections.BinaryTree;
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
    private static String delimiters = ".,;:(){}[]/´ ";
    private static DocParser instance;
    private DocParser() {
    }
    public static DocParser getInstance(){
        if(instance == null)
            instance = new DocParser();
        return instance;
    }

    /**
     * Parse the file and create a word tree from it
     * @param file
     * @return the document with its respective word tree
     */
    public Document parseDocument(File file) {
        Document parsedDoc = new Document(file);
        parsedDoc.setType(DocumentType.DOC);
        generateTree(parsedDoc);
        return parsedDoc;
    }

    private void generateTree(Document fileToParse){
        Pair<String, TecList> value;
        BinaryTree tree = new BinaryTree();
        try{
            FileInputStream fis = new FileInputStream(fileToParse.getFile());
            XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
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
        } catch (IOException e){
            e.printStackTrace();
        } catch (InvalidFormatException e){
            e.printStackTrace();
        }
    }

    /**
     * look for the context surrounding a word or phrase in the file
     * @param doc
     * @param word_phrase
     */
    public static void getContext(Document doc, String word_phrase){
        StringTokenizer stk = new StringTokenizer(word_phrase, delimiters);
        String[] sentence = new String[stk.countTokens()];
        int pos = 0;
        while (stk.hasMoreTokens()){
            String word = Normalizer
                    .normalize(stk.nextToken(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "");
            sentence[pos] = word;
            pos++;
        }
        if(sentence.length == 1)
            word(doc, word_phrase);
        else
            phrase(doc, sentence, word_phrase);
    }

    private static void word(Document doc, String word) {
        BinaryTree tree = doc.getTree();
        File file = doc.getFile();
        TecList list;
        try {
            list = tree.searchNode(word).getValue();
        }catch(Exception e){ return; }

        String context = "";
        int prevLine = 0;
        int numLines = 1;
        Pair value;
        int line;
        try{
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
            for (int i=0; i<list.size(); i++){
                value = (Pair) list.get(i);
                line = (int) value.getKey();
                if(prevLine!=line){
                    while (numLines!=line){
                        numLines++;
                    }
                    context = paragraphList.get(numLines-1).getText();
                    int cnt= 0;
                    while(context.split("").length<500 && paragraphList.size()>(numLines+cnt)){
                        if(paragraphList.get(numLines+cnt).getText() != "")
                            context+= "\n"+paragraphList.get(numLines+cnt).getText();
                        cnt++;
                    }
                    SearchResult temp = new SearchResult(doc, context, value, word);
                    FileListManager.getInstance().addSearchResult(temp);
                }
                prevLine = line;
            }
        } catch (Exception e){ e.printStackTrace(); }
    }

    private static void phrase(Document doc, String[] sentence, String word_phrase) {
        BinaryTree tree = doc.getTree();
        File file = doc.getFile();
        TecList list;
        try{
            list = tree.searchNode(sentence[0]).getValue();
        }catch(Exception e){ return; }

        String context = "";
        int prevLine = 0;
        int numLines = 1;
        Pair value;
        int line;
        try{
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphList = docx.getParagraphs();
            for (int i=0; i<list.size(); i++){
                value = (Pair) list.get(i);
                line = (int) value.getKey();
                boolean exist;
                if(prevLine!=line){
                    while (numLines!=line){
                        numLines++;
                    }
                    exist = verify(paragraphList.get(numLines-1).getText(), sentence, 1);
                    if(exist){
                        context = paragraphList.get(numLines-1).getText();
                        SearchResult temp = new SearchResult(doc, context, value, word_phrase);
                        FileListManager.getInstance().addSearchResult(temp);
                    }
                }
                prevLine = line;
            }
        } catch (Exception ex){ return; }
    }
    private static boolean verify(String line, String[] sentence, int pos) {
        boolean result = false;
        StringTokenizer stk = new StringTokenizer(line, delimiters);
        while (stk.hasMoreTokens()){
            String word = Normalizer
                    .normalize(stk.nextToken(), Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "");
            if (word.equalsIgnoreCase(sentence[0])){
                break;
            }
        }

        while (stk.hasMoreTokens()){
            if (pos<sentence.length){
                String word = Normalizer
                        .normalize(stk.nextToken(), Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", "");
                if(word.equalsIgnoreCase(sentence[pos])){
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
package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.TextFinder.FileListManager;
import cr.ac.tec.util.Collections.BinaryTree;
import cr.ac.tec.util.Collections.List.TecList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import javafx.util.Pair;

import javax.print.Doc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.StringTokenizer;

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
public class PdfParser implements TextFileParser{
    private static String delimiters = ".,;:(){}[]/´ ";
    private static PdfParser instance;

    private PdfParser() {
    }
    public static PdfParser getInstance() {
        if(instance == null)
            instance = new PdfParser();
        return instance;
    }

    /**
     * Parse the file and create a word tree from it
     * @param file
     * @return the document with its respective word tree
     */
    public Document parseDocument(File file) {
        Document parsedDoc = new Document(file);
        parsedDoc.setType(DocumentType.PDF);
        generateTree(parsedDoc);
        return parsedDoc;
    }

    private void generateTree(Document fileToParse) {
        Pair<String, TecList> value;
        BinaryTree tree = new BinaryTree();
        try (PDDocument document = PDDocument.load(fileToParse.getFile())){
            if (!document.isEncrypted()) {
                PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
                pdfTextStripperByArea.setSortByPosition(true);
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                String pdfText = pdfTextStripper.getText(document);
                String lines[] = pdfText.split("\\r?\\n");
                int numLine = 1;
                int position = 0;
                for (String line : lines) {
                    StringTokenizer stk = new StringTokenizer(line, delimiters);
                    while(stk.hasMoreTokens()){
                        String word = Normalizer
                                .normalize(stk.nextToken(), Normalizer.Form.NFD)
                                .replaceAll("[^\\p{ASCII}]", "");
                        TecList list = new TecList();
                        list.add(new Pair<Integer,Integer>(numLine,position));
                        value = new Pair<String, TecList>(word,list);
                        tree.insert(value);
                        position++;
                    }
                    position = 0;
                    numLine++;
                }
                document.close();
                fileToParse.setTree(tree);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * look for the context surrounding a word or phrase in the file
     * @param doc
     * @param word_phrase
     */
    public static void getContext(Document doc, String word_phrase) {
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
            phrase(doc, sentence);
    }

    private static void word(Document doc, String word) {
        BinaryTree tree = doc.getTree();
        File file = doc.getFile();
        TecList list;
        try {
            list = tree.searchNode(word).getValue();
        }catch(Exception e){ return; }

        int prevLine = -3;
        int numLines = 1;
        Pair value;
        int line;
        try (PDDocument document = PDDocument.load(file)){
            if (!document.isEncrypted()) {
                PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
                pdfTextStripperByArea.setSortByPosition(true);
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                String pdfText = pdfTextStripper.getText(document);
                String lines[] = pdfText.split("\\r?\\n");
                for (int i=0; i<list.size(); i++){
                    value = (Pair) list.get(i);
                    line = (int) value.getKey();
                    String context = "";
                    String pre = "";
                    String pos = "";
                    if (prevLine-2==line || prevLine-1==line || prevLine==line || prevLine+1==line || prevLine+2==line)
                        continue;
                    else{
                        while(numLines!=line){
                            numLines++;
                        }
                        if (numLines>=3)
                            pre += lines[numLines-3] + "\n" + lines[numLines-2];
                        if (numLines+1<lines.length)
                            pos += lines[numLines] + "\n" + lines[numLines+1];
                        context += pre + "\n" + lines[numLines-1] + "\n" + pos;
                        System.out.println(context);
                        System.out.println();
                        //SearchResult temp = new SearchResult(doc, context, value, word);
                        //FileListManager.getInstance().addSearchResult(temp);
                    }
                    prevLine = line;
                }
                document.close();
            }
        }catch (IOException ex){ return; }
    }
    private static void phrase(Document doc, String[] sentence) {
        BinaryTree tree = doc.getTree();
        File file = doc.getFile();
        TecList list;
        try {
            list = tree.searchNode(sentence[0]).getValue();
        }catch(Exception e){ return; }

        int prevLine = -3;
        int numLines = 1;
        Pair value;
        int line;
        try (PDDocument document = PDDocument.load(file)){
            if (!document.isEncrypted()) {
                PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
                pdfTextStripperByArea.setSortByPosition(true);
                PDFTextStripper pdfTextStripper = new PDFTextStripper();
                String pdfText = pdfTextStripper.getText(document);
                String lines[] = pdfText.split("\\r?\\n");
                for (int i=0; i<list.size(); i++){
                    value = (Pair) list.get(i);
                    line = (int) value.getKey();
                    String context = "";
                    String pre = "";
                    String pos = "";
                    if (prevLine-2==line || prevLine-1==line || prevLine==line || prevLine+1==line || prevLine+2==line)
                        continue;
                    else {
                        boolean exist;
                        while(numLines!=line){
                            numLines++;
                        }
                        exist = verify(lines[numLines-1], sentence, 1);
                        if(exist){
                            if (numLines>=3)
                                pre += lines[numLines-3] + "\n" + lines[numLines-2];
                            if (numLines+1<lines.length)
                                pos += lines[numLines] + "\n" + lines[numLines+1];
                            context += pre + "\n" + lines[numLines-1] + "\n" + pos;
                            System.out.println(context);
                            System.out.println();
                            //SearchResult temp = new SearchResult(doc, context, value, word);
                            //FileListManager.getInstance().addSearchResult(temp);
                        }
                    }
                    prevLine = line;
                }
                document.close();
            }
        }catch (IOException ex){ return; }
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

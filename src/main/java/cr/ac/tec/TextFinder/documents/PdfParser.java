package cr.ac.tec.TextFinder.documents;

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
    private static PdfParser instance;

    private PdfParser() {
    }
    public static PdfParser getInstance() {
        if(instance == null)
            instance = new PdfParser();
        return instance;
    }
    @Override
    public Document parseDocument(File file) {
        Document parsedDoc = new Document(file);
        parsedDoc.setType(DocumentType.PDF);
        generateTree(parsedDoc);
        return parsedDoc;
    }

    private void generateTree(Document fileToParse) {
        try (PDDocument document = PDDocument.load(fileToParse.getFile())){
            Pair<String, TecList> value;
            BinaryTree tree = new BinaryTree();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea pdfDocument = new PDFTextStripperByArea();
                pdfDocument.setSortByPosition(true);
                PDFTextStripper pdfFile = new PDFTextStripper();
                String pdfText = pdfFile.getText(document);

                String lines[] = pdfText.split("\\r?\\n");
                String delimiters = ".,;:(){}[]/´ ";
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
                fileToParse.setTree(tree);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Read error");
        }
    }

    public static void getContext(Document doc, String word_phrase) {
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
        try (PDDocument document = PDDocument.load(file)){
            if (!document.isEncrypted()) {
                PDFTextStripperByArea pdfDocument = new PDFTextStripperByArea();
                pdfDocument.setSortByPosition(true);
                PDFTextStripper pdfFile = new PDFTextStripper();
                String pdfText = pdfFile.getText(document);
                String lines[] = pdfText.split("\\r?\\n");
                int numLines = 1;
                for (int i=0; i<list.size(); i++){
                    value = (Pair) list.get(i);
                    line = (int) value.getKey();
                    while(numLines!=line){
                        numLines++;
                    }
                    context = lines[numLines-1];
                    //SearchResult(doc, context, value)
                    System.out.println(lines[numLines-1]);
                }
            }
        }catch (IOException ex){ return; }
    }
    private static void phrase(Document doc, String phrase) {

    }
}

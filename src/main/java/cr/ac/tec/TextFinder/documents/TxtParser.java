package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.util.Collections.BinaryTree;
import cr.ac.tec.util.Collections.List.TecList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import javafx.util.Pair;
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
public class TxtParser implements TextFileParser{
    private static TxtParser instance;
    public TxtParser() {
    }
    public static TxtParser getInstance(){
        if(instance == null)
            instance = new TxtParser();
        return instance;
    }
    public Document parseDocument(File file) {
        Document parsedDoc = new Document(file);
        parsedDoc.setType(DocumentType.TXT);
        generateTree(parsedDoc);
        return parsedDoc;
    }

    private void generateTree(Document fileToParse) {
        try {
            Pair<String, TecList> value;
            BinaryTree tree = new BinaryTree();
            FileReader fileReader = new FileReader(fileToParse.getFile());
            BufferedReader buffer = new BufferedReader(fileReader);
            String delimiters = ".,;:(){}[]/´ ";
            int numLine = 1;
            int position = 0;
            String line;
            while((line = buffer.readLine()) != null){
                StringTokenizer stk = new StringTokenizer(line, delimiters);
                while (stk.hasMoreTokens()){
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
            fileReader.close();
            buffer.close();
            fileToParse.setTree(tree);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e){
            System.out.println("Read error");
        }
    }

    public static String getContext(BinaryTree tree, File file, String word_phrase){
        TecList list = tree.searchNode(word_phrase).getValue();
        String result = null;
        Pair value;
        int line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fileReader);
            int numLines = 1;
            String sline;
            for(int i=0; i<list.size(); i++){
                value = (Pair) list.get(i);
                line = (int) value.getKey();
                while ((sline = buffer.readLine()) != null) {
                    System.out.println(numLines);
                    if(numLines == line){
                        System.out.println(sline);
                        numLines++;
                        break;
                    }
                    numLines++;
                }
            }
        } catch (IOException ex){
            System.out.println("Read error");
        }
        return "Fin";
    }


}

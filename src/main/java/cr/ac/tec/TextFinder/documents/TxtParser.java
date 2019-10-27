package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.util.Collections.List.TecList;
import cr.ac.tec.TextFinder.FileListManager;
import cr.ac.tec.util.Collections.BinaryTree;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.text.Normalizer;
import java.io.IOException;
import java.io.FileReader;
import javafx.util.Pair;
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
public class TxtParser implements TextFileParser{
    private static String delimiters = ".,;:(){}[]/´ ";
    private static TxtParser instance;
    public TxtParser() {
    }

    public static TxtParser getInstance(){
        if(instance == null)
            instance = new TxtParser();
        return instance;
    }

    /**
     * Parse the file and create a word tree from it
     * @param file
     * @return the document with its respective word tree
     */
    public Document parseDocument(File file) {
        Document parsedDoc = new Document(file);
        parsedDoc.setType(DocumentType.TXT);
        generateTree(parsedDoc);
        return parsedDoc;
    }

    private void generateTree(Document fileToParse) {
        Pair<String, TecList> value;
        BinaryTree tree = new BinaryTree();
        try {
            FileReader fileReader = new FileReader(fileToParse.getFile());
            BufferedReader reader = new BufferedReader(fileReader);
            int numLine = 1;
            int position = 0;
            String line;
            while((line = reader.readLine()) != null){
                StringTokenizer stk = new StringTokenizer(line, delimiters);
                while (stk.hasMoreTokens()){
                    String word = Normalizer
                            .normalize(stk.nextToken(), Normalizer.Form.NFD)
                            .replaceAll("[^\\p{ASCII}]", "");
                    TecList list = new TecList();
                    list.add(new Pair<Integer,Integer>(numLine, position));
                    value = new Pair<String, TecList>(word, list);
                    tree.insert(value);
                    position++;
                }
                position = 0;
                numLine++;
            }
            fileReader.close();
            reader.close();
            fileToParse.setTree(tree);
        } catch (IOException e) {
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

        String context = "";
        int numLines = 1;
        Pair value;
        int line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            for(int i=0; i<list.size(); i++){
                value = (Pair) list.get(i);
                line = (int) value.getKey();
                String sline;
                while ((sline = reader.readLine()) != null) {
                    if(numLines == line){
                        context=sline;
                        System.out.println(context);
                        //SearchResult temp = new SearchResult(doc, context, value, word);
                        //FileListManager.getInstance().addSearchResult(temp);
                        numLines++;
                        break;
                    }
                    numLines++;
                }
            }
            fileReader.close();
            reader.close();
        } catch (IOException e){ e.printStackTrace(); }
    }

    private static void phrase(Document doc, String[] sentence) {
        BinaryTree tree = doc.getTree();
        File file = doc.getFile();
        TecList list;
        try{
            list = tree.searchNode(sentence[0]).getValue();
        }catch(Exception e){ return; }

        String context = "";
        int numLines = 1;
        Pair value;
        int line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            for (int i=0; i<list.size(); i++){
                value = (Pair) list.get(i);
                line = (int) value.getKey();
                boolean exist;
                String sline;
                while ((sline = reader.readLine()) != null){
                    if(numLines == line){
                        exist = verify(sline, sentence, 1);
                        System.out.println(exist);
                        if (exist){
                            context=sline;
                            System.out.println(context);
                            //SearchResult(doc, context, value)
                        }
                        numLines++;
                        break;
                    }
                    numLines++;
                }
            }
            fileReader.close();
            reader.close();
        } catch (IOException ex){ return; }
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

package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.util.Collections.BinaryTree;

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
public class PdfParser implements TextFileParser{
    private  File file;
    public PdfParser(File file) {
        this.file = file;
    }

    @Override
    public BinaryTree getTree() {
        return null;
    }
}

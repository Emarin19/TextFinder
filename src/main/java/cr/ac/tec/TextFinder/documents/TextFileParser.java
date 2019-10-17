package cr.ac.tec.TextFinder.documents;

import cr.ac.tec.util.Collections.BinaryTree;

import java.io.File;

public interface TextFileParser {
    Document parseDocument(File file);

}

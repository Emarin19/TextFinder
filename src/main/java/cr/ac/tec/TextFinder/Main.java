package cr.ac.tec.TextFinder;

import cr.ac.tec.util.Collections.BinaryTree;
import cr.ac.tec.util.Collections.List.TecList;
import javafx.util.Pair;

public class Main {
    public static void main(String[] args) {
        BinaryTree l = new BinaryTree();
        l.insert(new Pair<>("Hola",new TecList()));
        l.insert(new Pair<>("ssa",new TecList()));
        l.insert(new Pair<>("dddd",new TecList()));
        l.insert(new Pair<>("zzzz",new TecList()));
        System.out.println(l.searchNode("zzzz").toString());
    }

}

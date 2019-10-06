package cr.ac.tec.util.Collections;

import cr.ac.tec.util.Collections.List.TecList;
import javafx.util.Pair;

public class TreeNode {
    Pair<String, TecList> data;
    TreeNode right;
    TreeNode left;
    public TreeNode(Pair<String, TecList> value){
        this.data = value;
    }

    @Override
    public String toString() {
        return data.getKey() + "|" + data.getValue().toString();
    }
}

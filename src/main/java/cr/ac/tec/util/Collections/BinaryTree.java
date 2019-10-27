package cr.ac.tec.util.Collections;

import cr.ac.tec.util.Collections.List.TecList;
import javafx.util.Pair;

import java.text.Normalizer;

public class BinaryTree {
    private TreeNode root;

    /**
     * Checks if the Tree is empty
     * @return tree empty or not
     */
    public boolean isEmpty(){
        return root==null;
    }

    /**
     * inserts a new node in the Binary Tree
     * @param value Pair value to be inserted on the tree
     */
    public void insert(Pair<String, TecList> value){
        if(isEmpty()){
            root = new TreeNode(value);
        }else{
            TreeNode current = root;
            while(true){
                if(value.getKey().compareToIgnoreCase(current.data.getKey()) < 0) {
                    //mayor
                    if (current.right == null) {
                        current.right = new TreeNode(value);
                        break;
                    }else{
                        current = current.right;
                    }
                }
                else if(value.getKey().compareToIgnoreCase(current.data.getKey()) > 0) {
                    //menor
                    if (current.left == null) {
                        current.left = new TreeNode(value);
                        break;
                    }else{
                        current = current.left;
                    }
                }else {
                    TecList list = value.getValue();
                    Pair pair = (Pair) list.get(0);
                    current.data.getValue().add(pair);
                    break;
                }
            }
        }
    }
    public boolean contains(String word){
        boolean result = false;
        word = Normalizer
                .normalize(word, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        if(!isEmpty()){
            TreeNode current = root;
            while(current!=null) {
                int compareResult = word.compareToIgnoreCase(current.data.getKey());
                if(compareResult == 0){
                    result = true;
                    break;
                }
                else if(compareResult>0){
                    //menor
                    current = current.left;
                }
                else if(compareResult<0){
                    //mayor
                    current = current.right;
                }
            }
            if(current == null) result = false;
        }
        return result;
    }
    public Pair<String, TecList> searchNode(String word){
        word = Normalizer
                .normalize(word, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
        Pair<String, TecList>  result =null;
        if(contains(word)){
            TreeNode current = root;
            while(current!=null) {
                int compareResult = word.compareToIgnoreCase(current.data.getKey());
                if(compareResult == 0){
                    result = current.data;
                    break;
                }
                else if(compareResult>0){
                    current = current.left;
                }
                else if(compareResult<0){
                    current = current.right;
                }
            }
        }
        return result;
    }

    private String preOrder(TreeNode node, String message) {
        if (node != null) {
            System.out.println("uwu");
            message += node.data.toString() + " ";
            message += preOrder(node.left, message);
            message += preOrder(node.right, message);
        }
        return message;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

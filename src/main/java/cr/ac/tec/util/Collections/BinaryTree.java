package cr.ac.tec.util.Collections;

import cr.ac.tec.util.Collections.List.TecList;
import javafx.util.Pair;

public class BinaryTree {
    private TreeNode root;
    public boolean isEmpty(){
        return root==null;
    }
    public void insert(Pair<String, TecList> value){
        if(isEmpty()){
            root = new TreeNode(value);
        }else{
            TreeNode current = root;
            while(true){
                if(value.getKey().compareTo(current.data.getKey()) < 0) {
                    //mayor
                    if (current.right == null) {
                        current.right = new TreeNode(value);
                        break;
                    }else{
                        current = current.right;
                    }
                }
                else if(value.getKey().compareTo(current.data.getKey()) > 0) {
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
        if(!isEmpty()){
            TreeNode current = root;
            while(current!=null) {
                int compareResult = word.compareTo(current.data.getKey());
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
        Pair<String, TecList>  result =null;
        if(contains(word)){
            TreeNode current = root;
            while(current!=null) {
                int compareResult = word.compareTo(current.data.getKey());
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

    @Override
    public String toString() {
        return super.toString();
    }
}

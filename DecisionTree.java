/**
 *  Implements a binary decision tree
 *
 *  @author Ramsha Rauf
 *  @version Spring 2022
 *
 */

import java.util.*;
import java.io.*;
public class DecisionTree extends BinaryTree<String> {
  /** leaf constructor */
  public DecisionTree(String s) {
    super(s);
  }

  /** @override */
  public void setLeft(BinaryTree<String> left) {
    if ((left==null)||(left instanceof DecisionTree)) {
      super.setLeft(left);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /** @override */
  public DecisionTree getLeft() {
    return (DecisionTree)super.getLeft();
  }

    /** @override */
  public void setRight(BinaryTree<String> right) {
    if ((right==null)||(right instanceof DecisionTree)) {
      super.setRight(right);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  /** @override */
  public DecisionTree getRight() {
    return (DecisionTree)super.getRight();
  }

  /**
  * follows the path of a string containing a series of YNNYNY to get to the correct node
  * @param String that contains a a path to a node
  * @return a decisionTree 
  */
  public DecisionTree followPath(String location){
    DecisionTree node = null;
    if (location.equals("")){
      node= this;
    }else{
      char[] loc = location.toCharArray();
      for(int i = 0; i< loc.length ;i++){
        if (i==0){
          if (loc[i] == 'Y'){
            node = this.getLeft();
          }else if (loc[i] == 'N'){
            node = this.getRight();
          }
        }else{
          if (loc[i] == 'Y'){
            node = node.getLeft();
          }else if (loc[i] == 'N'){
            node = node.getRight();
          }
        }
      }
    }
    return node;
    
  }

  /**
  * prints the entire tree 
  * @param root of the tree 
  */
  public void printTree(String filename, DecisionTree root) throws IOException{
    int h = root.height(); //how many sub trees there are within the tree 
    String currentpath = ""; //the root does not have a Y or N
    PrintWriter out = new PrintWriter(new FileWriter(filename));// file name
    
    for (int i=1; i<=h; i++){
      printSubTree(root, i, currentpath,out); //prints each subtree in the file 
    }
    out.close();//closes the file after it is printed 
  }
  
  /**
  * prints each subtree 
  * @param the root, each how many subtrees there are, current path which is a series of YNYN, what file to write 
    it in 
  */
  public void printSubTree (DecisionTree root, int level, String current_path, PrintWriter out)throws IOException{
      
      if (root == null){
        return;
      } else if (level == 1){//base case: if reached the root
        String print_in_file = current_path + " " + root.getData();  
        
        out.println(print_in_file);//prints it in the file 
        
        
      }else if (level > 1){ //recursive step: if the level is not a root
      String subpath_left = current_path; //creates a different current path for each side 
      String subpath_right = current_path; 
      printSubTree(root.getLeft(), level-1, subpath_left+="Y",out); //does the left first then right
      printSubTree(root.getRight(), level-1,subpath_right+="N",out);
      }  
  }

  /**
  * reads in a file and creates a decision tree 
  * @param String File name
  * @return DecisionTree that you read from the file 
  */
  public static DecisionTree readTree(String filename){
    Scanner file = null;
    DecisionTree tree = null;
    try {
      file = new Scanner(new File(filename));
      while (file.hasNextLine()) {
      
        String line = file.nextLine();
        String path = line.substring(0, line.indexOf(" "));
        String data= line.substring(line.indexOf(" ")+1);
        if(path.equals("")){
          tree = new DecisionTree(data);
        }
        else{
          String parent = path.substring(0,path.length()-1); //stores parent path
          String child = path.substring(path.length()-1);//stores which child
          DecisionTree subtree = tree.followPath(parent); //find the parent

          if (child.equals("N")){
            subtree.setRight(new DecisionTree (data)); //sets the child to parent's right
          }else if (child.equals("Y")){
            subtree.setLeft(new DecisionTree(data)); //sets the child to parent's left
          }
        }
      }
      file.close();
      
    } catch (FileNotFoundException e) {
      System.err.println("Cannot locate file.");
      System.exit(-1);
    }
    return tree;
  }
  
  /**
  * updates the file by overwriting it with the new information
  * @param Name of the file, question, left node data, right node data, the leaf that is going to change, the tree that the leaf belongs to
  */
  public static void updateFile(String filename, String question, String left, String right, DecisionTree leaf, DecisionTree tree ){
    leaf.setData(question);
    leaf.setLeft(new DecisionTree(left));
    leaf.setRight(new DecisionTree(right));
    try{
      tree.printTree(filename, tree);
    }catch(IOException ioe){
      System.out.println("Exception");
    }

  }

  
}
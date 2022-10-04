/**
 *  allows user to play the animal guess game 
 *
 *  @author Ramsha Rauf
 *  @version Spring 2022
 *
 */

import java.util.*;
public class AnimalGuess {
  /** stores user's answer if responded yes = true/no = false */
  private static boolean user_answer;
  
  public static void main(String[] args) {
    //prints intro
    boolean play_again = true;
    while (play_again){
      System.out.println("🦒🐢🦓 Welcome to Animal Guessing Game 🪱🐞🦅 \n -Where I will guess your favorite Animal by asking you a few questions to which you can respond to with: \n✅yes \nor \n❌no.");
      System.out.println("Let's begin");
      String filename = args[0];
      playGame(filename);
      Scanner user_input=new Scanner(System.in);  
      System.out.println("Wanna play again? ");

      boolean user_response = isItValid(user_input.nextLine());
      if(user_response){
        if (user_answer){
          play_again =true;
        }else{
          play_again = false;
          System.out.println("Bye-bye I will see you later 👋  ");
        }
      }else{
        System.out.println("Invalid Input. Please try again.");
      }
    }
  }


  /**
  * checks whether or not the user's repsonse is valid 
  * @param user's response 
  * @return boolean whether or not the response was valid or not  
  */
  public static boolean isItValid(String user_response){
    boolean valid= false;
    try{
      if (user_response.equals("yes")||user_response.equals("YES")||user_response.equals("y")||user_response.equals("Y")||user_response.equals("Yes")|| user_response.equals("YEs")||user_response.equals("yES")||user_response.equals("YeS")){
        user_answer = true;
        valid = true;
      }else if (user_response.equals("no")||user_response.equals("NO")||user_response.equals("n")||user_response.equals("N")||user_response.equals("nO")|| user_response.equals("No")){
        user_answer= false;
        valid = true;
      
      }
    } catch (Exception e){
      throw new NullPointerException("invalid input. ");
    }
    return valid;
  }

  /**
  * starts each round of game
  * @param filename
  */
  public static void playGame(String filename){
    DecisionTree tree = DecisionTree.readTree(filename);
     Scanner user_input=new Scanner(System.in);  
    DecisionTree position = tree;
    //while it does not reach the end it keeps asking the user questions 
    while (!position.isLeaf()){
      //prints question and stores user's response
      System.out.println(position.getData());
      boolean user_response = isItValid(user_input.nextLine());

      //if user response is valid it checks yes/no and gets the next question
      if(user_response){
        if (user_answer){
          position = position.getLeft();
        }else{
          position = position.getRight();
        }
      }else{
        System.out.println("Invalid Input. Please try again.");
      }
    }

    //gives the animal guess after the questions are complete 
    System.out.println("Is your animal a " + position.getData()+ "?");
    //guessed right or not 
    boolean user_response = isItValid(user_input.nextLine());
    while(!user_response){
      System.out.println("Invalid Input. Please try again.");
      System.out.println("Is your animal a " + position.getData() + "?");
      user_response = isItValid(user_input.nextLine());
    }
      if (user_answer){
        System.out.println("Yay! I guessed it.");
      }else{
        System.out.println("I got it wrong.");
        System.out.println("Please help me learn.");
        System.out.println("what was your favorite animal? ");
        
        String user_animal = user_input.nextLine(); //correct answer
        System.out.println("Type a yes or no question that would distinguish between " + position.getData() + " and " + user_animal);
        String question = user_input.nextLine(); //question
        System.out.println("Would you answer yes to this question for " + user_animal + "?");
        user_response = isItValid(user_input.nextLine()); //yes/no for the user's animal
        while(!user_response){
          System.out.println("Invalid Input. Please try again.");
          System.out.println(position.getData());
          user_response = isItValid(user_input.nextLine());
          
        }
        //nodes are created with user response
        String left_node;
        String right_node;
        if (user_answer){
          left_node = user_animal;
          right_node = position.getData();
        }else{
          left_node = position.getData();
          right_node = user_animal;
        }
        //file is overwritten
        DecisionTree.updateFile(filename, question, left_node,right_node, position, tree);
      }
    
  }
    
}
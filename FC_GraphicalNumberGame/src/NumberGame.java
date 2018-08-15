/**Number Game
 * @author crookfion  Fiona Crook
 * 300442873
 * 
 * There is one class in this programme:
 * NumberGame
 * 
 * This programme will generate a window where you can play Number Game.
 * The computer will think of a number between 0 and 9. Your job is to guess the number.
 * When you have entered a number into the text field, you can guess either by clicking the Guess button,
 * or pressing the Enter key.
 * The computer will tell you if your guess is too low, too high, the right number, or an invalid guess.
 * Guesses could be invalid if they are not a number between 0 and 9.
 * You can reset the game at any time by clicking the Reset button. This will wipe the previous guesses,
 * and the computer will choose a new number.
 * 
 * With further time, I could implement the ability to choose the upper and lower values the computer
 * has to guess between. At the moment, these are hard coded in the fields.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class NumberGame extends Application {

	//Fields
	private int width=450, height=400;
	
	//generating the UI items
	private final Text text=new Text();
	private TextField txfd=new TextField();
	private Label pastGuesses=new Label();
	private Button guessBtn=new Button();
	private Button reset=new Button();
	private Label instruct=new Label();
	private VBox pane=new VBox();
	private Image image;
	private HBox boxes=new HBox();
	
	//generating the numbers to use
	private int lowNum=0;
	private int highNum=9;
	private int guessNum=0;
	private int compNum=lowNum+(int)(Math.random()*((highNum-lowNum)+1));
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		initialiseUI();
		
		//action for textfield
		txfd.setOnKeyPressed(new EventHandler<KeyEvent>() {		 
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode().equals(KeyCode.ENTER)) {
		             guessNum();
		        }
		    }
		}); 
		
		
		//action for Guess button
		guessBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) { 	
				guessNum();		
			}
			
		}); 
		
		
		//action for Reset button
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) { 	
				compNum=lowNum+(int)(Math.random()*((highNum-lowNum)+1));
				text.setText("Guess a number!");
				pastGuesses.setText("Past Guesses: ");
				guessNum=0;
			}
		}); 
		
		
		//adding pane (layout) to scene
		primaryStage.setTitle("Guessing Number Game");
		primaryStage.setScene(new Scene(pane,width, height));
		primaryStage.show();
		
	} //end start
	
	/**
	 * set up all the UI items
	 */
	public void initialiseUI() {
		//setting the text attributes
		text.setText("Guess a number!");
		text.setFill(Color.WHITE);	
		text.setFont(Font.font(20));
		
		//setting the textField attributes
		txfd.setFont(Font.font(15));
		
		//setting the guess button attributes
		guessBtn.setText("Guess");
		guessBtn.setFont(Font.font(15));
		guessBtn.setPrefWidth(width/2-20);
		
		//setting the reset button attributes
		reset.setText("Restart game");
		reset.setFont(Font.font(15));
		reset.setPrefWidth(width/2-20);
		
		//setting up instruction label
		instruct.setText("The computer will choose a number between "+lowNum+" and "+highNum);
		instruct.setTextFill(Color.WHITE);
		instruct.setFont(Font.font(30));
	    instruct.setWrapText(true);
	    instruct.setTextAlignment(TextAlignment.CENTER);
		
		//setting pastGuesses label text
		pastGuesses.setAlignment(Pos.CENTER);
		pastGuesses.setText("Past guesses: ");
		pastGuesses.setTextFill(Color.WHITE);
		pastGuesses.setFont(Font.font(20));
		pastGuesses.setWrapText(true);
		pastGuesses.setTextAlignment(TextAlignment.CENTER);
		
		//setting window layout	
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(25,25,25,25));
		pane.setSpacing(10);
		
		//importing image to use as background
		image=new Image(getClass().getResourceAsStream("backgroundimage.jpg"));
		BackgroundImage myBI= new BackgroundImage(image,BackgroundRepeat.REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		pane.setBackground(new Background(myBI));
		
		//setting HBox to hold two buttons, guess and reset	
		boxes.setAlignment(Pos.CENTER);
		boxes.setSpacing(10);
		boxes.getChildren().addAll(guessBtn,reset);
		
		//adding text and buttons and label to layout
		pane.getChildren().addAll(instruct,text,txfd,boxes,pastGuesses);
		
	}

	//checks that the String num is an integer between lowNum and highNum
	/**
	 * Takes a String and checks whether it is a number
	 * @param num - the String to be checked
	 * @return true if num is a number, false if it is not
	 */
	public boolean isInteger(String num) { //or could use parseInt with try/catch exceptions or regular expressions
		//if num is null
		if(num.equals(null)) {
			return false;
		}
		//if nothing entered
		if(num.isEmpty()) {
			return false;
		}
		//convert string to individual chars, if any char is not a number
		//also prevents negative numbers
		for(int i=0;i<num.length();i++) {
			char c = num.charAt(i);		
	        if (c < '0' || c > '9') {
	            return false;
	        }
		}				
		return true;
	}
	
	/**
	 * takes the textfield input and compares it to the computers number
	 */
	public void guessNum() {
		//uses isInteger to check input data is a valid Integer
		boolean validNum=isInteger(txfd.getText());
		guessNum++;
		if(!validNum || Integer.valueOf(txfd.getText())>highNum || Integer.valueOf(txfd.getText())<lowNum) {
			text.setText("Please enter a valid number");
			addToPastGuesses(guessNum);			
		} else {			
			if(compNum > Integer.valueOf(txfd.getText())) { 		//if compNum higher than guess
				text.setText("Too low!");
				addToPastGuesses(guessNum);
			} else if(compNum < Integer.valueOf(txfd.getText())) { 	//if compNum lower than guess
				text.setText("Too high!");
				addToPastGuesses(guessNum);
			} else {
				text.setText("Congrats! You guessed right"); 		//guessed right
				addToPastGuesses(guessNum);
			}
			
		}	//end if valid num
		txfd.clear();
	} //end guessNum
	
	
	/**
	 * adds previous guesses to the pastGuess label
	 * @param guessNum - the number to be added to the pastGuess label
	 */
	public void addToPastGuesses(int guessNum) {
		if(guessNum==1) {
			pastGuesses.setText(pastGuesses.getText()+" "+txfd.getText());
		} else {
			pastGuesses.setText(pastGuesses.getText()+","+txfd.getText());
		}
	}

	/**
	 * the method to make everything work
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

} 

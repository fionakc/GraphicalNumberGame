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
import javafx.stage.Stage;

public class NumberGame extends Application {

	//fields
	//private String filePath="C:\\Users\\crookfion\\Downloads\\backgroundimage.jpg";
	//computer generate a number
	int compNum=(int)(Math.random()*10); //needs to be 10 to hit the number 9
	final Text text=new Text();
	//create text field
	TextField txfd=new TextField();
	//create label
	Label pastGuesses=new Label();
	
	
	
	int guessNum=0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//setting window title
		primaryStage.setTitle("Guessing Number Game");
		
		//generating text
		Text instruct=new Text("The computer will choose a number between 0 and 9");
		//instruct.setFont(Font.font ("Verdana", 12));
		instruct.setFill(Color.WHITE);
		
		text.setText("Guess a number!");
		//text.setFont(Font.font ("Verdana", 10));
		text.setFill(Color.WHITE);
		
		//setting label text
		pastGuesses.setAlignment(Pos.CENTER);
		pastGuesses.setText("Past guesses: ");
		pastGuesses.setTextFill(Color.WHITE);
		
		//button generation
		Button btn=new Button();
		btn.setText("Guess");
		
		Button reset=new Button("Restart game");
		
		//actions for the textfield
		txfd.setOnKeyPressed(new EventHandler<KeyEvent>() {
			 
		    @Override
		    public void handle(KeyEvent event) {
		        if(event.getCode().equals(KeyCode.ENTER)) {
		             guessNum();
		        }
		    }
		}); //end txfd keyPressed
		
		//actions for the guess button
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) { //what we want to happen when button pressed
				guessNum();		
			}
			
		}); //end btn set action
		
		//actions for the reset button
		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) { //what we want to happen when button pressed
				compNum=0+(int)(Math.random()*((9-0)+1));
				text.setText("Guess a number!");
				pastGuesses.setText("Past Guesses: ");
				guessNum=0;
			}
			
		}); //end reset set action
		
		//setting window layout
		VBox pane=new VBox();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(25,25,25,25));
		pane.setSpacing(10);
		
		Image image=new Image(getClass().getResourceAsStream("backgroundimage.jpg"));
		BackgroundImage myBI= new BackgroundImage(image,
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		pane.setBackground(new Background(myBI));
		
		//changes background to light green
//		BackgroundFill backFill=new BackgroundFill(Color.LIGHTGREEN,null, null);
//		pane.setBackground(new Background(backFill));
		
		
		//create new HBox to hold two buttons, guess and reset
		HBox boxes=new HBox();
		boxes.setAlignment(Pos.CENTER);
		boxes.setSpacing(10);
		boxes.getChildren().addAll(btn,reset);
		
		//adding text and buttons and label to layout
		pane.getChildren().addAll(instruct,text,txfd,boxes,pastGuesses);
		
				
		//adding pane (layout) to scene
		primaryStage.setScene(new Scene(pane,300, 250));
		primaryStage.show();
		
	} //end start
	
	
	
	public boolean isInteger(String num) { //or could use parseInt with try/catch exceptions
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
	
	public void guessNum() {
		//need to check input data
		boolean validNum=isInteger(txfd.getText());
		guessNum++;
		if(!validNum || Integer.valueOf(txfd.getText())>9 || Integer.valueOf(txfd.getText())<0) {
			text.setText("Please enter a valid number");
			addToPastGuesses(guessNum);			
		} else {			
			if(compNum > Integer.valueOf(txfd.getText())) { //if compNum higher than guess
				text.setText("Too low!");
				addToPastGuesses(guessNum);
			} else if(compNum < Integer.valueOf(txfd.getText())) { //if compNum lower than guess
				text.setText("Too high!");
				addToPastGuesses(guessNum);
			} else {
				text.setText("Congrats! You guessed right"); //guessed right
				addToPastGuesses(guessNum);
			}
			
		}	//end if valid num
		txfd.clear();
	} //end guessNum
	
	public void addToPastGuesses(int guessNum) {
		if(guessNum==1) {
			pastGuesses.setText(pastGuesses.getText()+" "+txfd.getText());
		} else {
			pastGuesses.setText(pastGuesses.getText()+","+txfd.getText());
		}
	}

	

	public static void main(String[] args) {
		launch(args);
	}

} //end NumberGame

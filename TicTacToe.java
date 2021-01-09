package Game;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//when we click a button then system will know about it, in order to do that we implement ActionListener interface
public class TicTacToe extends JFrame implements ActionListener{
	
	//parameters for my game
	public static int BOARD_SIZE=3;
	
	
	//when we have different variables of same datatype then we club them together
	public static enum GameStatus {
		
		Incomplete, XWins, ZWins, Tie
//		incomplete -> game is left incomplete
//		XWins -> X wins the game
//		ZWins -> 0 wins the game
//		Tie -> there is a tie
	}
	
	//JButton is a class used to implement the functionality of buttons
	// creating  a 2D array of buttons
	private JButton[][] buttons=new JButton[BOARD_SIZE][BOARD_SIZE];
	
	boolean crossTurn=true; //whenever cross plays its turn and click a button a cross will appear 
	
	public TicTacToe () {
		
		super.setTitle("Tic Tac Toe");
		super.setSize(500, 500);
		
		GridLayout grid=new GridLayout(BOARD_SIZE, BOARD_SIZE); // using this we are creating a 3X3 grid so that all the components are layered out in grid format
		super.setLayout(grid); // setting layout to be a grid
		
		Font font=new Font("Comic Sans", 5, 50);
		
		//creating buttons on the 3X3 grid 
		for (int row=0; row<BOARD_SIZE; row++) {
			for (int col=0; col<BOARD_SIZE; col++) {
				
				JButton button=new JButton(""); //creating a JButton and initially there will be an empty string on each button
				buttons[row][col]=button; //adding those buttons on our 2D matrix
				button.setFont(font); //setting the font of each button as font (the object we created of class Font)
				button.addActionListener(this); //we are attaching actionListener to each object we created
				super.add(button); //adding my buttons to frame
			}
		}
		
		super.setVisible(true); // makes the JFrame visible
		super.setResizable(false); // by setting this as false we are fixing the size of our gaming window i.e now it not resizable
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// our buttons are in listening mode so whenever an action is performed our button is notified and actionPerformed for a button is called
		
		JButton clickedButton=(JButton)e.getSource(); //clickedButton is the reference pointing to the button which is clicked
		makeMove (clickedButton); //make a move on the clicked button
		
		GameStatus gs=getGameStatus(); //after every move we are checking the status of the game
		
		if (gs==GameStatus.Incomplete) {
			//game is still in progress
			return;
		}
		//if the cursor reaches here then surely someone has won or it is a tie
		
		declareWinner(gs);
		
		//after declaring the winner we need to terminate the current game and ask user for some decision
		
		int choice=JOptionPane.showConfirmDialog(this, "Do you want to restart the Game ?");
		
		if (choice==JOptionPane.YES_OPTION) {
			//user wants to play again
			for (int row=0; row<BOARD_SIZE; row++) {
				for (int col=0; col<BOARD_SIZE; col++) {
					buttons[row][col].setText(""); //setting every button empty
				}
			}
			
			crossTurn=true; //first chance will be of X
		} else {
			//user wants to close the game
			
			super.dispose();
		}
	}

	private void declareWinner(GameStatus gs) {
		
		if (gs==GameStatus.XWins) {
			JOptionPane.showMessageDialog(this, "X Wins");
		} else if (gs==GameStatus.ZWins) {
			JOptionPane.showMessageDialog(this, "Z Wins");
		} else {
			JOptionPane.showMessageDialog(this, "It is a Tie");
		}
		
	}

	private GameStatus getGameStatus() {
		
		String txt1="", txt2="";
		int row=0, col=0;
		
		//checking horizontally
		while (row<BOARD_SIZE) {
			
			col=0;
			while (col<BOARD_SIZE-1) {
				txt1=buttons[row][col].getText();
				txt2=buttons[row][col+1].getText();
				
				if (txt1.equals(txt2)==false || txt1.length()==0) {
					break;
				}
				col++;
			}
			
			if(col==BOARD_SIZE-1) {
				
				if (txt1=="X") {
					return GameStatus.XWins;
				} else {
					return GameStatus.ZWins;
				}
			}
			row++;
		}
		
        row=0;
        col=0;
		
        //checking vertically
		while (col<BOARD_SIZE) {
			
			row=0;
			while (row<BOARD_SIZE-1) {
				txt1=buttons[row][col].getText();
				txt2=buttons[row+1][col].getText();
				
				if (txt1.equals(txt2)==false || txt1.length()==0) {
					break;
				}
				row++;
			}
			
			if(row==BOARD_SIZE-1) {
				
				if (txt1=="X") {
					return GameStatus.XWins;
				} else {
					return GameStatus.ZWins;
				}
			}
			
			col++;
		}
		
		row = 0;
		col = 0;
		
		//checking primary diagonal
		while (row < BOARD_SIZE - 1) {
			txt1 = buttons[row][col].getText();
			txt2 = buttons[row + 1][col + 1].getText();

			if (txt1.equals(txt2) == false || txt1.length() == 0) {
				break;
			}
			row++;
			col++;
		}

		if (row == BOARD_SIZE - 1) {

			if (txt1 == "X") {
				return GameStatus.XWins;
			} else {
				return GameStatus.ZWins;
			}
		}
		
		row = 0;
		col = BOARD_SIZE-1;
		
		//checking secondary diagonal
		while (row < BOARD_SIZE - 1) {
			txt1 = buttons[row][col].getText();
			txt2 = buttons[row + 1][col - 1].getText();

			if (txt1.equals(txt2) == false || txt1.length() == 0) {
				break;
			}
			row++;
			col--;
		}

		if (row == BOARD_SIZE - 1) {

			if (txt1 == "X") {
				return GameStatus.XWins;
			} else {
				return GameStatus.ZWins;
			}
		}
		
		String txt="";
		
		//checking if the game is still incomplete
		for (row=0; row<BOARD_SIZE; row++) {
			for (col=0; col<BOARD_SIZE; col++) {
				
				txt=buttons[row][col].getText();
				if (txt.length()==0) {
					return GameStatus.Incomplete;
				}
			}
		}
		
		//if all the blocks are filled then it is a tie
		return GameStatus.Tie;
		
		
		
	}

	private void makeMove(JButton clickedButton) {
		
		String btntext=clickedButton.getText(); //the text written on the button
		
		if (btntext.length()>0) {
			
			JOptionPane.showMessageDialog(this, "Invalid Move");
			
		} else {
			
			//if string length is 0 then there is nothing written on the button
			
			if (crossTurn) {
				//true value of crossTurn denotes that it is the turn of cross
				clickedButton.setText ("X");
				
			} else {
				//false value denotes that it the turn of 0
				clickedButton.setText ("0");
			}
			
			//now change the turn either from X to 0 or from 0 to X
			crossTurn=!crossTurn;
		}
	}
}


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
	
	private Scanner sc;
	private Model model;
	private View view;
	private boolean complete;
	private boolean tie;
	
	public enum Player{
		ONE, TWO
	}
	
	private boolean playerTwo = false;
	
	public Controller() {
		sc = new Scanner(System.in);
		while(true) {
			try {
				System.out.print("What size board? (3, 4, or 5): ");
				int s = sc.nextInt();
				if(s > 2 && s < 6) {
					setUpGame(s);
					break;
				}else {
					System.out.println("You must choose 3, 4, or 5");
					continue;
				}
			}catch(InputMismatchException e) {
				System.out.println("You have not entered a valid number");
				sc.next();
				continue;
			}
		}
	}

	private void setUpGame(int s) {
		model = new Model(this);
		model.setSize(s);
		view = new View(model, this);
		model.createBoard();
	}
	
	public Player getPlayerTurn(){
		if(!playerTwo) {
			return Player.ONE;
		}else {
			return Player.TWO;
		}
	}
	
	public void requestInput() {
		int size = model.getSize();
		Player player = getPlayerTurn();
		String p = "";
		if(player == Player.ONE) {
			p = "Player 1 ";
		}else {
			p = "Player 2 ";
		}
		System.out.print(p + "choose a row 1 - " + size + ": ");
		while(true) {
			try {
			int column = sc.nextInt();
			if (column > 0 && column <= model.getWidth()) {
				if(!model.isColumnFull(column - 1)) {
					model.addSpace(column - 1);
					break;
				}else {
					System.out.print("Column is full, make another choice: ");
					continue;
				}
			}else {
				System.out.print("Choice is not a valid column, make another choice: ");
				continue;
			}
			
			}catch(InputMismatchException e) {
				System.out.print("That is not a valid number, make another choice: ");
				continue;
			}
		}
	}
	
	public boolean isComplete() {return complete;}
	
	public void setComplete(boolean c) {
		complete = c;
	}

	public boolean isTie() {return tie;}

	public void setTie(boolean t) {
		tie = t;
	}
	
	public boolean isPlayerTwo() {return playerTwo;}
	
	public void setPlayerTwo() {
		playerTwo = !playerTwo;
	}
	
	public void checkForTie() {
		int size = model.getWidth();
		int fullCount = 0;
		for(int i=0;i<size;i++) {
			if(model.isColumnFull(i)) {
				fullCount++;
			}
		}
		if(fullCount == size) {
			setTie(true);
		}
		
	}

	public void checkForWin() {
		int size = model.getSize();
		ArrayList<ArrayList<Model.Space>> board = model.getBoard();
		//Check column
		for(ArrayList<Model.Space> column: board) {
			if(isWin(column)) {
				return;
			}
			
		}
		//Check row
		ArrayList<Model.Space> row = new ArrayList<Model.Space>();
		for (int i = 0; i < model.getHeight(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
			row.add(board.get(j).get(i));
			}
			if(isWin(row)) {
				return;
			}else {
				row.clear();
			}
		}
		
		ArrayList<Model.Space> diagonal = new ArrayList<Model.Space>();
		//Check diagonal from top left to bottom right
		for(int i = 0; i < size; i++) {
			diagonal.add(board.get(i).get(i));
		}
		if(isWin(diagonal)) {
			return;
		}
		diagonal.clear();
		int j = 0;
		//Check diagonal from bottom left to top right
		for(int i = size-1; i >= 0; i--) {
			diagonal.add(board.get(j).get(i));
			j++;
		}
		if(isWin(diagonal)) {
			return;
		}
	}
	
	private boolean isWin(ArrayList<Model.Space> check) {
		int count = 0;
		for(Model.Space space: check) {
			if(space == Model.Space.PLAYERONE) {
				count++;
			}else {
				count = 0;
			}
			if(count >= model.getSize()) {
				setComplete(true);
				return true;
			}
		}
		for(Model.Space space: check) {
			if(space==Model.Space.PLAYERTWO) {
				count++;
			}else {
				count = 0;
			}
			if(count >= model.getSize()) {
				setComplete(true);
				return true;
			}
		}
		return false;
		/*if(!check.contains(Model.Space.OPEN ) && (!check.contains(Model.Space.PLAYERONE )|| !check.contains(Model.Space.PLAYERTWO))) {
			setComplete(true);
			return true;
		}else {
			return false;
		}*/
	}

}

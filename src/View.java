import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;



public class View implements Observer {
	
	private Controller controller;
	private Model model;
	private Scanner sc;
	private AI ai;

	public View(Model model, Controller g) {
		model.addObserver(this);
		controller = g;
		this.model = model;
		sc = new Scanner(System.in);
		ai = new AI(model);
	}

	@Override
	public void update(Observable o, Object arg) {
		Model m = (Model) o;
		ArrayList<ArrayList<Model.Space>> board = m.getBoard();
		printBoard(board);
		if(!controller.isComplete() && !controller.isTie()) {
		requestInput();
		}else if (controller.isComplete()) {
			if(controller.getPlayerTurn() == Controller.Player.TWO) {
				System.out.println("Player 1 wins!");
			}else {
				System.out.println("Player 2 wins!");
			}
		}else if (controller.isTie()) {
			System.out.println("The game is a tie");
		}

	}
	
	public void requestInput() {
		int size = model.getWidth();
		Controller.Player player = controller.getPlayerTurn();
		if(player == Controller.Player.ONE) {
			System.out.print("Choose a row 1 - " + size + ": ");
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
		}else {
			int choice = ai.makeChoice();
			model.addSpace(choice);
		}
		
	}
	
	private void printBoard(ArrayList<ArrayList<Model.Space>> board) {
		System.out.println("The current board state is: ");
		for (int i = 0; i < model.getHeight(); i++) {
			for (int j = 0; j < model.getWidth(); j++) {
			Model.Space space = board.get(j).get(i);
			switch(space) {
				case OPEN:
					System.out.print("0  ");
					break;
				case PLAYERONE:
					System.out.print("1  ");
					break;
				case PLAYERTWO:
					System.out.print("2  ");
					break;
			}
			if(j == model.getWidth() -1) {
				System.out.print("\n");
			}
				
			}	
		}
		System.out.println("-----------------------");
		for (int i = 0; i < model.getWidth(); i++) {
			System.out.print(i+1 + "  ");
		}
		System.out.print("\n");
	}
	
	
	

}

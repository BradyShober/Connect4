import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
	
	private Controller controller;
	private Model model;

	public View(Model model, Controller g) {
		model.addObserver(this);
		controller = g;
		this.model = model;
	}

	@Override
	public void update(Observable o, Object arg) {
		Model m = (Model) o;
		ArrayList<ArrayList<Model.Space>> board = m.getBoard();
		printBoard(board);
		if(!controller.isComplete() && !controller.isTie()) {
		controller.requestInput();
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

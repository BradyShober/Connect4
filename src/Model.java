import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {
	
	private int size = 4;
	private int height = 6;
	private int width = 7;
	private ArrayList<ArrayList<Space>> board;
	private Controller contoller;
	public enum Space{
		OPEN, PLAYERONE, PLAYERTWO
	}
	
	
	
	public Model(Controller g) {
		contoller = g;
	}
	
	public void createBoard() {
		if(size == 3) {
			width--;
			height--;
		}else if (size == 5) {
			width++;
			height++;
		}
		board = new ArrayList<ArrayList<Space>>();
		for(int i = 0; i < width; i++) {
			ArrayList<Space> column = new ArrayList<Space>();
			for(int j = 0; j < height; j++) {
				column.add(Space.OPEN);
			}
			board.add(column);
		}
		setChanged();
		notifyObservers();
	}

	public void setSize(int s) {
		size = s;
	}
	
	public int getSize() {
		return size;
	}
	
	public ArrayList<ArrayList<Space>> getBoard(){
		return board;
	}
	
	
	
	public boolean isColumnFull(int column) {

		if(!(board.get(column).indexOf(Space.OPEN) >= 0)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public void addSpace(int column) {
		ArrayList<Space> c = board.get(column);
		int i = c.lastIndexOf(Space.OPEN);
		if(!contoller.isPlayerTwo()) {
			c.set(i, Space.PLAYERONE);
		}else {
			c.set(i, Space.PLAYERTWO);
		}
		contoller.checkForWin();
		contoller.checkForTie();
		contoller.setPlayerTwo();
		setChanged();
		notifyObservers();
	}
	
	public int getHeight() {return height;}
	
	public int getWidth() {return width;}
	
	

}

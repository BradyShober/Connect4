import java.util.Random;

public class AI {
	
	private Model model;
	
	public AI(Model m) {
		model = m;
	}
	
	public int makeChoice() {
		Random rand = new Random();
		int n = 0;
		do {
		n = rand.nextInt(model.getSize());
		}while(model.isColumnFull(n));
			
		
		return n;
	}

}

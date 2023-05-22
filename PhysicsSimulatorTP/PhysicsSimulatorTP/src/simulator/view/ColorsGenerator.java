package simulator.view;

import java.awt.Color;
import java.util.Random;

public class ColorsGenerator {
	
	private Random random;

	public ColorsGenerator() {
		random = new Random();
	}
	
	public Color nextColor() {
		// TODO Auto-generated method stub
		 Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	     return color;
	}

	public void reset() {
		;
	}

}

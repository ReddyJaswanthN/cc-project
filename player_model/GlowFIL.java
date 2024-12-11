package nine_mens_morris_game.player_model;

import java.awt.Color;

public class GlowFIL {
	private double x;
	private double y;
	private Color c;
	private boolean decreasing;
	
	public GlowFIL(){
		this.x = 35;
		this.y = 35;
		this.c = Color.WHITE;
		this.decreasing = true;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Color getColor() {
		return c;
	}

	public void update() {
		checkDirection();
		if(this.decreasing){
			this.x -= .1;
			this.y -= .1;
		}
		else{
			this.x += .1;
			this.y += .1;
		}
	}

	private void checkDirection() {
		if(this.x <= 12 || this.y <= 12)
			this.decreasing = false;
		else if(this.x >= 35 || this.y >= 35)
			this.decreasing = true;
	}
}

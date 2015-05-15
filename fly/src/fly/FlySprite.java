package fly;

import java.util.Random;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;



public class FlySprite extends AnimatedSprite{
	
	private static final float fly_VELOCITY = 200;
	private PhysicsHandler flyphysic;	
	private Random random = new Random(System.currentTimeMillis());
	private float RandomX;
	private float RandomY;
	
	private int score;
	private int type;
	
	private int screenheight;
	private int screenwidth;
	
	private boolean flyback = false;
	
	public FlySprite(int px, int py, TiledTextureRegion pTiledTextureRegion) {
		super(0, 0, pTiledTextureRegion);	
		this.screenwidth = px;
		this.screenheight = py;
		
		if(random.nextBoolean()){
			int x = 300+420 * random.nextInt(2);
			int y = random.nextInt(420);
			this.setPosition(x, y);
		}
		else
			this.setPosition(300+random.nextInt(420),0);
		flyphysic = new PhysicsHandler(this);
		this.registerUpdateHandler(flyphysic);
	}
	
	protected void onManagedUpdate(final float pSecondsElapsed) {
		RandomX = (float) (100*(this.random.nextInt(6))-2);
		RandomY = (float) (100*(this.random.nextInt(6))-2);
		float x = this.getX();
		float y = this.getY();
		if(x > 700 || flyback){
			this.flyphysic.setVelocityX(-RandomX);
			this.flyback = true;
		}
		else if ((y>400 && x<300 )|| flyback){
			this.flyphysic.setVelocity(-RandomX, -RandomY);
			this.flyback = true;
		}
		else{
	        this.flyphysic.setVelocityX(RandomX);
	        this.flyphysic.setVelocityY(RandomY);
		}
        super.onManagedUpdate(pSecondsElapsed);
    }

	public void setVelocity(){
		RandomX = (float) (100*(this.random.nextFloat())+1);
		RandomY = (float) (100*(this.random.nextFloat())+1);
		flyphysic.setVelocity(RandomX, RandomY);
	}
	
	public void setflyscore(int score){
		this.score = score;
	}
	
	public int getflyscore(){
		return this.score;
	}
	
	public int gettype() {
		return type;
	}
	
	public void settype(int type) {		
		this.type = type;	
	}



	

	
	
}
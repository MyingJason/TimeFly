package tongue;


import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;


public class TongueSprite extends AnimatedSprite{
	
	private static float tongue_VELOCITY;

	private PhysicsHandler tonguephysic;
	
	private float Velocity_X , Velocity_Y;

	private int level;

	public TongueSprite(int level, float pX, float pY,TiledTextureRegion pTiledTextureRegion) {
		super(pX, pY, pTiledTextureRegion);	
		this.level = level;
		tonguephysic = new PhysicsHandler(this);
		this.registerUpdateHandler(tonguephysic);

	}	
	public void setVelocity(float angle){
		if(level == 1)
			tongue_VELOCITY = 1500;
		else
			tongue_VELOCITY = 800;
		
		Velocity_X = (float) (tongue_VELOCITY * Math.sin(MathUtils.degToRad(angle)));
		Velocity_Y = (float) (tongue_VELOCITY * Math.cos(MathUtils.degToRad(angle)));
		
		tonguephysic.setVelocity(Velocity_X, -Velocity_Y);
	}
	public void setreversemove(){
		float x = -this.tonguephysic.getVelocityX();
		float y = -this.tonguephysic.getVelocityY();

		tonguephysic.setVelocity(x, y);
		
	}

	

	
	
}
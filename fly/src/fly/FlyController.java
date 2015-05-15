package fly;

import java.util.ArrayList;
import java.util.Random;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;




import GameInfo.GameInfo;


public class FlyController implements GameInfo{

	public ArrayList<FlySprite> fList= new ArrayList<FlySprite>();
    private int screenWidth;
    private int screenHeight;
	private TiledTextureRegion[] textureregion;
	private Random random = new Random(System.currentTimeMillis());

	public int getrandomtype(){	
		
		int randomtype = this.random.nextInt(10);
		if(randomtype<2)
			return FLY_BAD;
		else if(randomtype<5)
			return FLY_SUPER;
		else
			return FLY_NORMAL;

	}
	public FlyController(int screenWidth, int screenHeight, TiledTextureRegion[] pTextureRegion) {

		this.screenHeight=screenHeight;
		this.screenWidth = screenWidth;
		this.textureregion = pTextureRegion;
				
		for(int i = 0; i < FLY_MAX; i++ ){
			
			this.fList.add(createfly(screenWidth, screenHeight));
		}

	}
	public float getrandomx(){			
		return (float) (this.random.nextInt(this.screenWidth));
		
	}
	public float getrandomy(){			
		return (float) (this.random.nextInt(this.screenHeight));
		
	}
	public void lauchfly(Scene scene) {

		for(int i = 0; i < FLY_MAX; i++ )
			scene.attachChild(this.fList.get(i));
		
	}
	public FlySprite createfly(int px, int py){
		
		int type = getrandomtype();
		FlySprite fly = new FlySprite(px,py,this.textureregion[type-1]);
		
		switch(type){
		case 1: 
			fly.setflyscore(FLY_NORMAL_SCORE);
			break;
		case 2: fly.setflyscore(FLY_SUPER_SCORE);
			break;
		case 3: fly.setflyscore(FLY_BAD_SCORE);
			break;
		}
		fly.settype(type);
		fly.animate(50);
		return fly;
	}
	public void checkfly(Scene scene){
		while(fList.size()<FLY_MAX)
		{
			FlySprite tempfly = createfly(this.screenWidth, this.screenHeight);
			
			fList.add(tempfly);
			
			scene.getChild(LAYER_TONGUE).attachChild(tempfly);
		}
		for(int i = 0; i < FLY_MAX; i++){
			
			FlySprite fly = this.fList.get(i);
			float x = fly.getX();

			float y = fly.getY();
			
			if(x>this.screenWidth||y>this.screenWidth||x<300||y<0){
				fList.remove(i);
				scene.getChild(LAYER_TONGUE).detachChild(fly);
				fly = null;
				
				FlySprite tempfly = createfly(this.screenWidth, this.screenHeight);
				
				fList.add(tempfly);
				scene.getChild(LAYER_TONGUE).attachChild(tempfly);
			}
		}
	}
}

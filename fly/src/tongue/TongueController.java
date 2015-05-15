package tongue;


import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.util.Log;
import GameInfo.GameInfo;

public class TongueController implements GameInfo{
	
	
    private int screenWidth;
    private int screenHeight;
    
    private int tonguewidth;
    private int tongueheight;
    
    private int lasttouchx;
    private int lasttouchy;
    
    private TiledTextureRegion TongueTextureRegion;

	public TongueSprite tongue = null;
	public boolean isnotComingback = true;

	public TongueController(int screenWidth, int screenHeight, TiledTextureRegion TongueTextureRegion) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.tonguewidth = TongueTextureRegion.getWidth();
		this.tongueheight = TongueTextureRegion.getHeight();
		this.TongueTextureRegion = TongueTextureRegion;
//		this.tongue = new TongueSprite(-tonguewidth, this.screenHeight-tongueheight/2, TongueTextureRegion);
	}

	public TongueSprite lauchtongue(int level, int x, int y, float angle, Scene scene) {
		
		this.lasttouchx = x;
		this.lasttouchy = y;
		if (this.tongue != null){
			scene.getChild(LAYER_TONGUE).detachChild(tongue);
			this.tongue = null;
		}
		
		this.tongue = new TongueSprite(level, 150-tonguewidth, 510-tongueheight/2 , TongueTextureRegion);
		this.tongue.setRotation(angle);
		this.tongue.setVelocity(angle);
		this.isnotComingback = true;
		scene.getChild(LAYER_TONGUE).attachChild(tongue);
		
		return this.tongue;
	}

	public boolean checktongue(Scene scene) {
		if(this.tongue == null)
			return false;
		else
		{
			float x = this.tongue.getX();
			double rotation = MathUtils.degToRad(tongue.getRotation());
			if(rotation>Math.PI)
				rotation = Math.PI + rotation;
			Log.e("angle", rotation+"");
			if(x > Math.sin(rotation)*this.tongueheight)
			{
				changetobackmovement();
				return true;
			}
			else if(this.tongue.getX() < -300)
			{
				scene.getChild(LAYER_TONGUE).detachChild(tongue);
				this.tongue = null;
				return false;
			}
		}
		return false;
	}

	public int getlasttouchx() {
		return lasttouchx;
	}
	public int getlasttouchy() {
		return lasttouchy;
	}
	public void changetobackmovement() {
		this.tongue.setreversemove();
		this.isnotComingback  = false;
	}
	public void setisnotComingback(boolean t){
		this.isnotComingback = t;
	}

	public void reset() {
		this.tongue = null;
	}

}

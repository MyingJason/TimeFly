package screen;

import java.util.ArrayList;

import org.anddev.andengine.entity.scene.Scene;

import GameInfo.GameInfo;
import tongue.TongueController;
import fly.FlySprite;

public class ScreenMonitor implements GameInfo{


	public int Scoring(ArrayList<FlySprite> fList, TongueController tonguecontroller,Scene scene,int mScore){
		for(int i =0; i < FLY_MAX; i++){
			
			FlySprite tempfly = fList.get(i);
			
			if(fList.get(i).collidesWith(tonguecontroller.tongue) && tonguecontroller.isnotComingback){
				
				scene.getChild(LAYER_TONGUE).detachChild(fList.get(i));
				fList.remove(i);

				tonguecontroller.setisnotComingback(false);
				tonguecontroller.changetobackmovement();
				
				mScore= mScore + tempfly.getflyscore();

				break;
			
			}
		}
		return mScore;
		
	}
		

}
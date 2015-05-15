package init;

import java.util.ArrayList;

import screen.MainActivity;

import com.example.fly.R;
import com.example.fly.R.id;
import com.example.fly.R.layout;
import com.example.fly.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Difficulty extends Activity {

	int level=0;
	ArrayList<Integer> score;
	boolean hasNet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_difficulty);
		hasNet=this.getIntent().getExtras().getBoolean("hasNet");
//		if(hasNet){
//			score = this.getIntent().getIntegerArrayListExtra("score");
//		}
	}

	public void startNormal(View view){
		Intent intent = new Intent(Difficulty.this, MainActivity.class);
		level = 1;
		intent.putExtra("level", level);
		intent.putExtra("hasNet", hasNet);

	    startActivity(intent);
	    this.finish();
	}
	public void startHard(View view){
		Intent intent = new Intent(Difficulty.this, MainActivity.class);
		level = 2;
		intent.putExtra("level", level);
		intent.putExtra("hasNet", hasNet);
//	    if(hasNet){
//	    	intent.putIntegerArrayListExtra("score", score);
//	    }
		this.finish();
	    startActivity(intent);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.difficulty, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

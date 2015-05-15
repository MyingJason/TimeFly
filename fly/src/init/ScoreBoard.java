package init;

import java.util.ArrayList;

import scoreFactory.ScoreFactory;

import com.example.fly.R;


import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreBoard extends Activity {

	TextView tv;
	Button update;
	ArrayList<String> name;
	ArrayList<Integer> score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score_board);
		tv=(TextView)findViewById(R.id.score);
		if(this.getIntent().getExtras().getBoolean("hasNet")){
			score = this.getIntent().getIntegerArrayListExtra("score");
			name = this.getIntent().getStringArrayListExtra("name");
			String details = getDetail();
			tv.setText(details);
		}
		else tv.setText("Connection Failed");
		update = (Button)findViewById(R.id.update);

	}


	public void update(View view){
		ScoreFactory sf = new ScoreFactory();
		name = sf.getNameList();
		score=sf.getScoreList();
		String details = getDetail();
		tv.setText(details);
	}
	private String getDetail() {
		// TODO Auto-generated method stub
		String content="leading board:\n";
//		ArrayList<String> name = sf.getNameList();
//		ArrayList<Integer> score=sf.getScoreList();
		for(int i=0;i<10;i++){
			content = content+Integer.toString(i+1)+".  ";
			content = content+score.get(i).toString()+"  ";
			content = content+name.get(i)+"\n";
		}
		return content;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score_board, menu);
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

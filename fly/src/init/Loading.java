package init;


import com.example.fly.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Loading extends Activity {

	boolean isVisable = true;
	int status = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);

		final Handler handler = new Handler();
		final TextView progress = (TextView) findViewById(R.id.progress);
		progress.setVisibility(View.INVISIBLE);

		final ProgressBar horizontalDeterminate = (ProgressBar) findViewById(R.id.horizontal_determinate);
		Button determinate = (Button) findViewById(R.id.determinate);

		determinate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						status = 0;
						while (status < 100) {
							handler.post(new Runnable() {

								@Override
								public void run() {
									progress.setVisibility(View.VISIBLE);
									horizontalDeterminate
											.setVisibility(ProgressBar.VISIBLE);
									horizontalDeterminate.setProgress(status);
									progress.setText("Process£º" + status + "%");
								}
							});
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							status++;
						}
						handler.post(new Runnable() {

							@Override
							public void run() {
								horizontalDeterminate.setVisibility(View.INVISIBLE);
								progress.setVisibility(View.INVISIBLE);
							}
						});

					}
				}).start();
				onprocess();
			}
		});
		
	}


	private void onprocess() {

		// for(int i =1; i< 100; i++)
		// pb.setProgress(i);
//		boolean hasNet = true;
//		hasNet = isNetworkConnected();
		Intent intent = new Intent(Loading.this, WelcomeScreen.class);
//		intent.putExtra("hasNet", hasNet);
		startActivity(intent);
		this.finish();

	}

//	private boolean isNetworkConnected() {
//		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo ni = cm.getActiveNetworkInfo();
//		if (ni == null) {
//			// There are no active networks.
//			return false;
//		} else
//			return true;
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
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

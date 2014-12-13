package nl.mprog.projects.nPuzzle10265732;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

public class YouWin extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_you_win);
		Intent won = getIntent();
		int moves = won.getIntExtra("count", 0);
		
		TextView textView = (TextView) findViewById(R.id.wint);
		textView.setText(getString(R.string.winn, moves));
	}

	public void goSelect(View view) {
	    // Go back to selection menu
		YouWin.this.finish();
		}

    

}

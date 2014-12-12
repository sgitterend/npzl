package nl.mprog.projects.nPuzzle10265732;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class GamePlay extends ActionBarActivity {

	// go to YouWin activity when game is finished
	public void goNext(View view) {
	    // Do something in response to button
		Intent go = new Intent(this, YouWin.class);
		startActivity(go); 
		GamePlay.this.finish();
		}
	
	public void goNew(View view) {
	    // let user start new game
		finish();
		}
	
	// init options menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.game_play, menu);
	    return true; }

	// create functionality for options menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true; }
		
		if (id == R.id.quit) {
			moveTaskToBack(true); 
			GamePlay.this.finish(); }
		if (id == R.id.new_game) { 
			goNew(null);
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		// get user choices from last activity (difficulty and image)
		Intent go = getIntent();
		int difficulty = go.getIntExtra("diff", 0);
		int imgch = go.getIntExtra("image", 0);
		final int size = difficulty * difficulty;
		
		//crop images
		final Bitmap[] bmparray = bmpcrop.bmparray(this, imgch, difficulty);
		
		// create ImageView array
		final int[] ID = new int[size];
		final ImageView[] imageViews = new ImageView[bmparray.length];

		for (int i = 0; i < bmparray.length; i++) {
			imageViews[i] = new ImageView(this);
			ID[i] = i;
			imageViews[i].setTag(ID[i]);
		}
		
		// create name for toast
	    String[] names = {"Grid", "Cat", "Tree", "Mill"};
		
		// Display user choice in a toast
		Toast.makeText(GamePlay.this, getString(R.string.choice, difficulty, names[imgch]), 
	            Toast.LENGTH_SHORT).show();
		
		// Get display size
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int dpw = displaymetrics.widthPixels;
		
		// link activity to adapter
		GpAdapter adapter = new GpAdapter(bmparray, imageViews);
		
		// create grid
		final GridView gridView = (GridView) findViewById(R.id.grid);
		gridView.setColumnWidth((int)(dpw));
		gridView.setNumColumns(difficulty);
        gridView.setAdapter(adapter);
        
        // respond to clicking tiles
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	int blank = 0;
            	System.out.println("ID[" + position + "] =  " + ID[position]);
            	Toast.makeText(GamePlay.this, getString(R.string.var, position), 
    		            Toast.LENGTH_SHORT).show();
            	
            	for (int i = 0; i < size; i++) {
            		if ((Integer)gridView.getChildAt(i).getTag() == size - 1) {
            			blank = i;
            		}
            	}
            	System.out.println("blank = " + blank + ", click = " + position);
            	
            	// swap the views
            	Bitmap temp = bmparray[position];
    			bmparray[position] = bmparray[blank];
    			bmparray[blank] = temp;
    			
    			// swap the ID's
            	int tID = ID[position];
            	ID[position] = ID[position];
            	ID[position] = tID;
            	for (int i = 0; i < size; i++) {
            		imageViews[i].setTag(ID[i]);
            	}
    			
            	// refresh view
            	gridView.invalidateViews();
            }
        });
	}
			
	
}

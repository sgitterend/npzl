package nl.mprog.projects.nPuzzle10265732;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

	// keep track of no. moves made
	int moves;
	
	// go to YouWin activity when game is finished
	public void goNext(View view) {
		Intent go = new Intent(this, YouWin.class);
		go.putExtra("count", moves);
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
		
	    Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("http://en.wikipedia.org/wiki/15_puzzle"));
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true; }
		
		if (id == R.id.quit) {
			moveTaskToBack(true); 
			GamePlay.this.finish(); 
		}
		if (id == R.id.shuffle) {
			Intent shuffle = getIntent();
			finish();
			startActivity(shuffle); 
		}
		if (id == R.id.new_game) { 
			goNew(null);
		}
		if (id == R.id.help) {
		    startActivity(browse);
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_play);
		
		// get user choices from last activity (difficulty and image)
		Intent go = getIntent();
		final int difficulty = go.getIntExtra("diff", 0);
		final int imgch = go.getIntExtra("image", 0);
		final int size = difficulty * difficulty;
		
		//crop images
		final Bitmap[] bmparray = bmpcrop.bmparray(this, imgch, difficulty);
		
		// create ImageView array
		final int[] ID = new int[size];
		final ImageView[] imageViews = new ImageView[bmparray.length];

		// create IDs to keep track of tiles
		for (int i = 0; i < bmparray.length; i++) {
			imageViews[i] = new ImageView(this);
			ID[i] = i;
		}
		
		// create name for toast
	    String[] names = {"Cat", "Tree", "Mill"};
		
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
        
        // shuffle tiles
        doShuffle(size, difficulty, ID, bmparray);
        
        // tag views
        for (int i = 0; i < size; i++) {
    		imageViews[i].setTag(ID[i]);
    	}
        // respond to clicking tiles
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	
            	// get location of blank tile
            	int blank = 0;
            	for (int i = 0; i < size; i++) {
            		if ((Integer)gridView.getChildAt(i).getTag() == size - 1) {
            			blank = i;
            		}
            	}
            	
            	// check if move is allowed
            	if (checkValid(ID, blank, position, difficulty)) {
	            	
	            	// swap the bmp position in array
	            	Bitmap temp = bmparray[position];
	    			bmparray[position] = bmparray[blank];
	    			bmparray[blank] = temp;
	    			
	    			// swap the ID's
	            	int tID = ID[blank];
	            	ID[blank] = ID[position];
	            	ID[position] = tID;
	            	for (int i = 0; i < size; i++) {
	            		imageViews[i].setTag(ID[i]);
	            	}
	            	
	            	// check if user has won the game
	            	checkWin(gridView, size);
	            	
	            	// refresh view
	            	gridView.invalidateViews();
            	}
            }
        });
	}
		
	// check if game is won
	private void checkWin(GridView gridView, int size) {
		// check if tiles are in order
		int n = 0;
		moves++;
		for (int i = 0; i < size; i++) {
			if ((Integer)gridView.getChildAt(i).getTag() == i) {
				n++;
			}
		}
		// if n = size, all tiles are in order
		if (n == size) {
			// congratulate & go to YouWin activity
			Toast.makeText(GamePlay.this, getString(R.string.win),
		    Toast.LENGTH_LONG).show();
			goNext(null);

			}
						
	}


	// check if the clicked tile is a valid swap attempt
	private boolean checkValid(int[] ID, int blank, int position, int difficulty) {
	
		// correcting corner cases
		if ((position + 1 == blank && (blank % difficulty != 0)) ||
		    (position - 1 == blank && (position % difficulty != 0)) ||
			(position + difficulty == blank || position - difficulty == blank)) {
				return true;
			}
		return false;
	}
	
	// 'shuffling' tiles to a solvable order
	private void doShuffle(int size, int difficulty, int[] ID, Bitmap[] bmparray) {
		// create temp bitmap and ID array
		Bitmap[] tbmp = new Bitmap[size];
		int[] tID = new int[size];
		
		// create empty tile
		int ratio = (int)(bmparray[0].getWidth());
		Bitmap emptyTile = BitmapFactory.decodeResource(this.getResources(), R.drawable.px);
		Bitmap emp = Bitmap.createScaledBitmap(emptyTile, ratio, ratio, false);
		
		// fill temp array for shuffle
		for (int i = 0; i < size; i++) {
			tbmp[i] = bmparray[i];	
			tID[i] = ID[i];
			
		}
		// array reversed
		for (int i = 0; i < size - 1; i++) {
			bmparray[i] = tbmp[size - 2 - i];
			ID[i] = tID[size - 2 - i];
		}
		
		// odd number of tiles should have last two tiles swapped
		if (size % 2 == 0) {
			
			// swap bitmaps
			Bitmap temp = bmparray[size - 2];
			bmparray[size - 2] = bmparray[size - 3];
			bmparray[size - 3] = temp;
			
			// swap ID's
        	int oddID = ID[size - 2];
        	ID[size - 2] = ID[size - 3];
        	ID[size - 3] = oddID;
        	
		}
		// overwrite last tile with empty tile;
		bmparray[size - 1] = emp;
	}
}

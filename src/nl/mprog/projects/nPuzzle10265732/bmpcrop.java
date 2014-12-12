package nl.mprog.projects.nPuzzle10265732;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class bmpcrop {
	public static Bitmap[] bmparray(Context context, int imgch, int difficulty) {

	
	int[] images = {R.drawable.grid, R.drawable.cat, R.drawable.tree, R.drawable.mill };
	
	// create array for cropped bmps
	int size = (difficulty * difficulty);
	Bitmap[] crops = new Bitmap[size];
	
	
	// create bmp slices of size 1 / difficulty
	Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), images[imgch]);
	int height = (int)(bmp.getHeight() * (float)(1.0 / difficulty));
	int width = (int)(bmp.getWidth() * (float)(1.0 / difficulty));
	
	// create empty tile
	Bitmap emptyTile = BitmapFactory.decodeResource(context.getResources(), R.drawable.px);
	Bitmap emp = Bitmap.createScaledBitmap(emptyTile, width, height, false);
	
	// create cropped bitmaps
	int n = size;
	for (int i = 0; i < difficulty; i++) {
		for (int j = 0; j < difficulty; j++) {
			if (n == 1) {
				// if last tile, pass empty tile
				crops[size - 1] = emp;
			}
			else {
				crops[size - n] = Bitmap.createBitmap(bmp, width * j, height * i, width, height);
				n--;
			}
		}
	}
	return crops;
	}
}
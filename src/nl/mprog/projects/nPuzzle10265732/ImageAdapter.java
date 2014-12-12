package nl.mprog.projects.nPuzzle10265732;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	

	// load images
	Context c;
	int[] images = { R.drawable.grid, R.drawable.cat, R.drawable.tree, R.drawable.mill };
	
	
	public ImageAdapter(Context context) {
		c = context;
	}

	@Override
	public int getCount() {
		
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		return images[position];
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ImageView imageView;
		
		if (convertView == null) {
			imageView = new ImageView(c);
		} else {
			imageView = (ImageView) convertView;
		}
				
		imageView.setImageResource(images[position]);
		return imageView;
	}
	

}

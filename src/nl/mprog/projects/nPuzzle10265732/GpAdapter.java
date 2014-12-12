package nl.mprog.projects.nPuzzle10265732;


import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GpAdapter extends BaseAdapter {

	private Bitmap[] bmparray;
	private ImageView[] imageViews;
	
	public GpAdapter(Bitmap[] bmparray, ImageView[] imageViews) {
		
		// get data from GamePlay activity
		this.bmparray = bmparray;
		this.imageViews = imageViews;
	}

	@Override
	public int getCount() {
		
		return bmparray.length;
	}

	@Override
	public Object getItem(int position) {
		return bmparray[position];
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ImageView view = imageViews[position];
		view.setImageBitmap(bmparray[position]);
		System.out.println("tag[" + position + "] =  " + view.getTag());
		return view;
		
		/*als bovenstaande niet werkt
		if (convertView == null) {
			imageView = new ImageView(c);
		} else {
			imageView = (ImageView) convertView;
		}
				
		imageView.setImageBitmap(bmparray[position]);
		/*/
	}
	
	

}

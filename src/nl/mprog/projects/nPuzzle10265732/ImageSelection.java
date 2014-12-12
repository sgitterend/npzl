package nl.mprog.projects.nPuzzle10265732;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class ImageSelection extends ActionBarActivity {

	private int imageChosen;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selection);
        
        // create listview with images
        ImageAdapter adapter = new ImageAdapter(this);
        ListView listView = (ListView) findViewById(R.id.my_list);
        listView.setAdapter(adapter);

        // activate dialog when an image is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

               @Override
               public void onItemClick(AdapterView<?> parent, View view,
                       int position, long id) {
                   imageChosen = position;
                   showDialog();

               }
           });
        }
	public void goNext(int which, int imageChosen) {
		
		// let GamePlay and GpAdapter know the user chosen image and difficulty
		Intent go = new Intent(this, GamePlay.class);
		go.putExtra("diff", which);
		go.putExtra("image", imageChosen);
	    // Go to GamePlay
		startActivity(go); 
		}
	
	// let user select difficulty with a dialog
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageSelection.this);
        builder.setTitle(R.string.goselect)
               .setItems(R.array.dif, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                	   int dif = which + 1;
                	   if (dif != 0) {
                		   // make difficulty the size of the grid by adding 3
                		   goNext(which + 3, imageChosen);
                	   }
               }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    
}

package com.set.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.set.game.Card;
import com.set.game.Game;
import com.solo.set.R;



public class SinglePlayerScreen extends Activity {
	
	private Game game;
	private ArrayList<Integer> selectedCards;
	
	
	
	@SuppressLint("UseValueOf")
	public void selectCard(int position, View v, GridView parent){
		
		
		Integer pos = new Integer(position);
		Context context = getApplicationContext();
		
		//check if the card is already selected
		if (selectedCards.contains(pos)){
			selectedCards.remove(pos);	
			
			((ImageView) v).clearColorFilter();
			
		} 
		// if it is not selected
		else {
			
			selectedCards.add(pos);
			((ImageView) v).setColorFilter(Color.CYAN, PorterDuff.Mode.ADD);
			
			// if the selected card is the third card
			if (selectedCards.size() == 3) {	

				int[] selected = new int[] {selectedCards.get(0).intValue(),
						selectedCards.get(1).intValue(),
						selectedCards.get(2).intValue()};
				boolean valid_set = game.handleSelection(selected);
				selectedCards = new ArrayList<Integer>();
				
				if (valid_set){					
					game.player_scores[0]++;
					TextView score = (TextView) findViewById(R.id.playerscore);
					score.setText("Sets Found: "+Integer.toString(game.player_scores[0]));
				}
				
				parent.setAdapter(new CardAdapter(this, game.field));
				
				if (game.gameover){
					endGame();
					
				}

				//Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
				//toast.show();
			}
			
		}
		parent.setAdapter(new CardAdapter(this, game.field));
	  }
	 
	
	
	
	public class CardAdapter extends BaseAdapter{
	    private Context mContext;
	    private Card[] mField;

	    public CardAdapter(Context c, Card[] f) {
	        mContext = c;
	        mField = f;
	        
	    }

	    public int getCount() {
	        return mField.length;
	    }

	    public Object getItem(int position) {
	        return mField[position];
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	Card card = mField[position];

	    		
	    	ImageView imageView;
	    	
			//get dimensions
	    	int numRows = getCount() / 3;
			int height = parent.getHeight();
			int width = parent.getWidth();
			
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	            imageView = new ImageView(mContext);
	            
	            // if the full sized cards will fit on the screen
	            if (numRows* width *2 < 27 *height){	            
	            	imageView.setLayoutParams(new GridView.LayoutParams(width/3, width*2/9));
	            }
	            // else shrink the cards to fit
	            else{
	            	imageView.setLayoutParams(new GridView.LayoutParams(3*2*height/numRows, height/numRows));
	            }
	            	
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            //imageView.setPadding(8, 8, 8, 8);
	        } else {
	            imageView = (ImageView) convertView;
	        }

	        imageView.setImageResource(cardImageIds[card.color][card.shape][card.shading][card.number]);
	        return imageView;
	    }
	    

	    // references to our images
	    private int[][][][] cardImageIds = {
	    		{{{R.drawable.gde1,R.drawable.gde2,R.drawable.gde3},{R.drawable.gds1,R.drawable.gds2,R.drawable.gds3},{R.drawable.gdf1,R.drawable.gdf2,R.drawable.gdf3}},
	    			{{R.drawable.goe1,R.drawable.goe2,R.drawable.goe3},{R.drawable.gos1,R.drawable.gos2,R.drawable.gos3},{R.drawable.gof1,R.drawable.gof2,R.drawable.gof3}},
	    			{{R.drawable.gse1,R.drawable.gse2,R.drawable.gse3},{R.drawable.gss1,R.drawable.gss2,R.drawable.gss3},{R.drawable.gsf1,R.drawable.gsf2,R.drawable.gsf3}}},
	    		{{{R.drawable.rde1,R.drawable.rde2,R.drawable.rde3},{R.drawable.rds1,R.drawable.rds2,R.drawable.rds3},{R.drawable.rdf1,R.drawable.rdf2,R.drawable.rdf3}},
		    		{{R.drawable.roe1,R.drawable.roe2,R.drawable.roe3},{R.drawable.ros1,R.drawable.ros2,R.drawable.ros3},{R.drawable.rof1,R.drawable.rof2,R.drawable.rof3}},
		    		{{R.drawable.rse1,R.drawable.rse2,R.drawable.rse3},{R.drawable.rss1,R.drawable.rss2,R.drawable.rss3},{R.drawable.rsf1,R.drawable.rsf2,R.drawable.rsf3}}},
		    	{{{R.drawable.pde1,R.drawable.pde2,R.drawable.pde3},{R.drawable.pds1,R.drawable.pds2,R.drawable.pds3},{R.drawable.pdf1,R.drawable.pdf2,R.drawable.pdf3}},
			    	{{R.drawable.poe1,R.drawable.poe2,R.drawable.poe3},{R.drawable.pos1,R.drawable.pos2,R.drawable.pos3},{R.drawable.pof1,R.drawable.pof2,R.drawable.pof3}},
			    	{{R.drawable.pse1,R.drawable.pse2,R.drawable.pse3},{R.drawable.pss1,R.drawable.pss2,R.drawable.pss3},{R.drawable.psf1,R.drawable.psf2,R.drawable.psf3}}}};
		};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		newGame();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.play_screen, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.new_game:
	            raiseAlert("new");
	            return true;
	        case R.id.quit_game:
	            raiseAlert("quit");
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	// handles quit/new game verification from the menu and back button
	public void raiseAlert(String action){
		AlertDialog.Builder builder = new AlertDialog.Builder(SinglePlayerScreen.this);
	
		if (action.equals("quit")){
			builder.setMessage("Are you sure you wish to quit the game?")
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
		               quitGame();
					}
				});
		};
		if (action.equals("new")){
			builder.setMessage("Are you sure you wish to start a new game?")
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
		               newGame();
					}
				});
		};
		    
		
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User cancelled the dialog
		           }
		       });
		
		

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public void onBackPressed() {
        //start activity here
        raiseAlert("quit");  

    }
	
	public void newGame(){
		game = new Game(new int[] {1});
		game.startGame();
		selectedCards = new ArrayList<Integer>();
		
		setContentView(R.layout.single_player_screen);
		
		GridView fieldview = (GridView) findViewById(R.id.fieldview);
		// get rid of scrolling
		fieldview.setOnTouchListener(new OnTouchListener(){
		    @Override
		    public boolean onTouch(View v, MotionEvent event) {
		        if(event.getAction() == MotionEvent.ACTION_MOVE){
		            return true;
		        }
		        return false;
		    }
		});
		// handle card selection
		fieldview.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	selectCard(position, v, (GridView) parent);
	        }
	    });
		CardAdapter adapter = new CardAdapter(this, game.field);
		fieldview.setAdapter(adapter);		
		
		TextView score = (TextView) findViewById(R.id.playerscore);
		score.setText("Sets Found: 0");

	}
	
	
	public void quitGame(){
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
	
	public void help(View help){
		Context context = getApplicationContext();
		int[] set = game.findSet();
		String text = Integer.toString(set[0])+" "+Integer.toString(set[1])+" "+Integer.toString(set[2]);
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		toast.show();
		
	}
	
	public void endGame(){
		AlertDialog.Builder builder = new AlertDialog.Builder(SinglePlayerScreen.this);
		builder.setTitle("Game Over").setMessage("Would you like to play again?")
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
               newGame();
			}
		})
		.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
               quitGame();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	

	

}

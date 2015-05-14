package hu.ppke.simda.musiclibraryandroidonly;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowId;


/**
 * An activity representing a list of Songs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link SongDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link SongListFragment} and the item details
 * (if present) is a {@link SongDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link SongListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class SongListActivity extends Activity
        implements SongListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        
        setContentView(R.layout.activity_song_list);

        if (findViewById(R.id.song_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((SongListFragment) getFragmentManager()
                    .findFragmentById(R.id.song_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main_actionbar_items, menu);
		return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.main_actionbar_menu_add_song:
    		FragmentTransaction trans = getFragmentManager().beginTransaction();
    		AddSongDialogFragment dialogFragment = new AddSongDialogFragment();
    		dialogFragment.show(trans, "dialog_fragment");
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    };

    /**
     * Callback method from {@link SongListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(int id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putInt(SongDetailFragment.ARG_ITEM_ID, id);
            Fragment fragment = new SongDetailFragment();
            fragment.setArguments(arguments);
            FragmentTransaction trans = getFragmentManager().beginTransaction();
            trans.replace(R.id.song_detail_container, fragment);
            trans.addToBackStack(null);
            trans.commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, SongDetailActivity.class);
            detailIntent.putExtra(SongDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}

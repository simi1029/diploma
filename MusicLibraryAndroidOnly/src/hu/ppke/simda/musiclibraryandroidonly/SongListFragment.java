package hu.ppke.simda.musiclibraryandroidonly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import hu.ppke.simda.musiclibraryandroidonly.modell.DataProvider;
import hu.ppke.simda.musiclibraryandroidonly.modell.Song;

/**
 * A list fragment representing a list of Songs. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link SongDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class SongListFragment extends ListFragment implements OnItemLongClickListener {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    
    private SongListAdapter mAdapter;
	private ActionMode mActionMode;
	int actionModePosition = -1;
    
    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			// Do Nothing
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			/*
			 * Whenever the action mode is dismissed, clear all chosen items
			 */
			mAdapter.clearChoices();
			mAdapter.notifyDataSetChanged();
			mActionMode = null;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			if (mActionMode != null)
				return false;
			mActionMode = mode;
			mode.getMenuInflater().inflate(R.menu.action_mode_delete_items, menu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			if (item.getItemId() == R.id.action_mode_delete_items) {
				if (actionModePosition == -1)
					return false;

				SongListAdapter adapter = (SongListAdapter) getListAdapter();
				List<Song> songs = dp.getSongs ();
				Set<Integer> chosenItems = adapter.getChosenItems ();

				List<Song> toDelete = new ArrayList<Song>();

				for (Iterator<Integer> e = chosenItems.iterator(); e.hasNext();) { toDelete.add (songs.get(e.next())); }
				songs.removeAll(toDelete);

				adapter.clearChoices();
				//ListAdapter.Dispose ();
				setListAdapter(new SongListAdapter (getActivity(), dp.getSongs()));

				actionModePosition = -1;

				mode.finish();

				//Toast.MakeText(Activity, String.Format("{0} deleted.", song.Title), ToastLength.Short).Show();

				return true;
			}
			return false;
		}

	};

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         * @param id
         */
        public void onItemSelected(int id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int id) {
        }
    };

    private DataProvider dp = DataProvider.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new SongListAdapter(this.getActivity(), dp.getSongs());
        setListAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        getListView().setOnItemLongClickListener(this);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        
        SongListAdapter mAdapter = (SongListAdapter) getListView().getAdapter();
        
        mAdapter.setOpenedItem(position);
		mAdapter.clearChoices();
		if (mActionMode != null) {
			mActionMode.finish();
		}
		mAdapter.notifyDataSetChanged();

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		SongListAdapter mAdapter = (SongListAdapter) getListView().getAdapter();
		mAdapter.toggleItem(position);
		updateActionMode();
		return true;
	}
	
	private void updateActionMode() {
		/*
		 * Start the action mode if it isn't already started
		 */
		if (mActionMode == null) {
			mActionMode = getListView().startActionMode(actionModeCallback);
		}
		
		actionModePosition = 1;

		/*
		 * Update the title of the CAB to indicate number of items chosen
		 */
		mActionMode.setTitle(String.format("%d chosen",
				mAdapter.getChosenItemsCount()));
		mAdapter.notifyDataSetChanged();
	}
}

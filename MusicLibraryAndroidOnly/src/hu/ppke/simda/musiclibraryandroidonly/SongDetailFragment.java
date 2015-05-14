package hu.ppke.simda.musiclibraryandroidonly;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import hu.ppke.simda.musiclibraryandroidonly.modell.DataProvider;
import hu.ppke.simda.musiclibraryandroidonly.modell.Song;

/**
 * A fragment representing a single Song detail screen.
 * This fragment is either contained in a {@link SongListActivity}
 * in two-pane mode (on tablets) or a {@link SongDetailActivity}
 * on handsets.
 */
public class SongDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment represents.
     */
    public static final String ARG_ITEM_ID = "current_song_id";

    /**
     * The content this fragment is presenting.
     */
    private Song mItem;
    private DataProvider dp = DataProvider.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongDetailFragment() {
    }

    public static Fragment NewInstance(int songId) {
        Bundle arguments = new Bundle();
        Fragment detailsFrag = new SongDetailFragment();
        arguments.putInt("current_song_id", songId);
        detailsFrag.setArguments(arguments);
        return detailsFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = dp.getSongs().get(ShowSongId());
        }
    }

    public int ShowSongId() {
        return this.getArguments().getInt("current_song_id", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container==null) {
            return null;
        }

        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);

        // Show the content.
        if (mItem != null) {
            ((ImageView) rootView.findViewById(R.id.album_image)).setImageResource(mItem.getImg());
            ((TextView) rootView.findViewById(R.id.textview_album_artist)).setText(mItem.getArtist());
            ((TextView) rootView.findViewById(R.id.textview_song_title)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.textview_data_genre)).setText(mItem.getGenre());
            ((TextView) rootView.findViewById(R.id.textview_data_date)).setText(mItem.getDate());
            ((TextView) rootView.findViewById(R.id.textview_data_length)).setText(mItem.getLength());
            ((TextView) rootView.findViewById(R.id.textview_data_comment)).setText(mItem.getComment());

            final SongDetailFragment detail = this;
            Button editButton = (Button) rootView.findViewById(R.id.edit_details_button);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment edit = SongEditFragment.NewInstance(ShowSongId(), detail);
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction trans = manager.beginTransaction();
                    trans.replace(R.id.song_detail_container, edit);
                    trans.addToBackStack("detail");
                    trans.commit();
                }
            });
        }

        return rootView;
    }
}

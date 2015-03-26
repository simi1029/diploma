package hu.ppke.simda.musiclibraryandroidonly;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import hu.ppke.simda.musiclibraryandroidonly.modell.DataProvider;
import hu.ppke.simda.musiclibraryandroidonly.modell.Song;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongEditFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "current_song_id";

    /**
     * The content this fragment is presenting.
     */
    private Song mItem;
    private static DataProvider dp; //= DataProvider.getInstance();
    private static SongDetailFragment Detail;


    public static Fragment NewInstance(int songId, SongDetailFragment detail) {
        Bundle arguments = new Bundle();
        Fragment editFrag = new SongEditFragment();
        editFrag.setArguments(arguments);
        editFrag.getArguments().putInt("current_song_id", songId);
        Detail = detail;
        dp = DataProvider.getInstance();
        return editFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
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

        View rootView = inflater.inflate(R.layout.fragment_song_edit, container, false);
        //mItem = dp.getSongs().get(getArguments().getInt(ARG_ITEM_ID));
        //Toast.makeText(getActivity(), mItem.toString(), Toast.LENGTH_SHORT).show();

        // Show the content.
        if (mItem != null) {
            ((ImageView) rootView.findViewById(R.id.album_image)).setImageResource(mItem.getImg());
            final EditText artist = ((EditText) rootView.findViewById(R.id.edittext_album_artist));
            final EditText title = ((EditText) rootView.findViewById(R.id.edittext_song_title));
            final EditText genre = ((EditText) rootView.findViewById(R.id.edittext_data_genre));
            final EditText date = ((EditText) rootView.findViewById(R.id.edittext_data_date));
            final EditText length = ((EditText) rootView.findViewById(R.id.edittext_data_length));
            final EditText comment = ((EditText) rootView.findViewById(R.id.edittext_data_comment));

            artist.setText(mItem.getArtist());
            title.setText(mItem.getTitle());
            genre.setText(mItem.getGenre());
            date.setText(mItem.getDate());
            length.setText(mItem.getLength());
            comment.setText(mItem.getComment());

            Button saveButton = (Button) rootView.findViewById(R.id.save_details_button);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItem.modify(artist.getText().toString(), title.getText().toString(), length.getText().toString(), genre.getText().toString(), date.getText().toString(), comment.getText().toString());
                    getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, Detail)
                            .commit();
                }
            });

            Button cancelButton = (Button) rootView.findViewById(R.id.cancel_button);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, Detail)
                            .commit();
                }
            });
        }

        return rootView;
    }


}

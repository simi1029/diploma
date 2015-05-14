package hu.ppke.simda.musiclibraryandroidonly;

import hu.ppke.simda.musiclibraryandroidonly.modell.DataProvider;
import hu.ppke.simda.musiclibraryandroidonly.modell.Song;

import java.util.Random;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddSongDialogFragment extends DialogFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		View view = inflater.inflate(R.layout.dialog_fragment_add_song, container, false);
		final EditText artist = (EditText)view.findViewById(R.id.dialog_edittext_artist);
		final EditText title = (EditText)view.findViewById(R.id.dialog_edittext_title);
		final EditText length = (EditText)view.findViewById(R.id.dialog_edittext_length);
		
		Button addButton = (Button)view.findViewById(R.id.dialog_add_button);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Random r = new Random();
				int number = r.nextInt(2)+1;
				DataProvider.Songs.add(new Song(DataProvider.pictures[number], artist.getText().toString(), title.getText().toString(), length.getText().toString()));
				getDialog().dismiss();
			}
		});
		
		Button cancelButton = (Button)view.findViewById(R.id.dialog_dismiss_button);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		});
		return view;
	}

}

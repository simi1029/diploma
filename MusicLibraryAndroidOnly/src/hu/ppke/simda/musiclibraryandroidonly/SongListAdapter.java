package hu.ppke.simda.musiclibraryandroidonly;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.ppke.simda.musiclibraryandroidonly.modell.Song;

/**
 * Created by David on 2015.03.04.
 */
public class SongListAdapter extends BaseAdapter {
    private List<Song> songs;
    Activity context;

    private Set<Integer> chosenItems = new HashSet<Integer>();
    private int openedItem = -1;

    public SongListAdapter(Activity context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Song getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /* Functions to implement the Hybrid Choise functionality */
    public void setItemChosen(int position, boolean chosen) {
        if (!chosen && isItemChosen(position)) {
            chosenItems.remove(position);
        } else if (chosen && !isItemChosen(position)) {
            chosenItems.add(position);
        }
    }

    public boolean isItemChosen(int position) {
        return chosenItems.contains(position);
    }

    public Set<Integer> getChosenItems() {
        return chosenItems;
    }

    public void setOpenedItem(int position) {
        this.openedItem = position;
    }

    public int getOpenedItem() {
        return this.openedItem;
    }

    public boolean isItemOpened(int position) {
        return this.openedItem == position;
    }

    public void clearChoices() {
        chosenItems.clear();
    }

    public void toggleItem(int position) {
        if (isItemChosen(position)) {
            chosenItems.remove(position);
        } else {
            chosenItems.add(position);
        }
    }

    public int getChosenItemsCount() {
        return this.chosenItems.size();
    }

    public void setViewAsOpened(View v) {
        v.setBackgroundColor(Color.rgb(10,180,240));
    }

    public void setViewAsChosen(View v) {
        v.setBackgroundColor(Color.rgb(235,120,80));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView; // for reuse of view
        if (view == null) {
            view = context.getLayoutInflater().inflate(R.layout.song_list_item, null);
        }

        // get UI elements
        TextView titleText = (TextView)view.findViewById(R.id.item_title);
        TextView lengthText = (TextView)view.findViewById(R.id.item_length);
        ImageView img = (ImageView)view.findViewById(R.id.item_image);
        // set the appropriate data to show
        Song s = songs.get(position);
        titleText.setText(s.getTitle());
        lengthText.setText(s.getLength());
        img.setImageResource(s.getImg());

        view.setBackgroundResource(0);
        if (isItemOpened(position)) {
            setViewAsOpened(view);
        }
        if (isItemChosen(position)) {
            setViewAsChosen(view);
        }

        return view;
    }
}

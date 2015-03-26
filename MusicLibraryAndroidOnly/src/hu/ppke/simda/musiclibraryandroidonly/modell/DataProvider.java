package hu.ppke.simda.musiclibraryandroidonly.modell;

import java.util.ArrayList;
import java.util.List;

import hu.ppke.simda.musiclibraryandroidonly.R;

/**
 * Created by David on 2015.03.04..
 */
public class DataProvider {
    private static DataProvider instance = null;
    protected DataProvider() {
        Songs.add (new Song (R.drawable.ic_flac, "Porcupine Tree", "My Ashes", "5:07"));
        Songs.add (new Song (R.drawable.ic_wav, "Red", "Faceless", "3:24"));
        Songs.add (new Song (R.drawable.ic_mp3, "Evanescence", "Away From Me", "3:31"));
        Songs.add (new Song (R.drawable.ic_flac, "Porcupine Tree", "Nil Recurring", "6:08"));
        Songs.add (new Song (R.drawable.ic_wav, "Nightwish", "White Night Fantasy", "4:04"));
        Songs.add (new Song (R.drawable.ic_wav, "Dream Theater", "Erotomania", "6:44"));
        Songs.add (new Song (R.drawable.ic_flac, "Katatonia", "Teargas", "3:23"));
        Songs.add (new Song (R.drawable.ic_flac, "Bear McCreary", "Boomer Flees", "1:17"));
        Songs.add (new Song (R.drawable.ic_mp3, "Porcupine Tree", "Sentimental", "5:27"));
        Songs.add (new Song (R.drawable.ic_flac, "Janicsák Veca", "Fighter", "2:31"));
    }

    public static DataProvider getInstance() {
        if (instance==null) {
            instance = new DataProvider();
        }

        return instance;
    }

    public static List<Song> Songs = new ArrayList<Song>();
    public static int[] pictures = new int[] {R.drawable.ic_flac, R.drawable.ic_wav, R.drawable.ic_mp3};
    public List<Song> getSongs() {
        return Songs;
    }
}

package nested_classes.inner_classes.part3_exercise;

import java.util.ArrayList;
import java.util.LinkedList;

public class Album {
    // write code here
    private String name;
    private String artist;
    private SongList songs;


    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new SongList();
    }

    public boolean addSong(String title, double duration) {

        if (songs.findSong(title) == null) {
            songs.add(new Song(title, duration));
            return true;
        }
        return false;
    }


    public boolean addToPlayList(int trackNumber, LinkedList<Song> playList) {

        Song checkedSong = songs.findSong(trackNumber);
        if (checkedSong != null) {
            playList.add(checkedSong);
            return true;
        }
        System.out.println("This album does not have a track " + trackNumber);
        return false;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playList) {

        Song checkedSong = songs.findSong(title);
        if (checkedSong != null) {
            playList.add(checkedSong);
            return true;
        }
        return false;
    }

    public static class SongList{
        private ArrayList<Song> songs;

        private SongList(){
            songs = new ArrayList<Song>();
        }

        private boolean add(Song newSong){
            if (!songs.contains(newSong)) {
                songs.add(newSong);
                return true;
            }

            return false;
        }

        private Song findSong(String title) {

            for (Song checkedSong : songs) {
                if (checkedSong.getTitle().equals(title)) {
                    return checkedSong;
                }
            }
            return null;
        }


        private Song findSong(int trackNumber) {
            int index = trackNumber - 1;
            if ((index >= 0) && (index <= songs.size())) {
                return songs.get(index);
            }
            return null;
        }

    }
}

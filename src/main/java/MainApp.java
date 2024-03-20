import jukebox.playlist.AudioPlayer;
import jukebox.ui.JukeboxUI;
import jukebox.service.Jukebox;
import jukebox.db.DBConnection;
import java.sql.Connection;

public class MainApp {
    public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
        if (connection != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed.");
            return;
        }

        AudioPlayer audioPlayer = new AudioPlayer();
        Jukebox jukebox = new Jukebox(connection, audioPlayer);
        JukeboxUI jukeboxUI = new JukeboxUI(jukebox, audioPlayer);
        jukeboxUI.showMainMenu();
    }
}

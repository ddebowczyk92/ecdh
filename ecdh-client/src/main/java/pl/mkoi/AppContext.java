package pl.mkoi;

/**
 * Created by DominikD on 2016-01-17.
 */
public class AppContext {
    private static AppContext ourInstance = new AppContext();



    public static synchronized AppContext getInstance() {
        return ourInstance;
    }

    private AppContext() {
    }
}

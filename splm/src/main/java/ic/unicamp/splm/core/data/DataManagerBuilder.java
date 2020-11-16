package ic.unicamp.splm.core.data;

public class DataManagerBuilder {
    private static DataManager manager = null;

    private DataManagerBuilder() {
    }

    public static synchronized DataManager getSingletonInstance() {
        if (manager == null) manager = new DataManager();
        return manager;
    }
}

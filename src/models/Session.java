package models;

public class Session {
    private static int startupId;

    public static void setStartupId(int id) {
        startupId = id;
    }

    public static int getStartupId() {
        return startupId;
    }
}

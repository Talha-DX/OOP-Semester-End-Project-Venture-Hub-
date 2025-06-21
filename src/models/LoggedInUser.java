package models;

public class LoggedInUser {
    private static int startupId;

    public static void setStartupId(int id) {
        startupId = id;
    }

    public static int getStartupId() {
        return startupId;
    }
}

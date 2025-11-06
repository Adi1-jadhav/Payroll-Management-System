package Frontend;

public class RoleManager {
    public static boolean canEdit(String role) {
        return role.equalsIgnoreCase("HR");
    }

    public static boolean canView(String role) {
        return role.equalsIgnoreCase("Finance") || canEdit(role);
    }
}


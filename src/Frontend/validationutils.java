package Frontend;
import Backend.EmployeeDAO;


public class validationutils {
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static int parseInt(String s) throws NumberFormatException {
        return Integer.parseInt(s.trim());
    }

    public static double parseDouble(String s) throws NumberFormatException {
        return Double.parseDouble(s.trim());
    }

    public static boolean isDuplicateId(int id, EmployeeDAO dao) {
        return dao.getEmployee(id) != null;
    }
}


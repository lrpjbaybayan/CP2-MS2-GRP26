public class Feature4 {
    public static void main(String[] args) {
        String updateQuery = "UPDATE users SET email = 'new_email@example.com', status = 'active' WHERE user_id = 5;";
        String deleteQuery = "DELETE FROM employees WHERE employee_id = 1042;";
        System.out.println(updateQuery);
        System.out.println(deleteQuery);
    }
}
    

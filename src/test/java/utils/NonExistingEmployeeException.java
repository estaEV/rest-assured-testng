package utils;

public class NonExistingEmployeeException extends Exception {
    public NonExistingEmployeeException(String msg) {
        super(msg);
        System.out.println("Currently we are inside utils.NonExistingEmployeeException().");
    }
}

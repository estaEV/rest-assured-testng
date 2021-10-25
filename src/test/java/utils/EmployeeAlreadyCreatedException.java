package utils;

public class EmployeeAlreadyCreatedException extends Exception {
    public EmployeeAlreadyCreatedException (String msg) {
        super(msg);
        System.out.println("Currently we are inside utils.EmployeeAlreadyCreatedException().");
    }
}

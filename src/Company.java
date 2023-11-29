import java.util.List;

public interface Company {
    public /*@ pure @*/ List<Employee> getEmployees();
    /*@
    @ requires employee != null;
    @ requires !employee.isEmployed();
    @ requires !getEmployees().contains(employee);
    @ ensures employee.isEmployed();
    @ ensures getEmployees().contains(employee);
    @ ensures getEmployees().size() == \old(getEmployees().size()) + 1;
    @*/
    public void hire(Employee employee);

    /*@
    @ requires employee != null;
    @ requires employee.isEmployed();
    @ requires getEmployees().contains(employee);
    @ ensures !employee.isEmployed();
    @ ensures !getEmployees().contains(employee);
    @ ensures getEmployees().size() == \old(getEmployees().size()) - 1;
    @*/
    public void fire(Employee employee);

    // declare getEmployees() and Employee::isEmployed() as pure.
}

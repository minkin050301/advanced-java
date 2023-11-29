import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConcreteCompany implements Company {
    private List<Employee> employees = new ArrayList<>();
    @Override
    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    @Override
    public void hire(Employee employee) {
        assert employee != null;
        assert !employee.isEmployed();
        assert !employees.contains(employee);

        List<Employee> oldEmployees = new ArrayList<>(employees);
        employee.hire();
        employees.add(employee);

        assert employee.isEmployed();
        assert employees.contains(employee);
        assert employees.size() == oldEmployees.size() + 1;
    }

    @Override
    public void fire(Employee employee) {
        assert employee != null;
        assert employee.isEmployed();
        assert employees.contains(employee);

        List<Employee> oldEmployees = new ArrayList<>(employees);
        employee.fire();
        employees.remove(employee);

        assert !employee.isEmployed();
        assert !employees.contains(employee);
        assert employees.size() == oldEmployees.size() - 1;
    }
}

public class Employee {
    private boolean isEmployed;
    public Employee() {
        this.isEmployed = false;
    }
    protected void hire() {
        this.isEmployed = true;
    }
    protected void fire() {
        this.isEmployed = false;
    }
    public boolean isEmployed() {
        return isEmployed;
    }
}

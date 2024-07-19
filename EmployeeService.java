package org.example;

import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmployeeService {
    static String EMP_KEY = "Employee";
    private final Jedis jedis;
    EmployeeService employeeService;

    public EmployeeService(Jedis jedis) {
        this.jedis = jedis;
    }


    public void addEmployee(Employee employee) {
        String key = EMP_KEY + ":" + employee.getId();

        jedis.hset(key, "id", String.valueOf(employee.getId()));
        jedis.hset(key, "name", employee.getName());
        jedis.hset(key, "age", employee.getAge());
        jedis.hset(key, "department", employee.getDepartment());
        jedis.hset(key, "position", employee.getPosition());
        jedis.hset(key, "salary", String.valueOf(employee.getSalary()));
        jedis.hset(key, "hireDate", employee.getHireDate());

    }

    public Employee getEmployee(String id) {

        Map<String, String> empMap = jedis.hgetAll(EMP_KEY + ":" + id);

        return new Employee(empMap.get("id"), empMap.get("name"), empMap.get("age"), empMap.get("department"), empMap.get("position"), empMap.get("salary"), empMap.get("hireDate"));


    }

    public Employee updateEmployee(Employee updatedEmployee) {
        Map<String, String> empMap = jedis.hgetAll(EMP_KEY + ":" + updatedEmployee.getId());

        return new Employee(empMap.get("id"), empMap.get("name"), empMap.get("age"), empMap.get("department"), empMap.get("position"), empMap.get("salary"), empMap.get("hireDate"));
    }

    public void deleteEmployee(String id) {
        jedis.del(EMP_KEY + ":" + id);
    }

    public Set<Employee> getAllEmployee() {
        Set<Employee> employeeSet = new HashSet<>();

        Set<String> keys = jedis.keys("Employee:*");
        for (String key : keys) {
            Map<String, String> empMap = jedis.hgetAll(key);
            String id = empMap.get("id");
            Employee employee = getEmployee(id);
            employeeSet.add(employee);

        }
        return employeeSet;
    }
}

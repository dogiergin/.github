package org.example;

import redis.clients.jedis.Jedis;

import java.util.Scanner;
import java.util.Set;


public class App {
    static RedisConfig redisConfig = new RedisConfig();

    static Jedis jedis = redisConfig.getJedis();
    static EmployeeService employeeService = new EmployeeService(jedis);
    static String new_name, new_age, new_department, new_position, new_hireDate, new_id, new_salary;
    static String updated_name, updated_age, updated_department, updated_position, updated_hireDate, updated_id, updated_salary;
    static Jedis tempJedis = new Jedis("localhost", 6379);
    static NotificationService notificationService = new NotificationService(tempJedis);

    //Notification Service
    public static void main(String[] args) {

        notificationService.subscribeToChannel();
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println();
            System.out.println("###################");
            System.out.println("Selection 1 : Add Employee");
            System.out.println("Selection 2 : View Employee");
            System.out.println("Selection 3 : Update Employee");
            System.out.println("Selection 4 : Delete Employee");
            System.out.println("Selection 5 : list all Employees");
            System.out.println("Selection 6 : Exit the Program");
            System.out.println("###################");
            System.out.println("Enter your Selection : ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    System.out.println("Adding New Employee ");

                    System.out.println("Id :");
                    new_id = scanner.next();

                    System.out.println("Name :");
                    new_name = scanner.next();

                    System.out.println("Age");
                    new_age = scanner.next();

                    System.out.println("Department");
                    new_department = scanner.next();

                    System.out.println("Position");
                    new_position = scanner.next();

                    System.out.println("Salary");
                    new_salary = scanner.next();

                    System.out.println("Hire Date");
                    new_hireDate = scanner.next();

                    Employee newEmployee = new Employee(new_id, new_name, new_age, new_department, new_position, new_salary, new_hireDate);


                    employeeService.addEmployee(newEmployee);


                    jedis.publish("EmpChannel", "Employee Added , ID : " + newEmployee.getId());
                    break;
                case 2:
                    System.out.println("View employee");
                    System.out.println("Enter the Id of Employee :");
                    new_id = scanner.next();
                    System.out.println(employeeService.getEmployee((new_id)));
                    jedis.publish("EmpChannel", "Employee viewed , ID : " + employeeService.getEmployee(new_id));
                    break;
                case 3:

                    System.out.println("Updating New Employee ");

                    System.out.println("Id :");
                    updated_id = scanner.next();

                    System.out.println("Name :");
                    updated_name = scanner.next();

                    System.out.println("Age");
                    updated_age = scanner.next();

                    System.out.println("Department");
                    updated_department = scanner.next();

                    System.out.println("Position");
                    updated_position = scanner.next();

                    System.out.println("Salary");
                    updated_salary = scanner.next();

                    System.out.println("Hire Date");
                    updated_hireDate = scanner.next();

                    Employee updatedEmp = new Employee(updated_id, updated_name, updated_age, updated_department, updated_position, updated_salary, updated_hireDate);

                    employeeService.addEmployee(updatedEmp);
                    jedis.publish("EmpChannel", "Employee Updated , ID : " + updatedEmp.getId());
                    break;

                case 4:

                    System.out.println("delete employee");
                    System.out.println("Id :");
                    updated_id = scanner.next();
                    employeeService.deleteEmployee(updated_id);
                    jedis.publish("EmpChannel", "Employee deleted , ID : " + updated_id);
                    break;
                case 5:

                    System.out.println("list all employees");
                    Set<Employee> allEmp = employeeService.getAllEmployee();
                    for (Employee employee : allEmp) {
                        System.out.println(employee.toString());
                    }
                    jedis.publish("EmpChannel", "Employees Listed");
                    break;


                case 6:
                    jedis.publish("EmpChannel", "logged out");
                    System.exit(0);
            }
        }
    }
}

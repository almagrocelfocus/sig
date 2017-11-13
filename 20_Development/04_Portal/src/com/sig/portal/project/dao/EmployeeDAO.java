package com.sig.portal.project.dao;

import java.util.List;

import com.sig.portal.project.entities.Employee;

/**
 *
 * @author Home
 */
public interface EmployeeDAO {

    public void addEmployee(Employee employee);

    public void removeEmployee(Integer employeeId);

    public Employee getEmployee(final Integer employeeId);

    public List<Employee> listEmployees();

}

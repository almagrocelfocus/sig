package com.sig.portal.project.services;

import java.util.List;

import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowired;
import com.bea.core.repackaged.springframework.stereotype.Component;
import com.bea.core.repackaged.springframework.stereotype.Service;
import com.sig.portal.project.dao.EmployeeDAO;
import com.sig.portal.project.entities.Employee;

/**
 *
 * @author Home
 */
@Service("employeeService")
@Component
//@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO dao;

    @Override
    public void addEmployee(Employee employee) {
        dao.addEmployee(employee);
    }

    @Override
    public void removeEmployee(Integer employeeId) {
        dao.removeEmployee(employeeId);
    }

    @Override
    public Employee getEmployee(Integer employeeId) {
        return dao.getEmployee(employeeId);
    }

    @Override
    public List<Employee> listEmployees() {
        return dao.listEmployees();
    }

}

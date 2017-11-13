package com.sig.portal.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.swing.tree.RowMapper;

import org.springframework.jdbc.core.JdbcTemplate;

import com.bea.core.repackaged.springframework.beans.factory.BeanCreationException;
import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowired;
import com.bea.core.repackaged.springframework.stereotype.Repository;
import com.sig.portal.project.entities.Employee;

@Repository("emlpoyeeDAO")
public class JDBCEmployeeDAO implements EmployeeDAO {
//https://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html

    @Autowired
    private DataSource mySqlDataSource;

    private JdbcTemplate jdbcTemplate;

    public JDBCEmployeeDAO() {
    }

    @PostConstruct
    public void init() throws Exception {
        if (mySqlDataSource == null) {
            throw new BeanCreationException("Must set mySqlDataSource on " + this.getClass().getName());
        }
        this.jdbcTemplate = new JdbcTemplate(mySqlDataSource);
    }

    @Override
    public void addEmployee(Employee employee) {
        String query = "INSERT INTO EMPLOYEE (NAME, ROLE) VALUES(?,?)";
        Object[] args = new Object[]{employee.getName(), employee.getRole()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            System.out.println("Employee saved with id : " + employee.getId());
        }
    }

    @Override
    public void removeEmployee(Integer employeeId) {
        String query = "DELETE FROM EMPLOYEE WHERE ID = ?";

        Object[] args = new Object[]{employeeId};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            System.out.println("Employee with id : " + employeeId + "deleted successfully");
        }

    }

    @Override
    public Employee getEmployee(final Integer employeeId) {
        String query = "SELECT ID, NAME, ROLE FROM EMPLOYEE WHERE ID = ?";

        Object[] args = new Object[]{employeeId};
        Employee emp = jdbcTemplate.queryForObject(query, new Object[]{employeeId}, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                return employee;
            }
        });
        return emp;
    }

    @Override
    public List<Employee> listEmployees() {
        String query = "SELECT * FROM EMPLOYEE";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<Employee> employees = new ArrayList<Employee>();
        for (Map row : rows) {
            Employee employee = new Employee();
            Integer id = (Integer) row.get("ID");
            employee.setId(id);
            employee.setName((String) row.get("NAME"));
            employee.setRole((String) row.get("ROLE"));
            employees.add(employee);
        }
        return employees;
    }

}

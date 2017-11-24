package com.sig.portal.project.dao;

import java.sql.Date;
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
import com.sig.portal.project.entities.Log;

@Repository("logDAO")
@SuppressWarnings("rawtypes")
public class JDBCLogDAO implements LogDAO {

    @Autowired
    private DataSource oracleDataSource;

    private JdbcTemplate jdbcTemplate;

    public JDBCLogDAO() {
    }

    @PostConstruct
    public void init() throws Exception {
        if (oracleDataSource == null) {
            throw new BeanCreationException("Must set oracleDataSource on " + this.getClass().getName());
        }
        this.jdbcTemplate = new JdbcTemplate(oracleDataSource);
    }

//    @Override
//    public void addEmployee(Log log) {
//        String query = "INSERT INTO EMPLOYEE (NAME, ROLE) VALUES(?,?)";
//        Object[] args = new Object[]{log.getName(), log.getRole()};
//        int out = jdbcTemplate.update(query, args);
//        if (out != 0) {
//            System.out.println("Employee saved with id : " + log.getId());
//        }
//    }
//
//    @Override
//    public void removeEmployee(Integer employeeId) {
//        String query = "DELETE FROM EMPLOYEE WHERE ID = ?";
//
//        Object[] args = new Object[]{employeeId};
//        int out = jdbcTemplate.update(query, args);
//        if (out != 0) {
//            System.out.println("Employee with id : " + employeeId + "deleted successfully");
//        }
//
//    }

    
	@Override
    public List<Log> listLogs() {
        String query = "SELECT * FROM LOG WHERE ROWNUM < 100 ORDER BY CREATION_DATE DESC";

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
        List<Log> logs = new ArrayList<Log>();
        for (Map row : rows) {
            Log log = new Log();
            log.setId((String)row.get("ID"));
            log.setRequestId((String)row.get("REQUEST_ID"));
            log.setCorralationId((String)row.get("CORRELATION_ID"));
            log.setDomain((String)row.get("DOMAIN"));
            log.setCategory((String)row.get("CATEGORY"));
            log.setTarget((String)row.get("TARGET"));
            log.setService((String)row.get("SERVICE"));
            log.setOperation((String)row.get("OPERATION"));
            log.setVersion((String)row.get("VERSION"));
            log.setSource((String)row.get("SOURCE"));
            log.setTargetEndPoint((String)row.get("TARGET_ENDPOINT"));
            log.setLogLevel((String)row.get("LOG_LEVEL"));
            log.setTask((String)row.get("TASK"));
            log.setUserName((String)row.get("USERNAME"));
            log.setTimeStamp((Date)row.get("TIMESTAMP"));
            log.setCreationDate((Date)row.get("CREATION_DATE"));
            log.setPayload((String)row.get("PAYLOAD"));
            log.setStatusCode((String)row.get("STATUS_CODE"));
            log.setStatusMessage((String)row.get("STATUS_MESSAGE"));
            log.setEngine((String)row.get("ENGINE"));
            
            
            logs.add(log);
        }
        return logs;
    }

}

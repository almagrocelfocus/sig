package com.sig.portal.project.services;

import java.util.List;

import com.bea.core.repackaged.springframework.beans.factory.annotation.Autowired;
import com.bea.core.repackaged.springframework.stereotype.Component;
import com.bea.core.repackaged.springframework.stereotype.Service;
import com.sig.portal.project.dao.LogDAO;
import com.sig.portal.project.entities.Log;

/**
 *
 * @author Home
 */
@Service("logService")
@Component
//@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDAO dao;

//    @Override
//    public void addLog(Log employee) {
//        dao.addEmployee(employee);
//    }
//
//    @Override
//    public void removeLog(Integer employeeId) {
//        dao.removeEmployee(employeeId);
//    }
//
//    @Override
//    public Log getLog(Integer employeeId) {
//        return dao.getLog(employeeId);
//    }

    @Override
    public List<Log> listLog() {
        return dao.listLogs();
    }

}

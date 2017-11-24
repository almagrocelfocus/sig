package com.sig.portal.project.dao;

import java.util.List;

import com.sig.portal.project.entities.Log;

/**
 *
 * @author Home
 */
public interface LogDAO {

//    public void addEmployee(Log log);
//
//    public void removeEmployee(Integer logId);
//
//    public Log getLog(final Integer logId);

    public List<Log> listLogs();

}

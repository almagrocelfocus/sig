package com.vodafone.sobe.logger.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "LOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogEntity.findAll", query = "SELECT l FROM LogEntity l"),
    @NamedQuery(name = "LogEntity.findById", query = "SELECT l FROM LogEntity l WHERE l.id = :id"),
    @NamedQuery(name = "LogEntity.findByMessageId", query = "SELECT l FROM LogEntity l WHERE l.messageId = :messageId"),
    @NamedQuery(name = "LogEntity.findByServiceName", query = "SELECT l FROM LogEntity l WHERE l.serviceName = :serviceName"),
    @NamedQuery(name = "LogEntity.findByLogLevel", query = "SELECT l FROM LogEntity l WHERE l.logLevel = :logLevel"),
    @NamedQuery(name = "LogEntity.findByTask", query = "SELECT l FROM LogEntity l WHERE l.task = :task"),
    @NamedQuery(name = "LogEntity.findByCreatedBy", query = "SELECT l FROM LogEntity l WHERE l.createdBy = :createdBy"),
    @NamedQuery(name = "LogEntity.findByTimestamp", query = "SELECT l FROM LogEntity l WHERE l.timestamp = :timestamp"),
    @NamedQuery(name = "LogEntity.findByStatusCode", query = "SELECT l FROM LogEntity l WHERE l.statusCode = :statusCode"),
    @NamedQuery(name = "LogEntity.findByStatusMessage", query = "SELECT l FROM LogEntity l WHERE l.statusMessage = :statusMessage"),
    @NamedQuery(name = "LogEntity.findByEngine", query = "SELECT l FROM LogEntity l WHERE l.engine = :engine")})
public class LogEntity implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="LogIdGen")
	@TableGenerator(name = "LogIdGen", table = "SEQUENCE_GENERATOR",
			pkColumnName = "ID", valueColumnName = "SEQUENCE_VALUE", pkColumnValue= "LOG_ID",
			allocationSize=1, initialValue=1)
    @Column(name = "ID")
    private String id;
    
	@Size(max = 250)
    @Column(name = "MESSAGE_ID")
    private String messageId;
    
	@Size(max = 250)
    @Column(name = "SERVICE_NAME")
    private String serviceName;
    
	@Size(max = 20)
    @Column(name = "LOG_LEVEL")
    private String logLevel;
    
	@Size(max = 250)
    @Column(name = "TASK")
    private String task;
    
	@Size(max = 250)
    @Column(name = "CREATED_BY")
    private String createdBy;
    
	@Column(name = "TIMESTAMP")
    private Timestamp timestamp;
    
	@Lob
    @Column(name = "PAYLOAD")
    private String payload;
    
	@Size(max = 250)
    @Column(name = "STATUS_CODE")
    private String statusCode;
    
	@Size(max = 250)
    @Column(name = "STATUS_MESSAGE")
    private String statusMessage;
    
	@Size(max = 250)
    @Column(name = "ENGINE")
    private String engine;
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idLog")
    private List<LogKeys> logKeysList;

    public LogEntity() {
    }

    public LogEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
    	if(statusMessage.length() <= 100){
    		this.statusMessage = statusMessage;
    	}
    	else{
    		this.statusMessage = "Status Message too long. Check Payload.";
    	}
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @XmlTransient
    public List<LogKeys> getLogKeysList() {
        return logKeysList;
    }

    public void setLogKeysList(List<LogKeys> logKeysList) {
        this.logKeysList = logKeysList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof LogEntity)) {
            return false;
        }
        LogEntity other = (LogEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
    	StringBuilder stringBuilder = new StringBuilder();
    	
    	stringBuilder.append("id = " + id + "\n");
    	stringBuilder.append("messageId = " + messageId + "\n");
    	stringBuilder.append("serviceName = " + serviceName + "\n");
    	stringBuilder.append("logLevel = " + logLevel + "\n");
    	stringBuilder.append("task = " + task + "\n");
    	stringBuilder.append("createdBy = " + createdBy + "\n");
    	stringBuilder.append("timestamp = " + timestamp + "\n");
    	stringBuilder.append("payload = " + payload + "\n");
    	stringBuilder.append("statusCode = " + statusCode + "\n");
    	stringBuilder.append("statusMessage = " + statusMessage + "\n");
    	stringBuilder.append("engine = " + engine + "\n");
    	stringBuilder.append("logKeyList = \n");
    	
        if (logKeysList != null && !logKeysList.isEmpty()) {
        	for (LogKeys lk : logKeysList) {
        		stringBuilder.append("[" + lk.getKeyName() + ":" + lk.getKeyValue() + "]\n");
        	}
        } else {
        	stringBuilder.append("[]");
        }
        return stringBuilder.toString();
    }
    
}

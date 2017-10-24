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
    @NamedQuery(name = "LogEntity.findByRequestId", query = "SELECT l FROM LogEntity l WHERE l.requestId = :requestId"),
    @NamedQuery(name = "LogEntity.findByCorrelationId", query = "SELECT l FROM LogEntity l WHERE l.requestId = :correlationId"),
    @NamedQuery(name = "LogEntity.findByServiceName", query = "SELECT l FROM LogEntity l WHERE l.serviceName = :serviceName"),
    @NamedQuery(name = "LogEntity.findByLogLevel", query = "SELECT l FROM LogEntity l WHERE l.logLevel = :logLevel"),
    @NamedQuery(name = "LogEntity.findByTask", query = "SELECT l FROM LogEntity l WHERE l.task = :task"),
    @NamedQuery(name = "LogEntity.findByUsername", query = "SELECT l FROM LogEntity l WHERE l.createdBy = :username"),
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
    @Column(name = "REQUEST_ID")
    private String requestId;
    
	@Size(max = 250)
    @Column(name = "CORRELATION_ID")
    private String correlationId;
	
	@Size(max = 250)
    @Column(name = "DOMAIN")
    private String domain;
	
	@Size(max = 250)
    @Column(name = "CATEGORY")
    private String category;
	
	@Size(max = 250)
    @Column(name = "TARGET")
    private String target;
	
	@Size(max = 250)
    @Column(name = "SERVICE")
    private String service;
	
	@Size(max = 250)
    @Column(name = "OPERATION")
    private String operation;
	
	@Size(max = 250)
    @Column(name = "VERSION")
    private String version;
	
	@Size(max = 250)
    @Column(name = "SOURCE")
    private String source;
	
	@Size(max = 250)
    @Column(name = "TARGET_ENDPOINT")
    private String targetEndpoint;
	
	@Size(max = 20)
    @Column(name = "LOG_LEVEL")
    private String logLevel;
    
	@Size(max = 250)
    @Column(name = "TASK")
    private String task;
    
	@Size(max = 250)
    @Column(name = "USERNAME")
    private String username;
    
	@Column(name = "TIMESTAMP")
    private Timestamp timestamp;
	
	@Column(name = "CREATION_DATE")
    private Timestamp creationDate;
    
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}	

	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}	
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}		

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getTargetEndpoint() {
		return targetEndpoint;
	}
	
	public void setTargetEndpoint(String targetEndpoint) {
		this.targetEndpoint = targetEndpoint;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
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
    	stringBuilder.append("requestId = " + requestId + "\n");
    	stringBuilder.append("correlationId = " + correlationId + "\n");
    	stringBuilder.append("service = " + service + "\n");
    	stringBuilder.append("logLevel = " + logLevel + "\n");
    	stringBuilder.append("task = " + task + "\n");
    	stringBuilder.append("username = " + username + "\n");
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

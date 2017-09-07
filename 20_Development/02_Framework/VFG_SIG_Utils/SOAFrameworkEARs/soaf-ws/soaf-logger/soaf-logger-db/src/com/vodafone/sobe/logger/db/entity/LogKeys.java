package com.vodafone.sobe.logger.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "LOG_KEYS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogKeys.findAll", query = "SELECT l FROM LogKeys l"),
    @NamedQuery(name = "LogKeys.findById", query = "SELECT l FROM LogKeys l WHERE l.id = :id"),
    @NamedQuery(name = "LogKeys.findByKeyName", query = "SELECT l FROM LogKeys l WHERE l.keyName = :keyName"),
    @NamedQuery(name = "LogKeys.findByKeyValue", query = "SELECT l FROM LogKeys l WHERE l.keyValue = :keyValue")})
public class LogKeys implements Serializable {
    
	private static final long serialVersionUID = 1L;
    
	@Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
	@GeneratedValue(strategy = GenerationType.TABLE, generator="LogKeyIdGen")
	@TableGenerator(name = "LogKeyIdGen", table = "SEQUENCE_GENERATOR",
			pkColumnName = "ID", valueColumnName = "SEQUENCE_VALUE", pkColumnValue = "LOG_KEY_ID",
			allocationSize=1, initialValue=1)
    @Column(name = "ID")
    private String id;
    
	@Size(max = 250)
    @Column(name = "KEY_NAME")
    private String keyName;
    
	@Size(max = 2000)
    @Column(name = "KEY_VALUE")
    private String keyValue;
    
	@JoinColumn(name = "ID_LOG", referencedColumnName = "ID")
    @ManyToOne
    private LogEntity idLog;

    public LogKeys() {
    }
    
    public LogKeys(String keyName, String keyValue){
    	this.keyName = keyName;
    	this.keyValue = keyValue;
    }

    public LogKeys(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public LogEntity getIdLog() {
        return idLog;
    }

    public void setIdLog(LogEntity idLog) {
        this.idLog = idLog;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LogKeys)) {
            return false;
        }
        LogKeys other = (LogKeys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.sobe.logger.db.entity.LogKeys[ id=" + id + " ]";
    }
    
}
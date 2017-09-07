package com.vodafone.sobe.ws.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "EAI_SERVICE_CONFIGURATOR_PARAM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EaiServiceConfiguratorParam.findAll", query = "SELECT e FROM EaiServiceConfiguratorParam e"),
    @NamedQuery(name = "EaiServiceConfiguratorParam.findByParamName", query = "SELECT e FROM EaiServiceConfiguratorParam e WHERE e.paramName = :paramName"),
    @NamedQuery(name = "EaiServiceConfiguratorParam.findByParamValue", query = "SELECT e FROM EaiServiceConfiguratorParam e WHERE e.paramValue = :paramValue"),
    @NamedQuery(name = "EaiServiceConfiguratorParam.findById", query = "SELECT e FROM EaiServiceConfiguratorParam e WHERE e.id = :id")})
public class EaiServiceConfiguratorParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 4000)
    @Column(name = "PARAM_NAME")
    private String paramName;
    @Size(max = 4000)
    @Column(name = "PARAM_VALUE")
    private String paramValue;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ID")
    private String id;
    @JoinColumns({
        @JoinColumn(name = "DOMAIN", referencedColumnName = "DOMAIN"),
        @JoinColumn(name = "TARGET", referencedColumnName = "TARGET"),
        @JoinColumn(name = "SERVICE", referencedColumnName = "SERVICE"),
        @JoinColumn(name = "VERSION", referencedColumnName = "VERSION")})
    @ManyToOne
    private EaiServiceConfigurator eaiServiceConfigurator;

    public EaiServiceConfiguratorParam() {
    }

    public EaiServiceConfiguratorParam(String id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EaiServiceConfigurator getEaiServiceConfigurator() {
        return eaiServiceConfigurator;
    }

    public void setEaiServiceConfigurator(EaiServiceConfigurator eaiServiceConfigurator) {
        this.eaiServiceConfigurator = eaiServiceConfigurator;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EaiServiceConfiguratorParam)) {
            return false;
        }
        EaiServiceConfiguratorParam other = (EaiServiceConfiguratorParam) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }
}

package com.vodafone.sobe.ws.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class EaiServiceConfiguratorPK implements Serializable {

	private static final long serialVersionUID = -2415660831732694380L;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "DOMAIN")
    private String domain;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "TARGET")
    private String target;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "SERVICE")
    private String service;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "VERSION")
    private String version;

    public EaiServiceConfiguratorPK() {
    }

    public EaiServiceConfiguratorPK(String domain, String target, String service, String version) {
        this.domain = domain;
        this.target = target;
        this.service = service;
        this.version = version;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getDomain() != null ? getDomain().hashCode() : 0);
        hash += (getTarget() != null ? getTarget().hashCode() : 0);
        hash += (getService() != null ? getService().hashCode() : 0);
        hash += (getVersion() != null ? getVersion().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EaiServiceConfiguratorPK)) {
            return false;
        }
        EaiServiceConfiguratorPK other = (EaiServiceConfiguratorPK) object;
        if ((this.getDomain() == null && other.getDomain() != null) || (this.getDomain() != null && !this.getDomain().equals(other.getDomain()))) {
            return false;
        }
        if ((this.getTarget() == null && other.getTarget() != null) || (this.getTarget() != null && !this.getTarget().equals(other.getTarget()))) {
            return false;
        }
        if ((this.getService() == null && other.getService() != null) || (this.getService() != null && !this.getService().equals(other.getService()))) {
            return false;
        }
        if ((this.getVersion() == null && other.getVersion() != null) || (this.getVersion() != null && !this.getVersion().equals(other.getVersion()))) {
            return false;
        }
        return true;
    }
}

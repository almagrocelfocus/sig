package com.vodafone.sobe.ws.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "EAI_SERVICE_CONFIGURATOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EaiServiceConfigurator.findAll", query = "SELECT e FROM EaiServiceConfigurator e"),
    @NamedQuery(name = "EaiServiceConfigurator.findByDomain", query = "SELECT e FROM EaiServiceConfigurator e WHERE e.eaiServiceConfiguratorPK.domain = :domain"),
    @NamedQuery(name = "EaiServiceConfigurator.findByTarget", query = "SELECT e FROM EaiServiceConfigurator e WHERE e.eaiServiceConfiguratorPK.target = :target"),
    @NamedQuery(name = "EaiServiceConfigurator.findByService", query = "SELECT e FROM EaiServiceConfigurator e WHERE e.eaiServiceConfiguratorPK.service = :service"),
    @NamedQuery(name = "EaiServiceConfigurator.findByVersion", query = "SELECT e FROM EaiServiceConfigurator e WHERE e.eaiServiceConfiguratorPK.version = :version")})
public class EaiServiceConfigurator implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EaiServiceConfiguratorPK eaiServiceConfiguratorPK;
    
    
    @OneToMany(mappedBy = "eaiServiceConfigurator", fetch=FetchType.EAGER)
    private List<EaiServiceConfiguratorParam> eaiServiceConfiguratorParamList;

    public EaiServiceConfigurator() {
    }

    public EaiServiceConfigurator(EaiServiceConfiguratorPK eaiServiceConfiguratorPK) {
        this.eaiServiceConfiguratorPK = eaiServiceConfiguratorPK;
    }

    public EaiServiceConfigurator(String domain, String target, String service, String version) {
        this.eaiServiceConfiguratorPK = new EaiServiceConfiguratorPK(domain, target, service, version);
    }

    public EaiServiceConfiguratorPK getEaiServiceConfiguratorPK() {
        return eaiServiceConfiguratorPK;
    }

    public void setEaiServiceConfiguratorPK(EaiServiceConfiguratorPK eaiServiceConfiguratorPK) {
        this.eaiServiceConfiguratorPK = eaiServiceConfiguratorPK;
    }

    @XmlTransient
    public List<EaiServiceConfiguratorParam> getEaiServiceConfiguratorParamList() {
        return eaiServiceConfiguratorParamList;
    }

    public void setEaiServiceConfiguratorParamList(List<EaiServiceConfiguratorParam> eaiServiceConfiguratorParamList) {
        this.eaiServiceConfiguratorParamList = eaiServiceConfiguratorParamList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eaiServiceConfiguratorPK != null ? eaiServiceConfiguratorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EaiServiceConfigurator)) {
            return false;
        }
        EaiServiceConfigurator other = (EaiServiceConfigurator) object;
        if ((this.eaiServiceConfiguratorPK == null && other.eaiServiceConfiguratorPK != null) || (this.eaiServiceConfiguratorPK != null && !this.eaiServiceConfiguratorPK.equals(other.eaiServiceConfiguratorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.vodafone.sobe.ws.db.entity.EaiServiceConfigurator[ eaiServiceConfiguratorPK=" + eaiServiceConfiguratorPK + " ]";
    }
    
}
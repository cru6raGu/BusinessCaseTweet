package org.cru.tweet.domain.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractExternalMutableDomainObject<PK extends Serializable> extends AbstractMutableDomainObject<PK> {
    @Column(name = "guid", unique = true, nullable = false)
    private String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}


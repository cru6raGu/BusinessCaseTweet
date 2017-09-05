package org.cru.tweet.domain.jpa;

import org.cru.tweet.domain.jpa.internal.AbstractImmutableDomainObject;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractExternalImmutableDomainObject<PK extends Serializable> extends AbstractImmutableDomainObject<PK> {
    @Column(name = "guid", unique = true, nullable = false)
    private String guid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}


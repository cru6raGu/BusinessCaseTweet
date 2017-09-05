package org.cru.tweet.domain.jpa;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.cru.tweet.domain.jpa.internal.AbstractImmutableDomainObject;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractMutableDomainObject<PK extends Serializable> extends AbstractImmutableDomainObject<PK> {
    @Column(name = "update_time")
    private Date updateTime;

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AbstractMutableDomainObject abstractExternalMutableDomainObject = (AbstractMutableDomainObject) obj;
        return new EqualsBuilder()
                .append(getId(), abstractExternalMutableDomainObject.getId())
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().
                append(getId()).
                toHashCode();
    }

}


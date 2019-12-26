package org.kettle.scheduler.system.biz.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 描述:
 *
 * @author leo
 * @create 2019-12-26 17:50
 */
@Data
@Entity
//@EqualsAndHashCode(callSuper = true)
@Table(name = "k_mview")
public class Mview implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "mview_name")
    private String mviewName;

    @Column(name = "last_refresh_date")
    private Date lastRefreshDate;

    @Column(name = "refresh_method")
    private String refreshMethod;

    @Column(name = "invalid")
    private String invalid;

    @Column(name = "mview_tag_id")
    private String mviewTagId;

    @Column(name = "query")
    private String query;

    @Column(name = "status")
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMviewName() {
        return mviewName;
    }

    public void setMviewName(String mviewName) {
        this.mviewName = mviewName;
    }

    public Date getLastRefreshDate() {
        return lastRefreshDate;
    }

    public void setLastRefreshDate(Date lastRefreshDate) {
        this.lastRefreshDate = lastRefreshDate;
    }

    public String getRefreshMethod() {
        return refreshMethod;
    }

    public void setRefreshMethod(String refreshMethod) {
        this.refreshMethod = refreshMethod;
    }

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public String getMviewTagId() {
        return mviewTagId;
    }

    public void setMviewTagId(String mviewTagId) {
        this.mviewTagId = mviewTagId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

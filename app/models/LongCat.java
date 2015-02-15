package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LongCat extends Model {

    private static final long serialVersionUID = 1L;

    @Constraints.Required
    @Column(unique = true)
    @Id
    private String longId;
    @Column(columnDefinition="VARCHAR(4095)")
    private String longTitle;
    private Integer parentStip;

    public LongCat(String longId, String longTitle, Integer parentStip) {
        this.longId = longId;
        this.longTitle = longTitle;
        this.parentStip = parentStip;
    }

    public String getLongId() {
        return longId;
    }

    public void setLongId(String longId) {
        this.longId = longId;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public Integer getParentStip() {
        return parentStip;
    }

    public void setParentStip(Integer parentStip) {
        this.parentStip = parentStip;
    }
}
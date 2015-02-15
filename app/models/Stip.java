package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stip extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer stipId;
    @Constraints.Required
    @Column(unique = true)
    private String stipTitle;

    public Stip(String stipTitle) {
        this.stipTitle = stipTitle;
    }

    public String getStipTitle() {
        return stipTitle;
    }

    public void setStipTitle(String stipTitle) {
        this.stipTitle = stipTitle;
    }

    public Integer getStipId() {
        return stipId;
    }

    public void setStipId(Integer stipId) {
        this.stipId = stipId;
    }
}
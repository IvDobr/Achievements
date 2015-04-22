package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

@Entity
public class Stip extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator="stip_seq")
    private Integer stipId;

    @Constraints.Required
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

    public static Finder<String, Stip> find = new Finder<String, Stip>(String.class, Stip.class);
}
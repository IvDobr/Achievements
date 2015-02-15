package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Faculty extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer fclId;
    @Constraints.Required
    @Column(unique = true)
    private String fclTitle;
    private Integer fclTopModer;
    private Integer fclModer;
    private Integer fclUnderModer;
    @Column(columnDefinition="VARCHAR(2047)")
    private String fclAdress;

    public Faculty(
            String fclTitle,
            Integer fclTopModer,
            Integer fclModer,
            Integer fclUnderModer,
            String fclAdress) {
        this.fclTitle = fclTitle;
        this.fclTopModer = fclTopModer;
        this.fclModer = fclModer;
        this.fclUnderModer = fclUnderModer;
        this.fclAdress = fclAdress;
    }

    public String getFclAdress() {
        return fclAdress;
    }

    public void setFclAdress(String fclAdress) {
        this.fclAdress = fclAdress;
    }

    public String getFclTitle() {
        return fclTitle;
    }

    public void setFclTitle(String fclTitle) {
        this.fclTitle = fclTitle;
    }

    public Integer getFclTopModer() {
        return fclTopModer;
    }

    public void setFclTopModer(Integer fclTopModer) {
        this.fclTopModer = fclTopModer;
    }

    public Integer getFclModer() {
        return fclModer;
    }

    public void setFclModer(Integer fclModer) {
        this.fclModer = fclModer;
    }

    public Integer getFclUnderModer() {
        return fclUnderModer;
    }

    public void setFclUnderModer(Integer fclUnderModer) {
        this.fclUnderModer = fclUnderModer;
    }

    public Integer getFclId() {
        return fclId;
    }

    public void setFclId(Integer fclId) {
        this.fclId = fclId;
    }
}
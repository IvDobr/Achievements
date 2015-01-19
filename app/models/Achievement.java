package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Entity
public class Achievement extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer achId;
    private User achUser;
    private String achTitle;
    private Date achDate;
    private String achCat;

    public String getAchDop() {
        return achDop;
    }

    public void setAchDop(String achDop) {
        this.achDop = achDop;
    }

    private String achDop;
    private String achComment;
    private Integer achPrem;
    private Integer achStip;

    public Integer getAchId() {
        return achId;
    }

    public void setAchId(Integer achId) {
        this.achId = achId;
    }

    public User getAchUser() {
        return achUser;
    }

    public void setAchUser(User achUser) {
        this.achUser = achUser;
    }

    public String getAchTitle() {
        return achTitle;
    }

    public void setAchTitle(String achTitle) {
        this.achTitle = achTitle;
    }

    public Date getAchDate() {
        return achDate;
    }

    public void setAchDate(Date achDate) {
        this.achDate = achDate;
    }

    public String getAchCat() {
        return achCat;
    }

    public void setAchCat(String achCat) {
        this.achCat = achCat;
    }

    public String getAchComment() {
        return achComment;
    }

    public void setAchComment(String achComment) {
        this.achComment = achComment;
    }

    public Integer getAchPrem() {
        return achPrem;
    }

    public void setAchPrem(Integer achPrem) {
        this.achPrem = achPrem;
    }

    public Integer getAchStip() {
        return achStip;
    }

    public void setAchStip(Integer achStip) {
        this.achStip = achStip;
    }
}

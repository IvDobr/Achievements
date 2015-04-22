package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Achievement extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator="achievement_seq")
    private Integer achId;

    //foreign key
    private Integer achUserId;

    private String achTitle;

    private Date achDate;

    private String achCat; // <-- излишне - в дальнейнем удалить

    //foreign key
    private String achLongCat;

    @Column(columnDefinition="VARCHAR(4095)")
    private String achDop;

    @Column(columnDefinition="VARCHAR(4095)")
    private String achComment;

    private Integer achPrem;

    private Integer achStip;

    public Achievement(
            Integer achUserId,
            String achTitle,
            Date achDate,
            String achCat,
            String achLongCat,
            String achDop,
            String achComment,
            Integer achPrem,
            Integer achStip) {
        this.achUserId = achUserId;
        this.achTitle = achTitle;
        this.achDate = achDate;
        this.achCat = achCat;
        this.achLongCat = achLongCat;
        this.achDop = achDop;
        this.achComment = achComment;
        this.achPrem = achPrem;
        this.achStip = achStip;
    }

    public String getAchLongCat() {
        return achLongCat;
    }

    public void setAchLongCat(String achLongCat) {
        this.achLongCat = achLongCat;
    }

    public String getAchDop() {
        return achDop;
    }

    public void setAchDop(String achDop) {
        this.achDop = achDop;
    }

    public Integer getAchId() {
        return achId;
    }

    public void setAchId(Integer achId) {
        this.achId = achId;
    }

    public Integer getAchUserId() {
        return achUserId;
    }

    public void setAchUserId(Integer achUserId) {
        this.achUserId = achUserId;
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

    public static Finder<String, Achievement> find = new Finder<String, Achievement>(String.class, Achievement.class);
}

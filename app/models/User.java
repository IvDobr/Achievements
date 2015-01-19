package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer userId;
    @Constraints.Required
    @Column(unique=true)
    private String userLogin;
    @Constraints.Required
    private String userPass;
    @Constraints.Required
    private String userFaculty;
    private Date userReg;
    private String userStip;
    @Constraints.Required
    private Boolean userStatus;
    @Constraints.Required
    private String userGroup;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserFaculty() {
        return userFaculty;
    }

    public void setUserFaculty(String userFaculty) {
        this.userFaculty = userFaculty;
    }

    public Date getUserReg() {
        return userReg;
    }

    public void setUserReg(Date userReg) {
        this.userReg = userReg;
    }

    public String getUserStip() {
        return userStip;
    }

    public void setUserStip(String userStip) {
        this.userStip = userStip;
    }

    public Boolean getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}

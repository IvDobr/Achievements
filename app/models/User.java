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
    private String userFirstName;
    private String userLastName;
    @Constraints.Required
    private String userPass;
    @Constraints.Required
    private Integer userFaculty;
    private Date userReg;
    private Integer userStip;
    @Constraints.Required
    private Boolean userStatus;
    @Constraints.Required
    private String userGroup;

    public User() {
    }

    public User(
            String userLogin,
            String userFirstName,
            String userLastName,
            String userPass,
            Integer userFaculty,
            Date userReg,
            Integer userStip,
            Boolean userStatus,
            String userGroup) {
        this.userLogin = userLogin;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPass = userPass;
        this.userFaculty = userFaculty;
        this.userReg = userReg;
        this.userStip = userStip;
        this.userStatus = userStatus;
        this.userGroup = userGroup;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

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

    public Integer getUserFaculty() {
        return userFaculty;
    }

    public void setUserFaculty(Integer userFaculty) {
        this.userFaculty = userFaculty;
    }

    public Date getUserReg() {
        return userReg;
    }

    public void setUserReg(Date userReg) {
        this.userReg = userReg;
    }

    public Integer getUserStip() {
        return userStip;
    }

    public void setUserStip(Integer userStip) {
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

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
}

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
    private Integer fId;
    @Constraints.Required
    @Column(unique=true)
    private String fLogin;
    private String userFirstName;
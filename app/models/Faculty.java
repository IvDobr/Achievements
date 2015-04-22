package models;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;

@Entity
public class Faculty extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator="faculty_seq")
    private Integer fclId;

    @Column(unique = true)
    private String fclLongTitle;

    private String fclTitle;

    //foreign key
    private Integer fclTopModer;

    //foreign key
    private Integer fclModer;

    //foreign key
    private Integer fclUnderModer;

    @Column(columnDefinition="VARCHAR(2047)")
    private String fclAdress;

    public Faculty(
            String fclLongTitle,
            String fclTitle,
            Integer fclTopModer,
            Integer fclModer,
            Integer fclUnderModer,
            String fclAdress) {
        this.fclLongTitle = fclLongTitle;
        this.fclTitle = fclTitle;
        this.fclTopModer = fclTopModer;
        this.fclModer = fclModer;
        this.fclUnderModer = fclUnderModer;
        this.fclAdress = fclAdress;
    }

    public ObjectNode getFacultyInfo() {
        ObjectNode getFacultyInfo = Json.newObject();
        getFacultyInfo.put("fclId", fclId);
        getFacultyInfo.put("fclLongTitle", fclLongTitle);
        getFacultyInfo.put("fclTitle", fclTitle);
        getFacultyInfo.put("fclTopModer", fclTopModer);
        getFacultyInfo.put("fclModer", fclModer);
        getFacultyInfo.put("fclUnderModer", fclUnderModer);
        getFacultyInfo.put("fclAdress", fclAdress);
        return getFacultyInfo;
    }

    public String getFclAdress() {
        return fclAdress;
    }

    public void setFclAdress(String fclAdress) {
        this.fclAdress = fclAdress;
    }

    public String getFclLongTitle() {
        return fclLongTitle;
    }

    public void setFclLongTitle(String fclLongTitle) {
        this.fclLongTitle = fclLongTitle;
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

    public static Finder<String, Faculty> find = new Finder<String, Faculty>(String.class, Faculty.class);
}
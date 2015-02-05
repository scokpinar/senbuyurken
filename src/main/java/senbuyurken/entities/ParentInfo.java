package senbuyurken.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * User: SametCokpinar
 * Date: 20/12/14
 * Time: 21:12
 */
@Entity
@Table(name = "parentinformation")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ParentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_info_id")
    private Integer parentInfoId;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    @JoinColumn(name = "fk_user_id")
    private User user;

    @NotNull
    @Size(max = 64)
    @Column(name = "mother_name")
    private String motherName;

    @NotNull
    @Size(max = 64)
    @Column(name = "mother_surname")
    private String motherSurname;

    @NotNull
    @Size(max = 64)
    @Column(name = "father_name")
    private String fatherName;

    @NotNull
    @Size(max = 64)
    @Column(name = "father_surname")
    private String fatherSurname;

    @XmlElement(nillable = true)
    @Column(name = "wedding_anniversary")
    @XmlJavaTypeAdapter(MyDateAdapterShort.class)
    private Date weddingAnniversary;

    @XmlElement(nillable = true)
    @Size(max = 256)
    @Column(name = "mother_photo_url")
    private String photoURLMother;

    @XmlElement(nillable = true)
    @Size(max = 256)
    @Column(name = "photo_url_father")
    private String photoURLFather;

    public Integer getParentInfoId() {
        return parentInfoId;
    }

    public void setParentInfoId(Integer parentInfoId) {
        this.parentInfoId = parentInfoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherSurname() {
        return motherSurname;
    }

    public void setMotherSurname(String motherSurname) {
        this.motherSurname = motherSurname;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherSurname() {
        return fatherSurname;
    }

    public void setFatherSurname(String fatherSurname) {
        this.fatherSurname = fatherSurname;
    }

    public Date getWeddingAnniversary() {
        return weddingAnniversary;
    }

    public void setWeddingAnniversary(Date weddingAnniversary) {
        this.weddingAnniversary = weddingAnniversary;
    }

    public String getPhotoURLMother() {
        return photoURLMother;
    }

    public void setPhotoURLMother(String photoURLMother) {
        this.photoURLMother = photoURLMother;
    }

    public String getPhotoURLFather() {
        return photoURLFather;
    }

    public void setPhotoURLFather(String photoURLFather) {
        this.photoURLFather = photoURLFather;
    }
}

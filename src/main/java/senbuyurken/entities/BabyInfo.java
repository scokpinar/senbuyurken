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
 * Date: 17/12/14
 * Time: 22:46
 */

@Entity
@Table(name = "babyinformation")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class BabyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "baby_info_id")
    private Integer babyInfoId;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    @JoinColumn(name = "fk_user_id")
    private User user;

    @NotNull
    @Size(max = 64)
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(max = 64)
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "birthDate")
    @XmlJavaTypeAdapter(MyDateAdapterShort.class)
    private Date birthDate;

    @Size(max = 5)
    @Column(name = "birthHour")
    private String birthHour;

    @Size(max = 32)
    @Column(name = "birthPlace")
    private String birthPlace;

    @Size(max = 128)
    @Column(name = "hospital")
    private String hospital;

    @Size(max = 128)
    @Column(name = "gynecologyDoctor")
    private String gynecologyDoctor;

    @Size(max = 128)
    @Column(name = "pediatricianDoctor")
    private String pediatricianDoctor;

    @Column(name = "birthWeight")
    private int birthWeight = 0;

    @Column(name = "birthLength")
    private int birthLength = 0;

    @XmlElement(nillable = true)
    @Size(max = 256)
    @Column(name = "photoURL")
    private String photoURL;

    public Integer getBabyInfoId() {
        return babyInfoId;
    }

    public void setBabyInfoId(Integer babyInfoId) {
        this.babyInfoId = babyInfoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthHour() {
        return birthHour;
    }

    public void setBirthHour(String birthHour) {
        this.birthHour = birthHour;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getGynecologyDoctor() {
        return gynecologyDoctor;
    }

    public void setGynecologyDoctor(String gynecologyDoctor) {
        this.gynecologyDoctor = gynecologyDoctor;
    }

    public String getPediatricianDoctor() {
        return pediatricianDoctor;
    }

    public void setPediatricianDoctor(String pediatricianDoctor) {
        this.pediatricianDoctor = pediatricianDoctor;
    }

    public int getBirthWeight() {
        return birthWeight;
    }

    public void setBirthWeight(Integer birthWeight) {
        this.birthWeight = birthWeight;
    }

    public int getBirthLength() {
        return birthLength;
    }

    public void setBirthLength(Integer birthLength) {
        this.birthLength = birthLength;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public String toString() {
        return "BabyInfo{" +
                "babyInfoId=" + babyInfoId +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate=" + birthDate +
                ", birthHour='" + birthHour + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", hospital='" + hospital + '\'' +
                ", gynecologyDoctor='" + gynecologyDoctor + '\'' +
                ", pediatricianDoctor='" + pediatricianDoctor + '\'' +
                ", birthWeight=" + birthWeight +
                ", birthLength=" + birthLength +
                ", photoURL='" + photoURL + '\'' +
                '}';
    }
}

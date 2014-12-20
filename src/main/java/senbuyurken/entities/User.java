/**
 *
 */
package senbuyurken.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Siva
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.userId = :userId")
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private BabyInfo babyInfo;

    @NotNull
    @Size(max = 128)
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(max = 128)
    @Column(name = "password")
    private String password;


    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "active")
    private String active;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "usertype")
    private String userType;


    public User() {
    }

    public User(String email, String password, String active, String userType) {
        this.email = email;
        this.password = password;
        this.active = active;
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BabyInfo getBabyInfo() {
        return babyInfo;
    }

    public void setBabyInfo(BabyInfo babyInfo) {
        this.babyInfo = babyInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", active='" + active + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }


}

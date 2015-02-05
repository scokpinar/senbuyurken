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
 * User: SametCokpinar
 * Date: 17/12/14
 * Time: 23:08
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByUserId", query = "SELECT u FROM User u WHERE u.user_id = :user_id")
})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private BabyInfo baby_info;

    @NotNull
    @Size(max = 128)
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "active")
    private String active;

    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "user_type")
    private String user_type;


    public User() {
    }

    public User(String email, String active, String userType) {
        this.email = email;
        this.active = active;
        this.user_type = userType;
    }

    public Integer getUserId() {
        return user_id;
    }

    public void setUserId(Integer userId) {
        this.user_id = userId;
    }

    public BabyInfo getBabyInfo() {
        return baby_info;
    }

    public void setBabyInfo(BabyInfo baby_info) {
        this.baby_info = baby_info;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUserType() {
        return user_type;
    }

    public void setUserType(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", email='" + email + '\'' +
                ", active='" + active + '\'' +
                ", user_type='" + user_type + '\'' +
                '}';
    }


}

package senbuyurken.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * User: SametCokpinar
 * Date: 20/12/14
 * Time: 21:17
 */
@Entity
@Table(name = "diaryentry")
@XmlRootElement
public class DiaryEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_entry_id")
    private Integer diary_entry_id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
    private User user;

    @NotNull
    @Column(name = "entry_date")
    private Date entry_date;

    @NotNull
    @Size(max = 1024)
    @Column(name = "entry_content")
    private String entry_content;

    @Size(max = 256)
    @Column(name = "photo_url")
    private String photo_url;


    public Integer getDiary_entry_id() {
        return diary_entry_id;
    }

    public void setDiary_entry_id(Integer diary_entry_id) {
        this.diary_entry_id = diary_entry_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public String getEntry_content() {
        return entry_content;
    }

    public void setEntry_content(String entry_content) {
        this.entry_content = entry_content;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }
}

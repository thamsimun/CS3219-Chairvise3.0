package sg.edu.nus.comp.cs3219.viz.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;
import sg.edu.nus.comp.cs3219.viz.common.entity.record.Exportable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDetails {

    @Id
    @GenericGenerator(name = "UseExistingIdOtherwiseGenerateUsingIdentity", strategy = "sg.edu.nus.comp.cs3219.viz.common.entity.UseExistingIdOtherwiseGenerateUsingIdentity")
    @GeneratedValue(generator = "UseExistingIdOtherwiseGenerateUsingIdentity")
    @JsonSerialize(using = ToStringSerializer.class)
    @Column(name = "user_id")
    @Exportable(name = "userId", nameInDB = "user_id")
    private long userId;

    @Column(name = "user_name")
    @Exportable(name = "userName", nameInDB = "user_name")
    private String userName;

    @Column(name = "user_email")
    @Exportable(name = "userEmail", nameInDB = "user_email")
    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getUserId() {
        return userId;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSameUser(UserDetails otherUser) {
        return this.userId == otherUser.getUserId();
    }

}

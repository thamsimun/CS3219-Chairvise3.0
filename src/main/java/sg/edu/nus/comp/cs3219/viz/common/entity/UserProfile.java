package sg.edu.nus.comp.cs3219.viz.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class UserProfile {

    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    @JsonSerialize(using = ToStringSerializer.class)
    private UUID userId;

    private String userName;

    private String userEmail;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setId(UUID id) {
        this.userId = id;
    }

    /**
     * Auxillary method to provide more consistent data type usages
     * @return user's id in string format
     */
    public String getUserIdInString() {
        return getUserId().toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSameUser(UserProfile otherUser) {
        return this.userId.equals(otherUser.getUserId());
    }

}

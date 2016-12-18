package models;

import com.avaje.ebean.Ebean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import security.Password;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity(name = "user")
public class User {
    @Id
    String email;
    String password;

    public User(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public static User getUserByEmail(String email) {
        if(email == null) {
            return null;
        }
        return Ebean.find(User.class, email);
    }

    public static List<User> getAllUsers() {
        return Ebean.find(User.class).findList();
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    public boolean authenticate() {
        // Use shiro to pass through a email password token.
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        //token.setRememberMe(true)

        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    public boolean register() {
        if (getUserByEmail(email) == null) {
            create();
            return true;
        }
        return false;
    }

    private User create() {

        password = Password.encryptPassword(password);
        Ebean.save(this);
        return this;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

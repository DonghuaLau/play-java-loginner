package models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Model;
import com.avaje.ebean.ExpressionList;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import security.Password;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.List;
import java.sql.Timestamp;
import java.util.Date;

import play.Logger;

@Entity(name = "user")
public class User extends Model{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

	@Column(unique=true)
    private String email;

    private String password;

	@Column(columnDefinition = "datetime")
	private Timestamp create_time;

	//public static Finder<String, User> find_by_email = new Finder<String, User>(User.class);

    public User(String email, String password) {
        //this.uid = new Long(0);
        this.email = email;
        this.password = password;
		this.create_time = new Timestamp(System.currentTimeMillis());
    }

    public static User getUserByEmail(String email) {
        if(email == null) {
            return null;
        }
        //List<User> users = Ebean.find(User.class).where().eq("email", email).findList();
		//if(users.size() == 0){
		//	return null;
		//}else{
		//	return users.get(0);
		//}	
        User usr = Ebean.find(User.class).where().eq("email", email).findUnique();
		return usr;
    }

    public static User getUserByUid(Long uid) {
        if(uid == null) {
            return null;
        }
        return Ebean.find(User.class, uid);
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
		create_time = new Timestamp(System.currentTimeMillis());
        Ebean.save(this);
        return this;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "uid")
    public Long getUid() {
        return uid;
    }

    @Column(name = "uid")
    public void setUid(Long uid) {
        this.uid = uid;
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

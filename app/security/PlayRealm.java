package security;

import models.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Collections;
import java.util.Set;

/**
 * Custom realm, with thanks to
 * <a href="https://github.com/Arnauld/scalaadin/wiki/Authentication:-Vaadin+Shiro">the Vaadin Shiro integration</a>.
 *
 * @author wsargent
 * @since 1/8/12
 */
public class PlayRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        String username = upToken.getUsername();
        checkNotNull(username, "Null usernames are not allowed by this realm.");

        // retrieve the 'real' user password
        String password = passwordOf(username);

        checkNotNull(password, "No account found for user [" + username + "]");

        // return the 'real' info for username, security manager is then responsible
        // for checking the token against the provided info
        return new SimpleAuthenticationInfo(username, password, getName());
    }

    public CredentialsMatcher getCredentialsMatcher() {

        return (AuthenticationToken token, AuthenticationInfo info) -> {
            String message = new String((char[]) token.getCredentials());
            String digest = info.getCredentials().toString();
            return Password.checkPassword(message, digest);
        };
    }


    private String passwordOf(String username) {
        User user = User.getUserByEmail(username);
        return user == null ? null : user.getPassword();
    }

    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(rolesOf(username));
        info.setStringPermissions(permissionsOf(username));
        return info;
    }

    private Set<String> permissionsOf(String username) {
        return Collections.<String>emptySet();
    }

    private Set<String> rolesOf(String username) {
        if ("admin@example.org".equalsIgnoreCase(username)) {
            return Collections.singleton("admin");
        } else {
            return Collections.<String>emptySet();
        }
    }

    private void checkNotNull(String reference, String message) {
        if (reference == null) {
            throw new AuthenticationException(message);
        }
    }
}

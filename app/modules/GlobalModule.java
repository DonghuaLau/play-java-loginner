package modules;

import com.google.inject.AbstractModule;
import models.User;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import play.Configuration;
import play.Environment;
import security.PlayRealm;
import security.PlaySecurityManager;

/**
 * Created by kavi on 2/5/16.
 */
public class GlobalModule extends AbstractModule {

    private final Environment environment;
    private final Configuration configuration;

    public GlobalModule(Environment environment, Configuration configuration) {
        this.environment = environment;
        this.configuration = configuration;
    }

    @Override
    protected void configure() {
        initialize();
    }

    private void initialize() {
        PlayRealm sampleRealm = new PlayRealm();
        PlaySecurityManager securityManager = new PlaySecurityManager();
        securityManager.setRealm(sampleRealm);

        // Turn off session storage for better "stateless" management.
        // https://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%2528Sessionless%2529
        DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) securityManager.getSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = (DefaultSessionStorageEvaluator) subjectDAO.getSessionStorageEvaluator();

        sessionStorageEvaluator.setSessionStorageEnabled(false);

        org.apache.shiro.SecurityUtils.setSecurityManager(securityManager);
    }
}

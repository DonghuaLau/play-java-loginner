package security;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.SubjectContext;

/**
 * Created by kavi on 2/4/16.
 */
public class PlaySecurityManager extends DefaultSecurityManager {

    public PlaySecurityManager() {
        setSubjectFactory(new PlaySubjectFactory());
    }

    // Not applicable as we're not using the java.servlet API
    //this.subjectDAO.asInstanceOf[DefaultSubjectDAO].setSessionStorageEvaluator(new DefaultWebSessionStorageEvaluator())
    //setRememberMeManager(new CookieRememberMeManager())
    //setSessionManager(new ServletContainerSessionManager())

    protected PlaySubjectContext createSubjectContext() {
        return new PlaySubjectContext(null);
    }

    protected SubjectContext copy(SubjectContext subjectContext) {
        if (subjectContext instanceof PlaySubjectContext) {
            return new PlaySubjectContext(subjectContext);
        }
        return super.copy(subjectContext);
    }
}

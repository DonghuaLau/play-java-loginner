package security;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;

/**
 * Created by kavi on 2/4/16.
 */
public class PlaySubjectContext extends DefaultSubjectContext {
    public PlaySubjectContext(SubjectContext subjectContext) {
        super(subjectContext);
    }

    public PlaySubjectContext() {
        super();
    }

    public Session getSession() {
        return null;
    }

    public boolean isSessionCreationEnabled() {
        return false;
    }
}
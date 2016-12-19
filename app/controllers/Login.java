package controllers;

import models.User;
import services.*;
import play.data.Form;
import play.data.FormFactory;
import javax.inject.Inject;

import play.mvc.*;
import views.html.*;


public class Login extends Controller {

    private Form<User> loginForm;
    private final FormFactory formFactory;

	private SiteInfo _site_info;

    @Inject
    public Login (FormFactory formFactory) {
        this.formFactory = formFactory;
		loginForm = formFactory.form(User.class);
		this._site_info = new SiteInfo();
    }

    public Result index() {
		User user = _site_info.getCurrentUser(session());
        return ok(login.render(this._site_info, user, loginForm));
    }

	public Result logout() {
	    User.logout();
	    session().clear();
	    //flash("success", "You've been logged out");
	    return redirect("/");
	}


    public Result authenticate() {
		User user = loginForm.bindFromRequest().get();
        String loginError = authenticate(user);
        if(loginError != null) {
            loginForm.reject(loginError);
        }
        if(loginForm.hasErrors()) {
            return badRequest(login.render(this._site_info, user, loginForm));
        } else {
            session("email", user.getEmail());
            return redirect("/");
        }
    }

    private String authenticate(User user) {
        if (!user.authenticate()) {
            return "Invalid Email/Password";
        }
        return null;
    }
}

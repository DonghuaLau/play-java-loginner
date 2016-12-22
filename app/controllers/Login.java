package controllers;

import models.User;
import services.*;
import play.data.Form;
import play.data.FormFactory;
import javax.inject.Inject;
import play.i18n.Messages;
import play.i18n.MessagesApi;

import play.mvc.*;
import views.html.*;


public class Login extends Controller {

    private Form<User> loginForm;
    private final FormFactory formFactory;

	private SiteInfo _site_info;
	private final MessagesApi messagesApi;

    @Inject
    public Login (MessagesApi messagesApi, FormFactory formFactory) {

		this.messagesApi = messagesApi;

        this.formFactory = formFactory;
		loginForm = formFactory.form(User.class);
		this._site_info = new SiteInfo();
    }

    public Result index() {
		Messages messages = messagesApi.preferred(request());
		this._site_info._messages = messages;
		this._site_info.changeContext();
		this._site_info._title = this._site_info._messages.at("pages.title");
		this._site_info._sub_title = this._site_info._messages.at("pages.login.title");
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

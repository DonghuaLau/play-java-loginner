package controllers;

import models.User;
import services.*;
import play.data.Form;
import play.data.DynamicForm;
import play.mvc.*;
import views.html.*;
import play.data.FormFactory;
import javax.inject.Inject;
import play.i18n.Messages;
import play.i18n.MessagesApi;

import play.Logger;

import javax.persistence.PersistenceException;

public class Register extends Controller {

    Form<User> loginForm;
    private final FormFactory formFactory;

	private SiteInfo _site_info;

	private final MessagesApi messagesApi;

    @Inject
    public Register(MessagesApi messagesApi, FormFactory formFactory) {

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
		this._site_info._sub_title = this._site_info._messages.at("pages.register.title");
		User user = _site_info.getCurrentUser(session());
        return ok(register.render(this._site_info, user, loginForm));
    }

    public Result register() {
        User user = loginForm.bindFromRequest().get();
        try {
            if (user.isEmpty()) {
                loginForm.reject("E-mail or password shoudn't be empty.");
				
            } else if (!user.register()) {
                loginForm.reject("Provided email id already present");
            }
        } catch (PersistenceException e) {
            loginForm.reject(e.getLocalizedMessage());
        }
        if(loginForm.hasErrors()) {
            return badRequest(register.render(this._site_info, user, loginForm));
        } else {
            session("email", user.getEmail());
            return redirect("/");
        }
    }
}



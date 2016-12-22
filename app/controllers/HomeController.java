package controllers;

import models.User;
import services.*;
import play.mvc.*;
import views.html.*;
import scala.Option;
import javax.inject.Inject;
import play.i18n.Messages;
import play.i18n.MessagesApi;
import play.i18n.Lang;

public class HomeController extends Controller {

	private SiteInfo _site_info;
	private final MessagesApi messagesApi;

    @Inject
	public HomeController(MessagesApi messagesApi) {
		this.messagesApi = messagesApi;
		this._site_info = new SiteInfo();
	}

    public Result index() {
		Messages messages = messagesApi.preferred(request());
		this._site_info._messages = messages;
		this._site_info.changeContext();
		this._site_info._title = this._site_info._messages.at("pages.title");
		this._site_info._sub_title = this._site_info._messages.at("pages.index.title");

		User user = _site_info.getCurrentUser(session());
        return ok(index.render(this._site_info, user));
    }

}

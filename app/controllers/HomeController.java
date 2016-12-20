package controllers;

import models.User;
import services.*;
import play.mvc.*;
import views.html.*;
import scala.Option;
import javax.inject.Inject;

public class HomeController extends Controller {

	private SiteInfo _site_info;

    @Inject
	public HomeController() {
		this._site_info = new SiteInfo();
		this._site_info._sub_title = "Home";
	}

    public Result index() {
		User user = _site_info.getCurrentUser(session());
        return ok(index.render(this._site_info, user));
    }

}

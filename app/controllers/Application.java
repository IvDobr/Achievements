package controllers;

import models.User;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.auth;
import views.html.cab;

@Security.Authenticated(Secured.class)
public class Application extends Controller {

    public static Result cab() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        return ok(cab.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
    }

    public static Result auth() {
        return ok(auth.render(""));
    }

}
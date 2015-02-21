package controllers;

import models.User;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin_cab;
import views.html.admin_users;
import views.html.cab;

@Security.Authenticated(Secured.class)
public class Application extends Controller {
    public static Result cab() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        if (!current_user.getUserGroup().equals("administrator")){
            return ok(cab.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
        } else {
            return redirect(controllers.routes.Application.admin_cab());
        }
    }

    public static Result admin_cab() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        return ok(admin_cab.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
    }

    public static Result admin_users() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        return ok(admin_users.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
    }

    public static Result admin_faculty() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        return TODO;
//        return ok(admin_faculty.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
    }

    public static Result admin_aches() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        return TODO;
//        return ok(admin_aches.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
    }

    public static Result admin_stips() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        return TODO;
//        return ok(admin_stips.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
    }

    public static Result admin_cats() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        return TODO;
//        return ok(admin_cats.render(current_user.getUserFirstName() + " " + current_user.getUserLastName()));
    }
}
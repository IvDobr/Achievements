package controllers;

import play.mvc.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(play.mvc.Http.Context ctx) {
        return ctx.session().get("current_user");
    }
    @Override
    public Result onUnauthorized(play.mvc.Http.Context ctx) {
        return redirect("/");
    }
}
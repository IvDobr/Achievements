package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result cab() {
        return ok(cab.render("Личный кабинет студента"));
    }
    public static Result auth() {
        return ok(auth.render("Авторизация"));
    }

}
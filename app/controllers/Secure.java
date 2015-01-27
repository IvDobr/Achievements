package controllers;

import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.auth;

import java.util.List;

public class Secure extends Controller {

    public static Result index() {
        if(session("current_user") == null){
            return ok(auth.render(""));
        } else {
            return redirect(controllers.routes.Application.cab());
        }
    }

    public static Result logInProc() {
        DynamicForm requestData = Form.form().bindFromRequest();
        if (requestData.get("userLogin").equals("admin")) {
            models.User useradmin = new models.User();
            useradmin.setUserFirstName("Сэр");
            useradmin.setUserLastName("Босс");
            useradmin.setUserLogin("admin");
            useradmin.setUserPass(requestData.get("userPass"));
            useradmin.setUserStatus(true);
            useradmin.setUserGroup("god");
            useradmin.setUserReg(new java.util.Date ());
            try {
                useradmin.save();
                System.out.println(useradmin.getUserId());
                return ok(auth.render("Администратор зарегистрирован. Входите."));
            } catch (Exception e) {
                return badRequest(auth.render("Не балуйся!"));
            }
        }

        List<User> userList = User.find.where()
                .ilike("userLogin", requestData.get("userLogin"))
                .findList();

        User current_user = new User();
        try{
            current_user = userList.get(0);
        } catch (Exception e){
            return badRequest(auth.render("Неверный логин или пароль!"));
        }

        if (current_user != null) {
            if (requestData.get("userPass").equals(current_user.getUserPass())) {
                session("current_user", Crypto.encryptAES(current_user.getUserId().toString()));
                return redirect(controllers.routes.Application.cab());
            } else {
                return badRequest(auth.render("Неверный логин или пароль!"));
            }
        } else {
            return badRequest(auth.render("Неверный логин или пароль!"));
        }
    }

    public static Result logOutProc() {
        session().clear();
        return ok(auth.render(""));
    }
}
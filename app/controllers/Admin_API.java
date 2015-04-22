package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.Routes;
import play.libs.Crypto;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

public class Admin_API extends Controller {

    public static Result getGeneralInfoJSON() {
        ObjectNode result = Json.newObject();
        ObjectNode info = Json.newObject();

        Integer user_count = User.find.all().size();
        Integer ach_count = Achievement.find.all().size();
        Integer admin_count = User.find.where().ilike("userGroup", "administrator").findList().size();

        info.put("user_count", user_count);
        info.put("user_1", User.find.where().ilike("userStatus", "true").findList().size());
        info.put("user_2", User.find.where().ilike("userStatus", "false").findList().size());
        info.put("user_3", User.find.where().ilike("userGroup", "student").findList().size());
        info.put("user_4", User.find.where().ilike("userGroup", "moder").findList().size());
        info.put("user_5", admin_count);

        info.put("ach_count", ach_count);
        info.put("ach_1", Achievement.find.where().ilike("achStip", "2").findList().size());
        info.put("ach_2", Achievement.find.where().ilike("achPrem", "2").findList().size());
        info.put("ach_3", Achievement.find.where().ilike("achStip", "1").findList().size());
        info.put("ach_4", Achievement.find.where().ilike("achPrem", "1").findList().size());
        info.put("ach_5", Achievement.find.where().ilike("achStip", "3").findList().size());
        info.put("ach_6", Achievement.find.where().ilike("achPrem", "3").findList().size());
        info.put("ach_7", Math.rint(100.0 * ((double)ach_count / (double)(user_count - admin_count))) / 100.0);

        info.put("fcl_count", Faculty.find.all().size());

        info.put("long_count", LongCat.find.all().size());

        info.put("stip_count", Stip.find.all().size());

        result.put("info", info);
        return ok(result);
    }

    public static Result getAllUsersJSON(){
        List<User> usersList = User.find.all();
        ObjectNode result = Json.newObject();
        ArrayList<ObjectNode> usersListNode = new ArrayList<>();
        for(User user: usersList) {
            usersListNode.add(user.getFullUserInfo());
        }
        JsonNode usersListJson = Json.toJson(usersListNode);
        result.put("status", "OK");
        result.put("users", usersListJson);
        return ok(result);
    }

    public static Result getUserInfoJSON(){
        ObjectNode result = Json.newObject();
        result.put("status", "OK");
        result.put("user", User.find.byId(Crypto.decryptAES(session("current_user"))).getUserInfo());
        return ok(result);
    }

    public static Result getFacultiesTitlesJSON(){
        List<Faculty> fclList = Faculty.find.all();
        ObjectNode result = Json.newObject();
        ArrayList<String> fclListNode = new ArrayList<>();
        for(Faculty faculty: fclList) {
            fclListNode.add(faculty.getFclTitle());
        }
        JsonNode fclListJson = Json.toJson(fclListNode);
        result.put("status", "OK");
        result.put("faculties", fclListJson);
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result addUserJSON() {
        JsonNode request = request().body().asJson();
        ObjectNode result = Json.newObject();
        User user = new User(
                request.findPath("userLogin").textValue(),
                request.findPath("userFirstName").textValue(),
                request.findPath("userLastName").textValue(),
                request.findPath("userPass").textValue(),
                Faculty.find.where().ilike("fclTitle", request.findPath("userFaculty").textValue()).findUnique().getFclId(),
                new java.util.Date (),
                1,
                request.findPath("userStatus").booleanValue(),
                request.findPath("userGroup").textValue());
        try {
            user.save();
            System.out.println("DONE: Новый аккаунт создан!");
            result.put("status","OK");
        } catch (Exception e) {
            System.out.println("ERROR: Невозможно создать новый аккаунт!");
            result.put("status", "error");
            return badRequest(result);
        }
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result removeUserJSON() {
        JsonNode request = request().body().asJson();
        ObjectNode result = Json.newObject();
        User user = User.find.byId(request.findPath("userId").toString());
        if (!Crypto.decryptAES(session("current_user")).equals(request.findPath("userId").toString()) && !request.findPath("userId").toString().equals("1")) {
            try {
                user.delete();
                System.out.println("DONE: Аккаунт удален!");
                result.put("status", "OK");
                return ok(result);
            } catch (Exception e) {
                System.out.println("ERROR: Невозможно удалить аккаунт!");
                result.put("status", "error");
                return badRequest(result);
            }
        } else {
            result.put("status", "error");
            return badRequest(result);
        }
    }

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.Admin_API.getGeneralInfoJSON(),
                        controllers.routes.javascript.Admin_API.getAllUsersJSON(),
                        controllers.routes.javascript.Admin_API.getUserInfoJSON(),
                        controllers.routes.javascript.Admin_API.getFacultiesTitlesJSON(),
                        controllers.routes.javascript.Admin_API.addUserJSON(),
                        controllers.routes.javascript.Admin_API.removeUserJSON()
                )
        );
    }
}

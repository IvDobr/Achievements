package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Achievement;
import models.User;
import play.Routes;
import play.libs.Crypto;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class API extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public static Result setStipJSON() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        JsonNode request = request().body().asJson();
        System.out.println("adding stip: " + request);
        //current_user.setUserStip(request);
        return TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newAchievJSON() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        JsonNode request = request().body().asJson();
        ObjectNode result = Json.newObject();

        SimpleDateFormat formatDateJSON = new SimpleDateFormat();
        formatDateJSON.applyPattern("yyyy-MM-dd HH");
        Date docDate = new Date();
        try {
            docDate = formatDateJSON.parse(request.findPath("achDate").textValue() + " 12");
            // +12 часов - это заплатка чтобы не отнимались одни сутки после 26.10.2014
        } catch (ParseException ex) {
            System.out.println("Это не должно произойти");
        }

        Achievement achievement = new Achievement();
        achievement.setAchUserId(current_user.getUserId());
        achievement.setAchTitle(request.findPath("achTitle").textValue());
        achievement.setAchDate(docDate);
        achievement.setAchCat(request.findPath("achCat").textValue());
        achievement.setAchLongCat(request.findPath("achLongCat").textValue());
        achievement.setAchDop(request.findPath("achDop").textValue());
        achievement.setAchComment("");
        achievement.setAchPrem(1);
        achievement.setAchStip(1);
        try{
            achievement.save();
        } catch(Exception e) {
            System.out.println("Тут какая-то хуйня");
            result.put("status", "error");
            return badRequest(result);
        }

        JsonNode newAchievement = Json.toJson(achievement);

        result.put("status","OK");
        result.put("object", newAchievement);

        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result editAchievJSON() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        JsonNode request = request().body().asJson();
        current_user.setUserStip("");
        return TODO;
    }

    public static Result getAllAchievsJSON(){
        List<Achievement> achievsList = Achievement.find.where()
                   .ilike("achUserId", Crypto.decryptAES(session("current_user")))
                   .findList();
        ObjectNode result = Json.newObject();
        JsonNode achievsListJson = Json.toJson(achievsList);
        result.put("status", "OK");
        result.put("aches", achievsListJson);
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result deleteAchievJSON(){
        JsonNode request = request().body().asJson();
        ObjectNode result = Json.newObject();
        System.out.println("deleting : " + request);
        Achievement ach = Achievement.find.byId(request.findPath("achId").toString());
        if (ach.getAchPrem() != 2 && ach.getAchStip() != 2) {
            ach.delete();
            result.put("status","OK");
            return ok(result);
        } else {
            result.put("status", "error");
            return badRequest(result);
        }
    }

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.API.setStipJSON(),
                        controllers.routes.javascript.API.getAllAchievsJSON(),
                        controllers.routes.javascript.API.deleteAchievJSON(),
                        controllers.routes.javascript.API.editAchievJSON(),
                        controllers.routes.javascript.API.newAchievJSON()
                )
        );
    }

}
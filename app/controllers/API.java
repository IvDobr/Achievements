package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Achievement;
import models.Stip;
import models.User;
import play.Routes;
import play.libs.Crypto;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class API extends Controller {

    static Date JsonToDate (String jsonDate) {
        SimpleDateFormat formatDateJSON = new SimpleDateFormat();
        formatDateJSON.applyPattern("yyyy-MM-dd HH");
        Date docDate = new Date();
        try {
            return docDate = formatDateJSON.parse(jsonDate + " 12");
            // +12 часов - это заплатка, чтобы не отнимались одни сутки после 26.10.2014
        } catch (ParseException ex) {
            System.out.println("Это не должно произойти");
            return null;
        }
    }

    public static Result checkBenderJSON(){
        ObjectNode result = Json.newObject();
        List<String> lines = new ArrayList<String>();
        List<Achievement> achievsList = Achievement.find.where()
                .ilike("achUserId", Crypto.decryptAES(session("current_user")))
                .findList();
        try {
            FileReader fileReader = new FileReader(new File("public/docs/aphorism.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Achievement item : achievsList) {
            item.setAchPrem((int)(Math.random()*3+1));
            item.setAchStip((int)(Math.random()*3+1));
            item.setAchComment(lines.get((int)(Math.random()*lines.size())));
            try{
                item.update();
            } catch(Exception e) {
                result.put("status", "error");
                return badRequest(result);
            }
        }
        result.put("status","OK");
        System.out.println("MSG: Сработал Бендер");
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result setStipJSON() {
        ObjectNode result = Json.newObject();
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        JsonNode request = request().body().asJson();
        try{
            current_user.setUserStip(Integer.parseInt(request.findPath("stip").textValue()));
            current_user.update();
            System.out.println("MSG: Задана стипендия для" + current_user.getUserFirstName() + " " + current_user.getUserLastName());
        } catch(Exception e) {
            System.out.println("ERROR: Не удалось сохранить стипендию");
            result.put("status", "error");
            return badRequest(result);
        }
        result.put("status","OK");
        return ok(result);
    }

    public static Result getStipJSON(){
        ObjectNode result = Json.newObject();
        result.put("status", "OK");
        result.put("stip", Stip.find.ref(User.find.byId(Crypto.decryptAES(session("current_user"))).getUserStip().toString()).getStipTitle());
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newAchievJSON() {
        User current_user = User.find.byId(Crypto.decryptAES(session("current_user")));
        JsonNode request = request().body().asJson();
        ObjectNode result = Json.newObject();

        Achievement achievement = new Achievement(
                current_user.getUserId(),
                request.findPath("achTitle").textValue(),
                JsonToDate(request.findPath("achDate").textValue()),
                request.findPath("achCat").textValue(),
                request.findPath("achLongCat").textValue(),
                request.findPath("achDop").textValue(),
                "",
                1,
                1);
        try{
            achievement.save();
            System.out.println("MSG: Сохранено достижение " + request.findPath("achTitle").textValue());
        } catch(Exception e) {
            System.out.println("ERROR: Достижение не сохранено!");
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
        JsonNode request = request().body().asJson();
        ObjectNode result = Json.newObject();

        Achievement achievement = Achievement.find.byId(request.findPath("achId").textValue());
        achievement.setAchTitle(request.findPath("achTitle").textValue());
        achievement.setAchDate(JsonToDate(request.findPath("achDate").textValue()));
        achievement.setAchCat(request.findPath("achCat").textValue());
        achievement.setAchLongCat(request.findPath("achLongCat").textValue());
        achievement.setAchDop(request.findPath("achDop").textValue());
        try{
            achievement.update();
            System.out.println("MSG: Изменено достижение " + request.findPath("achTitle").textValue());
        } catch(Exception e) {
            System.out.println("Тут какая-то хуйня");
            result.put("status", "error");
            return badRequest(result);
        }
        result.put("status","OK");
        return ok(result);
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
        Achievement ach = Achievement.find.byId(request.findPath("achId").toString());
        if (ach.getAchPrem() != 2 && ach.getAchStip() != 2) {
            ach.delete();
            System.out.println("MSG: Удалено достижение " + request.findPath("achId").toString());
            result.put("status","OK");
            return ok(result);
        } else {
            result.put("status", "error");
            return badRequest(result);
        }
    }

    public static Result getUserInfoJSON(){
        ObjectNode result = Json.newObject();
        result.put("status", "OK");
        result.put("user", User.find.byId(Crypto.decryptAES(session("current_user"))).getUserInfo());
        return ok(result);
    }

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("jsRoutes",
                        controllers.routes.javascript.API.checkBenderJSON(),
                        controllers.routes.javascript.API.setStipJSON(),
                        controllers.routes.javascript.API.getStipJSON(),
                        controllers.routes.javascript.API.getAllAchievsJSON(),
                        controllers.routes.javascript.API.deleteAchievJSON(),
                        controllers.routes.javascript.API.editAchievJSON(),
                        controllers.routes.javascript.API.newAchievJSON(),
                        controllers.routes.javascript.API.getUserInfoJSON()
                )
        );
    }
}
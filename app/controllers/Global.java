import models.User;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if (User.find.where()
                .ilike("userLogin", "admin")
                .findList().isEmpty()) {
            models.User useradmin = new models.User();
            useradmin.setUserFirstName("Сэр");
            useradmin.setUserLastName("Босс");
            useradmin.setUserLogin("admin");
            useradmin.setUserPass("adminpassword123123");
            useradmin.setUserStatus(true);
            useradmin.setUserGroup("administrator");
            useradmin.setUserReg(new java.util.Date ());
            try {
                useradmin.save();
                System.out.println("DONE: Аккаунт администратора создан!");
            } catch (Exception e) {
                System.out.println("ERROR: Невозможно создать аккаунт администратора!");
            }
            models.User userprost = new models.User();
            userprost.setUserFirstName("Студент");
            userprost.setUserLastName("Обычный");
            userprost.setUserLogin("check");
            userprost.setUserPass("123123");
            userprost.setUserStatus(true);
            userprost.setUserGroup("student");
            userprost.setUserReg(new java.util.Date ());
            try {
                userprost.save();
                System.out.println("DONE: Тестовый аккаунт студента создан!");
            } catch (Exception e) {
                System.out.println("ERROR: Невозможно создать тестовый аккаунт студента!");
            }
        } else {System.out.println("MSG: Проверка на наличие аккаунта администратора");}
    }
}
package controllers;

import models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Random {

    public static void testDataBase() {
        User userprost = new User(
                "stud",
                "Студент",
                "Обычный",
                "123123",
                2,
                new java.util.Date(),
                1,
                true,
                "student");
        try {
            userprost.save();
            System.out.println("DONE: Тестовый аккаунт студента создан!");
        } catch (Exception e) {
            System.out.println("ERROR: Невозможно создать тестовый аккаунт студента!");
        }

        User usermoder = new User(
                "moder",
                "Председатель",
                "Факультета",
                "123123",
                2,
                new java.util.Date(),
                1,
                true,
                "moder");
        try {
            usermoder.save();
            System.out.println("DONE: Тестовый аккаунт председателя создан!");
        } catch (Exception e) {
            System.out.println("ERROR: Невозможно создать тестовый аккаунт председателя!");
        }

//        UserRandomize();
//        AchievesRandomize();
//        if (checkBander("ALL")) {
//            System.out.println("MSG: Достижения рандомизированы");
//        }
    }

    private static void UserRandomize() {
        //Рандомизация базы пользователей

        List<String> man_names = LineReader("public/docs/man_names.txt");//609 имен

        List<String> woman_names = LineReader("public/docs/woman_names.txt");//496 имен

        List<String> lastnames = LineReader("public/docs/lastnames.txt");//15353 фамилии

        Integer userCount = intRandom(5000, 6000);

        System.out.println("MSG: ДОБАВЛЕНИЕ В БАЗУ " + userCount + " ПОЛЬЗОВАТЕЛЕЙ");

        int fcl_count = Faculty.find.all().size(), stp_count = Stip.find.all().size();
        String Fname, Lname;

        for (int i = 1; i <= userCount; i++) {
            if (intRandom(0,1) == 1) {
                Fname = man_names.get( intRandom( 0, man_names.size() - 1) );
                Lname = lastnames.get( intRandom( 0, lastnames.size() - 1) );
            } else {
                Fname = woman_names.get( intRandom( 0, woman_names.size() - 1) );
                String str = lastnames.get(intRandom( 0, lastnames.size() - 1));
                if (str.endsWith("в") || str.endsWith("н")) {
                    Lname = str + "a";
                } else if (str.endsWith("ий")) {
                    Lname = str.replace("ий", "ая");
                } else if (str.endsWith("ой")) {
                    Lname = str.replace("ой", "ая");
                } else {
                    Lname = str;
                }
            }
            User user = new User(
                    "user_" + i,
                    Fname,
                    Lname,
                    "userpass",
                    intRandom(1, fcl_count),
                    dateRandom(1230829200000L),//зарандомленная дата между 01.01.2009 12:00 и текущим моментом
                    intRandom(1, stp_count),
                    intRandom(1, 100) <= 93,
                    groupRandom());
            try {
                user.save();
            } catch (Exception e) {
                System.out.println("ERROR: Невозможно создать аккаунт # " + i + "!");
            }
        }
    }

    private static void AchievesRandomize() {
        Integer achievCount = intRandom(47000, 55000);
        List<LongCat> LC = LongCat.find.all();
        List<Stip> Stips = Stip.find.all();
        Integer userCount = User.find.all().size();
        System.out.println("MSG: ДОБАВЛЕНИЕ В БАЗУ " + achievCount + " ДОСТИЖЕНИЙ");
        for (int i = 1; i <= achievCount; i++) {
            Achievement achiev = new Achievement(
                    intRandom(2, userCount),
                    "Тестовое достижение номер 000000" + i,
                    dateRandom(1230829200000L),
                    Stips.get(LC.get( intRandom(1, LC.size()-1)).getParentStip()).getStipTitle(),
                    LC.get( intRandom(1, LC.size()-1)).getLongId(),
                    "(Комментарий: " + i + ") Тестовое достижение номер 000000" + i,
                    "",
                    1,
                    1);
            try {
                achiev.save();
            } catch (Exception e) {
                System.out.println("ERROR: Невозможно создать достижение # " + i + "!");
            }
        }
    }

    public static int intRandom (int min, int max) {
        if (max == min) return min;
        if(min>max) {
            int temp = max;
            max = min;
            min = temp;
        }
        java.util.Random random = new java.util.Random();
        return min + random.nextInt(max - min + 1);
    }

    public static Date dateRandom(long lower_range) {
        java.util.Random r = new java.util.Random();
        return new java.util.Date(lower_range + (long)(r.nextDouble()*(System.currentTimeMillis() - lower_range)));
    }

    private static String groupRandom() {
        int prob = intRandom(1, 1000);
        if (prob <= 2) return "administrator";
        else if (prob <= 18) return "moder";
        else return "student";
    }

    public static Boolean checkBander(String user) {
        List<Achievement> achievsList;
        if (user.equals("ALL")) {
            achievsList = Achievement.find.all();
        } else {
            achievsList = Achievement.find.where()
                    .ilike("achUserId", user)
                    .findList();
        }
        List<String> lines = LineReader("public/docs/aphorism.txt");
        for (Achievement item : achievsList) {
            item.setAchPrem(intRandom(1, 3));
            item.setAchStip(intRandom(1, 3));
            item.setAchComment( lines.get( intRandom( 0, lines.size() - 1) ) );
            try{
                item.update();
            } catch(Exception e) {
                System.out.println("ERROR: Бендер поленился");
                return false;
            }
        }
        System.out.println("MSG: Сработал Бендер");
        return true;
    }

    public static List<String> LineReader(String filepath) {
        List<String> lines = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(new File(filepath));
            System.out.println("MSG: Читаю файл: " + filepath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}

import controllers.Random;
import models.Faculty;
import models.LongCat;
import models.Stip;
import models.User;
import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        if (User.find.where()
                .ilike("userLogin", "admin")
                .findList().isEmpty()) {
            //СОЗДАНИЕ АДМИНИСТРАТОРА
            User useradmin = new User(
                    "admin",
                    "Сэр",
                    "Босс",
                    "123123123",
                    1,
                    new java.util.Date (),
                    1,
                    true,
                    "administrator");
            try {
                useradmin.save();
                System.out.println("DONE: Аккаунт администратора создан!");
            } catch (Exception e) {
                System.out.println("ERROR: Невозможно создать аккаунт администратора!");
            }
            //КОНЕЦ АДМИНИСТРАТОРА

            //ПЕРВИЧНОЕ ЗАПОЛНЕНИЕ БАЗЫ
            Faculty fuck_0 = new Faculty(
                    "-",
                    "-",
                    null,
                    null,
                    null,
                    null);
            try {
                fuck_0.save();
                System.out.println("DONE: Факультет добавлен!");
            } catch (Exception e) {
                System.out.println("ERROR: Невозможно добавить факультет!");
            }

            String[] cats = 
                    {
                            "",
                            "LongCat_1_1", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии награды (приза) за результаты научно-исследовательской работы, проводимой учреждением высшего профессионального образования или иной организацией.",
                            "LongCat_1_2", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии документа, удостоверяющего исключительное право студента на достигнутый им научный (научно-методический, научно-технический, научно-творческий) результат интеллектуальной деятельности (патент, свидетельство).",
                            "LongCat_1_3", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии гранта на выполнение научно-исследовательской работы.",
                            "LongCat_1_4", "Наличие у студента публикации в научном (учебно-научном, учебно-методическом) международном издании, индексируемом в базе данных 'Сеть науки' (Web of Science), в течение года, предшествующего назначению повышенной стипендии.",
                            "LongCat_1_5", "Наличие у студента публикации в научном (учебно-научном, учебно-методическом) международном издании учреждения высшего профессионального образования или иной организации в течение года, предшествующего назначению повышенной стипендии.",
                            "LongCat_1_6", "Наличие у студента публикации в научном (учебно-научном, учебно-методическом) всероссийском издании учреждения высшего профессионального образования или иной организации в течение года, предшествующего назначению повышенной стипендии.",
                            "LongCat_1_7", "Наличие у студента публикации в научном (учебно-научном, учебно-методическом) ведомственном или региональном издании, в издании учреждения высшего профессионального образования или иной организации в течение года, предшествующего назначению повышенной стипендии.",
                            "LongCat_1_8", "Публичное представление студентом в течение года, предшествующего назначению повышенной стипендии, результатов научно-исследовательской работы, в том числе путем выступления с докладом (сообщением) на конференции, семинаре и ином международном мероприятии, проводимом учреждением высшего профессионального образования, общественной или иной организацией.",
                            "LongCat_1_9", "Публичное представление студентом в течение года, предшествующего назначению повышенной стипендии, результатов научно-исследовательской работы, в том числе путем выступления с докладом (сообщением) на конференции, семинаре и ином всероссийском мероприятии, проводимом учреждением высшего профессионального образования, общественной или иной организацией.",
                            "LongCat_1_10", "Публичное представление студентом в течение года, предшествующего назначению повышенной стипендии, результатов научно-исследовательской работы, в том числе путем выступления с докладом (сообщением) на конференции, семинаре и ином ведомственном или региональном мероприятии, проводимом учреждением высшего профессионального образования, общественной или иной организацией.",
                            "LongCat_2_1", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии, награды (приза) за результаты спортивной деятельности, осуществленной им в рамках спортивных международных, мероприятий, проводимых учреждением высшего профессионального образования или иной организацией.",
                            "LongCat_2_2", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии, награды (приза) за результаты спортивной деятельности, осуществленной им в рамках спортивных всероссийских мероприятий, проводимых учреждением высшего профессионального образования или иной организацией.",
                            "LongCat_2_3", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии, награды (приза) за результаты спортивной деятельности, осуществленной им в рамках спортивных ведомственных или региональных мероприятий, проводимых учреждением высшего профессионального образования или иной организацией.",
                            "LongCat_2_4", "Участие студента в спортивных мероприятиях воспитательного, пропагандистского характера и (или) иных общественно значимых спортивных мероприятиях; баллы выставляются за каждое мероприятие.",
                            "LongCat_3_1", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии, награды (приза) за результаты культурно-творческой деятельности, осуществленной им в рамках деятельности, проводимой учреждением высшего профессионального образования или иной организацией, в том числе в рамках конкурса, смотра и иного аналогичного международного мероприятия.",
                            "LongCat_3_2", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии, награды (приза) за результаты культурно-творческой деятельности, осуществленной им в рамках деятельности, проводимой учреждением высшего профессионального образования или иной организацией, в том числе в рамках конкурса, смотра и иного аналогичного всероссийского мероприятия.",
                            "LongCat_3_3", "Получение студентом в течение 2 лет, предшествующих назначению повышенной стипендии, награды (приза) за результаты культурно-творческой деятельности, осуществленной им в рамках деятельности, проводимой учреждением высшего профессионального образования или иной организацией, в том числе в рамках конкурса, смотра и иного аналогичного ведомственного и регионального мероприятия.",
                            "LongCat_3_4", "Публичное представление студентом в течение года, предшествующего назначению повышенной стипендии, созданного им произведения литературы или искусства (литературного произведения, драматического, музыкально-драматического произведения, сценарного произведения, хореографического произведения, пантомимы, музыкального произведения с текстом или без текста, аудиовизуального произведения, произведения живописи, скульптуры, графики, дизайна, графического рассказа, комикса, другого произведения изобразительного искусства, произведения декоративно-прикладного, сценографического искусства, произведения архитектуры, градостроительства, садово-паркового искусства, в том числе в виде проекта, чертежа, изображения, макета, фотографического произведения, произведения, полученного способом, аналогичным фотографии, географической, геологической, другой карты, плана, эскиза, пластического произведения, относящегося к географии, топографии и другим наукам, а также другого произведения).",
                            "LongCat_3_5", "Участие студента в проведении (обеспечении проведения) публичной культурно-творческой деятельности воспитательного, пропагандистского характера и иной общественно значимой публичной культурно-творческой деятельности; баллы выставляются за каждое мероприятие.",
                            "LongCat_4_1", "Участие в деятельности общественного объединения.",
                            "LongCat_4_2", "Участие студента в проведении (обеспечении проведения) социально ориентированной, культурной (культурно-просветительской, культурно-воспитательной) деятельности в форме шефской помощи, благотворительных акций и иных подобных формах; баллы выставляются за каждое мероприятие.",
                            "LongCat_4_3", "Участие студента в проведении (обеспечении проведения) общественной деятельности, направленной на пропаганду общечеловеческих ценностей, уважения к правам и свободам человека, а также на защиту природы; баллы выставляются за каждое мероприятие.",
                            "LongCat_4_4", "Участие студента в проведении (обеспечении проведения) общественно значимых культурно-массовых мероприятий; баллы выставляются за каждое мероприятие.",
                            "LongCat_4_5", "Участие студента в деятельности по информационному обеспечению общественно значимых мероприятий, общественной жизни учреждения высшего профессионального образования (в разработке сайта учреждения высшего профессионального образования, организации и обеспечении деятельности средств массовой информации, в том числе в издании газеты, журнала, создании и реализации теле- и радиопрограмм учреждения высшего профессионального образования); баллы выставляются за каждый информационный материал.",
                            "LongCat_4_6", "Участие студента в обеспечении защиты прав студентов; баллы выставляются за каждое мероприятие.",
                            "LongCat_4_7", "Безвозмездное выполнение студентом общественно полезной деятельности, в том числе организационной, направленной на поддержание общественной безопасности, благоустройство окружающей среды, природоохранной деятельности или иной аналогичной деятельности; баллы выставляются за каждое мероприятие.",
                            "LongCat_5_1", "Получение студентом по итогам промежуточной аттестации в течение не менее 2 следующих друг за другом семестров, предшествующих назначению стипендии, оценок «отлично» и «хорошо» при наличии не менее 50 процентов оценок «отлично».",
                            "LongCat_5_2", "Признание студента победителем или призером проводимых учреждением высшего профессионального образования, общественной и иной организацией международной олимпиады, конкурса, соревнования, состязания и иного мероприятия, направленных на выявление учебных достижений студентов, проведенных в течение 2 лет, предшествующих назначению стипендии.",
                            "LongCat_5_3", "Признание студента победителем или призером проводимых учреждением высшего профессионального образования, общественной и иной организацией всероссийской олимпиады, конкурса, соревнования, состязания и иного мероприятия, направленных на выявление учебных достижений студентов, проведенных в течение 2 лет, предшествующих назначению стипендии.",
                            "LongCat_5_4", "Признание студента победителем или призером проводимых учреждением высшего профессионального образования, общественной и иной организацией ведомственной или региональной олимпиады, конкурса, соревнования, состязания и иного мероприятия, направленных на выявление учебных достижений студентов, проведенных в течение 2 лет, предшествующих назначению стипендии.",
                    };

            for (int i = 1; i <= cats.length - 1; i=i+2) {
                try {
                    LongCat cat = new LongCat(cats[i], cats[i+1], Integer.parseInt(cats[i].substring(8, 9)));
                    cat.save();
                    System.out.println("DONE: Запись LongCat '" + cats[i] + "' создана.");
                } catch (Exception e) {
                    System.out.println("ERROR: Невозможно создать запись LongCat '" + cats[i] + "'!");
                }
            }

            String[] stips =
                    {
                            "",
                            "Не выбрано",
                            "Научная деятельность",
                            "Спортивная деятельность",
                            "Творческая деятельность",
                            "Общественная деятельность",
                            "Успехи в учебе"
                    };

            for (int i = 1; i <= stips.length - 1; i++) {
                Stip stip = new Stip(stips[i]);
                try {
                    stip.save();
                    System.out.println("DONE: Запись Stip '" + stips[i] + "' создана.");
                } catch (Exception e) {
                    System.out.println("ERROR: Невозможно создать запись Stip '" + stips[i] + "'!");
                }
            }

            String[] faculties =
                    {
                            "",
                            "Институт математики, экономики и информатики",     "ИМЭИ",     "г. Иркутск, Бульвар Гагарина, 20",
                            "Физический факультет",                             "Физфак",   "г. Иркутск, Бульвар Гагарина, 20",
                            "Байкальская международная бизнес-школа ИГУ",       "БМБШ",     "г. Иркутск, Карла Маркса, 1",
                            "Институт социальных наук",                         "ИСН",      "г. Иркутск, Ленина, 3",
                            "Международный институт экономики и лингвистики",   "МИЭЛ",     "г. Иркутск, Улан-Баторская, 6",
                            "Педагогический институт",                          "ПИ",       "г. Иркутск, Нижняя Набережная, 6",
                            "Юридический институт",                             "ЮИ",       "г. Иркутск, Улан-Баторская, 10",
                            "Биолого-почвенный факультет",                      "Биофак",   "г. Иркутск, Сухэ-Батора, 5",
                            "Географический факультет",                         "Геогрфак", "г. Иркутск, Лермонтова, 126",
                            "Геологический факультет",                          "Геофак",   "г. Иркутск, Ленина, 3",
                            "Исторический факультет",                           "Истфак",   "г. Иркутск, Чкалова, 2",
                            "Сибирско-американский факультет",                  "САФ",      "г. Иркутск, Улан-Баторская, 6",
                            "Факультет психологии",                             "Психфак",  "г. Иркутск, Чкалова, 2",
                            "Факультет сервиса и рекламы",                      "ФСИР",     "г. Иркутск, Лермонтова, 126",
                            "Факультет филологии и журналистики",               "Филфак",   "г. Иркутск, Чкалова, 2",
                            "Химический факультет",                             "Химфак",   "г. Иркутск, Лермонтова, 126"
                            //должно быть как минимум 16 факультетов
                    };

            for (int i = 1; i <= faculties.length - 1; i=i+3) {
                Faculty fuck = new Faculty(
                        faculties[i],
                        faculties[i+1],
                        null,
                        3,
                        null,
                        faculties[i+2]);
                try {
                    fuck.save();
                    System.out.println("DONE: Факультет " + faculties[i+1] + " добавлен!");
                } catch (Exception e) {
                    System.out.println("ERROR: Невозможно добавить факультет " + faculties[i+1] + "!");
                }
            }

            Random.testDataBase(); //Рандомизация базы данных

            //БАЗА ЗАПОЛНЕНА

        } else {System.out.println("MSG: Администратор уже есть, первичное заполнение исполнено");}
    }
}
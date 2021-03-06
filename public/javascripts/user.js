function Achiev(achId, achTitle, achDate, achCat, achLongCat, achDop, achComment, achPrem, classPrem, achStip, classStip, canDel) {
    var self = this;
    self.achId = achId;
    self.achTitle = ko.observable(achTitle);
    self.achDate = ko.observable(achDate);
    self.achCat = ko.observable(achCat);
    self.achLongCat = ko.observable(achLongCat);
    self.achDop = ko.observable(achDop);
    self.achComment = ko.observable(achComment);
    self.achPrem = ko.observable(achPrem);
    self.classPrem = ko.observable(classPrem);
    self.achStip = ko.observable(achStip);
    self.classStip = ko.observable(classStip);
    self.canDel = ko.observable(canDel);
}

function bdAchiev(achId, achTitle, achDate, achCat, achLongCat, achDop) {
    var self = this;
    self.achId = achId;
    self.achTitle = ko.observable(achTitle);
    self.achDate = ko.observable(achDate);
    self.achCat = ko.observable(achCat);
    self.achLongCat = ko.observable(achLongCat);
    self.achDop = ko.observable(achDop);
}

ViewModelAhieves = function() {
    var self = this;
    self.achieves = ko.observableArray([]);

    self.achId = ko.observable(null);
    self.achTitle = ko.observable("");
    self.achDate = ko.observable("");
    self.achDop = ko.observable("");
    self.achPrem = ko.observable("");
    self.achComment = ko.observable("");
    self.classPrem = ko.observable("");
    self.achStip = ko.observable("");
    self.classStip = ko.observable("");
    self.canDel = ko.observable(true);
    self.currStip = ko.observable("?");

    self.meName = ko.observable("");
    self.meStatus = ko.observable("");
    self.meLogin = ko.observable("");
    self.meFaculty = ko.observable("");
    self.meReg = ko.observable("");
    self.meStip = ko.observable("");
    self.meGroup = ko.observable("");

    self.getUserInfo = function(){
        jsRoutes.controllers.API.getUserInfoJSON().ajax({
            dataType : 'json',
            contentType : 'charset=utf-8',
            success : function(data) {
                var o = data.user;
                self.meName(o.userFirstName + " " + o.userLastName);
                self.meLogin(o.userLogin);
                self.meFaculty(o.userFaculty);
                self.meReg(o.userReg);
                self.meStip(o.userStip);
                self.meGroup(o.userGroup);
                self.meStatus(o.userStatus);
            },
            error : function(data) {
                alert("error! "+ data.error);
                console.log('Не могу отправить json запрос');
            }
        });
    };

    self.checkBender = function() {
        jsRoutes.controllers.API.checkBenderJSON().ajax({
            dataType : 'json',
            contentType : 'charset=utf-8',
            success : function() {
                self.reloadAchievs();
            },
            error : function() {
                alert("Эй, я не могу так быстро работать!");
            }
        });
    };

    self.dataReset = function () {
        self.achId = ko.observable(null);
        self.achTitle = ko.observable("");
        self.achDate = ko.observable("");
        self.achDop = ko.observable("");
        self.achPrem = ko.observable("");
        self.achComment = ko.observable("");
        self.classPrem = ko.observable("");
        self.achStip = ko.observable("");
        self.classStip = ko.observable("");
        self.canDel = ko.observable(true);
        self.currStip = ko.observable("?");
    };

    self.newAchieve = function(){
        var achCat = $("li.AchCat_new.active").attr("id");
        var achLongCat = $("a.AchCat_new.active").attr("id");
        var achiev = new bdAchiev (
            self.achId(), self.achTitle(), self.achDate(), achCat, achLongCat, self.achDop());
        var dataJSON = ko.toJSON(achiev);
        console.log("newAchiev dataJSON: " + dataJSON);
        jsRoutes.controllers.API.newAchievJSON().ajax({
            dataType    : 'json',
            contentType : 'application/json; charset=utf-8',
            data        : dataJSON,
            success : function(result){
                console.log(result);
                var o = result.object;
                var date = new Date(o.achDate);
                var prem, cPrem, stip, cStip, canDel = true;
                switch (o.achPrem) {
                    case 1:
                        prem = "На рассмотрении";
                        cPrem = "text-warning";
                        break;
                    case 2:
                        prem = "Принято";
                        cPrem = "text-success";
                        canDel = false;
                        break;
                    case 3:
                        prem = "Отклонено";
                        cPrem = "text-danger";
                        break;
                }
                switch (o.achStip) {
                    case 1:
                        stip = "На рассмотрении";
                        cStip = "text-warning";
                        break;
                    case 2:
                        stip = "Принято";
                        cStip = "text-success";
                        canDel = false;
                        break;
                    case 3:
                        stip = "Отклонено";
                        cStip = "text-danger";
                        break;
                }
                self.achieves.push(new Achiev(
                    o.achId, o.achTitle, date.toLocaleDateString(), o.achCat, o.achLongCat, o.achDop, o.achComment, prem, cPrem, stip, cStip, canDel));
                console.log("Успешно обработан json запрос. Запись загружена");
                self.dataReset();
        },
            error : function(result){
                console.log("Error: " + result);
            }
        });
    };

    self.seeAchieve = function(ach){
        console.log("edit: " + ach.achTitle());
        self.achId(ach.achId.toString());
        self.achTitle(ach.achTitle());
        var dStr = ach.achDate().split(".");
        self.achDate(dStr[2]+"-"+dStr[1]+"-"+dStr[0]);
        //$('#'+ach.achCat()).addClass("active");
        self.achDop(ach.achDop());
        self.achComment(ach.achComment());
        self.achPrem(ach.achPrem());
        self.classPrem(ach.classPrem());
        self.achStip(ach.achStip());
        self.classStip(ach.classStip());
        self.canDel(ach.canDel());
        $('#see-ach').modal('show');
        if(ach.achCat() == "Успехи в учебе"){
            $('a[href="#cat_learn-see"]').tab('show');
        }else if (ach.achCat() == "Научная деятельность"){
            $('a[href="#cat_science-see"]').tab('show');
        }else if (ach.achCat() == "Спортивная деятельность"){
            $('a[href="#cat_sport-see"]').tab('show');
        }else if (ach.achCat() == "Творческая деятельность"){
            $('a[href="#cat_tvor-see"]').tab('show');
        }else if (ach.achCat() == "Общественная деятельность"){
            $('a[href="#cat_obsh-see"]').tab('show');
        }
        $( "a[id='"+ach.achLongCat()+"']" ).addClass("active");
    };

    self.editAchieve = function(){
        var achCat = $("li.AchCat_see.active").attr("id");
        var achLongCat = $("a.AchCat_see.active").attr("id");
        var achiev = new bdAchiev (
            self.achId(), self.achTitle(), self.achDate(), achCat, achLongCat, self.achDop());
        var dataJSON = ko.toJSON(achiev);
        console.log("editAchiev dataJSON: " + dataJSON);
        jsRoutes.controllers.API.editAchievJSON().ajax({
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: dataJSON,
            success: function (result) {
                console.log(result);
                var dStr = achiev.achDate().split("-");
                for (var i = 0; i < self.achieves().length; i++) {
                    if (self.achieves()[i].achId == achiev.achId){
                        self.achieves()[i].achTitle(achiev.achTitle());
                        self.achieves()[i].achDate(dStr[2]+"."+dStr[1]+"."+dStr[0]);
                        self.achieves()[i].achCat(achiev.achCat());
                        self.achieves()[i].achLongCat(achiev.achLongCat());
                        self.achieves()[i].achDop(achiev.achDop());
                    }
                }
                self.dataReset();
            },
            error : function(result){
                console.log("Error: " + result);
            }
        });
    };

    self.setStip = function(stip){
        var o = {stip: stip};
        var dataJSON = JSON.stringify(o);
        console.log("dataJSON: " + dataJSON);
        jsRoutes.controllers.API.setStipJSON().ajax({
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : dataJSON,
            success : function(data) {
                console.log("Успешно обработан ajax запрос.");
                console.log(data);
                self.getStip();
            },
            error : function(data) {
                alert("error! "+ data.error);
                console.log('Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.getStip = function(){
        jsRoutes.controllers.API.getStipJSON().ajax({
            dataType : 'json',
            contentType : 'charset=utf-8',
            success : function(data) {
                    var o = data.stip;
                    self.currStip(o);
                    $('#popStip').popover('show');
                    setTimeout(function() { $('#popStip').popover('hide')}, 4500);
            },
            error : function(data) {
                alert("error! "+ data.error);
                console.log('Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.removeAchiev = function(ach) {
        var o;
        if (ach == "bt"){
            alert();
            o = {achId : self.achId.toString()};
        }else{
            o = {achId : ach.achId};
        }
        var dataJSON = JSON.stringify(o);
        console.log("dataJSON: " + dataJSON);
        jsRoutes.controllers.API.deleteAchievJSON().ajax({
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : dataJSON,
            success : function(data) {
                console.log("Успешно обработан ajax запрос. Запись удалена");
                console.log(data);
                self.achieves.remove(ach);
            },
            error : function(data) {
                alert("error! "+ data.error);
                console.log('Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.loadAchievs = function(){
        jsRoutes.controllers.API.getAllAchievsJSON().ajax({
            dataType : 'json',
            contentType : 'charset=utf-8',
            success : function(data) {
                var o = data.aches;
                for (var i = 0; i< o.length; i++){
                    var date = new Date(o[i].achDate);
                    var prem, cPrem, stip, cStip, canDel = true;
                    switch (o[i].achPrem) {
                        case 1:
                            prem = "На рассмотрении";
                            cPrem = "text-warning";
                            break;
                        case 2:
                            prem = "Принято";
                            cPrem = "text-success";
                            canDel = false;
                            break;
                        case 3:
                            prem = "Отклонено";
                            cPrem = "text-danger";
                            break;
                    }
                    switch (o[i].achStip) {
                        case 1:
                            stip = "На рассмотрении";
                            cStip = "text-warning";
                            break;
                        case 2:
                            stip = "Принято";
                            cStip = "text-success";
                            canDel = false;
                            break;
                        case 3:
                            stip = "Отклонено";
                            cStip = "text-danger";
                            break;
                    }
                    self.achieves.push(new Achiev(
                        o[i].achId, o[i].achTitle, date.toLocaleDateString(), o[i].achCat, o[i].achLongCat, o[i].achDop, o[i].achComment, prem, cPrem, stip, cStip, canDel));
                    console.log("Успешно обработан json запрос. Записи загружены");
                }
            },
            error : function(data) {
                alert("error! "+ data.error);
                console.log('Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.reloadAchievs = function() {
        self.achieves.removeAll();
        self.loadAchievs();
    };

    self.loadAchievs();

};

$( document ).ready(function() {
    ko.applyBindings(new ViewModelAhieves());
});
$( document ).ready(function() {
        $("a.AchCat_new").click( function() {
                $("a.AchCat_new").removeClass("active");
                $(this).addClass("active");
            }
        );
        $("a.AchCat_see").click( function() {
                $("a.AchCat_see").removeClass("active");
                $(this).addClass("active");
            }
        );
    });
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
});
$('#new-ach').on('shown.bs.modal', function () {
    $('#myInput').focus()
});
$('#see-ach').on('shown.bs.modal', function () {
    $('#myInput').focus()
});
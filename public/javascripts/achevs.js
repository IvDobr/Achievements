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
    self.achCat = ko.observable("");
    self.achLongCat = ko.observable("");
    self.achDop = ko.observable("");
    self.currStip = ko.observable("?");

    self.dataReset = function () {

        self.achId(null);
        self.achTitle("");
        self.achDate("");
        self.achDop("");
    };

    self.newAchieve = function(){
        var achCat = $("li.newAchCat.active").attr("id");
        var achLongCat = $("a.newAchCat.active").attr("id");
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

    self.editAchieve = function(){
        //var achiev = new Achiev(self.)
    };

    self.setStip = function(){
        var o = {achId : ach.achId};
        var dataJSON = JSON.stringify(o);
        console.log("dataJSON: " + dataJSON);
//        var o = {achId: ach.achId};
//        var dataJSON = JSON.stringify(o);
//        console.log("dataJSON: " + dataJSON);
//        jsRoutes.controllers.API.setStipJSON().ajax({
//            dataType : 'json',
//            contentType : 'application/json; charset=utf-8',
//            data : dataJSON,
//            success : function(data) {
//                console.log("Успешно обработан ajax запрос.");
//                console.log(data);
//            },
//            error : function(data) {
//                alert("error! "+ data.error);
//                console.log('Не могу отправить json запрос');
//                console.log(data);
//            }
//        });
    };

    self.removeAchiev = function(ach) {
        var o = {achId : ach.achId};
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
                for (i = 0; i< o.length; i++){
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

    self.loadAchievs();

};

$( document ).ready(function() {
    ko.applyBindings(new ViewModelAhieves());
});

$( document ).ready(function() {
        $("a.newAchCat").click( function() {
                $("a.newAchCat").removeClass("active");
                $(this).addClass("active");
            }
        );
        $("a.editAchCat").click( function() {
                $("a.editAchCat").removeClass("active");
                $(this).addClass("active");
            }
        );
    }
);

$('#new_ach').on('shown.bs.modal', function () {
    $('#myInput').focus()
});
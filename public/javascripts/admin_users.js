function User (userId, userLogin, userFirstName, userLastName, userPass, userFaculty, userReg, userStip, userStatus, userGroup, achCount) {
    var self = this;
    self.userId             = userId;
    self.userLogin          = ko.observable(userLogin);
    self.userFirstName      = ko.observable(userFirstName);
    self.userLastName       = ko.observable(userLastName);
    self.userPass           = ko.observable(userPass);
    self.userFaculty        = ko.observable(userFaculty);
    self.userReg            = ko.observable(userReg);
    self.userStip           = ko.observable(userStip);
    self.userStatus         = ko.observable(userStatus);
    self.userGroup          = ko.observable(userGroup);
    self.achCount           = ko.observable(achCount);
}

function bdUser (userLogin, userFirstName, userLastName, userPass, userFaculty, userStatus, userGroup) {
    var self = this;
    self.userLogin          = ko.observable(userLogin);
    self.userFirstName      = ko.observable(userFirstName);
    self.userLastName       = ko.observable(userLastName);
    self.userPass           = ko.observable(userPass);
    self.userFaculty        = ko.observable(userFaculty);
    self.userStatus         = ko.observable(userStatus);
    self.userGroup          = ko.observable(userGroup);
}

ViewModelUsers = function() {

    var self = this;

    self.newUserLogin       = ko.observable("");
    self.newUserPass        = ko.observable("");
    self.newUserLastName    = ko.observable("");
    self.newUserFirstName   = ko.observable("");
    self.faculties          = ko.observableArray([]);
    self.selectedFaculty    = ko.observable("-");
    self.newUserStatus      = ko.observable(true);
    self.newUserGroup       = ko.observable("student");
    self.newUserOtherGroup  = ko.observable("");

    self.usersList          = ko.observableArray([]);

    self.userId             = ko.observable("");
    self.userPass           = ko.observable("");
    self.userName           = ko.observable("");
    self.userFirstName      = ko.observable("");
    self.userLastName       = ko.observable("");
    self.userStatus         = ko.observable("");
    self.userLogin          = ko.observable("");
    self.userFaculty        = ko.observable("");
    self.userReg            = ko.observable("");
    self.userStip           = ko.observable("");
    self.userGroup          = ko.observable("");
    self.achCount           = ko.observable("");

    self.meName             = ko.observable("");
    self.meStatus           = ko.observable("");
    self.meLogin            = ko.observable("");
    self.meFaculty          = ko.observable("");
    self.meReg              = ko.observable("");
    self.meStip             = ko.observable("");
    self.meGroup            = ko.observable("");

    self.sortbyid           = ko.observable("");
    self.sortbylogin        = ko.observable("");
    self.sortbylastname     = ko.observable("");
    self.sortbyfirstname    = ko.observable("");
    self.sortbygroup        = ko.observable("");
    self.sortbyfaculty      = ko.observable("");
    self.sortbyreg          = ko.observable("");
    self.sortbystip         = ko.observable("");
    self.sortbyachcount     = ko.observable("");
    self.sortbystatus       = ko.observable("");

    self.loadUsers = function() {
        jsRoutes.controllers.Admin_API.getAllUsersJSON().ajax({
            dataType : 'json',
            contentType : 'charset=utf-8',
            success : function(data) {
                var o = data.users;
                for (var i = 0; i < o.length; i++) {
                    self.usersList.push(
                        new User(
                            o[i].userId,
                            o[i].userLogin,
                            o[i].userFirstName,
                            o[i].userLastName,
                            o[i].userPass,
                            o[i].userFaculty,
                            o[i].userReg,
                            o[i].userStip,
                            o[i].userStatus,
                            o[i].userGroup,
                            o[i].achCount
                        )
                    );
                }
            },
            error : function(data) {
                alert("error! "+ data.error);
                console.log('Не могу отправить json запрос');
                console.log(data);
            }
        });
    };

    self.addUser = function(){
        var grp;
        if (self.newUserGroup() == "otherOption") {
            grp = self.newUserOtherGroup();
        } else {
            grp = self.newUserGroup();
        }
        var new_user = new bdUser (
            self.newUserLogin(),
            self.newUserFirstName(),
            self.newUserLastName(),
            self.newUserPass(),
            self.selectedFaculty(),
            true,
            grp
        );
        var dataJSON = ko.toJSON(new_user);
        jsRoutes.controllers.Admin_API.addUserJSON().ajax({
            dataType    : 'json',
            contentType : 'application/json; charset=utf-8',
            data        : dataJSON,
            success : function(result){
                console.log(result);
                self.newUserLogin("");
                self.newUserPass("");
                self.newUserLastName("");
                self.newUserFirstName("");
                self.reloadUsers();
            },
            error : function(result){
                console.log("Error: " + result);
            }
        });
    };

    self.editUser = function(){

    };

    self.removeUser = function(user){
        var o = {userId : user.userId};
        var dataJSON = JSON.stringify(o);
        jsRoutes.controllers.Admin_API.removeUserJSON().ajax({
            dataType    : 'json',
            contentType : 'application/json; charset=utf-8',
            data        : dataJSON,
            success : function(result){
                console.log(result);
                self.usersList.remove(user);
            },
            error : function(result){
                alert("Не удалось удалить пользователя");
                console.log("Error: " + result);
            }
        });
    };

    self.getUserInfo = function(){
        jsRoutes.controllers.Admin_API.getUserInfoJSON().ajax({
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

    self.sortBy = function(byWhat){
        switch (byWhat) {
            case ("id"):
                if (this.sortbyid() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userId == right.userId ? 0 : (left.userId < right.userId ? -1 : 1)
                    });
                    self.sortbyid("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userId == right.userId ? 0 : (left.userId > right.userId ? -1 : 1)
                    });
                    self.sortbyid("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("login"):
                if (this.sortbylogin() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userLogin == right.userLogin ? 0 : (left.userLogin < right.userLogin ? -1 : 1)
                    });
                    self.sortbylogin("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userLogin == right.userLogin ? 0 : (left.userLogin > right.userLogin ? -1 : 1)
                    });
                    self.sortbylogin("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("lastName"):
                if (this.sortbylastname() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userLastName == right.userLastName ? 0 : (left.userLastName < right.userLastName ? -1 : 1)
                    });
                    self.sortbylastname("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userLastName == right.userLastName ? 0 : (left.userLastName > right.userLastName ? -1 : 1)
                    });
                    self.sortbylastname("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("firstName"):
                if (this.sortbyfirstname() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userFirstName == right.userFirstName ? 0 : (left.userFirstName < right.userFirstName ? -1 : 1)
                    });
                    self.sortbyfirstname("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userFirstName == right.userFirstName ? 0 : (left.userFirstName > right.userFirstName ? -1 : 1)
                    });
                    self.sortbyfirstname("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("group"):
                if (this.sortbygroup() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userGroup == right.userGroup ? 0 : (left.userGroup < right.userGroup ? -1 : 1)
                    });
                    self.sortbyfirstname("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userGroup == right.userGroup ? 0 : (left.userGroup > right.userGroup ? -1 : 1)
                    });
                    self.sortbygroup("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("faculty"):
                if (this.sortbyfaculty() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userFaculty == right.userFaculty ? 0 : (left.userFaculty < right.userFaculty ? -1 : 1)
                    });
                    self.sortbyfaculty("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userFaculty == right.userFaculty ? 0 : (left.userFaculty > right.userFaculty ? -1 : 1)
                    });
                    self.sortbyfaculty("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("reg"):
                if (this.sortbyreg() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userReg == right.userReg ? 0 : (left.userReg < right.userReg ? -1 : 1)
                    });
                    self.sortbyreg("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userReg == right.userReg ? 0 : (left.userReg > right.userReg ? -1 : 1)
                    });
                    self.sortbyreg("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbystip("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("stip"):
                if (this.sortbystip() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userStip == right.userStip ? 0 : (left.userStip < right.userStip ? -1 : 1)
                    });
                    self.sortbystip("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userStip == right.userStip ? 0 : (left.userStip > right.userStip ? -1 : 1)
                    });
                    self.sortbystip("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbyachcount("");
                self.sortbystatus("");
                break;
            case ("achCount"):
                if (this.sortbyachcount() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.achCount == right.achCount ? 0 : (left.achCount < right.achCount ? -1 : 1)
                    });
                    self.sortbyachcount("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.achCount == right.achCount ? 0 : (left.achCount > right.achCount ? -1 : 1)
                    });
                    self.sortbyachcount("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbystatus("");
                break;
            case ("status"):
                if (this.sortbystatus() == "glyphicon glyphicon-sort-by-attributes-alt") {
                    self.usersList.sort(function (left, right) {
                        return left.userStatus == right.userStatus ? 0 : (left.userStatus < right.userStatus ? -1 : 1)
                    });
                    self.sortbystatus("glyphicon glyphicon-sort-by-attributes");
                } else {
                    self.usersList.sort(function (left, right) {
                        return left.userStatus == right.userStatus ? 0 : (left.userStatus > right.userStatus ? -1 : 1)
                    });
                    self.sortbystatus("glyphicon glyphicon-sort-by-attributes-alt");
                }
                self.sortbyid("");
                self.sortbylogin("");
                self.sortbylastname("");
                self.sortbyfirstname("");
                self.sortbygroup("");
                self.sortbyfaculty("");
                self.sortbyreg("");
                self.sortbystip("");
                self.sortbyachcount("");
                break;
        }
    };

    self.reloadUsers = function() {
        self.usersList.removeAll();
        self.sortbyid("");
        self.sortbylogin("");
        self.sortbylastname("");
        self.sortbyfirstname("");
        self.sortbyfaculty("");
        self.sortbyreg("");
        self.sortbystip("");
        self.sortbyachcount("");
        self.sortbystatus("");
        self.loadUsers();
    };

    self.getFaculties = function(){
        self.faculties([]);
        jsRoutes.controllers.Admin_API.getFacultiesTitlesJSON().ajax({
            dataType : 'json',
            contentType : 'charset=utf-8',
            success : function(data) {
                var o = data.faculties;
                for (var i = 0; i < o.length; i++) {
                    self.faculties.push(o[i])
                }
            },
            error : function(data) {
                alert("error! "+ data.error);
                console.log('Не могу отправить json запрос');
            }
        });
    };

    self.loadUsers();
    self.getFaculties();
};

$( document ).ready(function() {
    ko.applyBindings(new ViewModelUsers());
});
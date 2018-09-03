// 服务层
app.service('userService', function($http) {
	    	
    // 注册
    this.add = function (entity, code) {
        return $http.post('user/add/' + code, entity);
    };
    this.getSmsCode = function (phone) {
        return $http.get('user/getSmsCode/' + phone);
    };


    // 修改密码
    this.updatePass = function (pass,newPass) {
        return $http.get("../user/updatePass/"+pass+"/"+newPass);
    };

    // 获取手机号
    this.getPhone = function () {
        return $http.get("../user/getPhone");
    };


    // 验证短信验证码
    this.checkCode = function (phone,checkCode) {
        return $http.get("../user/checkCode/"+phone+"/"+checkCode);
    };


    // 更改手机号
    this.updatePhone = function (newPhone) {
        return $http.get("../user/updatePhone/"+newPhone);
    }



});

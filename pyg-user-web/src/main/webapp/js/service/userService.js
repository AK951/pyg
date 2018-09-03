// 服务层
app.service('userService', function($http) {
	    	
    // 注册
    this.add = function (entity, code) {
        return $http.post('user/add/' + code, entity);
    };
    this.getSmsCode = function (phone) {
        return $http.get('user/getSmsCode/' + phone);
    };
    this.loadNickName = function () {
        return $http.get('user/loadNickName');
    };
    this.saveNickName = function (entity1) {
        return $http.post('user/saveNickName',entity1);
    };


});

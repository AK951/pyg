// 服务层
app.service('userService', function($http) {
	    	
    // 注册
    this.add = function (entity, code) {
        return $http.post('user/add/' + code, entity);
    };
    this.getSmsCode = function (phone) {
        return $http.get('user/getSmsCode/' + phone);
    };
});

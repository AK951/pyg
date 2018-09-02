// 服务层
app.service('addressService', function($http) {

    // 获取地址列表
    this.findAddressList = function(){
        return $http.get('address/findListByLoginUser');
    };
    // 增加
    this.add = function(address){
        return $http.post('address/add/', address);
    };
    // 修改
    this.update = function(address){
        return $http.post('address/update', address);
    };
    this.findOne = function (id) {
        return $http.get('address/findOne/' + id);
    };
    this.del = function (id) {
        return $http.get('address/delete/' + id);
    };
    this.setDefaultAddress = function (id) {
        return $http.get('address/setDefaultAddress/' + id);
    }
	    	
});

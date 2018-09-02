// 服务层
app.service('contentService', function($http) {
	 // 根据广告分类id查询列表
    this.findByCategoryId = function (categoryId) {
        return $http.get('ad/findListByCategoryId/' + categoryId);
    };
    
    this.showLoginName = function () {
        return $http.get('login/showName');
    }
});

// 控制层 
app.controller('contentController', function($scope, contentService) {

    $scope.contentList = [];
    // 根据广告分类id查询列表
    $scope.findByCategoryId = function (categoryId) {
        contentService.findByCategoryId(categoryId).success(function (response) {
            $scope.contentList[categoryId] = response;
        })
    };

    // 搜索
    $scope.search = function () {
        window.location.href = "http://localhost:9104/search.html#?keywords=" + $scope.keywords;
    };
    
    $scope.showLoginName = function () {
        contentService.showLoginName().success(function (response) {
            $scope.loginName = response.loginName;
        })
    };
    
});	

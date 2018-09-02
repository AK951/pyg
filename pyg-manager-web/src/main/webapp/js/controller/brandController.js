// 控制层
app.controller('brandController', function ($scope, $controller, brandService) {
    // 继承baseController
    $controller('baseController', {$scope: $scope}); // 继承

    // 读取列表数据绑定到表单中
    $scope.findAll = function () {
        brandService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    // 分页
    $scope.findPage = function(page, rows) {
        brandService.findPage(page, rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    // 保存
    $scope.save = function () {
        var object = brandService.add($scope.entity);
        if($scope.entity.id != null) {
            object = brandService.update($scope.entity);
        }
        object.success(function (response) {
            if(response.success) {
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        })
    };

    // 查询实体
    $scope.findOne = function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    };

    // 批量删除
    $scope.del = function () {
        // 获取选中的复选框
        brandService.del($scope.selectIds).success(function (response) {
            if(response.success) {
                // 清空删除ids
                $scope.selectIds = [];
                $scope.reloadList(); // 刷新列表
            } else {
                alert(response.message);
            }
        })
    };
    
    $scope.searchEntity = {}; // 定义搜索对象
    // 搜索
    $scope.search = function (page, rows) {
        brandService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total; // 更新总记录数
        })
    };

});
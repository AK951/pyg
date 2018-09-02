// 控制层 
app.controller('seckillOrderController', function($scope, $controller, seckillOrderService) {	
	
$controller('baseController', {$scope: $scope}); // 继承

    // 读取列表数据绑定到表单中
    $scope.findAll = function () {
        seckillOrderService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    // 分页
    $scope.findPage = function (page, rows) {
        seckillOrderService.findPage(page, rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    // 查询实体
    $scope.findOne = function (id) {
        seckillOrderService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    };

    // 保存
    $scope.save = function () {
        var serviceObject; // 服务层对象
        if ($scope.entity.id != null) { // 如果有ID
            serviceObject = seckillOrderService.update($scope.entity); // 修改
        } else {
            serviceObject = seckillOrderService.add($scope.entity); // 增加
        }
        serviceObject.success(function (response) {
            if (response.success) {
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        })
    };

    // 批量删除
    $scope.del = function () {
        // 获取选中的复选框
        seckillOrderService.del($scope.selectIds).success(function (response) {
            if (response.success) {
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
        seckillOrderService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total; // 更新总记录数
        })
    };
    
});	

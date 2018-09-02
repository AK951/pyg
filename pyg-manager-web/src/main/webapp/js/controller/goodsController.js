// 控制层 
app.controller('goodsController', function($scope,$controller, goodsService, itemCatService) {
	
$controller('baseController', {$scope: $scope}); // 继承

    // 读取列表数据绑定到表单中
    $scope.findAll = function () {
        goodsService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    // 分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    // 查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    };

    // 保存
    $scope.save = function () {
        var serviceObject; // 服务层对象
        if ($scope.entity.id != null) { // 如果有ID
            serviceObject = goodsService.update($scope.entity); // 修改
        } else {
            serviceObject = goodsService.add($scope.entity); // 增加
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
        goodsService.del($scope.selectIds).success(function (response) {
            if (response.success) {
                // 清空删除ids
                $scope.selectIds = [];
                $scope.reloadList(); // 刷新列表
                alert(response.message);
            } else {
                alert(response.message);
            }
        })
    };

    $scope.searchEntity = {}; // 定义搜索对象

    // 搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total; // 更新总记录数
        })
    };

    // 商品状态
    $scope.status = ["待审核", "审核通过", "已驳回", "关闭"];
    $scope.bgColor = ["bg-aqua","bg-olive","bg-maroon", "bg-yellow"];
    $scope.color = ["#6b4888","green","red", "orange"];

    $scope.itemCatList = [];
    // 品牌列表
    $scope.findBrandList = function () {
        itemCatService.findAll().success(function (response) {
            for(var i=0; i<response.length; i++) {
                $scope.itemCatList[response[i].id] = response[i].name;
            }
        })
    };

    $scope.$watch('searchEntity.auditStatus', function () {
       $scope.reloadList();
    });

    // 更新商品状态
    $scope.updateStatus = function (status) {
        goodsService.updateStatus(status, $scope.selectIds).success(function (response) {
            if (response.success) {
                // 清空删除ids
                $scope.selectIds = [];
                $scope.reloadList(); // 刷新列表
                alert(response.message);
            } else {
                alert(response.message);
            }
        });
    };

});	

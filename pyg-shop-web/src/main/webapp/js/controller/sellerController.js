// 控制层 
app.controller('sellerController', function($scope, $controller, sellerService) {	
	
$controller('baseController', {$scope: $scope}); // 继承

    // 读取列表数据绑定到表单中
    $scope.findAll = function () {
        sellerService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    // 分页
    $scope.findPage = function (page, rows) {
        sellerService.findPage(page, rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    // 查询实体
    $scope.findOne = function (id) {
        sellerService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    };

    // 保存
    $scope.save = function () {
        sellerService.add($scope.entity).success(function (response) {
            if (response.success) {
                location.href = "index.html";
            } else {
                alert(response.message);
            }
        })
    };


    // 批量删除
    $scope.del = function () {
        // 获取选中的复选框
        sellerService.del($scope.selectIds).success(function (response) {
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
        sellerService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total; // 更新总记录数
        })
    };

    //回显资料
    $scope.findSellerForId = function () {
        sellerService.findSellerForId().success(function (data) {
            $scope.entity = data;
        })
    };

    // 修改资料
    $scope.update = function () {
        sellerService.update($scope.entity).success(function (response) {
            if (response.success) {
                alert(response.message);
                location.href = "seller.html";
            } else {
                alert(response.message);
            }
        })
    };

    //修改密码
    $scope.updatePsw = function () {

        $scope.flag = false;
        if ($scope.newPsw != $scope.psw){
            // alert("两次密码不一致!");
            $scope.flag = true;
            return;
        }

        if ($scope.newPsw == '' || $scope.oldPsw == '') {
            alert("密码不能为空");
            return;
        }

        sellerService.updatePsw($scope.oldPsw,$scope.newPsw).success(function (data) {
            if (data.success){
                alert(data.message);
                location.href = "index.html";
            } else if (data.message === '请输入6-18位的密码'){
                alert(data.message);
                return;
            }else {
                alert(data.message);
            }
        })
    }
    
});	

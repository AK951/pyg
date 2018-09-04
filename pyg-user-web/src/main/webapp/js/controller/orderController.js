// 控制层 
app.controller('orderController', function ($scope, $http, $location, $controller, orderService) {

    $controller('baseController', {$scope: $scope}); // 继承

    // 获取登录用户名
    $scope.showLoginUser = function () {
        $http.get('login/showUser').success(function (response) {
            $scope.loginUser = response;
        });
    };

    // 读取列表数据绑定到表单中
    $scope.findAll = function () {
        orderService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    // 分页
    $scope.findPage = function (page, rows) {
        orderService.findPage(page, rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    // 查询实体
    $scope.findOne = function (id) {
        orderService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    };

    // 保存
    $scope.save = function () {
        var serviceObject; // 服务层对象
        if ($scope.entity.id != null) { // 如果有ID
            serviceObject = orderService.update($scope.entity); // 修改
        } else {
            serviceObject = orderService.add($scope.entity); // 增加
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
        orderService.del($scope.selectIds).success(function (response) {
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
        orderService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total; // 更新总记录数
        })
    };

    $scope.payStatus = ["", "等待买家付款", "买家已付款", "卖家已发货"];
    $scope.orderStatus = ["", "立即付款", "提醒发货", "确认收货"];
    $scope.paymentType = ['', '微信', '货到付款'];


    //订单详情
    $scope.findOneOrder = function () {
        var id = $location.search()['id'];
        orderService.findOneOrder(id).success(function (data) {
            $scope.orderDetail = data;
            $scope.he();
            for (var i = 0; i < $scope.orderDetail.orderItemList.length; i++) {
                $scope.findSpecForItemId($scope.orderDetail.orderItemList[i].itemId);
            }
        })
    };

    $scope.he = function () {
        $scope.totalValue = {totalNum: 0, totalMoney: 0.00};//合计实体
        for (var j = 0; j < $scope.orderDetail.orderItemList.length; j++) {
            var orderItem = $scope.orderDetail.orderItemList[j];//购物车明细
            $scope.totalValue.totalNum += orderItem.num;
            $scope.totalValue.totalMoney += orderItem.totalFee;
        }
    };

    $scope.itemSpec = [];

    //查询规格
    $scope.findSpecForItemId = function (itemId) {
        orderService.findSpecForItemId(itemId).success(function (data) {
            $scope.itemSpec[itemId] = data;
            $scope.itemSpec[itemId].spec = JSON.parse($scope.itemSpec[itemId].spec);
        })
    };

    //跳转
    $scope.goto = function (orderId) {
        location.href = "home-orderDetail.html#?id=" + orderId;
    }

});	

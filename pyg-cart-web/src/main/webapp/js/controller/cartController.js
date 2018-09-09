// 控制层 
app.controller('cartController', function ($scope, $http, cartService) {

    // 更改购物车状态
    $scope.updateStatus = function () {
        cartService.updateStatus($scope.cartList).success(function (response) {
            $scope.findCartList();
        });
    };

    $scope.cartList = [];
    // 购物车列表
    $scope.findCartList = function () {
        cartService.findCartList().success(function (response) {
            $scope.cartList = response;
            $scope.checkStatus();
            $scope.findOrderCartList();
        })
    };

    // 已选购物车列表
    $scope.findOrderCartList = function () {
        cartService.findOrderCartList().success(function (response) {
            $scope.totalValue = cartService.sum(response); //求合计数
        })
    };

    // 添加商品到购物车
    $scope.addGoodsToCartList = function (itemId, num) {
        cartService.addGoodsToCartList(itemId, num).success(function (response) {
            if (response.success) {
                $scope.findCartList();
            } else {
                alert(response.message);
            }
        })
    };

    // 获取登录用户名
    $scope.showLoginName = function () {
        $http.get('login/showName').success(function (response) {
            $scope.loginName = response.loginName;
        });
    };

    // 提交订单
    $scope.submitOrder = function () {
        if ($scope.totalValue.totalMoney <= 0) {
            alert("请选择需要下单的商品");
            return;
        }
        location.href = "http://localhost:9108/getOrderInfo.html";
    };

    $scope.updateItem = function (status, cartIndex) {
        $scope.cartList[cartIndex].cartNum = $scope.cartList[cartIndex].cartNum + (status ? 1 : -1);
        if($scope.cartList[cartIndex].cartNum===$scope.cartList[cartIndex].orderItemList.length) {
            $scope.cartList[cartIndex].cartStatus = true;
        } else {
            $scope.cartList[cartIndex].cartStatus = false;
        }
        $scope.updateStatus();
    };

    $scope.updateSeller = function ($index) {
        for (var i = 0; i < $scope.cartList[$index].orderItemList.length; i++) {
            $scope.cartList[$index].orderItemList[i].cartStatus = $scope.cartList[$index].cartStatus;
        }
        $scope.cartList[$index].cartNum = $scope.cartList[$index].cartStatus ? $scope.cartList[$index].orderItemList.length : 0;
        $scope.updateStatus();
    };

    $scope.allStatus = false;
    $scope.checkStatus = function() {
        if($scope.cartList.length !== 0) {
            $scope.allStatus = cartService.checkStatus($scope.cartList);
        }
    };

    $scope.updateAll = function() {
        $scope.cartList = cartService.selectAll($scope.cartList, $scope.allStatus);
        $scope.updateStatus();
    };

});

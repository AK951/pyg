// 控制层 
app.controller('cartController', function($scope, $http, cartService, addressService) {
	
    // 购物车列表
    $scope.findCartList = function () {
        cartService.findCartList().success(function (response) {
            $scope.cartList = response;
            $scope.totalValue = cartService.sum($scope.cartList); //求合计数
        })
    };

    // 添加商品到购物车
    $scope.addGoodsToCartList = function (itemId, num) {
        cartService.addGoodsToCartList(itemId, num).success(function (response) {
            if(response.success) {
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

    // 保存地址
    $scope.saveAddress = function() {
        var serviceObject; // 服务层对象
        if ($scope.address.id != null) { // 如果有ID
            serviceObject = addressService.update($scope.entity); // 修改
        } else {
            if($scope.addressList.length === 0) {
                $scope.entity.isDefault = '1';
            } else {
                $scope.entity.isDefault = '0';
            }
            serviceObject = addressService.add($scope.entity); // 增加
        }
        serviceObject.success(function (response) {
            if (response.success) {
                $scope.findAddressList();
            } else {
                alert(response.message);
            }
        });
    };

    // 查询实体
    $scope.findAddressById = function(id) {
        addressService.findOne(id).success(function (response) {
            $scope.entity = response;
        });
    };

    // 获取地址列表
    $scope.findAddressList = function(){
        addressService.findAddressList().success(function(response) {
            $scope.addressList=response;
            for(var i=0; i< $scope.addressList.length; i++){
                if($scope.addressList[i].isDefault === '1'){
                    $scope.address=$scope.addressList[i];
                    break;
                }
            }
        });
    };

    // 选中地址
    $scope.selectAddress = function (address) {
        $scope.address = address;
    };

    $scope.order={paymentType:'1'};
    // 支付方式
    $scope.selectPayType = function(type) {
        $scope.order.paymentType = type;
    };

    // 提交订单
    $scope.submitOrder = function () {
        $scope.order.receiverAreaName = $scope.address.address;
        $scope.order.receiverMobile=$scope.address.mobile;
        $scope.order.receiver=$scope.address.contact;
        cartService.submitOrder($scope.order).success(function (response) {
            if(response.success) {
                if($scope.order.paymentType === '1') {
                    location.href = "pay.html";
                } else {
                    location.href = "paysuccess.html";
                }
            } else {
                alert(response.message);
            }
        })
    }

});	

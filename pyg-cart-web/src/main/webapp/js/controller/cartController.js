// 控制层 
app.controller('cartController', function($scope, $http, cartService, addressService) {

    // 更改购物车状态
    $scope.updateStatus = function (status, id) {
        cartService.updateStatus(status, id, $scope.cartList).success(function (response) {
            $scope.findCartList();
        });
    };

    $scope.cartList = [];
    // 购物车列表
    $scope.findCartList = function () {
        cartService.findCartList().success(function (response) {
            $scope.cartList = response;
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
        if ($scope.entity.id != null) { // 如果有ID
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

    // 删除地址
    $scope.deleteAddressById = function(id) {
        addressService.del(id).success(function (response) {
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

    // 设为默认地址
    $scope.setDefaultAddress = function(id) {
        addressService.setDefaultAddress(id).success(function (response) {
            if (response.success) {
                $scope.findAddressList();
            } else {
                alert(response.message);
            }
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
    };

});	

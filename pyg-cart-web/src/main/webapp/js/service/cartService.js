// 服务层
app.service('cartService', function($http) {

    // 获得购物车列表
    this.findCartList = function () {
        return $http.get('cart/findCartList');
    };
    // 获得已选购物车列表
    this.findOrderCartList = function () {
        return $http.get('cart/findOrderCartList');
    };
    // 添加商品到购物车
    this.addGoodsToCartList = function (itemId, num) {
        return $http.get('cart/addGoodsToCartList/' + itemId + "/" + num);
    };
    //求合计
    this.sum = function(cartList){
        var totalValue={totalNum:0, totalMoney:0.00 };//合计实体
        for(var i=0;i<cartList.length;i++){
            var cart=cartList[i];
            for(var j=0;j<cart.orderItemList.length;j++){
                var orderItem=cart.orderItemList[j];//购物车明细
                totalValue.totalNum+=orderItem.num;
                totalValue.totalMoney+= orderItem.totalFee;
            }
        }
        return totalValue;
    };
    //保存订单
    this.submitOrder = function (order) {
        return $http.post('order/add', order);
    };
    // 更改购物车状态
    this.updateStatus = function (cartList) {
        return $http.post('cart/updateStatus', cartList);
    };
    //求合计
    this.selectAll = function(cartList, status){
        for(var i=0;i<cartList.length;i++){
            var cart=cartList[i];
            cart.cartStatus = status;
            cart.cartNum = status ? cart.orderItemList.length : 0;
            for(var j=0;j<cart.orderItemList.length;j++){
                var orderItem=cart.orderItemList[j];//购物车明细
                orderItem.cartStatus = status;
            }
        }
        return cartList;
    };
    //求合计
    this.checkStatus = function(cartList){
        for(var i=0;i<cartList.length;i++){
            var cart=cartList[i];
            if(!cart.cartStatus) {
                return false;
            }
        }
        return true;
    };

});

// 服务层
app.service('cartService', function($http) {

    // 获得购物车列表
    this.findOrderCartList = function () {
        return $http.get('cart/findOrderCartList');
    };
    // 获得购物车列表
    this.findCartList = function () {
        return $http.get('cart/findCartList');
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

});

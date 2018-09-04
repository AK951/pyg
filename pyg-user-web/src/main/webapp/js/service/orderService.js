// 服务层
app.service('orderService', function($http) {
	    	
    // 读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../order/findAll');
    };
    // 分页
    this.findPage = function (page, rows) {
        return $http.get('../order/findPage/' + page + '/' + rows);
    };
    // 查询实体
    this.findOne = function (id) {
        return $http.get('../order/findOne/' + id);
    };
    // 增加
    this.add = function (entity) {
        return $http.post('../order/add', entity);
    };
    // 修改
    this.update = function (entity) {
        return $http.post('../order/update', entity);
    };
    // 删除
    this.del = function (ids) {
        return $http.get('../order/delete/' + ids);
    };
    // 搜索
    this.search = function (page, rows, searchEntity) {
        return $http.post('../order/search/' + page + "/" + rows, searchEntity);
    };

    //订单详情
    this.findOneOrder = function (id) {
        return $http.get("../order/findOneOrder/" + id);
    };

    //查询规格
    this.findSpecForItemId = function (itemId) {
        return $http.get("../order/findSpecForItemId/" + itemId);
    };

    //求合计
    this.sum1 = function(orderDetail){
        var totalValue={totalNum:0, totalMoney:0.00};//合计实体
            for(var j=0;j<orderDetail.orderItemList.length;j++){
                var orderItem=orderDetail.orderItemList[j];//购物车明细
                totalValue.totalNum+=orderItem.num;
                totalValue.totalMoney+= orderItem.totalFee;
            }
        return totalValue;
    };
});

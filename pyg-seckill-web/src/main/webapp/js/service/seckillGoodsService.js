// 服务层
app.service('seckillGoodsService', function($http) {
	    	
    // 读取列表数据绑定到表单中
    this.findAll = function () {
        return $http.get('../seckillGoods/findAll');
    };
    // 分页
    this.findPage = function (page, rows) {
        return $http.get('../seckillGoods/findPage/' + page + '/' + rows);
    };
    // 查询实体
    this.findOne = function (id) {
        return $http.get('../seckillGoods/findOne/' + id);
    };
    // 增加
    this.add = function (entity) {
        return $http.post('../seckillGoods/add', entity);
    };
    // 修改
    this.update = function (entity) {
        return $http.post('../seckillGoods/update', entity);
    };
    // 删除
    this.del = function (ids) {
        return $http.get('../seckillGoods/delete/' + ids);
    };
    // 搜索
    this.search = function (page, rows, searchEntity) {
        return $http.post('../seckillGoods/search/' + page + "/" + rows, searchEntity);
    };
});

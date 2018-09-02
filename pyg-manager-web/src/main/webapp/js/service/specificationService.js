app.service("specificationService", function ($http) {
    this.findAll = function () {
        return $http.get('../specification/findAll');
    };

    this.findPage = function (page, rows) {
        return $http.get('../specification/findPage/' + page + '/' + rows);
    };

    this.add = function (entity) {
        return $http.post('../specification/add', entity);
    };

    this.findOne = function (id) {
        return $http.get('../specification/findOne/' + id);
    };

    this.update = function (entity) {
        return $http.post('../specification/update', entity);
    };

    this.del = function (ids) {
        return $http.get('../specification/del/' + ids);
    };

    this.search = function (page, rows, searchEntity) {
        return $http.post('../specification/search/' + page + "/" + rows, searchEntity);
    };
    // 下拉列表数据
    this.selectOptionList = function () {
        return $http.get('../specification/findSpecList');
    }
});
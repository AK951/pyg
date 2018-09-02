app.controller('specificationController', function ($scope, $controller, specificationService) {
    // 继承baseController
    $controller('baseController', {$scope: $scope});

    $scope.findAll = function () {
        specificationService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    $scope.findPage = function(page, rows) {
        specificationService.findPage(page, rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    $scope.save = function () {
        var object = specificationService.add($scope.entity);
        if($scope.entity.specification.id != null) {
            object = specificationService.update($scope.entity);
        }
        object.success(function (response) {
            if(response.success) {
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        })
    };

    $scope.findOne = function (id) {
        specificationService.findOne(id).success(function (response) {
            $scope.entity = response;
        })
    };
    
    $scope.del = function () {
        specificationService.del($scope.selectIds).success(function (response) {
            if(response.success) {
                // 清空删除ids
                $scope.selectIds = [];
                $scope.reloadList();
            } else {
                alert(response.message);
            }
        })
    };
    
    $scope.searchEntity = {};
    
    $scope.search = function (page, rows) {
        specificationService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    $scope.entity={specificationOptionList:[]};
    // 增加规格选项行
    $scope.addTableRow = function () {
        $scope.entity.specificationOptionList.push({});
    }

    // 删除规格选项行
    $scope.delTableRow = function (index) {
        $scope.entity.specificationOptionList.splice(index, 1);
    }

});
app.controller('searchController', function ($scope, $http, $location, searchService) {

    // 定义搜索对象的结构 category:商品分类
    // 1,主查询条件 （关键词搜索条件）
    // 2,分类查询参数
    // 3，品牌参数
    // 4,规格属性参数
    // 5,价格参数
    // 6,排序
    // 7,分页
    $scope.searchMap = {
        keywords: "",
        category : "",
        brand : "",
        spec : {},
        price : "",
        // sort : "ASC",
        sort : "",
        // sortField : "price",
        sortField : "",
        pageNo : 1,
        pageSize : 20
    };

    $scope.loadkeywords = function () {
        //接受静态页面，或者ng-model绑定参数，都可以使用$location服务接受参数
        //接受参数语法：
        //$location.name
        //$location.search()['keywords']
        $scope.searchMap.keywords = $location.search()['keywords'];
        $scope.search();
    };

    //搜索
    $scope.search = function () {
        $scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo); // 转化为数字
        //调用服务层方法，传递关键词进行搜索
        searchService.searchList($scope.searchMap).success(function (response) {
            $scope.resultMap = response;
            buildPageLable();
        });
    };

    buildPageLable = function () {

        $scope.pageLabel = [];
        var firstPage = 1;
        var lastPage = $scope.resultMap.totalPages;
        $scope.firstDot = true;
        $scope.lastDot = true;

        if ($scope.resultMap.totalPages > 5) {
            if ($scope.searchMap.pageNo <= 3) {
                lastPage = 5;
            } else if ($scope.searchMap.pageNo >= $scope.resultMap.totalPages - 2) {
                firstPage = $scope.resultMap.totalPages - 4;
            } else {
                firstPage = $scope.searchMap.pageNo - 2;
                lastPage = $scope.searchMap.pageNo + 2;
            }

        }
        if ($scope.resultMap.totalPages >= 7) {
            if ($scope.searchMap.pageNo < 5) {
                $scope.firstDot = false;
            } else if ($scope.searchMap.pageNo > $scope.resultMap.totalPages - 4) {
                $scope.lastDot = false;
            }
        } else {
            $scope.firstDot = false;
            $scope.lastDot = false;
        }

        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageLabel.push(i);
        }
    };

    // 增加搜索条件
    $scope.addFilterCondition = function (key, value) {
        if(key == 'brand' || key =='category' || key == 'price') {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value;
        }
        $scope.search();
    };

    $scope.removeSearchItem = function (key) {
        if(key == 'brand' || key =='category' || key == 'price') {
            $scope.searchMap[key] = "";
        } else {
            delete $scope.searchMap.spec[key];
        }
        $scope.search();
    };


    // 排序
    $scope.setSort = function (sortField, sort) {
        $scope.searchMap.sortField = sortField;
        $scope.searchMap.sort = sort;
        $scope.search();
    };

    // 分页查询
    $scope.queryByPage = function (pageNo) {
        if (pageNo < 1 || pageNo > $scope.resultMap.totalPages) {
            return;
        }
        $scope.searchMap.pageNo = pageNo;
        $scope.search();
    };


    $scope.isTopPage = function () {
        if ($scope.searchMap.pageNo == 1) {
            return true;
        } else {
            return false;
        }
    };


    $scope.isEndPage = function () {
        if ($scope.searchMap.pageNo == $scope.resultMap.totalPages) {
            return true;
        } else {
            return false;
        }
    };

    $scope.isCurPage = function (pageNo) {
        if ($scope.searchMap.pageNo == pageNo) {
            return true;
        } else {
            return false;
        }
    };

    $scope.$watch('searchMap.pageNo', function () {
        $scope.search();
    });

    // 购物车列表
    $scope.findCartList = function () {
        cartService.findCartList().success(function (response) {
            $scope.cartList = response;
            $scope.totalValue = cartService.sum($scope.cartList); //求合计数
        })
    };

    // 添加商品到购物车
    $scope.addGoodsToCartList = function (itemId, num) {
        $http.get('http://localhost:9107/cart/addGoodsToCartList/' + itemId + '/' + num ,{'withCredentials':true}).success(function (response) {
            if(response.success) {
                window.open("success-cart.html",'_blank');
            } else {
                alert(response.message);
            }
        })
    }

});
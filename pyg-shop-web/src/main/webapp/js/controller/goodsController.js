// 控制层 
app.controller('goodsController', function($scope, $controller, goodsService, itemCatService,
                                           typeTemplateService, uploadService) {
	
$controller('baseController', {$scope: $scope}); // 继承

    // 读取列表数据绑定到表单中
    $scope.findAll = function () {
        goodsService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    // 分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        })
    };

    // 查询实体
    $scope.findOne = function (id) {
        goodsService.findOne(id).success(function (response) {
            $scope.entity = response;
            editor.html(response.goodsDesc.introduction);
            $scope.entity.goodsDesc.itemImages = JSON.parse(response.goodsDesc.itemImages);
            $scope.entity.goodsDesc.specificationItems = JSON.parse(response.goodsDesc.specificationItems);
            $scope.entity.goodsDesc.customAttributeItems = JSON.parse(response.goodsDesc.customAttributeItems);
            for(var i=0; i<response.itemList.length; i++) {
                $scope.entity.itemList[i].spec = JSON.parse(response.itemList[i].spec);
            }
        })
    };

    $scope.isSpecChecked = function(name, value) {
        var list = $scope.entity.goodsDesc.specificationItems;
        var obj = $scope.searchObjectByKey(list, "attributeName", name);
        if(obj != null) {
            if(obj.attributeValue.indexOf(value) < 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    };

    // 保存
    $scope.save = function () {
        $scope.entity.goodsDesc.introduction = editor.html();
        var serviceObject; // 服务层对象
        if ($scope.entity.goods.id != null) { // 如果有ID
            serviceObject = goodsService.update($scope.entity); // 修改
        } else {
            serviceObject = goodsService.add($scope.entity); // 增加
        }
        serviceObject.success(function (response) {
            if (response.success) {
                alert(response.message);
                $scope.entity = {};
                editor.html('');
                location.href = 'goods.html';
            } else {
                alert(response.message);
            }
        })
    };

    // 批量删除
    $scope.del = function () {
        // 获取选中的复选框
        goodsService.del($scope.selectIds).success(function (response) {
            if (response.success) {
                // 清空删除ids
                $scope.selectIds = [];
                $scope.reloadList(); // 刷新列表
            } else {
                alert(response.message);
            }
        })
    };

    $scope.searchEntity = {}; // 定义搜索对象

    // 搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total; // 更新总记录数
        })
    };

    // 分类列表级联查询
    // 一级分类列表
    $scope.selectItemCat1List = function () {
        itemCatService.findByParentId(0).success(function (response) {
            $scope.itemCat1List = response;
        })
    };

    // 二级分类列表
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
       itemCatService.findByParentId(newValue).success(function (response) {
           $scope.itemCat2List = response;
       });
    });

    // 三级分类列表
    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        itemCatService.findByParentId(newValue).success(function (response) {
            $scope.itemCat3List = response;
        });
    });

    // 更新模板id
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(function (response) {
            $scope.entity.goods.typeTemplateId = response.typeId;
        });
    });

    // 根据模板id返回品牌列表,扩展属性列表
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(function (response) {
            // 返回品牌列表
            $scope.brandList = JSON.parse(response.brandIds);
            // 返回扩展属性列表
            if($scope.entity.goods.id == null) {
                $scope.entity.goodsDesc.customAttributeItems = JSON.parse(response.customAttributeItems);
            }
        });
        // 返回规格选项列表
        typeTemplateService.findSpecList(newValue).success(function (response) {
            $scope.specList = response;
        });
    });

    // 图片上传
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(function (response) {
            if(response.success) {
                $scope.image_entity.url=response.message;
            } else {
                alert(response.message);
            }
        }).error(function () {
            alert("上传出错");
        });
    };

    // 初始化entity对象
    $scope.entity = {"goods":{}, "goodsDesc":{itemImages:[],specificationItems:[]}};
    // 保存图片
    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImages.push($scope.image_entity);
    };

    // 删除图片
    $scope.remove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImages.splice(index, 1);
    };

    // 根据选择更新规格选项
    $scope.updateSpecAttribute = function($event, name, option) {
        var list = $scope.entity.goodsDesc.specificationItems;
        var obj = $scope.searchObjectByKey(list, "attributeName", name);
        if(obj != null) {
            if($event.target.checked) {
                obj.attributeValue.push(option);
            } else {
                obj.attributeValue.splice(obj.attributeValue.indexOf(option), 1);
                if(obj.attributeValue.length === 0) {
                    list.splice(list.indexOf(obj), 1);
                }
            }
        } else {
            list.push({"attributeName":name,"attributeValue":[option]});
        }
    };

    $scope.$watch('entity.goods.isEnableSpec', function(newValue, oldValue) {
        if (newValue == 0) {
            $scope.entity.goodsDesc.specificationItems = [];
            $scope.entity.itemList = [];
        }
    });

    // 创建sku列表
    $scope.createItemList = function () {
        $scope.entity.itemList = [{spec:{}, price:0, num:99999, status:'1', isDefault:'0'}];
        var items = $scope.entity.goodsDesc.specificationItems;
        if(items.length === 0) {
            $scope.entity.itemList = [];
            return;
        }
        for(var i=0; i<items.length; i++) {
            $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
        }
    };
    
    addColumn = function (list, colName, colValue) {
        var newList = [];
        for(var i=0; i<list.length; i++) {
            var oldRow = list[i];
            for(var j=0; j<colValue.length; j++) {
                var newRow = JSON.parse( JSON.stringify(oldRow) );
                newRow.spec[colName] = colValue[j];
                newList.push(newRow);
            }
        }
        return newList;
    };

    // 商品状态
    $scope.status = ["待审核", "审核通过", "已驳回", "关闭"];
    $scope.bgColor = ["bg-aqua","bg-olive","bg-maroon", "bg-yellow"];
    $scope.color = ["#6b4888","green","red", "orange"];
    $scope.markertable = ["上架","下架"];
    $scope.markertColor = ["#248854", "#de4016"];

    $scope.itemCatList = [];
    // 品牌列表
    $scope.findBrandList = function () {
        itemCatService.findAll().success(function (response) {
            for(var i=0; i<response.length; i++) {
                $scope.itemCatList[response[i].id] = response[i].name;
            }
        })
    };

    $scope.$watch('searchEntity.auditStatus', function () {
        $scope.reloadList();
    });

    // 更新商品状态
    $scope.updateStatus = function (status) {
        goodsService.updateStatus(status, $scope.selectIds).success(function (response) {
            if (response.success) {
                // 清空删除ids
                $scope.selectIds = [];
                $scope.reloadList(); // 刷新列表
                alert(response.message);
            } else {
                alert(response.message);
            }
        });
    };

    // 商品上下架
    $scope.isMarkertable = function(status) {
        goodsService.isMarkertable(status, $scope.selectIds).success(function (response) {
            if (response.success) {
                // 清空删除ids
                $scope.selectIds = [];
                $scope.reloadList(); // 刷新列表
                alert(response.message);
            } else {
                alert(response.message);
            }
        })
    };

    $scope.edit = function (goodsId) {
        var hre = 'goods_edit.html?goodsId=' + goodsId;
        location.href = hre;
    };

    var urlValue='';
    var href = location.href; //取得整个地址栏
    if(href.indexOf("=")>=0) {
        urlValue = href.substr(href.indexOf("=") + 1);
        if(urlValue.length>0){
            $scope.findOne(urlValue);
        }
    }


});

app.controller('baseController', function ($scope) {

    $scope.selectIds = []; //用户勾选的id集合
    // 获取复选框的id
    $scope.updateSelection = function ($event, id) {
        if ($event.target.checked) {
            $scope.selectIds.push(id);
        } else {
            var index = $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(index, 1);
        }
    };

    //提取json字符串数据中某个属性，返回拼接字符串 逗号分隔
    $scope.jsonToString=function(jsonString,key){
        var json=JSON.parse(jsonString);//将json字符串转换为json对象
        var value="";
        for(var i=0;i<json.length;i++){
            if(i>0){
                value+=","
            }
            value+=json[i][key];
        }
        return value;
    };

    // 在list集合中根据某key的位置查询对象
    $scope.searchObjectByKey=function (list,key,keyValue) {

        for(var i=0;i<list.length;i++) {
            if(list[i][key]==keyValue) {
                return list[i];
            }
        }
        return null;
    }
});
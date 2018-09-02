// 控制层 
app.controller('seckillGoodsController', function($scope, $http, $location, $interval, seckillGoodsService) {
	
    // 读取列表数据绑定到表单中
    $scope.findAll = function () {
        seckillGoodsService.findAll().success(function (response) {
            $scope.list = response;
        })
    };

    // 秒杀详情页
    $scope.findOne = function () {
        //获取路径中的ID
        var id = $location.search()['id'];
        if(id > 0) {
            seckillGoodsService.findOne(id).success(function (response) {
                $scope.entity = response;

                //计算出剩余时间
                var endTime = new Date($scope.entity.endTime).getTime();
                var nowTime = new Date().getTime();

                //剩余时间
                $scope.secondes =Math.floor( (endTime-nowTime)/1000 );

                var time =$interval(function () {
                    if($scope.secondes>0){
                        //时间递减
                        $scope.secondes=$scope.secondes-1;
                        //时间格式化
                        $scope.timeString=$scope.convertTimeString($scope.secondes);
                    }else{
                        //结束时间递减
                        $interval.cancel(time);
                    }
                },1000);
            });
        }
    };

    // 获取登录用户名
    $scope.showLoginName = function () {
        $http.get('login/showName').success(function (response) {
            $scope.loginName = response.loginName;
        });
    };

    //时间计算转换
    $scope.convertTimeString=function (allseconds) {
        //计算天数
        var days = Math.floor(allseconds/(60*60*24));

        //小时
        var hours =Math.floor( (allseconds-(days*60*60*24))/(60*60) );

        //分钟
        var minutes = Math.floor( (allseconds-(days*60*60*24)-(hours*60*60))/60 );

        //秒
        var seconds = allseconds-(days*60*60*24)-(hours*60*60)-(minutes*60);

        //拼接时间
        var timString="";
        if(days>0){
            timString=days+"天:";
        }
        return timString += (hours < 10 ? '0' + hours : hours) + ":" + (minutes < 10 ? '0' + minutes : minutes) + ":" + (seconds < 10 ? '0' + seconds : seconds);
    }


});

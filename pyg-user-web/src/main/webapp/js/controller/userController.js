// 控制层 
app.controller('userController', function($scope,$location,$http, userService) {
	
    // 注册
    $scope.save = function () {
        if($scope.entity.username == null || $scope.entity.password == null ||
            $scope.entity.phone == null || $scope.code == null) {
            alert("注册信息不能为空");
            return;
        }
        if($scope.entity.password !== $scope.password) {
            alert("两次密码不一致");
            return;
        }
        userService.add($scope.entity, $scope.code).success(function (response) {
            if (response.success) {
                alert(response.message);
            } else {
                alert(response.message);
            }
        })
    };


    // 发送短信验证码
    $scope.getSmsCode = function () {
        if($scope.entity.phone == null) {
            alert("手机号不能为空");
            return;
        }
        userService.getSmsCode($scope.entity.phone).success(function (response) {
            if(response.message != null) {
                alert(response.message);
            }
        });
    };


    $scope.entity = {};
    // 修改密码
    $scope.updatePass = function () {
        if($scope.entity.newPassword != $scope.entity.rePassword){
            $scope.flag = false;
            alert("新密码前后不一致");
            $scope.entity = {};
            return;
        }
        userService.updatePass($scope.entity.password,$scope.entity.newPassword).success(function (data) {
            if(data.success){
                $scope.flag = data.success;
                $scope.errMessage = data.message;
            }else{
                alert(data.message);
                $scope.flag = false;
                $scope.entity = {};
            }
        })
    };


    // 获取手机号
    $scope.getPhone = function () {
        userService.getPhone().success(function (data) {
            for(var i=0; i<data.length; i++){
                $scope.entity2 = data[i];
                $scope.mobile = data[i].phone;
                for(var i=0; i<$scope.mobile.length; i++){
                    if(i>=3 && i<=6){
                        $scope.mobile = $scope.mobile.substr(0,3)+"****"+$scope.mobile.substr(7);
                    }
                }
            }
        })
    };

    // 获取短信验证码
    $scope.takeSmsCode = function () {
        if($scope.entity2.phone == null) {
            alert("手机号不能为空");
            return;
        }
        userService.getSmsCode($scope.entity2.phone).success(function (response) {
            if(response.message != null) {
                alert("发送成功");
            }
        });
    };


    // 验证验证码
    $scope.checkCode = function () {
        if($scope.messCode == null){
            alert("验证码不能为空");
            return;
        }
        userService.checkCode($scope.entity2.phone,$scope.messCode).success(function (data) {
            if(data.success){
                $scope.messCode = "";
                alert("验证通过");
                location.href = "home-setting-address-phone.html";
            }else{
                alert(data.message);
            }
        })
    };

    $scope.check2Code = function () {
        if($scope.mess2Code == null){
            alert("验证码不能为空");
            return;
        }
        userService.checkCode($scope.entity2.phone,$scope.mess2Code).success(function (data) {
            if(data.success){
                $scope.updatePhone($scope.entity2.phone);
                location.href = "home-setting-address-complete.html";
            }else{
                alert(data.message);
            }
        })
    };

    // 更改手机号
    $scope.updatePhone = function (newPhone) {
        userService.updatePhone(newPhone).success(function (data) {
            if(data.success){
                alert(data.message);
            }
        })
    };


    $scope.status = ['active',''];


    // 改变样式，实现上一步跳转
    $scope.changeStyle = function (number) {
        if(number == "1"){
            $scope.status[0] = 'active';
            $scope.status[1] = '';
        }else if(number == "2"){
            $scope.status[0] = '';
            $scope.status[1] = 'active';
        }
    };

    // 接收setting-safe传递过来的参数
    $scope.getStyle = function () {
        var number = $location.search()['number'];
        if(number === undefined){
            return;
        }
        $scope.changeStyle(number);
    }






});	

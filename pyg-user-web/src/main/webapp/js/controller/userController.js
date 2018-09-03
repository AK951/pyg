// 控制层 
app.controller('userController', function($scope, userService,loginService) {
	
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

    // 发送验证码
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
    }

    $scope.showLoginName = function () {
        loginService.showLoginName().success(function (response) {
            $scope.loginName = response.loginName;
        });
    }


});	

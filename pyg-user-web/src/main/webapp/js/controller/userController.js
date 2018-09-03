// 控制层 
app.controller('userController', function($scope,userService,uploadService) {
	
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

    // 图片上传
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(function (response) {
            if(response.success) {
                $scope.entity1.headPic=response.message;
                $scope.saveNickName();
            } else {
                alert(response.message);
            }
        }).error(function () {
            alert("上传出错");
        });
    };


    $scope.loadNickName = function () {
        userService.loadNickName().success(function (data) {
            $scope.entity1=data;
        })
    }


    $scope.saveNickName =function () {
        userService.saveNickName($scope.entity1).success(function (data) {
            if (data.success){
                alert(data.message)
            }else {
                alert(data.message)
            }
        })
    }


    $scope.flag=false;
    var b=2;
    $scope.flag1=function () {
        if (b % 2==0) {
        $scope.flag=true;
        }else {
        $scope.flag=false;
        }
        b++;
    }
    $scope.$watch('entity1.birthday',function (b,d) {
        $scope.flag=false;
    })

});

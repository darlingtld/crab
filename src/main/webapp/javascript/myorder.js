/**
 * Created by darlingtld on 2015/7/4 0004.
 */
var module = angular.module('MycaiModule', ['ngRoute']);
var app = '/crab';
var user;
var wechatId;

module.config(function () {

});


module.controller('routerController', function ($http, $scope, $location) {
    getUserInfo();
    if (user == undefined || user == null) {
        var code = getURLParameter('code');
        $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatId', wechatId);
        });
    }
    sleep(2000)
    $location.path('/order/history');

});

module.controller('orderController', function ($http, $scope) {
    var url = app + '/order/get/' + wechatId;
    if (user == undefined || user == null) {
        var code = getURLParameter('code');
        $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatId', wechatId);
            $http.get(url).success(function (data, status, headers, config) {
                $scope.orders = data;
            });
        });
    }
    if (wechatId == undefined) {
        wechatId = getLocalStorage('wechatId');
    }
    if ($scope.orders == undefined) {
        $http.get(url).success(function (data, status, headers, config) {
            $scope.orders = data;
        });
    }
});

module.controller('orderDetailController', function ($http, $scope, $routeParams) {
    var url = app + '/order/detail/' + $routeParams.id;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.orderDetail = data;
        $scope.items = JSON.parse($scope.orderDetail.bill).items;
        $scope.total = JSON.parse($scope.orderDetail.bill);
        $scope.total.price = parseFloat($scope.total.price).toFixed(2);
        $scope.itemAmount = $scope.items.length;
        if (JSON.parse($scope.orderDetail.confirmBill) != null) {
            $scope.items = JSON.parse($scope.orderDetail.confirmBill).items;
            $scope.total = JSON.parse($scope.orderDetail.bill);
            $scope.actTotal = JSON.parse($scope.orderDetail.confirmBill)
            $scope.itemAmount = $scope.items.length;
        }
    });

});

module.directive('confirmCode', function () {
    return {
        restrict: 'AE',
        scope: {},
        link: function (scope, element, attr) {
            var value = attr.confirmCode;
            element.bind('click', function () {
                alert("确认码 ：\r\n" + value);
            })
        }
    }
});

module.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/order/history', {
            controller: 'orderController',
            templateUrl: 'orderHistory.html'
        })
        .when('/order/details/:id', {
            controller: 'orderDetailController',
            templateUrl: 'orderDetails.html'
        })
        .when('/router', {
            controller: 'routerController',
            template: ''
        })
        .otherwise({
            redirectTo: '/order/history'
        });
}]);

module.filter('part', function () {
    return function (input, which) {
        if (!angular.isString(input)) {
            return input;
        }
        return input.split(' ')[which];
    };
});

function getUserInfo(callback) {
    var code = getURLParameter('code');
    $.ajax({
        type: 'get',
        url: app + "/user/code/" + code,
        success: function (data) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatId', wechatId);
            if (callback && typeof(callback) === "function") {
                callback();
            }
        }
    });
}

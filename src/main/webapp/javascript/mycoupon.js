/**
 * Created by darlingtld on 2015/7/4 0004.
 */
var couponModule = angular.module('CouponModule', ['ngRoute']);
var app = '/crab';
var user;
var wechatId;

couponModule.config(function () {

});

couponModule.controller('couponController', function ($http, $scope) {
    var url = app + '/coupon/all/wechatid/' + wechatId;
    if (user == undefined || user == null) {
        var code = getURLParameter('code');
        $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatId', wechatId);
            $http.get(url).success(function (data, status, headers, config) {
                $scope.coupons = data;
            });
        });
    }
    if (wechatId == undefined) {
        wechatId = getLocalStorage('wechatId');
    }
    if ($scope.coupons == undefined) {
        $http.get(url).success(function (data, status, headers, config) {
            $scope.coupons = data;
        });
    }

});

couponModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/coupon/all', {
            controller: 'couponController',
            templateUrl: 'coupon_all.html'
        })
        .when('/coupon/details/:id', {
            controller: 'couponDetailController',
            templateUrl: 'coupon_detail.html'
        })
        .otherwise({
            redirectTo: '/coupon/all'
        });
}]);




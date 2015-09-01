/**
 * Created by darlingtld on 2015/7/4 0004.
 */
var cardcodeModule = angular.module('CardcodeModule', ['ngRoute']);
var app = '/crab';
var user;
var wechatId;

cardcodeModule.config(function () {

});

cardcodeModule.controller('cardcodeController', function ($http, $scope, $location) {
    $('#refresh').text('点击刷新');
    var url = app + '/product/cardcode/wechatid/' + wechatId;
    if (user == undefined || user == null) {
        var code = getURLParameter('code');
        $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatId', wechatId);
            $http.get(url).success(function (data, status, headers, config) {
                $scope.cardcodes = data;
            });
        });
    }
    if (wechatId == undefined) {
        wechatId = getLocalStorage('wechatId');
    }
    if ($scope.cardcodes == undefined) {
        $http.get(url).success(function (data, status, headers, config) {
            $scope.cardcodes = data;
        });
    }
    $scope.showDetail = function (productId, cardcode) {
        $location.path('/cardcode/detail/' + productId + '/' + cardcode);
    }

});

cardcodeModule.controller('cardcodeDetailController', function ($http, $scope, $routeParams) {
    $('#refresh').text('回到我的提货券');
    var url = app + '/product/itemid/' + $routeParams.id;
    $scope.cardcode = $routeParams.cardcode;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.products = data;
    });
    $http.get(app + '/card/get/' + $scope.cardcode).success(function (data, status, headers, config) {
        $scope.card = data;
        if ($scope.card.used == false) {
            $scope.usedCardcode = {display: 'none'}
        }
    });


});

cardcodeModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/cardcode/all', {
            controller: 'cardcodeController',
            templateUrl: 'cardcode_all.html'
        })
        .when('/cardcode/detail/:id/:cardcode', {
            controller: 'cardcodeDetailController',
            templateUrl: 'cardcode_detail.html'
        })
        .otherwise({
            redirectTo: '/cardcode/all'
        });
}]);




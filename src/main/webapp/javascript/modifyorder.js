/**
 * Created by darlingtld on 2015/7/4 0004.
 */
var mycaiModule = angular.module('MycaiModule', ['ngRoute']);
var app = '/createsh';

mycaiModule.config(function () {

});

mycaiModule.controller('orderController', function ($http, $scope) {
    $http.get(app + "/order/all/modify").success(function (data, status, headers, config) {
        $scope.orders = data;
    });
});

mycaiModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/order/all', {
            controller: 'orderController',
            templateUrl: 'orderall.html'
        })
        .otherwise({
            redirectTo: '/order/all'
        });
}]);

mycaiModule.filter('part', function () {
    return function (input, which) {
        if (!angular.isString(input)) {
            return input;
        }
        return input.split(' ')[which];
    };
});

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function getURLParameter(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}



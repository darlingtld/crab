/**
 * Created by tangl9 on 2015-08-28.
 */
var mainModule = angular.module('MainModule', ['ngRoute']);
var app = '/crab';

mainModule.controller('expenseController', function ($scope, $http, $location) {
    $scope.confirm = function () {
        console.log($scope.cardcode);
        $location.path('/delivery/' + $scope.cardcode);

    };
    $scope.cancel = function () {
        $('#confirmDialog button.close').click();
    };
});

mainModule.controller('deliveryController', function ($scope, $http, $location, $routeParams) {
    $http.post(app + '/card/check/' + $routeParams.cardcode, {}).success(function () {
        $('div.fade').remove();
    }).error(function () {
        alert('该提货券不存在或已被使用');
        location.href = 'index.html';
    });

    $('.datetime').mobiscroll().datetime({
        theme: 'sense-ui',     // Specify theme like: theme: 'ios' or omit setting to use default
        mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default
        lang: 'zh',       // Specify language like: lang: 'pl' or omit setting to use default
        minDate: new Date(),  // More info about minDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-minDate
        maxDate: new Date(2020, 1, 1, 1, 1),   // More info about maxDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-maxDate
        stepMinute: 10  // More info about stepMinute: http://docs.mobiscroll.com/2-14-0/datetime#!opt-stepMinute
    });

    $http.get(app + '/card/get/' + $routeParams.cardcode).success(function (data) {
        $scope.card = data;
    });

    $scope.confirm = function () {
        //$scope.card.code = $routeParams.cardcode;
        $scope.card.consigneeDatetime = Date.parse($scope.card.consigneeDatetime);
        console.log($scope.card);
        $('body').html('<h3 class="text-center">提交订单中</h3>');
        $http.post(app + '/card/expense', $scope.card).success(function () {
            alert('提交成功');
            $('body').html('<h3 class="text-center">提交成功</h3>');
        });
    }
});

mainModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/expense', {
            controller: 'expenseController',
            templateUrl: 'expense.html'
        })
        .when('/delivery/:cardcode', {
            controller: 'deliveryController',
            templateUrl: 'delivery.html'
        })
        .otherwise({
            redirectTo: '/expense'
        });
}]);
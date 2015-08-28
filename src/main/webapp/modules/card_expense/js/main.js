/**
 * Created by tangl9 on 2015-08-28.
 */
var mainModule = angular.module('MainModule', ['ngRoute']);

mainModule.controller('expenseController', function ($scope, $http) {
    $scope.confirm = function () {
        console.log($scope.cardcode);
    };
    $scope.cancel = function () {
        $('#confirmDialog button.close').click();
    };
});

mainModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/expense', {
            controller: 'expenseController',
            templateUrl: 'expense.html'
        })
        .when('/delivery', {
            controller: 'deliveryController',
            templateUrl: 'delivery.html'
        })
        .otherwise({
            redirectTo: '/expense'
        });
}]);
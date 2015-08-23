/**
 * Created by darlingtld on 2015/7/4 0004.
 */
var delivModule = angular.module('DelivModule', []);
var user;
var wechatId;
var confirmCode;
var type;
var orderStatus = {
    NOT_DELIVERED: "未配送",
    IN_DELIVERY: "配送中",
    DELIVERED_NOT_PAID: "已配送（未付款）",
    DELIVERED_PAID: "已配送（已付款）"
}

var app = '/crab';
var bill = {
    items: [],
    totalAmount: 0,
    totalPrice: 0
}

delivModule.config(function () {
        confirmCode = getURLParameter('confirm_code');
        type = getURLParameter('type')
        if (type != null && type == 'update') {
            $('#confirmBtn').attr('data-target', '#updateModal');
        }
    }
);


delivModule.controller('mainController', function ($scope, $http) {
    var url = app + '/order/confirm_code/' + confirmCode;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.order = data;
        if ($scope.order.status == orderStatus.DELIVERED_PAID) {
            alert('该订单已配送（已付款）！');
            $('body').html('订单已确认！');
        }
        if ($scope.order.confirmBill != undefined) {
            $scope.actItems = JSON.parse($scope.order.confirmBill).items;
            $scope.totalPrice = JSON.parse($scope.order.confirmBill).totalPrice;
        } else {
            $scope.totalPrice = JSON.parse($scope.order.bill).totalPrice;
        }
        $scope.items = JSON.parse($scope.order.bill).items;
        for (var i = 0; i < $scope.items.length; i++) {
            var amount = $scope.items[i].amount;
            var price = $scope.items[i].productPrice;
            if ($scope.actItems != undefined) {
                $scope.items[i].totalPrice = ($scope.actItems[i].amount * price).toFixed(2);
                $scope.items[i].actAmount = $scope.actItems[i].amount;
            } else {
                $scope.items[i].totalPrice = (amount * price).toFixed(2);
                $scope.items[i].actAmount = amount;
            }

        }


    });

    $scope.updateTotalPrice = function () {
        this.item.totalPrice = (this.item.actAmount * this.item.productPrice).toFixed(2);
        modifyBill($scope.order, this.item);
        setTimeout('refreshCheckoutUI()', 200);
    }

    function modifyBill(order, item) {
        var totalPrice = 0;
        bill = order.confirmBill == undefined ? JSON.parse(order.bill) : JSON.parse(order.confirmBill);
        for (var i = 0; i < bill.items.length; i++) {
            if (item.productId == bill.items[i].productId) {
                bill.items[i].amount = item.actAmount;
            }
            totalPrice += bill.items[i].amount * bill.items[i].productPrice;

        }
        bill.totalPrice = totalPrice.toFixed(2);
        order.confirmBill = JSON.stringify(bill);
        order.totalPrice = totalPrice.toFixed(2);
        order.confirmTs = new Date().Format("yyyy-MM-dd hh:mm:ss");
    }

    $scope.confirm = function (target) {
        $scope.order.status = target.target.innerText;
        console.log($scope.order);

        $scope.order.confirmBill = JSON.stringify(bill);
        $scope.order.confirmTs = new Date().Format("yyyy-MM-dd hh:mm:ss");

        $http.post(app + "/order/update", JSON.stringify($scope.order)).
            success(function (data, status, headers, config) {
                alert('确认成功！');
                $('body').html('订单已确认！');
            }).error(function () {
                alert('确认失败!');
            });
    }

    $scope.update = function () {
        $scope.order.status = orderStatus.NOT_DELIVERED;
        console.log($scope.order);
        $scope.order.confirmBill = JSON.stringify(bill);
        $scope.order.confirmTs = new Date().Format("yyyy-MM-dd hh:mm:ss");

        $http.post(app + "/order/update", JSON.stringify($scope.order)).
            success(function (data, status, headers, config) {
                alert('修改成功！');
                $('body').html('订单已修改！');
                window.location.href = app + '/modifyorder.html';
            }).error(function () {
                alert('修改失败!');
            });
    }
});

function getURLParameter(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function refreshCheckoutUI() {
    var priceList = $('span.actTotalPrice');
    var totalPrice = 0;
    try {
        for (var i = 0; i < priceList.length; i++) {
            totalPrice += parseFloat(priceList[i].innerText);
        }
    } catch (err) {
        console.log(err);
        return;
    }
    $('#totalPrice').text(totalPrice.toFixed(2));
}

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
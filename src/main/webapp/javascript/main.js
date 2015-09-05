/**
 * Created by darlingtld on 2015/7/4 0004.
 */
var module = angular.module('CtModule', ['ngRoute']);

var app = '/crab';
var bill = {
    items: [],
    totalAmount: 0,
    totalPrice: 0
}
var user;
var wechatId;
var username;
var buyType = getURLParameter('buy_type');

module.service('authService', function ($http) {
    this.getUserInfo = function (callback) {
        if (user != undefined && user != null) {
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            if (callback && typeof(callback) === "function") {
                callback();
            }
        } else {
            var code = getURLParameter('code');
            $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
                user = data;
                wechatId = user.openid;
                $('img.user-icon').attr('src', user.headimgurl);
                $http.post(app + "/user/save_or_update", JSON.stringify(user)).success(function (data, status, headers, config) {
                    user = data;
                    if (callback && typeof(callback) === "function") {
                        callback();
                    }
                });
            });
        }
    };
});

module.config(function () {
        var isOrderHistory = getURLParameter('order_history');
        if (isOrderHistory != null) {
            goToOrderHistory();
        }

        if (typeof(Storage) != "undefined") {
            try {
                var ls = getLocalStorage('bill');
                if (ls != undefined) {
                    bill = JSON.parse(ls);
                    refreshCheckoutUI(bill.totalAmount, bill.totalPrice);
                }
            }
            catch (err) {
                bill = {
                    items: [],
                    totalAmount: 0,
                    totalPrice: 0
                }
            }
        }
        else {
            console.log("local storage is not supported!")
        }

    }
)


module.controller('mainController', function ($scope, $location, authService) {
    authService.getUserInfo();
    var isOrderHistory = getURLParameter('order_history');
    if (isOrderHistory != null) {
        goToOrderHistory();
        sleep(2000);
        $location.path("/order/history");
    }
    $scope.clearAndReload = function () {
        console.log('clear local storage');
        setLocalStorage('bill', null);
        setLocalStorage('wechatId', null);
        location.reload();
    }
});

module.controller('navController', function ($scope, $http, $routeParams) {
    goToNav();
    var url = app + '/nav/type/' + $routeParams.type;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.productList = data;
    });
});

module.controller('productController', function ($scope, $http, $routeParams) {
    goToProduct();
    if (user == undefined || user == null) {
        var code = getURLParameter('code');
        $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatid', wechatId);
        });
    }
    if (wechatId == undefined) {
        wechatId = getLocalStorage('wechatid');
    }
    var url = app + '/product/itemid/' + $routeParams.itemid;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.products = data;
        fillSpinner($scope.products);
    });
});

module.controller('mostBuyController', function ($scope, $http, $routeParams) {
    goToProduct();
    if (user == undefined || user == null) {
        var code = getURLParameter('code');
        $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatid', wechatId);
        });
    }
    if (wechatId == undefined) {
        wechatId = getLocalStorage('wechatid');
    }
    var url = app + '/product/most_bought/' + $routeParams.type + '/wechatid/' + wechatId;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.products = data;
        fillSpinner($scope.products);
    });
});

module.controller('checkoutController', function ($scope, $location, $http) {
    if (bill.totalAmount == 0) {
        alert('您还未购买任何物品');
        init();
        $location.path('/');
    } else {
        goToCheckout();
        if (user == undefined || user == null) {
            var code = getURLParameter('code');
            $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
                user = data;
                wechatId = user.openid;
                $('img.user-icon').attr('src', user.headimgurl);
                $http.post(app + "/user/save_or_update", JSON.stringify(user)).success(function (data, status, headers, config) {
                    user = data;
                });
            });
        }
        $scope.bill = bill;
    }

});


module.controller('confirmController', function ($scope, $http, $location) {
        if (bill.totalAmount == 0) {
            alert('您还未购买任何物品');
            init();
            $location.path('/');
        } else {

            goToConfirm();
            if (user == undefined || user == null) {
                var code = getURLParameter('code');
                $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
                    user = data;
                    wechatId = user.openid;
                    $('img.user-icon').attr('src', user.headimgurl);
                    $http.post(app + "/user/save_or_update", JSON.stringify(user)).success(function (data, status, headers, config) {
                        user = data;
                    });
                    setLocalStorage('wechatid', wechatId);
                });
            }
            //$('.datetime').mobiscroll().datetime({
            //    theme: 'sense-ui',     // Specify theme like: theme: 'ios' or omit setting to use default
            //    mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default
            //    lang: 'zh',       // Specify language like: lang: 'pl' or omit setting to use default
            //    minDate: new Date(),  // More info about minDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-minDate
            //    maxDate: new Date(2020, 1, 1, 1, 1),   // More info about maxDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-maxDate
            //    //stepMinute: 10  // More info about stepMinute: http://docs.mobiscroll.com/2-14-0/datetime#!opt-stepMinute
            //});

            $scope.bill = bill;
            var url = app + '/coupon/calc/wechatid/' + wechatId;
            $http({
                url: url,
                params: {bill: encodeURI(JSON.stringify($scope.bill))}
            }).success(function (data, status, headers, config) {
                $scope.couponList = data;
            });
            var origTotalPrice = $scope.bill.totalPrice;
            $scope.modifyTotalPrice = function () {
                if ($scope.selectedCoupon == null) {
                    $scope.bill.totalPrice = origTotalPrice;
                    $scope.bill.usedCoupon = null;
                } else {
                    $scope.bill.totalPrice = $scope.selectedCoupon.modifiedTotalPrice;
                    $scope.bill.usedCoupon = $scope.selectedCoupon;
                }

            }

            //$('#delivery_ts').val('次日上午8:00到10:30间');
            $('.datetime').mobiscroll().datetime({
                theme: 'sense-ui',     // Specify theme like: theme: 'ios' or omit setting to use default
                mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default
                lang: 'zh',       // Specify language like: lang: 'pl' or omit setting to use default
                minDate: new Date(),  // More info about minDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-minDate
                maxDate: new Date(2020, 1, 1, 1, 1),   // More info about maxDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-maxDate
                stepMinute: 10  // More info about stepMinute: http://docs.mobiscroll.com/2-14-0/datetime#!opt-stepMinute
            });

            if (user != undefined) {
                $('#buyer_info').val(user.buyerInfo);
                $('#buyer_address').val(user.buyerAddress);
                $('#consignee').val(user.consignee);
                $('#consignee_contact').val(user.consigneeContact);
            } else if (getLocalStorage('buyer_info') != undefined && getLocalStorage('buyer_info') != null) {
                $('#buyer_info').val(getLocalStorage('buyer_info'));
                $('#buyer_address').val(getLocalStorage('buer_address'));
                $('#consignee').val(getLocalStorage('consignee'));
                $('#consignee_contact').val(getLocalStorage('consignee_contact'));
            }

            $('div.checkout').on('click', 'a.next', function () {
                    if (user == undefined) {
                        user = {
                            nickname: 'songda',
                            openid: 'songda'
                        }
                    }
                    var order = {
                        userId: user.nickname,
                        wechatId: wechatId,
                        bill: JSON.stringify(bill),
                        orderTs: new Date().Format("yyyy-MM-dd hh:mm:ss"),
                        deliveryTs: $('#delivery_ts').val(),
                        buyerInfo: $('#buyer_info').val(),
                        buyerAddress: $('#buyer_address').val(),
                        consignee: $('#consignee').val(),
                        consigneeContact: $('#consignee_contact').val()
                    };

                    if (validateOrder(order)) {
                        goToOrderHistory();
                        $('#content').html('<h2 class="text-center">订单提交中！</h2>');
                        $http.post(app + "/order/submit", JSON.stringify(order)).
                            success(function (data, status, headers, config) {
                                alert('提交订单成功！');
                                clearLocalStorage();
                                setLocalStorage('buyer_info', order.buyerInfo);
                                setLocalStorage('buyer_address', order.buyerAddress);
                                setLocalStorage('consignee', order.consignee);
                                setLocalStorage('consignee_contact', order.consigneeContact);
                                clearBill();
                                init();
                                $location.path('/order/history');
                            }).error(function () {
                                alert("存货不足");
                                init();
                                $location.path('/')
                            });
                    }
                }
            )

        }
    }
)
;

module.controller('messageController', function ($scope, $location, $http) {
    goToMessage();
    if (user == undefined || user == null) {
        var code = getURLParameter('code');
        $http.get(app + "/user/code/" + code).success(function (data, status, headers, config) {
            user = data;
            wechatId = user.openid;
            $('img.user-icon').attr('src', user.headimgurl);
            setLocalStorage('wechatid', wechatId);
        });
    }
    if (wechatId == undefined) {
        wechatId = getLocalStorage('wechatid');
    }
    var url = app + '/message/wechatid/' + wechatId;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.messages = data;
    });

});

module.controller('orderController', function ($http, $scope) {
    goToOrderHistory();
    var url = app + '/order/get/' + wechatId;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.orders = data;
    });
});

module.controller('orderDetailController', function ($http, $scope, $routeParams) {
    goToOrderHistory();
    var url = app + '/order/detail/' + $routeParams.id;
    $http.get(url).success(function (data, status, headers, config) {
        $scope.orderDetail = data;
        $scope.items = JSON.parse($scope.orderDetail.bill).items;
        $scope.total = JSON.parse($scope.orderDetail.bill)
        $scope.total.price = parseFloat($scope.total.price).toFixed(2);
        if (JSON.parse($scope.orderDetail.confirmBill) != null) {
            $scope.items = JSON.parse($scope.orderDetail.confirmBill).items;
            $scope.total = JSON.parse($scope.orderDetail.bill);
            $scope.actTotal = JSON.parse($scope.orderDetail.confirmBill)
        }
    });

});
module.controller('routerController', function ($http, $scope, $location) {
    if (user == undefined || user.username == undefined) {
        alert('您尚未注册，请先注册~')
        $location.path('/register');
    } else {
        $location.path('/confirm')
    }
});
module.controller('registerController', function ($http, $scope, $location) {
    goToRegister();
    $scope.register = function () {
        if (user == undefined) {
            user = {
                username: $scope.username,
                email: $scope.email,
                mobile: $scope.mobile
            }
        } else {
            user.username = $scope.username;
            user.email = $scope.email;
            user.mobile = $scope.mobile;
        }
        if (user.username == undefined || user.username.trim() == '') {
            alert('请输入用户名');
            return;
        } else if (user.mobile == undefined || user.mobile.trim() == '') {
            alert('请输入手机号');
            return;
        } else if (user.email == undefined || user.email.trim() == '') {
            alert('请输入邮箱');
            return;
        }
        //console.log(user);
        $http.post(app + "/user/save_or_update", JSON.stringify(user)).success(function (data, status, headers, configs) {
            alert('注册成功');
            $location.path('/confirm');
        });
    }

});

function clearBill() {
    bill = {
        items: [],
        totalAmount: 0,
        totalPrice: 0
    }
}

module.controller('submitController', function ($scope) {
    $('footer.bg-dark').hide();
    clearBill();
});

module.directive('spinnerInstance', function () {
    return {
        restrict: 'AE',
        scope: {},
        link: function (scope, element, attr) {
            var value = attr.spinnerInstance;
            if (value == undefined || value == "") {
                value = 0;
            }
            element.css('width', '20%');
            element.css('text-align', 'center');
            element.css('color', 'green');
            element.css('background-color', 'white');
            element.spinner({
                value: value,
            });
        }
    }
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
        .when('/product/category/:category', {
            controller: 'productController',
            templateUrl: 'product.html'
        })
        .when('/product/itemid/:itemid', {
            controller: 'productController',
            templateUrl: 'product.html'
        })
        .when('/product/most_bought/:type', {
            controller: 'mostBuyController',
            templateUrl: 'product.html'
        })
        .when('/checkout', {
            controller: 'checkoutController',
            templateUrl: 'checkout.html'
        })
        .when('/confirm', {
            controller: 'confirmController',
            templateUrl: 'confirm.html'
        })
        .when('/submit', {
            controller: 'submitController',
            templateUrl: 'success.html'
        })
        .when('/nav/:type', {
            controller: 'navController',
            templateUrl: 'nav.html'
        })
        .when('/order/history', {
            controller: 'orderController',
            templateUrl: 'orderHistory.html'
        })
        .when('/order/details/:id', {
            controller: 'orderDetailController',
            templateUrl: 'orderDetails.html'
        })
        .when('/register', {
            controller: 'registerController',
            templateUrl: 'register.html'
        })
        .when('/router', {
            controller: 'routerController',
            template: ''
        })
        .when('/message', {
            controller: 'messageController',
            templateUrl: 'message_all.html'
        })
        .otherwise({
            redirectTo: '/nav/' + buyType
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

function fillSpinner(products) {
    for (var i = 0; i < bill.items.length; i++) {
        var productId = bill.items[i].productId;
        for (var j = 0; j < products.length; j++) {
            if (productId == products[j].id) {
                products[j].purchaseAmount = bill.items[i].amount;
                break;
            }
        }

    }
}

function init() {
    $('footer.bg-dark').show();
    $('.checkout').html('<div><span class="glyphicon glyphicon-shopping-cart"></span></div><div>物件数：<span id="totalAmount">0</span>件</div><div>总价：<span id="totalPrice">0</span>元</div><div style="text-align: center;"><a class="next btn btn-info" href="#/checkout">下一步</a></div>');
    refreshCheckoutUI(bill.totalAmount, bill.totalPrice.toFixed(2));

}


function refreshCheckoutUI(totalAmount, totalPrice) {
    if (totalPrice < 0.01) {
        totalPrice = 0;
    }
    $('#totalAmount').text(totalAmount);
    $('#totalPrice').text(parseFloat(totalPrice).toFixed(2));
}

function isFirstBuy(items, productId) {
    for (var i = 0; i < items.length; i++) {
        if (productId == items[i].productId) {
            return false;
        }
    }
    return true;
}

function validateOrder(order) {
    if (order.deliveryTs.trim() == '') {
        alert('请选择配送时间');
        return false;
    } else if (order.buyerInfo.trim() == '') {
        alert('请输入买家信息');
        return false;
    } else if (order.buyerAddress.trim() == '') {
        alert('请输入买家地址');
        return false;
    } else if (order.consignee.trim() == '') {
        alert('请输入收货人姓名');
        return false;
    } else if (order.consigneeContact.trim() == '') {
        alert('请输入收货人联系方式');
        return false;
    } else {
        return true;
    }
}

function refreshCheckoutItemUI(ele, amount, productPrice) {
    if (ele.find('.amount>span').length == 0) {
        return;
    }
    $(ele.find('.amount>span')[0]).text(amount);
    $(ele.find('.price>span')[0]).text(amount * productPrice);
}

function changeTotalCost(_this) {
    var ele = _this.parent().parent().parent();
    var amount = _this.siblings('input')[0].value;
    var productId = $(ele.find('.product_id')[0]).data('product_id');
    var productName = $(ele.find('.product_name')[0]).data('product_name');
    var productDescription = $(ele.find('.product_description')[0]).data('product_description');
    var productPrice = $(ele.find('.product_price')[0]).data('product_price');
    var productUnit = $(ele.find('.product_unit')[0]).data('product_unit');
    var picurl = $(ele.find('.product_picurl')[0]).data('product_picurl');
    if (_this.hasClass('increase')) {
        bill.totalAmount++;
        bill.totalPrice += parseFloat(productPrice);
        if (isFirstBuy(bill.items, productId)) {
            bill.items.push({
                    productId: productId,
                    productName: productName,
                    description: productDescription,
                    amount: amount,
                    productPrice: productPrice,
                    picurl: picurl,
                    productUnit: productUnit,
                }
            );
        } else {
            for (var i = 0; i < bill.items.length; i++) {
                if (productId == bill.items[i].productId) {
                    bill.items[i].amount++;
                }
            }
        }
    }

    else {
        bill.totalAmount--;
        bill.totalPrice -= parseFloat(productPrice);
        for (var i = 0; i < bill.items.length; i++) {
            if (productId == bill.items[i].productId) {
                bill.items[i].amount--;
                if (bill.items[i].amount == 0) {
                    bill.items.splice(i, 1);
                }
            }
        }
    }
    refreshCheckoutUI(bill.totalAmount, bill.totalPrice.toFixed(2));
    refreshCheckoutItemUI(ele, amount, productPrice);
    saveToLocalStorage(bill);
//console.log(totalAmount + ":" + totalPrice.toFixed(2));
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

function getURLParameter(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function DateAdd(interval, number, date) {
    switch (interval) {
        case "y ":
        {
            date.setFullYear(date.getFullYear() + number);
            return date;
            break;
        }
        case "q ":
        {
            date.setMonth(date.getMonth() + number * 3);
            return date;
            break;
        }
        case "m ":
        {
            date.setMonth(date.getMonth() + number);
            return date;
            break;
        }
        case "w ":
        {
            date.setDate(date.getDate() + number * 7);
            return date;
            break;
        }
        case "d ":
        {
            date.setDate(date.getDate() + number);
            return date;
            break;
        }
        case "h ":
        {
            date.setHours(date.getHours() + number);
            return date;
            break;
        }
        case "m ":
        {
            date.setMinutes(date.getMinutes() + number);
            return date;
            break;
        }
        case "s ":
        {
            date.setSeconds(date.getSeconds() + number);
            return date;
            break;
        }
        default:
        {
            date.setDate(d.getDate() + number);
            return date;
            break;
        }
    }
}

function restoreBuyPage() {
    $('#ma-menu-bar').show();
    $('#subCategoryBlock').show();
    $('#mainListBlock').css('width', '75%');
    init();
}

function goToCheckout() {
    init();
    $('#ma-menu-bar').show();
    $('#subCategoryBlock').hide();
    $('#mainListBlock').css('width', '100%');
    $('a.next').attr('href', '#/confirm');
    $('a.next').text('确认订单');
}

function goToConfirm() {
    init();
    $('.checkout').html('<div><a class="next">提交</a>');
    $('a.next').css('margin-left', '43%');
    $('a.next').css('font-size', 'x-large');
}

function goToOrderHistory() {
    $('#subCategoryBlock').hide();
    $('#mainListBlock').css('width', '100%');
    $('#ma-menu-bar').hide();
    $('footer').hide();
}

function goToRegister() {
    $('#subCategoryBlock').hide();
    $('#mainListBlock').css('width', '100%');
    $('#ma-menu-bar').hide();
    $('footer').hide();
}

function goToNav() {
    $('footer').hide();
}

function goToProduct() {
    $('footer').show();
}

function goToMessage() {
    $('#subCategoryBlock').hide();
    $('#mainListBlock').css('width', '100%');
    $('footer').hide();
}

function saveToLocalStorage(bill) {
    setLocalStorage('bill', JSON.stringify(bill));
}

function clearLocalStorage() {
    if (typeof(Storage) != "undefined") {
        setLocalStorage('bill', null);
    } else {
        console.log("local storage is not supported!")
    }
}


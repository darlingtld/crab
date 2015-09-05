/**
 * Created by tangl9 on 2015-08-06.
 */
var isTest = false;
if (isTest) {
    user = {
        nickname: 'lingda',
        openid: 'o5Irvt5957jQ4xmdHmDp59epk0UU',
        headimgurl: 'http://wx.qlogo.cn/mmopen/0pygn8iaZdEeVBqUntWJB9rzhkKIyKnQFzIqswrYFrhHefEXiaCOhJnBqIicxMRd0IeOHe9ffAtKTvXzOfokp9UhS2BlYXh5PxO/0',
        consignee: '灵达',
        consigneeContact: '13402188638',
        buyerInfo: '新中源大楼',
        buyerAddress: '长阳路1930号'
    }
    wechatId = 'o5Irvt5957jQ4xmdHmDp59epk0UU';
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

function setLocalStorage(key, value) {
    if (typeof(Storage) != "undefined") {
        localStorage.setItem(app + '_' + key, value);
        //console.log('[' + key + ']:[' + value + ']');
    } else {
        console.log("local storage is not supported!")
    }
}

function getLocalStorage(key) {
    if (typeof(Storage) != "undefined") {
        return localStorage.getItem(app + '_' + key);
    } else {
        console.log("local storage is not supported!");
    }
}

function sleep(d) {
    for (var t = Date.now(); Date.now() - t <= d;);
}

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
/**
 * Created by sarazhu on 15/5/24.
 */

var _order;
var _dadaId=0;
var app='/crab';

//$(function () {
//    _initialPage();
//});


function saveId(id){
    _dadaId=id;
}

function _initialPage() {
    $('#deliveryTs').mobiscroll().datetime({
        theme: 'sense-ui',     // Specify theme like: theme: 'ios' or omit setting to use default
        mode: 'scroller',       // Specify scroller mode like: mode: 'mixed' or omit setting to use default
        lang: 'zh',       // Specify language like: lang: 'pl' or omit setting to use default
        minDate: new Date(),  // More info about minDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-minDate
        maxDate: new Date(2020, 1, 1, 1, 1),   // More info about maxDate: http://docs.mobiscroll.com/2-14-0/datetime#!opt-maxDate
        stepMinute: 10  // More info about stepMinute: http://docs.mobiscroll.com/2-14-0/datetime#!opt-stepMinute
    });

    //init table
    $.ajax({
        type: "get",
        url: app+"/order/getall",
        dataType: "json",
        success: function (data) {
            $("#orderTemplate").tmpl(data).appendTo("#orderList");
            _order = data;
            _pageTable(10);
            $("#loading").addClass("hidden");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus);
            console.log(XMLHttpRequest.status);
            console.log(XMLHttpRequest.responseText);
        }
    });

    $("#pagination").pagination(15, {
        items_per_page: 10
    });
}


function _updateOrder() {
    console.log(_dadaId);
    //var eleSiblings = $('#orderList').find('tr>input[value='+_dadaId+']').siblings();
    var order={
        id:_dadaId,
        deliveryTs: $('#deliveryTs').val(),
        status:$('#orderStatus').val()
    }


    $.ajax({
        type: "post",
        url: app+"/order/update",
        contentType: "application/json",
        data: JSON.stringify(order),
        success: function (data) {
            alert('保存成功！');
            location.reload();
        },
        error: function (data) {
            alert(data.status);
        }
    });
}


function paginationEnter(evt, val) {
    var evt = evt ? evt : (window.event ? window.event : null);//兼容IE和FF
    if (evt.keyCode == 13) {
        if(val < 1){
            alert("请输入大于0的数！");
            return;
        }
        _pageTable($("#numPerPage").val());
    }
}

function _pageTable(numPerPage) {
    $("#pagination").pagination(_order.length, {
        callback: pageSelectCallback,
        items_per_page: numPerPage,
        prev_text: "上一页",
        next_text: "下一页"
    });
}

function pageSelectCallback(page_index) {
    var numPerPage = $("#numPerPage").val();
    $("#orderList tr").hide();
    if (page_index == 0) {
        $("#orderList tr:lt(" + numPerPage + ")").show();
    } else {
        $("#orderList tr:gt(" + (page_index * numPerPage - 1) + ")").show();
        $("#orderList tr:gt(" + ((page_index + 1) * numPerPage - 1) + ")").hide();
    }
}
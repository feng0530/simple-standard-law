$(document).ready(function() {

    // 從 localStorage 獲取 JWT
    const token = localStorage.getItem('jwt');
    if (token) {

        const payload = jwt_decode(token); // 使用 jwt_decode 而不是 jwtDecode

        $('#user_name').text(payload.user.userName);

        user.funcs.list(payload.user.userId);
        init();
    
    } else {
        alert('請先進行登入');
        // 重定向至登入頁面
        window.location.href = '/login.html';
    }
});

function init() {

    // 綁定側邊欄點擊事件來加載對應內容
    $('#sidebar').on('click', 'a', function(event) {
        showContent(this);
    });

    // 匯入按鈕點擊事件
    $('#importButton').click(function() {
        const file = $('#fileUpload')[0].files[0];
        if (file) {
            alert('檔案已上傳: ' + file.name);
            // 這裡可以加入 AJAX 上傳的代碼
        } else {
            alert('請先選擇要上傳的檔案');
        }
    });

    // 監聽業務功能項目點擊事件
    $(".card .nav-item .nav-link").on('click',function() {
        // 移除所有業務功能項目的 "selected" 樣式
        $(".card .nav-item .nav-link").removeClass("selected");
        // 對點擊的業務功能項目添加 "selected" 樣式
        $(this).addClass("selected");
    });

    // 標籤點擊事件
    $('#mainTabs a').click(function() {
        $('#mainTabs a').removeClass('active');
        $(this).addClass('active');

        const option = $(this).parent('li').attr('id');

        const optionMap = {
            'report': showReport,
            'upload': showUpload,
            'adjust': showAdjust,
            'select': showSelect
        }
    
        // 根據 option 執行對應的函數
        if (optionMap[option]) {
            optionMap[option]($(this).attr('name'));
        }
    })

    $('#logout').on('click', function() {
        var o = {
            url: domain + "/users/logout",
            type: "DELETE",
            Authorization: Authorization_Prefix + localStorage.getItem('jwt'),
            successCallback: function(res) {
                if (res.msg === "Success!"){
                    alert("登出成功!");
                    window.location.href = 'login.html';
                }
            }
        }

        if (confirm("確定要登出嗎?")) {
            system.ajax(o);
        }
    })
}

function showContent(element) {
    $('#mainTabs a').removeClass('active');
    $('#mainTabs #report a').addClass('active');
    $('#home-welcome').hide();
    $('#import-section').hide();
    $('#mainTabs').show();
    $('#tab-content').show();

    let target = $(element).data('target');
    if (target === 'r') {
        // 隱藏除 <li id="report"> 之外的所有 <li> 元素
        $('#mainTabs li').not('#report').css('display', 'none');
    } else {
        // 移除所有 <li> 元素上的 display: none 樣式
        $('#mainTabs li').css('display', '');
    }

    let reportName = $(element).attr('name');
    showReport(reportName);
    $('#mainTabs a').attr('name', reportName);
}

function showReport(reportName) {
    $('#import-section').hide();
    $('#tab-content').show();
    $('#tab-content').html($(''));
    $('#tab-content').html($(`<div>${reportName}: showReport....</div>`));
}
function showUpload(reportName) {
    $('#tab-content').hide();
    $('#import-section').show();
}
function showAdjust(reportName) {
    $('#import-section').hide();
    $('#tab-content').show();
    $('#tab-content').html($(''));
    $('#tab-content').html($(`<div>${reportName}: showAdjust....</div>`));
}
function showSelect(reportName) {
    $('#import-section').hide();
    $('#tab-content').show();
    $('#tab-content').html($(''));
    $('#tab-content').html($(`<div>${reportName}: showSelect....</div>`));
}

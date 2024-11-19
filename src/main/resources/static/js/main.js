$(document).ready(function () {

    // 從 localStorage 獲取 JWT
    const token = localStorage.getItem('jwt');
    if (token) {

        const payload = jwt_decode(token); // 使用 jwt_decode 而不是 jwtDecode

        $('#user_name').text(payload.user.userName);

        user.funcs.list(payload.user.userId);
        main.init();

    } else {
        alert('請重新登入!');
        // 重定向至登入頁面
        window.location.href = '/login.html';
    }
});

main = {
    init: function () {
        // 綁定側邊欄點擊事件來加載對應內容
        $('#sidebar').on('click', 'a', function (e) {
            e.preventDefault();
            main.showContent(this);
        });

        // 匯入按鈕點擊事件
        $('#importButton').click(function () {
            // const file = $('#fileUpload')[0].files[0];
            // if (file) {
            //     alert('檔案已上傳: ' + file.name);
            //     // 這裡可以加入 AJAX 上傳的代碼
            // } else {
            //     alert('請先選擇要上傳的檔案');
            // }

            let report = $('#report a').data('optioncode');

            var o = {
                url: "/reports/" + report,
                type: "POST",
                contentType: "application/json",
                Authorization: true,
                data: {
                    msg:"MQ",
                    exchange:"Generate Report Exchange",
                    routingKey:"report.IR01"
                },
                successCallback: function (res) {

                    if (res.msg !== "Success!") {
                        alert(res.msg);
                        return;
                    }

                    alert("成功!");
                }
            }
            system.ajax(o);
        });

        // 監聽業務功能項目點擊事件
        $(".card .nav-item .nav-link").on('click', function () {
            // 移除所有業務功能項目的 "selected" 樣式
            $(".card .nav-item .nav-link").removeClass("selected");
            // 對點擊的業務功能項目添加 "selected" 樣式
            $(this).addClass("selected");
        });

        // 標籤點擊事件
        $('#mainTabs a').click(function (e) {
            e.preventDefault();

            const option = $(this).parent('li').attr('id');

            const optionMap = {
                'report': main.showReport,
                'upload': main.showUpload,
                'adjust': main.showAdjust,
                'select': main.showSelect
            }

            // 根據 option 執行對應的函數
            if (optionMap[option]) {
                optionMap[option]($(this).data('optioncode'));
            }
        })

        $('#logout').on('click', function () {
            var o = {
                url: "/users/logout",
                type: "DELETE",
                Authorization: true,
                successCallback: function (res) {
                    if (res.msg === "Success!") {
                        localStorage.removeItem('jwt');
                        alert("登出成功!");
                        window.location.href = 'login.html';
                    }
                }
            }

            if (confirm("確定要登出嗎?")) {
                system.ajax(o);
            }
        })
    },
    showContent: function (element) {
        $('#mainTabs a').removeClass('active');
        $('#report a').addClass('active');
        $('#home-welcome').hide();
        $('#import-section').hide();
        $('#mainTabs').show();
        $('#tab-content').show();

        let isSet = $(element).data('optioncode').startsWith('SET');
        let optioncode = $(element).data('optioncode');
        if (isSet) {
            var settingMap = {
                "SET01": main.showUserManage,
                "SET02": main.showRoleManage
            }
            settingMap[optioncode]();

        } else {
            // level = r 表示只有 Reading權限，故隱藏其他選項
            let level = $(element).data('level');
            if (level === 'r') {
                // 隱藏除 <li id="report"> 之外的所有 <li> 元素
                $('#mainTabs li').not('#report').css('display', 'none');
            } else {
                // 移除所有 <li> 元素上的 display: none 樣式
                $('#mainTabs li').css('display', '');
            }

            let reportName = $(element).data('optioncode');
            main.showReport(reportName);
            $('#mainTabs a').data('optioncode', reportName);
        }
    },
    showReport: function (reportName) {
        $('#mainTabs a').removeClass('active');
        $('#report a').addClass('active');
        $('#import-section').hide();
        $('#tab-content').show();
        $('#tab-content').html($(''));
        $('#tab-content').html($(`
            <div>
                <button class="btn btn-primary" id="getReport">查詢${reportName}</button>
                <a href="IR01/IR01.pdf">下載${reportName}</a>
            </div>
        `));

        $('#getReport').on('click', function () {
            main.getReport(reportName)
        });
    },
    showUpload: function (reportName) {
        $('#mainTabs a').removeClass('active');
        $('#upload a').addClass('active');
        $('#tab-content').hide();
        $('#import-section').show();
    },
    showAdjust: function (reportName) {
        $('#mainTabs a').removeClass('active');
        $('#adjust a').addClass('active');
        $('#import-section').hide();
        $('#tab-content').show();
        $('#tab-content').html($(''));
        $('#tab-content').html($(`<div>${reportName}: showAdjust....</div>`));
    },
    showSelect: function (reportName) {
        $('#mainTabs a').removeClass('active');
        $('#select a').addClass('active');
        $('#import-section').hide();
        $('#tab-content').show();
        $('#tab-content').html($(''));
        $('#tab-content').html($(`<div>${reportName}: showSelect....</div>`));
    },
    getReport: function (reportName) {
        var o = {
            url: "/reports/" + reportName,
            type: "GET",
            Authorization: true,
            successCallback: function (res) {
                if (res.msg !== "Success!") {
                    alert(res.msg);
                    return;
                }

                $('#tab-content').html('');
                //"data:application/pdf;base64," + 
                let base64 = res.result;
                $('#tab-content').html('<canvas id="pdf-canvas"></canvas>');

                var pdfData = atob(base64);
                var loadingTask = pdfjsLib.getDocument({ data: pdfData });
                loadingTask.promise.then(
                    function (pdf) {
                        pdf.getPage(1).then(function (page) {
                            // 設定縮放比例
                            var scale = 2.0; // 可以根據需求調整到 2.0 或更高

                            // 獲取頁面的視口信息
                            var viewport = page.getViewport({ scale: scale });

                            var canvas = document.getElementById('pdf-canvas');
                            var context = canvas.getContext('2d');

                            // 設定 canvas 的實際大小
                            canvas.width = viewport.width;  // 實際寬度
                            canvas.height = viewport.height; // 實際高度

                            // 設定 canvas 的 CSS 樣式，以保持顯示大小
                            canvas.style.width = viewport.width / scale + 'px';  // 顯示寬度
                            canvas.style.height = viewport.height / scale + 'px'; // 顯示高度

                            // 渲染上下文設置
                            var renderContext = {
                                canvasContext: context,
                                viewport: viewport
                            };

                            // 渲染頁面
                            page.render(renderContext);
                        });
                    }
                );
            }
        }
        system.ajax(o);
    },
    getRoleList: function () {
        var o = {
            url: "/roles",
            type: "GET",
            Authorization: true,
            async: false,
            successCallback: function (res) {
                $('#userRoles').html("");
                $.each(res.result, function (index, role) {
                    var div = $('<div>').addClass('form-check').css('width', '50%').css('float', 'left');
                    var checkbox = $('<input>')
                        .attr('type', 'checkbox')
                        .addClass('form-check-input')
                        .val(role.id)
                        .attr('id', 'role_' + role.roleId);

                    var label = $('<label>')
                        .addClass('form-check-label')
                        .attr('for', 'role_' + role.roleId)
                        .text(role.roleName);

                    div.append(checkbox).append(label);
                    $('#userRoles').append(div);
                })
            }
        }
        system.ajax(o);
    },
    showUserManage: function () {
        $('#mainTabs').hide();
        $('#tab-content').show();
        $('#tab-content').html($(''));
        $('#tab-content').html('<div style="margin-top:10px;"></div><button id="addUser_btn" class="btn btn-primary">新增使用者</button></div>');
        $('#tab-content').append(`<table id="userTable" class="table table-striped table-bordered" style="width:100%"></table>`);

        let obj = {
            aoColumnDefs: [
                {
                    "targets": "_all", // 所有列
                    "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).removeClass('dt-type-numeric'); // 移除 dt-type-numeric
                        $(td).addClass('text-center'); // 讓 td 置中
                    }
                },
                {
                    "targets": "_all",
                    "className": "text-center", // 將所有表頭置中
                    "headerCell": function (th) {
                        $(td).removeClass('dt-type-numeric'); // 移除 dt-type-numeric
                    }
                }
            ],
            aoColumns: [
                { "data": "userId", "title": "使用者ID" },
                { "data": "userName", "title": "使用者名稱" },
                { "data": "account", "title": "帳號" },
                { "data": "createTime", "title": "建立時間" },
                {
                    "data": null,
                    "title": "操作",
                    "sortable": false,
                    "render": function (data, type, row) {
                        return '<button type="button" class="btn btn-primary userEdit_btn" data-toggle="modal" data-target="#manageModal">編輯</button>'
                            + ' <button type="button" class="btn btn-danger userDelete_btn" data-id="' + row.userId + '">刪除</button>';
                    },
                }
            ],
            aaSorting: [[0, 'asc']],
            pageLength: 10,
            bLengthChange: true,
            searching: true,
            paging: true
        }
        let o = {
            url: "/users",
            type: "GET",
            Authorization: true,
            async: false,
            successCallback: function (res) {
                dataTable.create("userTable", obj, res.result);
            }
        }
        system.ajax(o);

        $('.userDelete_btn').on('click', function () {
            var userId = $(this).data('id');
            var o = {
                url: "/users/" + userId,
                type: "DELETE",
                Authorization: true,
                successCallback: function (res) {
                    alert(res.msg);

                    if (res.msg === "刪除成功!") {
                        main.showUserManage();
                    }
                }
            }

            if (confirm("確定要刪除此使用者嗎?")) {
                system.ajax(o);
            }
        })

        $('.userEdit_btn').on('click', function () {
            $('#manageModal .modal-content').html('');

            var modalHeader = $('<div></div>').addClass('modal-header')
                .append($('<h5></h5>').addClass('modal-title').text('編輯使用者'));

            // 創建 userName 的 div
            var modalBody = $('<div></div>').addClass('modal-body')
                .append(
                    $('<form></form>').attr('id', 'modalForm')
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'userId').text('使用者ID :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'userId',
                                    name: 'userId',
                                    readonly: true
                                }))
                        )
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'userName').text('使用者名稱 :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'userName',
                                    name: 'userName',
                                    readonly: true
                                })))
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'userRoles').text('使用者角色 :'))
                                .append($('<div></div>').attr('id', 'userRoles'))
                        )
                );

            var modalFooter = $('<div></div>').addClass('modal-footer')
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-secondary',
                    "data-dismiss": 'modal'
                }).text('取消'))
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-primary',
                    id: 'saveUserUpdate'
                }).text('保存變更'))

            $('#manageModal .modal-content').append(modalHeader);
            $('#manageModal .modal-content').append(modalBody);
            $('#manageModal .modal-content').append(modalFooter);

            // 假設你可以從表格中獲取角色的詳細資訊
            var userId = $(this).closest('tr').find('td:eq(0)').text();
            var userName = $(this).closest('tr').find('td:eq(1)').text();
            // 填充到彈出視窗的表單中
            $('#userId').val(userId);
            $('#userName').val(userName);
            main.getRoleList();

            var checkBoxs = $('#userRoles input[type="checkbox"]');
            var o = {
                url: "/users/" + userId + "/roles",
                type: "GET",
                Authorization: true,
                successCallback: function (res) {
                    if (res.result !== null) {
                        $.each(res.result.roleList, function (index, userRole) {
                            checkBoxs.each(function () {
                                if ($(this).attr('id') === "role_" + userRole.roleId) {
                                    $(this).prop('checked', true);
                                }
                            })
                        })
                    }
                    $('#manageModal').modal('show');
                }
            }
            system.ajax(o);

            $('#saveUserUpdate').on('click', function () {
                // 這裡可以進行資料驗證和提交表單
                var userId = $('#userId').val();
                var roleIdList = [];
                checkBoxs.each(function () {
                    if ($(this).prop('checked') === true) {
                        let roleId = parseInt($(this).attr('id').substring("role_".length));
                        roleIdList.push(roleId);
                    }
                })

                var o = {
                    url: "/users/" + userId + "/roles",
                    type: "PUT",
                    contentType: "application/json",
                    Authorization: true,
                    data: {
                        roleIdList: roleIdList
                    },
                    successCallback: function (res) {
                        $('#manageModal').modal('hide');
                        alert(res.msg);
                    }
                }
                system.ajax(o);

            });
        });

        $('#addUser_btn').on('click', function () {
            $('#manageModal .modal-content').html('');

            var modalHeader = $('<div></div>').addClass('modal-header')
                .append($('<h5></h5>').addClass('modal-title').text('新增使用者'));

            // 創建 userName 的 div
            var modalBody = $('<div></div>').addClass('modal-body')
                .append(
                    $('<form></form>').attr('id', 'manageModalForm')
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'userName').text('使用者名稱 :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'userName',
                                    name: 'userName',
                                }))
                        )
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'userAccount').text('使用者帳號 :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'userAccount',
                                    name: 'userAccount',
                                })))
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'userPassword').text('使用者密碼 :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'userPassword',
                                    name: 'userPassword',
                                })))
                );

            var modalFooter = $('<div></div>').addClass('modal-footer')
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-secondary',
                    "data-dismiss": 'modal'
                }).text('取消'))
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-primary',
                    id: 'addUserConfirm'
                }).text('新增'))

            $('#manageModal .modal-content').append(modalHeader);
            $('#manageModal .modal-content').append(modalBody);
            $('#manageModal .modal-content').append(modalFooter);
            $('#manageModal').modal('show');

            $('#addUserConfirm').on('click', function () {

                if ($('#userName').val() === '' || $('#userAccount').val() === '' || $('#userPassword').val() === '') {
                    alert("請填寫完整資訊!");
                    return;
                }

                var o = {
                    url: "/users/register",
                    type: "POST",
                    contentType: "application/json",
                    Authorization: true,
                    data: {
                        userName: $('#userName').val(),
                        account: $('#userAccount').val(),
                        password: $('#userPassword').val()
                    },
                    successCallback: function (res) {
                        alert(res.msg);

                        if (res.msg === "Success!") {
                            $('#manageModal').modal('hide');
                            main.showUserManage();
                        }
                    }
                }
                if (confirm("確定要新增嗎?")) {
                    system.ajax(o);
                }
            });
        })
    },
    getFuncList: function () {
        var o = {
            url: "/funcs",
            type: "GET",
            Authorization: true,
            async: false,
            successCallback: function (res) {
                var table = $('<table></table>').addClass('table table-bordered')
                    .append(
                        $('<thead></thead>')
                            .append(
                                $('<tr></tr>')
                                    .append($('<th>功能</th>'))
                                    .append($('<th>讀取權限</th>'))
                                    .append($('<th>執行權限</th>'))
                            )
                            .css({
                                "background-color": "#f2f2f2",
                                position: "sticky",
                                top: "0", /* 將 thead 固定在上方 */
                                "z-index": "1" /* 保證 thead 位於最前面 */
                            })
                    );
                var tbody = $('<tbody></tbody>');
                $.each(res.result, function (index, func) {
                    var tr = $('<tr></tr>').append($('<td></td>').text(func.subjectName + " - " + func.funcName));

                    var td = $('<td></td>');
                    if (func.subjectName !== "系統管理") {
                        td.append($('<input>').attr({
                            type: "checkbox",
                            // name: func.funcId,
                            "data-funcId": func.funcId,
                            "data-level": 'r'
                        }))
                    }

                    tr.append(td);
                    tr.append($('<td></td>')
                        .append($('<input>').attr({
                            type: "checkbox",
                            // name: func.funcId,
                            "data-funcId": func.funcId,
                            "data-level": 'x'
                        })));
                    tbody.append(tr);
                })
                table.append(tbody);
                $('#roleFuncs').append(table);

                // 增加執行權限的同時，也增加讀取權限
                $('input[data-level="x"]').on('change', function () {
                    if ($(this).is(':checked')) {
                        funcId = $(this).data('funcid');
                        $(this).closest('tr').find(`input[data-funcid="${funcId}"][data-level="r"]`).prop('checked', true);
                    }
                })
                // 取消讀取權限的同時，也取消執行權限
                $('input[data-level="r"]').on('change', function () {
                    if (!$(this).is(':checked')) {
                        funcId = $(this).data('funcid');
                        $(this).closest('tr').find(`input[data-funcid="${funcId}"][data-level="x"]`).prop('checked', false);
                    }
                })
            }
        }
        system.ajax(o);
    },
    showRoleManage: function () {
        $('#mainTabs').hide();
        $('#tab-content').show();
        $('#tab-content').html($(''));
        $('#tab-content').html('<div style="margin-top:10px;"></div><button id="addRole_btn" class="btn btn-primary">新增角色</button></div>');
        $('#tab-content').append(`<table id="roleTable" class="table table-striped table-bordered" style="width:100%"></table>`);

        let obj = {
            aoColumnDefs: [
                {
                    "targets": "_all", // 所有列
                    "createdCell": function (td, cellData, rowData, row, col) {
                        $(td).removeClass('dt-type-numeric'); // 移除 dt-type-numeric
                        $(td).addClass('text-center'); // 讓 td 置中
                    }
                },
                {
                    "targets": "_all",
                    "className": "text-center", // 將所有表頭置中
                    "headerCell": function (th) {
                        $(td).removeClass('dt-type-numeric'); // 移除 dt-type-numeric
                    }
                }
            ],
            aoColumns: [
                { "data": "roleId", "title": "角色ID" },
                { "data": "roleName", "title": "角色名稱" },
                { "data": "createTime", "title": "建立時間" },
                {
                    "data": null,
                    "title": "操作",
                    "sortable": false,
                    "render": function (data, type, row) {
                        return '<button type="button" class="btn btn-primary roleEdit_btn" data-toggle="modal" data-target="#roleModal">編輯</button>'
                            + ' <button type="button" class="btn btn-danger roleDelete_btn" data-id="' + row.roleId + '">刪除</button>';
                    },
                }
            ],
            aaSorting: [[0, 'asc']],
            pageLength: 10,
            bLengthChange: true,
            searching: true,
            paging: true
        }
        let o = {
            url: "/roles",
            type: "GET",
            Authorization: true,
            async: false,
            successCallback: function (res) {
                dataTable.create("roleTable", obj, res.result);
            }
        }
        system.ajax(o);

        $('.roleEdit_btn').on('click', function () {
            $('#manageModal .modal-content').html('');

            var modalHeader = $('<div></div>').addClass('modal-header')
                .append($('<h5></h5>').addClass('modal-title').text('編輯角色'));

            var modalBody = $('<div></div>').addClass('modal-body')
                .append(
                    $('<form></form>').attr('id', 'modalForm')
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'roleId').text('角色ID :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'roleId',
                                    name: 'roleId',
                                    readonly: true
                                }))
                        )
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'roleName').text('角色名稱 :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'roleName',
                                    name: 'roleName',
                                    readonly: true
                                })))
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'roleFuncs').text('角色權限 :'))
                                .append($('<div></div>').attr('id', 'roleFuncs')
                                    .css({
                                        "overflow-y": "auto",
                                        "max-height": "35vh"
                                    }))
                        )
                );

            var modalFooter = $('<div></div>').addClass('modal-footer')
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-secondary',
                    "data-dismiss": 'modal'
                }).text('取消'))
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-primary',
                    id: 'saveRoleUpdate'
                }).text('保存變更'))

            $('#manageModal .modal-content').append(modalHeader);
            $('#manageModal .modal-content').append(modalBody);
            $('#manageModal .modal-content').append(modalFooter);

            // 假設你可以從表格中獲取角色的詳細資訊
            var roleId = $(this).closest('tr').find('td:eq(0)').text();
            var roleName = $(this).closest('tr').find('td:eq(1)').text();
            // 填充到彈出視窗的表單中
            $('#roleId').val(roleId);
            $('#roleName').val(roleName);
            main.getFuncList();

            var o = {
                url: "/roles/" + roleId + "/funcs",
                type: "GET",
                Authorization: true,
                successCallback: function (res) {
                    if (res.result !== null) {
                        $.each(res.result.funcList, function (index, func) {
                            $('#roleFuncs tr').each(function () {

                                if (func.level === "x") {
                                    let checkBoxs = $(this).find(`input[type="checkbox"][data-funcid=${func.funcId}]`);

                                    if (checkBoxs.length > 0) {
                                        checkBoxs.each(function () {
                                            $(this).prop('checked', true);
                                        })
                                        return false;
                                    }
                                } else if (func.level === "r") {
                                    let checkbox = $(this).find(`input[type="checkbox"][data-funcid=${func.funcId}][data-level=${func.level}]`);

                                    // .find() 方法會返回一個 jQuery 物件，即使找不到對應元素也不會返回 null，而是返回一個空的 jQuery 物件
                                    if (checkbox.length > 0) {
                                        checkbox.prop('checked', true);
                                        return false; // 在 jQuery 的 $.each() return false 可以跳出迴圈
                                    }
                                }
                            });
                        })
                    }
                    $('#manageModal').modal('show');
                }
            }
            system.ajax(o);

            $('#saveRoleUpdate').on('click', function () {
                var funcList = {};
                $('#roleFuncs tr').each(function () {
                    // 查找該行中 checked 的 <input>，並檢查是否有選中
                    let checkedInput = $(this).find('input[type="checkbox"]:checked');

                    if (checkedInput.length > 1) {
                        let funcId = checkedInput.data('funcid');
                        funcList[funcId] = "x";
                    } else if (checkedInput.length > 0) {
                        let funcId = checkedInput.data('funcid');
                        funcList[funcId] = "r";
                    }
                });

                var o = {
                    url: "/roles/" + $('#roleId').val() + "/funcs",
                    type: "PUT",
                    contentType: "application/json",
                    Authorization: true,
                    data: {
                        funcList: funcList
                    },
                    successCallback: function (res) {
                        console.log(res);

                        if (res.msg === '修改成功!') {
                            $('#manageModal').modal('hide');
                            alert(res.msg);
                        }
                    }
                }
                system.ajax(o);
            })
        })

        $('.roleDelete_btn').on('click', function () {
            var roleId = $(this).data('id');
            var o = {
                url: "/roles/" + roleId,
                type: "DELETE",
                Authorization: true,
                successCallback: function (res) {
                    alert(res.msg);

                    if (res.msg === "刪除成功!") {
                        main.showRoleManage();
                    }
                }
            }

            if (confirm("確定要刪除此角色嗎?")) {
                system.ajax(o);
            }
        })

        $('#addRole_btn').on('click', function () {
            $('#manageModal .modal-content').html('');

            var modalHeader = $('<div></div>').addClass('modal-header')
                .append($('<h5></h5>').addClass('modal-title').text('新增角色'));

            // 創建 userName 的 div
            var modalBody = $('<div></div>').addClass('modal-body')
                .append(
                    $('<form></form>').attr('id', 'modalForm')
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'roleName').text('角色名稱 :'))
                                .append($('<input>').attr({
                                    type: 'text',
                                    class: 'form-control',
                                    id: 'roleName',
                                    name: 'roleName'
                                })))
                        .append(
                            $('<div></div>').addClass('form-group')
                                .append($('<label></label>').attr('for', 'roleFuncs').text('角色權限 :'))
                                .append($('<div></div>').attr('id', 'roleFuncs')
                                    .css({
                                        "overflow-y": "auto",
                                        "max-height": "35vh"
                                    }))
                        )
                );

            var modalFooter = $('<div></div>').addClass('modal-footer')
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-secondary',
                    "data-dismiss": 'modal'
                }).text('取消'))
                .append($('<button></button>').attr({
                    type: 'button',
                    class: 'btn btn-primary',
                    id: 'addRoleConfirm'
                }).text('新增'))

            $('#manageModal .modal-content').append(modalHeader);
            $('#manageModal .modal-content').append(modalBody);
            $('#manageModal .modal-content').append(modalFooter);

            main.getFuncList();
            $('#manageModal').modal('show');

            $('#addRoleConfirm').on('click', function () {

                if ($('#roleName').val() === '') {
                    alert("請填寫角色名稱!");
                    return;
                }

                var funcList = {};
                $('#roleFuncs tr').each(function () {
                    // 查找該行中 checked 的 <input>，並檢查是否有選中
                    let checkedInput = $(this).find('input[type="checkbox"]:checked');

                    if (checkedInput.length > 1) {
                        let funcId = checkedInput.data('funcid');
                        funcList[funcId] = "x";
                    } else if (checkedInput.length > 0) {
                        let funcId = checkedInput.data('funcid');
                        funcList[funcId] = "r";
                    }
                });

                var o = {
                    url: "/roles",
                    type: "POST",
                    contentType: "application/json",
                    Authorization: true,
                    data: {
                        roleName: $('#roleName').val(),
                        roleFunc: {
                            funcList: funcList
                        }
                    },
                    successCallback: function (res) {
                        alert(res.msg);

                        if (res.msg === "新增成功!") {
                            $('#manageModal').modal('hide');
                            main.showRoleManage();
                        }
                    }
                }
                if (confirm("確定要新增嗎?")) {
                    system.ajax(o);
                }
            });
        })
    }
}
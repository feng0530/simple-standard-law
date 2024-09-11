system = {
    ajax : function(o) {

        $.ajax({
            url: o.url,
            type: o.type,
            contentType: o.contentType,
            data: JSON.stringify(o.data),
            async: o.async != undefined ? o.async : true,
            headers: o.Authorization != undefined ? {'Authorization': o.Authorization } : {},
            success: function(response) {
                // 如果jwt過期，直接返回登入頁
                if (response.msg === "請重新登入!") {
                    alert(response.msg);
                    localStorage.removeItem('jwt');
                    window.location.href = "login.html";
                }
                o.successCallback(response);
            }
            /* $.ajax 對於 HTTP狀態碼不為 2xx時，會自動呼叫 errorCallback */
            /* 因後端設計僅回傳 200的狀態碼，其餘資訊由 Response Body判斷，故在此註解 */

            // ,
            // error: function(xhr, textStatus, errorThrown) {
            //     o.errorCallback(xhr, textStatus, errorThrown);
            // }
        });
    }
}
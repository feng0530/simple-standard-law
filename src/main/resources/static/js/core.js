system = {
    ajax : function(o) {

        let jwt;
        if(o.Authorization === true) {

            jwt = localStorage.getItem('jwt');
            if (jwt === null && o.url !== domain + "/users/logout") {
                alert("請重新登入!");
                window.location.href = "login.html";
            }
        }

        $.ajax({
            url: domain + o.url,
            type: o.type,
            contentType: o.contentType,
            data: JSON.stringify(o.data),
            async: o.async !== undefined ? o.async : true,
            headers: o.Authorization === true ? {'Authorization': Authorization_Prefix + jwt} : {},
            success: function(response) {
                // 如果jwt過期，直接返回登入頁
                if (response.msg === "請重新登入!") {
                    alert(response.msg);
                    localStorage.removeItem('jwt');
                    window.location.href = "login.html";
                } else {
                    o.successCallback(response);
                }
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
user = {
    funcs: {
        list: function (userId) {
            var o = {
                url: "/users/" + userId + "/funcs",
                type: "GET",
                Authorization: true,
                successCallback: function(res) {
                    if (res.msg === "Success!") {
                        user.funcs.func(res);
                    }else {
                        alert(res.msg);
                    }
                },
                async: false
            }
            system.ajax(o);
        },
        func: function(res) {
            const subjectOptionMapping = {
                "利率風險": {
                    code: "IR",
                    options: {
                        "IR01": "利率風險-市場風險處計提資本彙總表",
                        "IR02": "利率[個別風險]之資本計提計算表",
                        "IR03": "一般市場風險之資本計提計算表(到期法)",
                        "IR04": "一般市場風險之資本計提計算表(存續期間法)"
                    }
                },
                "權益證卷風險": {
                    code: "MS",
                    options: {
                        "MS01": "權益證券風險—市場風險處計提資本彙總表",
                        "MS02": "[個別風險]之資本計提計算表（國家別）",
                        "MS03": "[一般市場風險]之資本計提計算表（國家別）"
                    }
                },
                "外匯(含黃金)風險": {
                    code: "FE",
                    options: {
                        "FE01": "外匯(含黃金)風險—市場風險處計提資本彙總表",
                        "FE02": "各幣別淨部位彙總表（by CCY）",
                        "FE03": "各幣別淨部位彙總表"
                    }
                },
                "商品風險": {
                    code: "PR",
                    options: {
                        "PR01": "商品風險—市場風險處計提資本彙總表",
                        "PR02": "市場風險處計提資本計算表（簡易法）",
                        "PR03": "市場風險處計提資本計算表（期限別法）"
                    }
                },
                "選擇權風險": {
                    code: "SA",
                    options: {
                        "SA01": "選擇權採用簡易法計提資本計算表",
                        "SA02": "選擇權採敏感性分析法加計 Gamma 及 Vega 風險之資本計算表"
                    }
                },
                "市場風險": {
                    code: "MR",
                    options: {
                        "MR01": "市場風險資本加除項目彙總表"
                    }
                },
                "信用風險": {
                    code: "CR",
                    options: {
                        "CR01": "信用風險加權風險性資產、作業風險暨市場風險資本計提彙總表"
                    }
                },
                "系統管理": {
                    code: "SET",
                    options: {
                        "SET01": "使用者管理",
                        "SET02": "角色管理"
                    }
                }
            }

            let isManager = function(data) {
                return data.some(item => item.authorities.some(authority => authority.startsWith("root")));
            }

            if (isManager(res.result)){

                Object.keys(subjectOptionMapping).forEach(key => {
                    const subjectCode = subjectOptionMapping[key].code; // 取得對應的代碼
                    const heading = "heading" + subjectCode;
                    const collapse = "collapse" + subjectCode;
                    const navbar = "navbar" + subjectCode;

                    // 創建 <div class="card bg-dark">
                    const cardDiv = $('<div></div>', {
                        class: "card bg-dark"
                    });
        
                    // 創建 <div class="card-header" id="heading">
                    const cardHeaderDiv = $('<div></div>', {
                        class: "card-header",
                        id: heading
                    });
        
                    // 創建 <h2 class="mb-0"> 和 <button>
                    const h2 = $('<h2></h2>', { class: "mb-0" });
                    const button = $('<button></button>', {
                        class: "btn btn-link text-white collapsed",
                        type: "button",
                        "data-toggle": "collapse",
                        "data-target": "#" + collapse,
                        "aria-expanded": "false",
                        "aria-controls": collapse,
                        text: key
                    });
        
                    // 將 button 加入 h2，將 h2 加入 card-header
                    h2.append(button);
                    cardHeaderDiv.append(h2);
                    cardDiv.append(cardHeaderDiv);
        
                    // 創建 <div id="collapse" class="collapse" aria-labelledby="heading" data-parent="#sidebarAccordion">
                    const collapseDiv = $('<div></div>', {
                        id: collapse,
                        class: "collapse",
                        "aria-labelledby": heading,
                        "data-parent": "#sidebarAccordion"
                    });
        
                    // 創建 <div class="card-body bg-secondary">
                    const cardBodyDiv = $('<div></div>', { class: "card-body bg-secondary" });
        
                    // 創建 <ul class="nav flex-column" id="navbar">
                    const ul = $('<ul></ul>', {
                        class: "nav flex-column",
                        id: navbar
                    });
        
                    // 動態生成 authorities 的 <li> 和 <a>
                    Object.keys(subjectOptionMapping[key].options).forEach(value => {
                        let option = subjectOptionMapping[key].options[value];
                        const li = $('<li></li>', { class: "nav-item" });
                        const a = $('<a></a>', {
                            class: "nav-link",
                            href: "#",
                            "data-optioncode": value,
                            "data-level": "x",
                            text: option
                        });
                        li.append(a);
                        ul.append(li);
                    });
        
                    // 組合各部分
                    cardBodyDiv.append(ul);
                    collapseDiv.append(cardBodyDiv);
                    cardDiv.append(collapseDiv);

                    // 將生成的卡片插入到頁面上
                    $('#sidebarAccordion').append(cardDiv);
                })
            }else {
                res.result.forEach(item => {
                    const subjectCode = subjectOptionMapping[item.subjectName].code; // 取得對應的代碼
                    const heading = "heading" + subjectCode;
                    const collapse = "collapse" + subjectCode;
                    const navbar = "navbar" + subjectCode;

                    // 創建 <div class="card bg-dark">
                    const cardDiv = $('<div></div>', {
                        class: "card bg-dark"
                    });
        
                    // 創建 <div class="card-header" id="heading">
                    const cardHeaderDiv = $('<div></div>', {
                        class: "card-header",
                        id: heading
                    });
        
                    // 創建 <h2 class="mb-0"> 和 <button>
                    const h2 = $('<h2></h2>', { class: "mb-0" });
                    const button = $('<button></button>', {
                        class: "btn btn-link text-white collapsed",
                        type: "button",
                        "data-toggle": "collapse",
                        "data-target": "#" + collapse,
                        "aria-expanded": "false",
                        "aria-controls": collapse,
                        text: item.subjectName
                    });
        
                    // 將 button 加入 h2，將 h2 加入 card-header
                    h2.append(button);
                    cardHeaderDiv.append(h2);
                    cardDiv.append(cardHeaderDiv);
        
                    // 創建 <div id="collapse" class="collapse" aria-labelledby="heading" data-parent="#sidebarAccordion">
                    const collapseDiv = $('<div></div>', {
                        id: collapse,
                        class: "collapse",
                        "aria-labelledby": heading,
                        "data-parent": "#sidebarAccordion"
                    });
        
                    // 創建 <div class="card-body bg-secondary">
                    const cardBodyDiv = $('<div></div>', { class: "card-body bg-secondary" });
        
                    // 創建 <ul class="nav flex-column" id="navbar">
                    const ul = $('<ul></ul>', {
                        class: "nav flex-column",
                        id: navbar
                    });

                    // 動態生成 authorities 的 <li> 和 <a>
                    item.authorities.forEach(authority => {
                        let optionCode = authority.split("_")[0];
                        let level = authority.split("_")[1];
                        let optionName = subjectOptionMapping[item.subjectName].options[optionCode];
                        const li = $('<li></li>', { class: "nav-item" });
                        const a = $('<a></a>', {
                            class: "nav-link",
                            href: "#",
                            "data-optioncode": optionCode,
                            "data-level": level,
                            text: optionName
                        });
                        li.append(a);
                        ul.append(li);
                    });
                    
                    // 組合各部分
                    cardBodyDiv.append(ul);
                    collapseDiv.append(cardBodyDiv);
                    cardDiv.append(collapseDiv);

                    // 將生成的卡片插入到頁面上
                    $('#sidebarAccordion').append(cardDiv);
                })
            }
        }
    }
}

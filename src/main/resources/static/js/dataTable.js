
/**
 * 基本 Data table 組成
 */
dataTable =  {
	create : function (id, o, data) {
		if ( id == null ){
			return;
		}
		
		if (!jQuery().dataTable) {
	        return;
	    }
		$('#'+id).dataTable ({
			"bProcessing": false,
			"bDestroy": true,
	        "oLanguage": {
	        	"sProcessing": "處理中...",
	            "sLengthMenu": "_MENU_ 紀錄",
	            "sZeroRecords": "沒有匹配結果",
	            "sInfo": "顯示第 <b>_START_ 至 _END_ 筆，</b> 共 _TOTAL_ 筆",
	            "sInfoEmpty": "顯示第 0 至 0 項結果，共 0 項",
	            "sInfoFiltered": "(從 _MAX_ 項結果過濾)",
	            "sInfoPostFix": "",
	            "sSearch" :"搜尋 :",
	            "sUrl": "",
	            "oPaginate": {
	                "sFirst": "首頁",
	                "sPrevious": "&laquo;",
	                "sNext": "&raquo;",
	                "sLast": "尾頁"
	            }
	        },
	        //欄位定義
	        "aoColumnDefs" : o.aoColumnDefs != undefined ? o.aoColumnDefs : undefined,
	        //欄位
	        "aoColumns" : o.aoColumns != undefined ? o.aoColumns : undefined,
	        "oTableTools" : o.oTableTools != undefined ? o.oTableTools : { "aButtons": [] },
	        "aaSorting" : o.aaSorting != undefined ? o.aaSorting : [[ 0, "asc" ]],
	        "paging":   o.paging != undefined ? o.paging : true, // 2021.04.19 Mark 增加是否要開啟分頁選項的欄位
	        "ordering": o.ordering != undefined ? o.ordering : true, //排序按鈕
	        "info":     true, // 是否要顯示"目前有 x  筆資料"
	        "pageLength" : o.pageLength != undefined ? o.pageLength : 15,  //分頁資料筆數
	        "searching": o.searching != undefined ? o.searching : true, //查詢欄位
	        "bLengthChange": o.bLengthChange != undefined ? o.bLengthChange : true, //查詢筆數選擇
	        "iDisplayLength": o.iDisplayLength != undefined ? o.iDisplayLength : 10, //預設查詢筆數
	        "lengthMenu": o.lengthMenu != undefined ? o.lengthMenu : [ [15, 25, 50, 100], [15, 25, 50, 100] ],
	        //"lengthMenu": o.lengthMenu != undefined ? o.lengthMenu : [[10, 25, 50, 100, -1], [10, 25, 50, 100, $.i18n.prop('All')]],
	        "createdRow": o.createdRow != undefined ? o.createdRow : undefined,
	        "autoWidth": o.autoWidth != undefined ? o.autoWidth : true,
	        "bServerSide": o.bServerSide != undefined ? o.bServerSide : false,//是否要透過服務取得資料
            "sAjaxSource": o.sAjaxSource != undefined ? o.sAjaxSource : undefined, //url
            "fnServerData": o.fnServerData != undefined ? o.fnServerData : undefined, //
			"ajax": o.ajax != undefined ? o.ajax : undefined,
			"data": data != undefined ? data : undefined
		});
	}
};

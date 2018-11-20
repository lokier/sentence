/**
 * jfinal-admin-common.js 添加一些公共的函数
 * 例如弹出提示框
 */

/**
 * 存放当前页面环境变量
 */
var env = {
	ueditor : null
	, deleteTarget : null
};

/**
 * 弹出框显示操作成功
 */
function showOkMsg(msg, callback) {
	layer.msg(
		msg
		,{	shift: 0
			, shade: 0.4
			, time: 0
			, offset: "140px"
			, closeBtn: 1
			, shadeClose: true
			, maxWidth: "1000"
		}
		, callback
	);
}


/**
 * 弹出框显示操作失败
 */
function showFailMsg(msg, callback) {
	layer.msg(
			msg
			,{	shift: 6
				, shade: 0.4
				, time: 0
				, offset: "140px"
				, closeBtn: 1
				, shadeClose: true
				, maxWidth: "1000"
			}
			, callback
	);
}

/**
 * 确认对话框层，点击确定才真正操作
 * @param msg 对话框的提示文字
 * @param operationUrl 点击确认后请求到的目标 url
 */
function confirmAjaxAction(msg, operationUrl) {
	layer.confirm(msg, {
		icon: 0
		, title:''                                    // 设置为空串时，title消失，并自动切换关闭按钮样式，比较好的体验
		, shade: 0.4
		, offset: "139px"
	}, function(index) {                            // 只有点确定后才会回调该方法
		// location.href = operationUrl;     // 操作是一个 GET 链接请求，并非 ajax
		// 替换上面的 location.href 操作，改造成 ajax 请求。后端用 renderJson 更方便，不需要知道 redirect 到哪里
		ajaxAction(operationUrl);
		layer.close(index);                           // 需要调用 layer.close(index) 才能关闭对话框
	});
}

/**
 * ajax 做通用的操作，不传递表单数据，仅传id值的那种
 */
function ajaxAction(url) {
	$.ajax(url, {
		type: "GET"
		, cache: false
		, dataType: "json"
		// , data: {	}
		, beforeSend: function() {}
		, error: function(ret) {alert(ret.statusText);}
		, success: function(ret) {
			if (ret.state == "ok") {
				showAjaxActionMsg(0, ret.msg);
			} else {
				showAjaxActionMsg(6, ret.msg);
			}
		}
	});
}

function showAjaxActionMsg(shift, msg) {
	layer.msg(msg, {
			shift: shift
			, shade: 0.4
			, time: 0
			, offset: "140px"
			, closeBtn: 1
			, shadeClose: true
			,maxWidth: "1000"
		}, function () {
			if (shift != 6) {
				if (env.deleteTarget) {
					env.deleteTarget.remove();
					env.deleteTarget = null;
				} else {
					// location.reload();
					$.pjax.reload('#pjax-container', {});
				}
			}
		}
	);
}

/**
 * 初始化 ueditor
 */
function initUeditor(){
	// 加载 ueditor，传入的初始化参数可以覆盖掉 ueditor.config.js 中的配置
	// 对于同一项目中使用不同的配置的 ueditor 场景非常有用
	env.ueditor = UE.getEditor('container', {
		initialFrameHeight:300
		// , initialFrameWidth:796
		,wordCount:false
	});

	// 由于ctrl+回车提交表单会让ajax form失效，所以让该快捷键失效
	// $(document).ready(function() {ue.shortcutkeys["autosubmit"] = "";});
	// 另一种让该表单提交快捷键失效的方法，注册同名plugin覆盖掉旧的，这种方式更加简单可靠
	UE.plugin.register('autosubmit',function(){});

	UE.plugin.register('jfajaxsubmit',function(){
		return {
			shortcutkey:{
				"jfajaxsubmit":"ctrl+13" // ctrl + 回车 提交表单
			},
			commands:{
				'jfajaxsubmit':{
					execCommand:function () {
						var me=this;
						if(me.fireEvent("beforesubmit")===false){
							return;
						}
						me.sync();
						$("#myArticleForm").submit();
					}
				}
			}
		}
	});
}
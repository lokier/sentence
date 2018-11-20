
/**
 * 注意：本文件仅用于备份不断升级过程中有价值的但被删除的代码
 */


/**
 * 初始化 Switchery 组件
 * @param prepareAction 用于准备发送请求时的 action，该回调函数例子如下：

 // $this 代表触发 onChange 事件的 checkbox 的 jquery 对象，
 // state 为 true 时表示 chechbox 选中，否则未选中
 // 返回值 url 用于发送 ajax 请求，data 即为 ajax 请求时的数据
 function prepareAction($this, state) {
				return {
					url: state ? "/admin/role/addPermission" : "/admin/role/deletePermission",

					data : {
						// data() 方法居然只支持小写属性，roleId 会自动转成 roleid，经测试 attr() 方法是支持大写的
						roleId: $this.data("role-id"),
						permissionId: $this.data("permission-id")
					}
				}
			}

 * @param switcheryOptions 用于 Switchery 组件创建时的参数，省略时默认为：{size: "small"}
 */
function initSwitchery(prepareAction, switcheryOptions) {
	switcheryOptions = switcheryOptions || {size: "small"};

	$("span.switchery").remove();
	var sw = $(".js-switch");
	sw.each(function(index, elem) {
		new Switchery(elem, switcheryOptions);
	});

	// 锁定开关绑定事件
	sw.on("change", function(event) {
		var $this = $(event.target);	// 或者 $(this)
		var state = $this.get(0).checked ? true : false;
		var action = prepareAction($this, state);

		$.ajax(action.url, {
			type: "POST"
			, cache: false
			, dataType: "json"
			, data: action.data
			, error: function(ret) {
				alert(ret.statusText);
				resetSwitchery($this, switcheryOptions);
			}
			, success: function(ret) {
				if (ret.state == "fail") {
					showFailMsg(ret.msg);
					resetSwitchery($this, switcheryOptions);
				}
			}
		});
	});
}

/**
 * Switchery 组件触发 ajax 请求失败后复位到上一个状态
 */
function resetSwitchery($checkbox, options) {
	var checkbox = $checkbox.get(0);
	checkbox.checked = !checkbox.checked;

	$checkbox.next("span.switchery").remove();

	options = options || {size: "small"};
	new Switchery($checkbox.get(0), options);
}


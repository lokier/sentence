/**
 * 初始化
 *
 * pajx 官方文档：https://github.com/defunkt/jquery-pjax
 * $.pjax.reload('#pjax-container', options)
 *
 * 重要：
 * $(parent-element).on(...) 可以绑定在交互过程中动态添加的元素上
 * $(...).bind 只能绑定在确定的元素上
 * 因此，右侧通过 pjax 动态加载的内容区域必须使用前者绑定
 */
$(document).ready(function() {
	// 绑定首页菜单事件
	$(".jfa-main-menu[home='true']").bind("click", clickSubMenu);

	// 绑定主菜单事件
	$(".jfa-main-menu[home='false']").bind("click", clickMainMenu);

	// 绑定子菜单事件，由于点击菜单需要额外动作，所以用 js 触发 pjax
	$("ul.jfa-sub-menu a").bind("click", clickSubMenu);

	// pjax timeout 配置
	$.pjax.defaults.timeout = 5000;

	// data-pjax 属性与 a 标签组合选择器绑定 pjax，例如分页链接、操作按钮
	$(document).pjax('[data-pjax] a, a[data-pjax]', '#pjax-container');

	// table 操作栏按钮绑定 click 事件，动态添加的元素必须使用 $(...).on(...) 才能绑定
	$('#pjax-container').on("click", "a[data-delete]", deleteArticle);

	// 进度条效果
	// NProgress.configure({ showSpinner: false });
	$(document).on('pjax:start', function() { NProgress.start(); });
	$(document).on('pjax:end',   function() { NProgress.done();  });

	// 设置当前选中菜单
	setCurrentAdminMenu();
});

/**
 * 点击主菜单动画效果
 */
function clickMainMenu(event) {
	event.preventDefault();	// 取代 return false 防止页面跳转
	var $this = $(this);
	var subMenu = $this.next(".jfa-sub-menu");
	$(subMenu).slideToggle("fast");

	var i = $this.children(".right-icon");
	if (i.hasClass("fa-angle-down")) {
		i.removeClass("fa-angle-down");
		i.addClass("fa-angle-up");
	} else {
		i.removeClass("fa-angle-up");
		i.addClass("fa-angle-down");
	}
}

/**
 * 点击子菜单
 */
function clickSubMenu(event) {
	event.preventDefault();	// 取代 return false 防止页面跳转
	var $this = $(this);
	var url = $this.attr("href");
	sendPjax(url, "#pjax-container");

	// 设置当前选中菜单样式
	$(".jfa-sub-menu a").removeClass("jfa-cur-menu");
	$(".jfa-main-menu[home='true']").removeClass("jfa-cur-menu");
	$this.addClass("jfa-cur-menu");
}

/**
 * 发送 pjax
 * url：后台 action
 * container：承载响应内容的容器
 * extData：扩展数据，可以在 pajx 事件回调方法中通过 options.extData 获取
 * 			例如：$(document).on('pjax:success', function(event, data, status, xhr, options)
 */
function sendPjax(url, container, extData) {
	$.pjax({
		url: url
		, container: container
		, extData: extData
	});
}

/**
 * 设置当前选中菜单
 */
function setCurrentAdminMenu() {
	var pathName = location.pathname;
	if (pathName == '/admin') {
		$(".jfa-main-menu[home='true']").addClass("jfa-cur-menu");
		return false;
	}

	$(".jfa-sub-menu a[href]").each(function(index, element) {
		var href = $(element).attr("href");
		if (pathName.indexOf(href) >= 0) {
			var currentMenu = $(".jfa-sub-menu a[href='" + href + "']");
			currentMenu.addClass("jfa-cur-menu");
			return false;
		}
	});
}

/**
 * 删除文章
 */
function deleteArticle(event) {
	event.preventDefault();
	var $this = $(this);
	env.deleteTarget = $this.closest("tr");	// 传递给 showAjaxActionMsg() 删除一行数据

	var action = $this.attr("data-action");
	var title = $this.attr("data-title");
	confirmAjaxAction("确定删除《" + title + "》？ 删除后无法恢复！", action);
}


/**
 * 初始化 magic input 组件
 * @param prepareAction 用于准备发送请求时的 action，该回调函数例子如下：

 // $this 代表触发 click 事件的 checkbox 的 jquery 对象，
 // state 为 true 时表示 checkbox 选中，否则未选中
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

 * 加个选择器参数为好，因为同一个 html 中可能要用到多种不同的 magic input 组件
 * 不同的组件需要实现不同的功能，让用户绑定事件的时候，不同的组件绑定不同的 prepareAction 方法
 * 这样通用性更加强了
 *
 * var magicInput = jquery选择器 ? $(jquery选择器) : $("input.mgc-switch,input.mgc");
 * 考虑新加的参数放在第一的位置，看看 jquery 中有关参数类型的判断，因为 jquery 中大量这种判断
 * 用来实现多态
 *
 */
function initMagicInput(prepareAction) {
	var magicInput = $("input.mgc-switch,input.mgc");

	// 锁定开关绑定事件
	magicInput.on("click", function(event) {
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
				resetMagicInput($this);
			}
			, success: function(ret) {
				if (ret.state == "fail") {
					showFailMsg(ret.msg);
					resetMagicInput($this);
				}
			}
		});
	});
}

/**
 * magic input 组件触发 ajax 请求失败后复位到上一个状态
 */
function resetMagicInput($checkbox) {
	var checkbox = $checkbox.get(0);
	checkbox.checked = !checkbox.checked;
}

/**
 * 显示 share/feedback 的 reply 内容
 */
function showReplyContent() {
	var url = $(this).attr("data-url");
	$.ajax(url, {
		type: "GET"
		, cache: false
		, dataType: "json"
		, success: function(ret) {
			layer.msg(
				ret.reply.content
				,{	shift: 0
					, shade: 0.4
					, time: 0
					, offset: "140px"
					, closeBtn: 1
					, shadeClose: true
					, maxWidth: "650"
				}
			);
		}
	});
}

/**
 * 删除 share/feedback 的 reply 记录
 */
function deleteReply() {
	var url = $(this).attr("data-url");
	confirmAjaxAction("确定删除?", url);
}
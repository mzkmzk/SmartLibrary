	//成功提示
function messceshi1(mes){
		Messenger().post(mes);
	}
//操作失败提示
function messceshi2(mes){
	Messenger().post({
		message: mes,
		type: 'error',
		showCloseButton: true
	});
}

function messceshi3(){
	var msg;
	msg = Messenger().post("My Message");
	msg.update("I changed my mind, this is my message"); 
	msg.hide();	
}
function messceshi4(){
	var msg;
	msg = Messenger().post({
	  message: 'Launching thermonuclear war...',
	  type: 'info',
	  actions: {
	    cancel: {
	      label: 'cancel launch',
	      action: function() {
	        return msg.update({
	          message: 'Thermonuclear war averted',
	          type: 'success',
	          actions: false
	        });
	      }
	    }
	  }
	});
}
	
function messceshi5(){
	var i;
	
	i = 0;
	
	Messenger().run({
	  errorMessage: 'Error destroying alien planet',
	  successMessage: 'Alien planet destroyed!',
	  action: function(opts) {
	    if (++i < 4) {
	      return opts.error({
	        status: 500,
	        readyState: 0,
	        responseText: 0
	      });
	    } else {
	      return opts.success();
	    }
	  }
	});
}

function messceshi6(){
	var msg;
 
	msg = Messenger().post({
	type: 'error',
  	message: '学生信息导入错误!',
  	actions: {
    retry: {
      label: '下载',
      phrase: 'Retrying TIME',
      auto: false,
      action: function() {
      	window.location.href = '../Dept/downLoadErrorExcel';
      }
    },
    cancel: {
      label: '取消',
      action: function() {
        return msg.cancel();
      }
    }
  		}
	});	
}
function messceshi7(){
	msg.on('action:retry', function() {
	  return alert('Hey, you retried!');
	});		
	}
function messceshi8(){
 
	msg.update({
	  events: {
	    'success click': function() {},
	    'error click a.awesome-class': function() {}
	  }
	});	
}
function messceshi9(){
		Messenger().post({
	  	message: "Weeeeee",
	  	hideAfter: 10,
	  	hideOnNavigate: true
	});
}
function messceshi10(){
		Messenger().post({
	  message: "Only one at a time!",
	  id: "Only-one-message"
	});
}
function messceshi11(){
	Messenger().post({
	  message: "It's just me!",
	  id: '4',
	  singleton: true
	});
 
	Messenger().post({
	  message: "You'll never see me",
	  id: '4',
	  singleton: true
	});
}
function messceshi12(){
	var msg;
	 
	msg = Messenger().run();
	 
	Messenger().run({
	  messageInstance: msg
	});
}
function messceshi13(){
	var msg;
	 
	msg = Messenger().post({
	  message: "You'll see me!",
	  scrollTo: true
	});
}
function messceshi14(){
	msg.scrollTo();
}
function messceshi15(){
 
	Messenger().run({
	  successMessage: 'Data saved.',
	  errorMessage: 'Error saving data',
	  progressMessage: 'Saving data'
	}, {
	  url: '/data'
	});
}
function messceshi16(){
	Messenger().run({
	  errorMessage: 'Oops'
	}, {
	  url: '/data',
	  error: function(xhr) {
	    if ((xhr != null ? xhr.status : void 0) === 404) {
	      return "Data not found";
	    }
	    return true;
	  }
	});
}
function messceshi17(){
	Messenger().run({
	  successMessage: 'Successfully saved.',
	  errorMessage: 'Error saving',
	  showSuccessWithoutError: false
	}, {
	  url: '/data'
	});
}
function messceshi18(){
	Messenger().run({
	  successMessage: 'Bomb defused successfully',
	  action: defuseBomb
	});
}
function messceshi19(){
	Messenger().hideAll();
}
function messceshi20(){
	Messenger().run({}, {
	  url: 'a'
	}).fail(function() {
	  return alert("Uh oh");
	});
}
function messceshi21(){
	Messenger().hookBackboneAjax();
}
function messceshi22(){
	$('div#message-container').messenger().post("My message");
}
function messceshi23(){
	var myAwesomeMessenger;
 
	myAwesomeMessenger = $('.mess').messenger();
 
	Messenger({
  	instance: myAwesomeMessenger
	});
 
	Messenger();
}
function messceshi24(){
	Messenger({
  	'parentLocations': ['.page', 'body']
	});
}
function messceshi25(){
	({
	  'parentLocations': ['body'],
	  'maxMessages': 9,
	  'extraClasses': 'messenger-fixed messenger-on-right messenger-on-bottom messenger-theme-future',
	  'instance': void 0,
	  'messageDefaults': {}
	});
}
function messceshi26(){
	Messenger.options = {
	  'extraClasses': 'messenger-fixed messenger-on-left'
	};
}
	
function messageLoad(message) {
	
	if(message == 1) {
		messceshi1("借书成功！");
	}
	else if(message == 2){
		messceshi1("驳回成功！");
	}
	else if(message == 3) {
		messceshi1("删除成功！");
	}
	else if(message == 4) {
		messceshi1("添加成功！");
	}
	else if(message == 5) {
		messceshi1("批量审核成功！");
	}
	else if(message == 6) {
		messceshi1("批量驳回成功！");
	}
	else if(message == 7) {
		messceshi1("批量删除成功！");
	}
	else if(message == 8) {
		messceshi1("修改成功，审核状态已重置！");
	}
	else if(message == 9) {
		messceshi1("提交申请成功！");
	}
	else if(message == 10) {
		messceshi1("已提交，不可重复提交！");
	}
	else if(message == 11) {
		messceshi1("修改成功！");
	}
	else if(message == 12) {
		messceshi2("权限不够！");
	}
	else if(message == 13) {
		messceshi2("权限不够！,请排除终审状态");
	}
	else if(message == 14) {
		messceshi2("账户或者密码错误!");
	}
	else if(message == 15) {
		messceshi1("系统备份成功!");
	}
	else if(message == 16) {
		messceshi1("系统恢复成功!");
	}
	else if(message == 17) {
		messceshi1("学生信息导入成功!");
	}
	else if(message == 18) {
		messceshi6();
	}
	else if(message == 19) {
		messceshi2("学号必须为数字!");
	}
	else if(message == 20){
		messceshi1("修改成功!");
	}
	else if(message == 21){
		messceshi1("识别成功!");
	}
	else if(message == 22){
		messceshi1("识别失败!");
	}
	else if(message == 23){
		messceshi1("登记成功!");
	}
	else if(message == -1){
		messceshi2("操作失败！");
	}
	
}
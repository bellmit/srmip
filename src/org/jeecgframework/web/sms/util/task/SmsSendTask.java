package org.jeecgframework.web.sms.util.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.jeecgframework.web.sms.service.TSSmsServiceI;



/**
 * 
 * @ClassName:SmsSendTask 所有信息的发送定时任务类
 * @Description: TODO
 * @author Comsys-skyCc cmzcheng@gmail.com
 * @date 2014-11-13 下午5:06:34
 * 
 */
@Service("smsSendTask")
public class SmsSendTask {
	
	@Autowired
	private TSSmsServiceI tSSmsService;
//	@Scheduled(cron="0 0/1 * * * ?")
	public void run() {
		tSSmsService.send();
	}
}

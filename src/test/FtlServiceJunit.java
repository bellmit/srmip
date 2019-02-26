package test;

import java.util.List;

import org.jeecgframework.web.cgform.entity.cgformftl.CgformFtlEntity;
import org.jeecgframework.web.cgform.service.cgformftl.CgformFtlServiceI;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * 单元测试
 * @author admin
 *
 */
public class FtlServiceJunit extends SpringTxTestCase {

	@Autowired
	private CgformFtlServiceI cgformFtlService;
	
	
	@Test
	public void testInsert() {
		for(int i=0;i<1;i++){
		CgformFtlEntity entity = new CgformFtlEntity();
		entity.setCgformId("asfafafaf3355232");
		entity.setCgformName("学生");
		entity.setFtlContent("<html>页面内容</html>");
		entity.setFtlStatus("1");
		entity.setFtlVersion(0);
		entity.setFtlWordUrl("学生录入.doc");
		cgformFtlService.saveOrUpdate(entity);
		}
		List<CgformFtlEntity> userList = cgformFtlService.findHql("from CgformFtlEntity", null);
		System.out.println("数据库记录数:"+userList.size());
	}

	

}

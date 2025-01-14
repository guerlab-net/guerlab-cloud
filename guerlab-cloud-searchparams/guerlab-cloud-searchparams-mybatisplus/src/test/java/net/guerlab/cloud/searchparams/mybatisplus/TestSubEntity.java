package net.guerlab.cloud.searchparams.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("TEST_SUB_ENTITY")
public class TestSubEntity {

	@TableId
	private Long id;

	private String name;
}

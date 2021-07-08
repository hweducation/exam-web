package com.icbc.mapper;
import com.icbc.pojo.QAnswer;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * SellerMapper 数据访问接口
 * @date 2019-07-28 09:14:10
 * @version 1.0
 */
public interface QAnswerMapper extends Mapper<QAnswer> {
    public void save(QAnswer qAnswer);
}

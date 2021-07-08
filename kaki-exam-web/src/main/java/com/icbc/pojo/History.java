package com.icbc.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @desc 历史考试实体类
 * @author Kaki Nakajima
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_exam_test_history")
public class History implements Serializable {

    private static final long serialVersionUID = -831588229842049L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name = "paper_id")
    private long paperId;

    @Column(name = "paper_name")
    private String paperName;

    @Column(name = "src_score")
    private double srcScore;

    @Column(name = "user_score")
    private double userScore;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "create_date")
    private Date createDate;

    /** 与前端交互的时间字符串*/
    @Transient
    private String createDateString;

    @Column(name = "dept_flag")
    private int deptFlag;
}

package com.icbc.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_exam_q_answer")
public class QAnswer implements Serializable {
    private static final long serialVersionUID = -8315881229842049L;//??这是啥

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="pid")
    private Integer pid;

    @Column(name="qid")
    private Integer qid;

    @Column(name="uid")
    private String uid;

    @Column(name="answer")
    private String answer;

    @Column(name="review1_1")
    private String review1_1;

    @Column(name="review1_2")
    private String review1_2;

    @Column(name="review1_3")
    private String review1_3;
    //第二
    @Column(name="review2_1")
    private String review2_1;

    @Column(name="review2_2")
    private String review2_2;

    @Column(name="review2_3")
    private String review2_3;

    @Column(name="start_time")
    private Date startTime;

    @Column(name="end_time")
    private Date endTime;

    @Column(name = "l1")
    private String l1;

    @Column(name = "l2")
    private String l2;

    @Column(name = "l3")
    private String l3;

    /** 与前端交互的字符串*/
    @Transient
    private HashMap<String,Object> qAnswerMap;

}

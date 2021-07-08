package com.icbc.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 卷纸实体类
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "t_exam_paper")
public class Paper implements Serializable {

    private static final long serialVersionUID = -8315229842049L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name = "score")
    private Double score;

    @Column(name = "dept_flag")
    private String deptFlag;

    @Column(name = "time")
    private Integer time;

    @Column(name = "status")
    private String status;

    @Transient
    private String deptName;

    @Transient
    private List<Question> questionList;
}

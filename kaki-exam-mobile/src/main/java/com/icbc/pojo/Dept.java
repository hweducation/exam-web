package com.icbc.pojo;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 部门实体类
 */
@Table(name="t_exam_dept")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dept implements Serializable {

    private static final long serialVersionUID = -8315881229842049L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="dept_name")
    private String deptName;

}

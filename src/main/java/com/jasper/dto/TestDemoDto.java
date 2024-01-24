package com.jasper.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDemoDto implements Serializable {
    private String empno;
    private String ename;
    private String dname;
    private String job;
    private Date hiredate;
    private String location;
    private String sal;
}

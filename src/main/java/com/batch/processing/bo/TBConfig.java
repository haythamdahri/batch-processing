package com.batch.processing.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TBCONFIG")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TBConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EXECUTIONORDER")
    private String executionOrder;

    @Column(name = "COMMAND")
    private String command;

    @Column(name = "IDJOB")
    private String jobId;

    @Column(name = "JOBNAME")
    private String jobName;

    @Column(name = "DESCJOB")
    private String jobDescription;

    @Column(name = "IDSTEP")
    private int stepId;

    @Column(name = "IDOPERAZIONE")
    private int operationId;

    @Column(name = "SOURCEPATH")
    private String sourcePath;

    @Column(name = "DESTINATIONPATH")
    private String destinationPath;

    @Column(name = "SCHEDULATION")
    private String scheduling;

    @Column(name = "AVANZAMENTO")
    private String advancement;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "IDUSERINS")
    private String userIdInsert;

    @Column(name = "INSERTDATE")
    private Date insertDate;

    @Column(name = "IDUSERUPD")
    private String userIdUpdate;

    @Column(name = "LASTUPDATE")
    @Version
    private Timestamp lastUpdate;

}

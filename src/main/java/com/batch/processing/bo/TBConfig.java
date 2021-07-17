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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NUMBER")
    private Long id;

    @Column(name = "EXECUTION_ORDER")
    private String executionOrder;

    @Column(name = "COMMAND")
    private String command;

    @Column(name = "ID_JOB")
    private String jobId;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "DESC_JOB")
    private String jobDescription;

    @Column(name = "ID_STEP")
    private int stepId;

    @Column(name = "ID_OPERAZIONE")
    private int operationId;

    @Column(name = "SOURCE_PATH")
    private String sourcePath;

    @Column(name = "DESTINATION_PATH")
    private String destinationPath;

    @Column(name = "SCHEDULATION")
    private String scheduling;

    @Column(name = "AVANZAMENTO")
    private String advancement;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "IDUSER_INS")
    private String userIdInsert;

    @Column(name = "INSERT_DATE")
    private Date insertDate;

    @Column(name = "IDUSER_UPD")
    private String userIdUpdate;

    @Column(name = "LAST_UPDATE")
    @Version
    private Timestamp lastUpdate;

}

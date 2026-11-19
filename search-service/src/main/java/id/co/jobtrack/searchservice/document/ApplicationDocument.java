package id.co.jobtrack.searchservice.document;

import id.co.jobtrack.searchservice.enums.ApplicationStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(indexName = "job-applications", createIndex = true)
@Getter
@Setter
public class ApplicationDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String companyName;

    @Field(type = FieldType.Text)
    private String position;

    @Field(type = FieldType.Text)
    private String location;

    @Field(type = FieldType.Text)
    private String employmentType;

    @Field(type = FieldType.Keyword)
    private ApplicationStatus status;

    @Field(type = FieldType.Double)
    private BigDecimal salary;

    @Field(type = FieldType.Date)
    private LocalDate appliedAt;
}

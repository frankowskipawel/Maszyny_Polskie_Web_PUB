package pl.maszyny_polskie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SER_ID")
    int id;
    @Column(name = "SER_NUMBER")
    int number;
    @Column(name = "SER_YEAR")
    String year;
    @Column(name = "SER_DATE")
    Date date;
    @ManyToOne
    @JoinColumn(name = "SER_ID_CUSTOMER")
    Customer customer;
    @ManyToOne
    @JoinColumn(name = "SER_ID_DEVICE")
    Device device;
    @Column(name = "SER_TYPE")
    String type;
    @Column(name = "SER_PAYMENT_TYPE")
    String paymentType;
    @Column(name = "SER_INSPECTION_REPORT")
    String deviceInspectionReport;
    @Column (name = "SER_FAULT_DESCRIPTION")
    String faultDescription;
    @Column (name = "SER_RAGNE_OF_WORKS")
    String rangeOfWorks;
    @Column (name = "SER_MATERIAL_USED")
    String materialsUsed;
    @Column (name = "SER_COMMENTS")
    String comments;
    @Column (name = "SER_START_DATE")
    Date startDate;
    @Column (name = "SER_START_TIME")
    String startTime;
    @Column (name = "SER_END_DATE")
    Date endDate;
    @Column (name = "SER_END_TIME")
    String endTime;
    @Column(name = "SER_WORKING_TIME")
    String workingTime;
    @Column(name = "SER_DRIVE_DISTANCE")
    String driveDistance;
    @Column(name = "SER_DAYS_AT_HOTEL")
    String daysAtHotel;
    @Column(name = "SER_USER")
    String user;
    @Column(name = "SER_STATE")
    String state;
    @Column(name = "SER_GPS_LOCATION")
    String gpsLocation;


    @Transient
    List<String> files=new ArrayList<>();

}

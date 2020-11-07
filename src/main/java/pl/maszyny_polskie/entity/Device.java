package pl.maszyny_polskie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device {

    @Id
    @Column(name = "DEV_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "DEV_NAME")
    String name;
    @Column(name = "DEV_SERIAL_NUMBER")
    String serialNumber;
    @Column(name = "DEV_SOURCE_POWER")
    String sourcePower;
    @JoinColumn(name = "DEV_CUSTOMER_ID")
    @ManyToOne
    Customer customer;
    @JoinColumn(name = "DEV_CATEGORY_ID")
    @ManyToOne
    Category category;
    @Column(name = "DEV_TRANSFER_DATE")
    Date transferDate;
    @Column(name = "DEV_GPS_LOCATION")
    String gpsLocation;
    @Column(name = "DEV_STREET_ADDRESS")
    String streetAddress;
    @Column(name = "DEV_ZIP_CODE")
    String zipCode;
    @Column(name = "DEV_CITY")
    String city;
    @Column(name = "DEV_PARTS")
    @ManyToMany(cascade = CascadeType.ALL)
    List<Part> parts;
}

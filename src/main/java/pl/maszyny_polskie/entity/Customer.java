package pl.maszyny_polskie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "CUS_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "CUS_SHORT_NAME")
    String shortName;
    @Column(name = "CUS_FULL_NAME")
    String fullName;
    @Column(name = "CUS_STREET_ADDRESS")
    String streetAddress;
    @Column(name = "CUS_ZIP_CODE")
    String zipCode;
    @Column(name = "CUS_CITY")
    String city;
    @Column(name = "CUS_NIP")
    String nip;
    @Column(name = "CUS_REGON")
    String regon;
    @Column(name = "CUS_PHONE_NUMBER")
    String phoneNumber;
    @Column(name = "CUS_EMAIL")
    String email;
}

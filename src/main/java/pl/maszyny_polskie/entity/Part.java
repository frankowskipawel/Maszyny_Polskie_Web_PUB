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
@Table(name = "parts")
public class Part {

    @Id
    @Column(name = "PART_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "PART_NAME")
    String name;
    @Column(name = "PART_SERIAL_NUMBER")
    String serialNumber;
    @Column(name = "PART_PRODUCER")
    String producer;
    @Column(name = "PART_DESCRIPTION")
    String decsription;
}

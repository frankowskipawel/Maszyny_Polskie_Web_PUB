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
@Table(name = "files")
public class File {

    @Id
    @Column(name = "FIL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @JoinColumn(name = "FIL_ID_DEVICE")
    @ManyToOne
    Device device;
    @JoinColumn(name = "FIL_ID_SERVICE")
    @ManyToOne(fetch = FetchType.LAZY)
    Service service;
    @Column(name = "FIL_DATE")
    String date;
    @Column(name = "FIL_TYPE")
    String type;
    @Column(name = "FIL_DESCRIPTION")
    String description;
    @Column(name = "FIL_FILENAME")
    String filename;

}

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
@Table(name = "devices_notes")
public class DeviceNote {

    @Id
    @Column(name = "NOT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @JoinColumn(name = "NOT_ID_DEVICE")
    @ManyToOne
    Device device;
    @Column(name = "NOT_TEXT")
    String text;
}

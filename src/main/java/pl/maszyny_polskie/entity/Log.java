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
@Table(name = "logs")
public class Log {

    @Id
    @Column(name = "LOG_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "LOG_DATE")
    String date;
    @Column(name = "LOG_USER")
    String user;
    @Column(name ="LOG_VALUE")
    String value;
}

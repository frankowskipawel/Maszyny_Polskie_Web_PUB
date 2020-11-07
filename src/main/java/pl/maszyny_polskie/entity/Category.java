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
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "CAT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(name = "CAT_NAME")
    String name;
    @Column(name = "CAT_SERVICE_REVIEW_OPERATIONS_LIST")
    String serviceReviewOperationsList;
    @Column(name = "CAT_OPERATOR_TRAINING_LIST")
    String operatorTrainingList;
}

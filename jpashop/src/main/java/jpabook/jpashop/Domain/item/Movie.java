package jpabook.jpashop.Domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@DiscriminatorValue("M") //Dtype에 나올 이름
public class Movie extends Item {
    private String director;
    private String actor;

}
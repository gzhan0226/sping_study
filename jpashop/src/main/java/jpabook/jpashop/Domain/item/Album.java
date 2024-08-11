package jpabook.jpashop.Domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@DiscriminatorValue("A") //Dtype에 나올 이름
public class Album extends Item {
    private String artist;
    private String etc;
}

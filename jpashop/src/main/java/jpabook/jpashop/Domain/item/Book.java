package jpabook.jpashop.Domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@DiscriminatorValue("B") //Dtype에 나올 이름
public class Book extends Item {
    private String author;
    private String isbn;
}
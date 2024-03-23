package com.example.markusbot.model;

import com.example.markusbot.enumBot.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@DynamicUpdate
@AllArgsConstructor
@Entity
@Table(name = "orders", schema = "public")
public class OrderEntity extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.NEW;

    private Double priceRub;

    private Double pricePoizon;

    private String link;

    private Double sizeSm;

    private Double sizeEurope;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "photo_id")
    private FileEntity photo;

    private String fio;

    private String address;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "check_id")
    private FileEntity check;

    public boolean isPaid() {
        return Objects.nonNull(check);
    }

    public String getOrderNumber() {
        return "GD" + this.getId();
    }
}

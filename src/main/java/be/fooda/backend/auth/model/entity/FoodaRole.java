package be.fooda.backend.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum FoodaRole {
    ROLE_FOODA_PARENT("Fooda Application Customer"),
    ROLE_RESTA_PARENT("Restaurant Managers"),
    ROLE_DELLA_PARENT("Couriers");

    private final String authority;
}

package com.abc.bean;

import lombok.*;

//Lombok注解
@Data//作用相当于：@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Builder
public class Depart {
    private Integer id;
    private String name;

    public Depart() {
    }

    public Depart(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

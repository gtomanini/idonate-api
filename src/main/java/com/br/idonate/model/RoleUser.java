package com.br.idonate.model;

import com.br.idonate.utils.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleUser extends BaseModel{

    @Enumerated(EnumType.STRING)
    private RoleName name;
}

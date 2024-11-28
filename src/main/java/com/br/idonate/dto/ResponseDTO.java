package com.br.idonate.dto;

import com.br.idonate.model.Role;
import com.br.idonate.utils.RoleName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record ResponseDTO (String name, String token, List<RoleName> roles) { }

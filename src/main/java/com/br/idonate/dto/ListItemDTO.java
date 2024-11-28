package com.br.idonate.dto;

import java.util.List;

public record ListItemDTO(Long id, String name, String description, List<CategoryDTO> categories) {
}

package com.br.idonate.dto;

import com.br.idonate.model.Category;

import java.util.List;

public record ItemDTO(Long id, String name, String description, List<CategoryDTO> categories) {
}

package com.demoproj.user_management.DTOs;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Set;

@Data
public class SearchDTO {
    private String search;
    private ResultOrderDTO resultOrder;
    private Set<FilterDTO> filters;
    private int pageNo;
    private int pageSize;
}

package com.demoproj.user_management.DTOs;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SearchDTO {
    private String search;
    private ResultOrderDTO resultOrder;
    private FilterDTO filter;
    private int pageNo;
    private int pageSize;
}

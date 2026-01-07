package com.demoproj.user_management.utils;

import com.demoproj.user_management.DTOs.FilterDTO;
import com.demoproj.user_management.entities.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserSpecification {
    public static Specification<User> build(Set<FilterDTO> filters, String searchKeyword){
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filters!=null && !filters.isEmpty()) {
                filters.forEach((filter) -> {
                    predicates.add(
                            criteriaBuilder.equal(root.get(filter.getColumn()), filter.getValue())
                    );
                });
            }

            if (searchKeyword!=null && !searchKeyword.isEmpty()) {
                String likePattern = "%" + searchKeyword.toLowerCase() + "%";

                Predicate firstNameSearch = criteriaBuilder.like(
                  criteriaBuilder.lower(root.get("firstName")), likePattern
                );

                Predicate lastNameSearch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("lastName")), likePattern
                );

                Predicate emailSearch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")), likePattern
                );

                Predicate mobileSearch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("mobile")), likePattern
                );

                Predicate usernameSearch = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("username")), likePattern
                );

                Predicate searchPredicate = criteriaBuilder.or(
                        firstNameSearch,
                        lastNameSearch,
                        emailSearch,
                        mobileSearch,
                        usernameSearch
                );

                predicates.add(searchPredicate);
            }

            return predicates.isEmpty()
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

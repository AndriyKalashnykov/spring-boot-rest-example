package com.test.example.dao.jpa;

import com.test.example.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository can be used to delegate CRUD operations
 */
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
    Hotel findHotelByCity(String city);
    Hotel findById(long id);
    Page findAll(Pageable pageable);
}

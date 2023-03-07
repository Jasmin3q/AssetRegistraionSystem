package com.apress.ravi.chapter2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.apress.ravi.chapter2.dto.AssetsDTO;

@Component
public interface AssetJpaRepository extends JpaRepository<AssetsDTO, Long> {

	AssetsDTO findById(Long id);

	AssetsDTO findByName(String name);
}

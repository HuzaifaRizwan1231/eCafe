package com.SDA.eCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SDA.eCafe.model.Menu;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {


}

package com.SDA.eCafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SDA.eCafe.model.Menu;
public interface MenuRepository extends JpaRepository<Menu, Integer> {

}

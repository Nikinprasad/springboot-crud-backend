package net.crudapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.crudapp.model.Crud;

@Repository
public interface CrudRepository extends JpaRepository<Crud, Long> {

}

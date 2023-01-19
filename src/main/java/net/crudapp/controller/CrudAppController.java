package net.crudapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.crudapp.exception.ResourceNotFoundException;
import net.crudapp.model.Crud;
import net.crudapp.repository.CrudRepository;

@RestController
@RequestMapping("/api/v1/")
public class CrudAppController {
	@Autowired
	private CrudRepository crudRepository;

	// getAll
	@GetMapping("crud")
	public List<Crud> getAllData() {
		return this.crudRepository.findAll();
	}

	// getById
	@GetMapping("/crud/{id}")
	public ResponseEntity<Crud> getCrudById(@PathVariable(value = "id") Long crudId) throws ResourceNotFoundException {
		Crud crud = crudRepository.findById(crudId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + crudId));
		return ResponseEntity.ok().body(crud);
	}

	// saveDetails
	@PostMapping("addCrud")
	public Crud createCrud(@RequestBody Crud crud) {
		return this.crudRepository.save(crud);
	}
	// updateDetails

	@PutMapping("crud/{id}")
	public ResponseEntity<Crud> updateCrud(@PathVariable(value = "id") Long crudId,
			@Validated @RequestBody Crud crudDetails) throws ResourceNotFoundException {
		Crud crud = crudRepository.findById(crudId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + crudId));
		crud.setEmail(crudDetails.getEmail());
		crud.setName(crudDetails.getName());
		crud.setPhone(crudDetails.getPhone());

		return ResponseEntity.ok(this.crudRepository.save(crud));
	}

	// deleteDetails
	@DeleteMapping("crud/{id}")
	public Map<String, Boolean> deleteCrud(@PathVariable(value = "id") Long crudId) throws ResourceNotFoundException {
		Crud crud = crudRepository.findById(crudId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + crudId));
		this.crudRepository.delete(crud);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}

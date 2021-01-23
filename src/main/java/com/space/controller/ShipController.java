package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class ShipController {
	private final ShipRepository repository;

	//CREATE
	@PostMapping("/ships")
	public ResponseEntity<Ship> create(@RequestBody Ship ship){
		if(ShipUtils.prepareToSave(ship)) {
			ship = repository.save(ship);
			return new ResponseEntity(ship, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	//READ
	@GetMapping("/ships")
	public @ResponseBody
	List<Ship> findAll(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "planet", defaultValue = "") String planet,
			@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "3") int pageSize,
			@RequestParam(value = "minSpeed", defaultValue = "0.01") double minSpeed,
			@RequestParam(value = "maxSpeed", defaultValue = "0.99") double maxSpeed,
			@RequestParam(value = "minCrewSize", defaultValue = "1") int minCrew,
			@RequestParam(value = "maxCrewSize", defaultValue = "9999") int maxCrew,
			@RequestParam(value = "minRating", defaultValue = "0") double minRating,
			@RequestParam(value = "maxRating", defaultValue = "50") double maxRating,
			@RequestParam(value = "after", defaultValue = "26192235600000") long after,
			@RequestParam(value = "before", defaultValue = "33134734799999") long before,
			@RequestParam(value = "isUsed", required = false) Boolean isUsed,
			@RequestParam(value = "shipType", required = false) ShipType shipType,
			@RequestParam(value = "order", defaultValue = "ID") ShipOrder order
	) {
		String sortingParam = order.getFieldName();
		Pageable pageSettings = PageRequest.of(pageNumber, pageSize, Sort.by(sortingParam));
		Date start = new Date(after);
		Date end = new Date(before);

		if (isUsed != null && shipType != null) {
			return repository.customShipQueryUsedAndType(isUsed, shipType, name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end, pageSettings);
		} else if (shipType != null) {
			return repository.customShipQueryType(shipType, name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end, pageSettings);
		} else if (isUsed != null) {
			return repository.customShipQueryUsed(isUsed, name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end, pageSettings);
		} else {
			return repository.customShipQuery(name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end, pageSettings);
		}
	}

	@GetMapping("/ships/count")
	public @ResponseBody
	Integer countAll(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "planet", defaultValue = "") String planet,
			@RequestParam(value = "minSpeed", defaultValue = "0.01") double minSpeed,
			@RequestParam(value = "maxSpeed", defaultValue = "0.99") double maxSpeed,
			@RequestParam(value = "minCrewSize", defaultValue = "1") int minCrew,
			@RequestParam(value = "maxCrewSize", defaultValue = "9999") int maxCrew,
			@RequestParam(value = "minRating", defaultValue = "0") double minRating,
			@RequestParam(value = "maxRating", defaultValue = "50") double maxRating,
			@RequestParam(value = "after", defaultValue = "26192235600000") long after,
			@RequestParam(value = "before", defaultValue = "33134734799999") long before,
			@RequestParam(value = "isUsed", required = false) Boolean isUsed,
			@RequestParam(value = "shipType", required = false) ShipType shipType
	) {
		Date start = new Date(after);
		Date end = new Date(before);
		if (isUsed != null && shipType != null) {
			return repository.customCountUsedShipsOfType(isUsed, shipType, name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end);
		} else if (shipType != null) {
			return repository.customCountShipsOfType(shipType, name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end);
		} else if (isUsed != null) {
			return repository.customCountUsedShips(isUsed, name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end);
		} else {
			return repository.customCountShips(name, planet, minSpeed, maxSpeed, minCrew, maxCrew, minRating, maxRating, start, end);
		}
	}

	@GetMapping("/ships/{id}")
	public ResponseEntity<Ship> findById(@PathVariable Long id){
		if(id < 1){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Ship ship = repository.findById(id).orElse(null);
		if(ship == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(ship, HttpStatus.OK);
	}

	//UPDATE
	@PostMapping("/ships/{id}")
	public ResponseEntity<Ship> update(@PathVariable Long id, @RequestBody @NonNull Ship ship){
		if(id < 1){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Ship shipToEdit = repository.findById(id).orElse(null);
		if(shipToEdit == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		if(!ShipUtils.prepareToUpdate(shipToEdit, ship)){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		shipToEdit = repository.save(shipToEdit);
		return new ResponseEntity<>(shipToEdit, HttpStatus.OK);
	}

	//DELETE
	@DeleteMapping("/ships/{id}")
	public ResponseEntity delete(@PathVariable Long id){
		if(id < 1){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Ship ship = repository.findById(id).orElse(null);
		if(ship == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		repository.delete(ship);
		return new ResponseEntity(HttpStatus.OK);
	}

	@Autowired
	public ShipController(ShipRepository repository) {
		this.repository = repository;
	}
}

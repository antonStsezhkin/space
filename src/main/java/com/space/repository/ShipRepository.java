package com.space.repository;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ShipRepository extends JpaRepository<Ship, Long> {

	@Query(value = "select s FROM #{#entityName} s where" +
			" s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	List<Ship> customShipQuery(
			@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end
			//paging and sorting
			, Pageable pageSettings);


	//USED QUERY
	@Query(value = "select s FROM #{#entityName} s where" +
			" s.isUsed = :used" +
			" and s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	List<Ship> customShipQueryUsed(
			@Param("used") Boolean used
			,@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end
			//paging and sorting
			, Pageable pageSettings);

	//TYPE QUERY
	@Query(value = "select s FROM #{#entityName} s where" +
			" s.shipType = :sType" +
			" and s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	List<Ship> customShipQueryType(
			@Param("sType") ShipType type
			,@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end
			//paging and sorting
			, Pageable pageSettings);

	//TYPE AND USED QUERY
	@Query(value = "select s FROM #{#entityName} s where" +
			" s.isUsed = :used" +
			" and s.shipType = :sType" +
			" and s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	List<Ship> customShipQueryUsedAndType(
			@Param("used") Boolean isUsed
			,@Param("sType") ShipType type
			,@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end
			//paging and sorting
			, Pageable pageSettings);


	//************************************************* COUNT QUERIES ***********************************************//
	@Query(value = "select count(s.id) FROM #{#entityName} s where" +
			" s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	Integer customCountShips(
			@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end);

	//USED QUERY
	@Query(value = "select count(s.id) FROM #{#entityName} s where" +
			" s.isUsed = :used" +
			" and s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	Integer customCountUsedShips(
			@Param("used") Boolean used
			,@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end);

	//TYPE QUERY
	@Query(value = "select count(s.id) FROM #{#entityName} s where" +
			" s.shipType = :sType" +
			" and s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	Integer customCountShipsOfType(
			@Param("sType") ShipType type
			,@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end);

	//TYPE AND USED QUERY
	@Query(value = "select count(s.id) FROM #{#entityName} s where" +
			" s.isUsed = :used" +
			" and s.shipType = :sType" +
			" and s.name like %:name%" +
			" and s.planet like %:planet%" +
			" and s.speed between :minSpeed and :maxSpeed" +
			" and s.crewSize between :minCrew and :maxCrew" +
			" and s.rating between :minRating and :maxRating" +
			" and s.prodDate >= :startD and s.prodDate <= :endD"
	)
	Integer customCountUsedShipsOfType(
			@Param("used") Boolean isUsed
			,@Param("sType") ShipType type
			,@Param("name") String name
			,@Param("planet") String planet
			//speed
			,@Param("minSpeed") double minSpeed
			,@Param("maxSpeed") double maxSpeed
			//crew
			,@Param("minCrew") int minCrew
			,@Param("maxCrew") int maxCrew
			//rating
			,@Param("minRating") double minRating
			,@Param("maxRating") double maxRating
			//production date
			,@Param("startD") Date start
			,@Param("endD") Date end);
}

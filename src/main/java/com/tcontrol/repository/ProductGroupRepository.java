package com.tcontrol.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcontrol.models.ProductGroup;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

	@Query(value =  "SELECT pc.*, pg.description AS description_group FROM product_groups pg INNER JOIN product_categories pc ON pg.id = pc.group_id "
			+ "	where pg.id = ?1", nativeQuery = true)
	List<Map<String, Object>> getCategoriesByProductGroup(Long group_id);
	
//	@Query("SELECT  new com.tcontrol.DTO.ProductCategoryDTO (pc.id, pc.description, pc.userId, pc.groupId) FROM ProductGroup pg "
//			+ " JOIN pg.productCategory")

	List<ProductGroup> findByDescriptionContains(String description);

}

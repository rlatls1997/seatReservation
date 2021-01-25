package net.skhu.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import net.skhu.entity.Board;
import net.skhu.model.Pagination;

public interface BoardRepository extends JpaRepository<Board, Integer>  {
	public default List<Board> findAll(Pagination pagination){
		Page<Board> page = this.findAll(PageRequest.of(pagination.getPg() - 1, pagination.getSz(),
				Sort.Direction.ASC, "id"));
		pagination.setRecordCount((int)page.getTotalElements());
		return page.getContent();
	}
}
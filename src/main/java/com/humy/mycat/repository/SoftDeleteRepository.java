package com.humy.mycat.repository;

import com.humy.mycat.constant.RepositoryConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @Author: Milo Hu
 * @Date: 11/4/2019 13:23
 * @Description:
 */
@NoRepositoryBean
public interface SoftDeleteRepository<T, ID> extends JpaRepository<T, ID> {

//    @Query(value = "update #{#entityName} e set e." + RepositoryConstant.DELETED_COLUMN_NAME + " = 1 where e.id = ?1 ")
//    @Modifying
//    @Transactional
//    Integer softDeleteById(Long id);

    @Override
    <S extends T> S save(S entity);

    @Query("select e from #{#entityName} e where e.id=?1 and e." + RepositoryConstant.NOT_DELETED_SQL)
    Optional<T> softFindById(ID id);

    @Override
    boolean existsById(ID id);

    @Override
    @Query("select count(e) from #{#entityName} e where e." + RepositoryConstant.NOT_DELETED_SQL)
    long count();

    @Override
    @Query(value = "update #{#entityName} e set e." + RepositoryConstant.DELETE_SQL + " where e.id = ?1")
    @Modifying
    @Transactional
    void deleteById(ID id);

    @Override
    void delete(T entity);

    @Override
    void deleteAll(Iterable<? extends T> entities);

    @Override
    void deleteAll();
}

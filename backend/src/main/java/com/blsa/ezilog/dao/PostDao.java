package com.blsa.ezilog.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blsa.ezilog.model.post.Post;

public interface PostDao extends JpaRepository<Post, Long>{
    
    @Query(value = "SELECT * FROM post WHERE writer LIKE %:writer% AND secret=false",
            countQuery = "SELECT count(*) FROM post WHERE writer LIKE %:writer% AND secret=false",
            nativeQuery = true)
    Page<Post> findPostByWriter(String writer, Pageable request);
    
    @Query(value = "SELECT * FROM post WHERE writer=:writer",
            countQuery = "SELECT * FROM post WHERE writer=:writer",
            nativeQuery = true)
    Page<Post> findAllPostByWriter(String writer, Pageable request);
    
    Optional<Post> findPostByNo(Long no);
    
    @Query(value ="SELECT * FROM post WHERE category_id =:id",
            countQuery = "SELECT count(*) FROM post WHERE category_id=:id",
            nativeQuery = true)
    Page<Post>  findPostByCategoryId(Long id, Pageable request);
    
    @Query(value = "SELECT * FROM post WHERE title LIKE %:title%",
            countQuery = "SELECT count(*) FROM post WHERE title LIKE %:title%",
            nativeQuery = true)
    Page<Post> findPostByTitle(String title, Pageable request);
    
    @Query(value = "SELECT * FROM post WHERE title LIKE %:keyword% OR writer LIKE %:keyword%",
            countQuery = "SELECT count(*) FROM post WHERE title LIKE %:keyword% OR writer LIKE %:keyword%",
            nativeQuery = true)
    Page<Post> findPostByTitleORWriter(String keyword, Pageable request);
    
    @Query(value = "SELECT * FROM post WHERE allow = true AND no not in (SELECT no FROM selection_history) AND no not in (SELECT no FROM selection_post)",
            countQuery = "SELECT count(*) FROM post WHERE allow = true AND no not in (SELECT no FROM selection_history) AND no not in (SELECT no FROM selection_post)",
            nativeQuery = true)
    Page<Post> findPostByAllowIsTrueAndNotInHistory(Pageable request);
    
    @Query(value = "SELECT * FROM post where no in (SELECT no FROM selection_post) AND no < ?1 ORDER BY no desc",
            countQuery = "SELECT count(*) FROM post where no in (SELECT no FROM selection_post) AND no < ?1",
            nativeQuery = true)
    Page<Post> findPostByNoLessThanInSelection(Long no, Pageable pageable);
    
    @Query(value = "SELECT * FROM post where no in (SELECT no FROM selection_post) ORDER BY no desc",
            countQuery = "SELECT count(*) FROM post where no in (SELECT no FROM selection_post)",
            nativeQuery = true)
    Page<Post> findPostInSelection(Pageable pageable);
    
    @Query(value = "SELECT * FROM post where no in (SELECT no FROM selection_history) AND no < ?1 ORDER BY no desc",
            countQuery = "SELECT count(*) FROM post where no in (SELECT no FROM selection_history) AND no < ?1",
            nativeQuery = true)
    Page<Post> findPostByNoLessThanInHistory(Long no, Pageable pageable);

    @Query(value = "SELECT * FROM post where no in (SELECT no FROM selection_history) ORDER BY no desc",
            countQuery = "SELECT count(*) FROM post where no in (SELECT no FROM selection_history)",
            nativeQuery = true)
    Page<Post> findPostInHistory(Pageable pageable);
    
    
    @Query(value = "SELECT * FROM post WHERE secret=true ",
            countQuery = "SELECT count(*) FROM post WHERE secret=true",
            nativeQuery = true)
    Page<Post> findPostByAnonymous(Pageable request);
    
    @Query(value = "SELECT * FROM post WHERE secret=true OR title LIKE %:keyword%",
            countQuery = "SELECT count(*) FROM post WHERE secret=true OR title LIKE %:keyword%",
            nativeQuery = true)
    Page<Post> findPostByAnonymousAll(String keyword, Pageable request);
    
    @Query(value="SELECT * FROM post WHERE allow = true AND no not in (SELECT no FROM selection_history)", nativeQuery = true)
    Optional<List<Post>> findRecommandPostAllowed();
}

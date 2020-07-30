package com.blsa.ezilog.model.like;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeCount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    BigInteger id;
    
    @Column(name="post_no")
    BigInteger postNo;
    
    @Column
    BigInteger uid;
    
    @Column
    LocalDateTime stamp;
    
    @Column
    String type;

    public LikeCount(BigInteger postNo, BigInteger uid, LocalDateTime stamp, String type) {
        super();
        this.postNo = postNo;
        this.uid = uid;
        this.stamp = stamp;
        this.type = type;
    }

    
    
    
}

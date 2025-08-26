package com.deepanshu.snaplink.repo;

import com.deepanshu.snaplink.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepo extends JpaRepository<ShortUrl,Integer> {
}

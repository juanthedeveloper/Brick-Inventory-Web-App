package com.jcpdev.brickinventoryserver.Repo;

import com.jcpdev.brickinventoryserver.Models.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepo extends JpaRepository<Items,Long> {
    boolean existsByPartNumber(long PartNumber);
    Items findByPartNumber(long PartNumber);
}

package com.jcpdev.brickinventoryserver.Repo;

import com.jcpdev.brickinventoryserver.Models.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepo extends JpaRepository<Items,Long> {
}

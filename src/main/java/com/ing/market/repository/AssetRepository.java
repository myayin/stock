package com.ing.market.repository;

import com.ing.market.model.Asset;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByCustomerIdAndAssetName(long customerId, String assetName);

    List<Asset> findByCustomerId(long customerId);
}

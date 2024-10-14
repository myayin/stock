package com.ing.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ing.market.exception.MarketException;
import com.ing.market.model.Asset;
import com.ing.market.repository.AssetRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AssetServiceBlockAssetTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    private Asset asset;

    @BeforeEach
    void setUp() {
        asset = new Asset();
        asset.setCustomerId(1L);
        asset.setAssetName("Bitcoin");
        asset.setUsableSize(new BigDecimal("10.0"));
    }

    @Test
    void testBlockAssetSuccess() throws MarketException {
        // Arrange
        doReturn(Optional.of(asset)).when(assetRepository).findByCustomerIdAndAssetName(1L, "Bitcoin");

        // Act
        assetService.blockAsset(1L, "Bitcoin", new BigDecimal("5.0"));

        // Assert
        assertEquals(new BigDecimal("5.0"), asset.getUsableSize());
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    void testBlockAssetNotEnoughSize() {
        // Arrange
        doReturn(Optional.of(asset)).when(assetRepository).findByCustomerIdAndAssetName(1L, "Bitcoin");
        // Act & Assert
        MarketException exception = assertThrows(MarketException.class, () -> {
            assetService.blockAsset(1L, "Bitcoin", new BigDecimal("15.0"));
        });

        assertEquals("E_ASSET_NOT_ENOUGH", exception.getErrorCode());
        verify(assetRepository, never()).save(any(Asset.class));
    }

    @Test
    void testBlockAssetNotFound() {
        // Arrange
        doReturn(Optional.empty()).when(assetRepository).findByCustomerIdAndAssetName(1L, "Bitcoin");
        // Act & Assert
        MarketException exception = assertThrows(MarketException.class, () -> {
            assetService.blockAsset(1L, "Bitcoin", new BigDecimal("5.0"));
        });

        assertEquals("E_ASSET_NOT_FOUND", exception.getErrorCode());
        verify(assetRepository, never()).save(any(Asset.class));
    }

}

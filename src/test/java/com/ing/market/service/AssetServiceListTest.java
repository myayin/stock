package com.ing.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ing.market.dto.AssetDto;
import com.ing.market.dto.GetAssetResponseDTO;
import com.ing.market.mapper.AssetMapper;
import com.ing.market.model.Asset;
import com.ing.market.model.Customer;
import com.ing.market.repository.AssetRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class AssetServiceListTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private Customer customer;
    private Asset asset;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        asset = new Asset();
        asset.setCustomerId(1L);
        asset.setAssetName("TRY");
        asset.setSize(BigDecimal.valueOf(100.0));
        asset.setUsableSize(BigDecimal.valueOf(80.0));
    }

    @Test
    void testListAssetsSuccess() {
        // Arrange
        List<Asset> assets = List.of(asset);
        AssetDto assetDto = AssetMapper.INSTANCE.toDto(asset); // Assuming the mapper works correctly

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customer);
        SecurityContextHolder.setContext(securityContext);

        when(assetRepository.findByCustomerId(1L)).thenReturn(assets);

        // Act
        GetAssetResponseDTO responseDTO = assetService.list();

        // Assert
        assertNotNull(responseDTO);
        assertEquals(1, responseDTO.getAssetDtos().size());
        assertEquals(assetDto.getAssetName(),
                responseDTO.getAssetDtos().get(0).getAssetName()); // Check if mapping is correct
        verify(assetRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testListAssetsEmpty() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customer);
        SecurityContextHolder.setContext(securityContext);

        when(assetRepository.findByCustomerId(1L)).thenReturn(Collections.emptyList());

        // Act
        GetAssetResponseDTO responseDTO = assetService.list();

        // Assert
        assertNotNull(responseDTO);
        assertTrue(responseDTO.getAssetDtos().isEmpty());
        verify(assetRepository, times(1)).findByCustomerId(1L);
    }
}

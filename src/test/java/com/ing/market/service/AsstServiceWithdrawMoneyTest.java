package com.ing.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ing.market.dto.WithdrawMoneyRequestDto;
import com.ing.market.exception.MarketException;
import com.ing.market.model.Asset;
import com.ing.market.model.Customer;
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
public class AsstServiceWithdrawMoneyTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    private Customer customer;
    private Asset asset;
    private static final String TRY = "TRY"; // Asset name constant

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        asset = new Asset();
        asset.setCustomerId(1L);
        asset.setAssetName(TRY);
        asset.setSize(new BigDecimal("100.0"));
        asset.setUsableSize(new BigDecimal("80.0"));
    }

    @Test
    void testWithdrawMoneySuccess() throws MarketException {
        // Arrange
        WithdrawMoneyRequestDto withdrawDto = new WithdrawMoneyRequestDto();
        withdrawDto.setAmount(new BigDecimal("50.0"));
        withdrawDto.setCustomerId(1L);

        doReturn(Optional.of(asset)).when(assetRepository).findByCustomerIdAndAssetName(1L, TRY);

        // Act
        assetService.withdrawMoney(withdrawDto);

        // Assert
        assertEquals(new BigDecimal("50.0"), asset.getSize());
        assertEquals(new BigDecimal("30.0"), asset.getUsableSize());
        verify(assetRepository, times(1)).save(asset);
    }

    @Test
    void testWithdrawMoneyAssetSizeNotEnough() {
        // Arrange
        WithdrawMoneyRequestDto withdrawDto = new WithdrawMoneyRequestDto();
        withdrawDto.setAmount(new BigDecimal("150.0"));
        withdrawDto.setCustomerId(1L);

        doReturn(Optional.of(asset)).when(assetRepository).findByCustomerIdAndAssetName(1L, TRY);

        // Act & Assert
        MarketException exception = assertThrows(MarketException.class, () -> {
            assetService.withdrawMoney(withdrawDto);
        });

        assertEquals("E_ASSET_NOT_ENOUGH", exception.getErrorCode());
        verify(assetRepository, never()).save(any(Asset.class));
    }

    @Test
    void testWithdrawMoneyBankTimeoutResponse() throws MarketException {
        // Arrange
        WithdrawMoneyRequestDto withdrawDto = new WithdrawMoneyRequestDto();
        withdrawDto.setAmount(new BigDecimal("50.0"));
        withdrawDto.setCustomerId(1L);

        doReturn(Optional.of(asset)).when(assetRepository).findByCustomerIdAndAssetName(1L, TRY);

        // Act
        assetService.withdrawMoney(withdrawDto);

        // Assert
        verify(assetRepository, times(1)).save(asset);
    }
}


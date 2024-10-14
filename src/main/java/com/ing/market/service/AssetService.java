package com.ing.market.service;

import com.ing.market.constants.BankResponseCode;
import com.ing.market.dto.AssetDto;
import com.ing.market.dto.DepositMoneyRequestDto;
import com.ing.market.dto.GetAssetResponseDTO;
import com.ing.market.dto.WithdrawMoneyRequestDto;
import com.ing.market.exception.MarketException;
import com.ing.market.mapper.AssetMapper;
import com.ing.market.model.Asset;
import com.ing.market.model.Customer;
import com.ing.market.repository.AssetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ing.market.constants.Constants.TRY;
import static com.ing.market.constants.ResponseCode.E_ASSET_NOT_ENOUGH;
import static com.ing.market.constants.ResponseCode.E_ASSET_NOT_FOUND;

@AllArgsConstructor
@Service
@Slf4j
public class AssetService {

    private final AssetRepository assetRepository;

    public void blockAsset(long customerId, String assetName, BigDecimal size) throws MarketException {
        Asset asset = findAssetByCustomerIdAndAssetName(customerId, assetName);
        validateAssetSize(asset, size);
        asset.setUsableSize(asset.getUsableSize().subtract(size));
        assetRepository.save(asset);
    }

    public void unblockAsset(long customerId, String assetName, BigDecimal size) throws MarketException {
        Asset asset = findAssetByCustomerIdAndAssetName(customerId, assetName);
        validateAssetSize(asset, size);
        asset.setUsableSize(asset.getUsableSize().add(size));
        assetRepository.save(asset);
    }

    public void createAsset(long customerId, String assetName, BigDecimal size) throws MarketException {
        if (!isAssetExist(customerId, assetName)) {
            Asset asset = new Asset();
            asset.setAssetName(assetName);
            asset.setCustomerId(customerId);
            asset.setSize(size);
            asset.setUsableSize(size);
            assetRepository.save(asset);
        }
    }

    public void depositMoney(DepositMoneyRequestDto dto) throws MarketException {
        Asset asset = findAssetByCustomerIdAndAssetName(dto.getCustomerId(), TRY);
        asset.setSize(asset.getSize().add(dto.getAmount()));
        asset.setUsableSize(asset.getUsableSize().add(dto.getAmount()));
        assetRepository.save(asset);
    }

    public void withdrawMoney(WithdrawMoneyRequestDto dto) throws MarketException {
        Asset asset = findAssetByCustomerIdAndAssetName(dto.getCustomerId(), TRY);
        validateAssetSize(asset, dto.getAmount());
        asset.setSize(asset.getSize().subtract(dto.getAmount()));
        asset.setUsableSize(asset.getUsableSize().subtract(dto.getAmount()));
        //banka servisine istek atılır. başarısız ise kayıt işlemi gerçekleşmez.
        // timeout alındı ise kayıt gerçekleşir, işlem job ile sorgulanır
        String bankResponseCode = BankResponseCode.SUCCESS.name();
        if (!BankResponseCode.FAILED.name().equals(bankResponseCode)) {
            assetRepository.save(asset);
        }
    }

    public GetAssetResponseDTO list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = ((Customer) authentication.getPrincipal());
        List<Asset> orders = assetRepository.findByCustomerId(customer.getId());
        List<AssetDto> assetDtos = orders.stream().map(AssetMapper.INSTANCE::toDto).collect(Collectors.toList());
        GetAssetResponseDTO responseDTO = new GetAssetResponseDTO();
        responseDTO.setAssetDtos(assetDtos);
        return responseDTO;
    }

    private Asset findAssetByCustomerIdAndAssetName(long customerId, String assetName) throws MarketException {
        return assetRepository.findByCustomerIdAndAssetName(customerId, assetName)
                .orElseThrow(() -> new MarketException(E_ASSET_NOT_FOUND.name(), E_ASSET_NOT_FOUND.getMessage()));
    }

    private boolean isAssetExist(long customerId, String assetName) {
        Optional<Asset> assets = assetRepository.findByCustomerIdAndAssetName(customerId, assetName);
        return assets.isPresent();
    }

    private void validateAssetSize(Asset asset, BigDecimal requiredSize) throws MarketException {
        if (asset.getUsableSize().compareTo(requiredSize) < 0) {
            throw new MarketException(E_ASSET_NOT_ENOUGH.name(), E_ASSET_NOT_ENOUGH.getMessage());
        }
    }

}
